import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Matrix {
    private final List<List<ComplexNum>> matrix;
    private final int width;
    private final int height;

    public Matrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = createMatrix(width, height);
    }

    public Matrix(int width, int height, List<List<ComplexNum>> matrix) {
        this.width = width;
        this.height = height;
        this.matrix = matrix;
    }

    public void fill(Supplier<ComplexNum> sup) {
        for (int y = 0; y < height; y++) {
            List<ComplexNum> line = matrix.get(y);
            for (int x = 0; x < width; x++) {
                line.set(x, sup.get());
            }
        }
    }

    public ComplexNum get(int x, int y) {
        return matrix.get(y).get(x);
    }

    public void set(int x, int y, ComplexNum value) {
        matrix.get(y).set(x, value);
    }

    public void set(int x, int y, Number value) {
        matrix.get(y).set(x, new ComplexNum(value.doubleValue(), 0));
    }

    public Matrix sum(Matrix b) {
        if (this.height != b.height || this.width != b.width) {
            throw new RuntimeException("Matrices have to be same size");
        }

        var copy = clone();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copy.addAtIndex(x, y, b.get(x, y));
            }
        }

        return copy;
    }

    private void addAtIndex(int x, int y, ComplexNum value) {
        matrix.get(y).set(x, get(x, y).sum(value));
    }

    public Matrix sub(Matrix b) {
        if (this.height != b.height || this.width != b.width) {
            throw new RuntimeException("Matrices have to have same size");
        }

        var copy = clone();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copy.subAtIndex(x, y, b.get(x, y));
            }
        }

        return copy;
    }

    private void subAtIndex(int x, int y, ComplexNum value) {
        matrix.get(y).set(x, get(x, y).sub(value));
    }

    public Matrix mult(Matrix b) {
        if (this.height != b.width || this.width != b.height) {
            throw new ArithmeticException("Matrices have to have reversed size");
        }

        var copy = clone();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copy.set(x, y, multAndSumLines(matrix.get(y), b, x));
            }
        }

        return copy;
    }

    private ComplexNum multAndSumLines(List<ComplexNum> line, Matrix b, int x) {
        ComplexNum result = ComplexNum.ZERO;
        for (int i = 0; i < width; i++) {
            result = result.sum(line.get(x).mult(b.get(x, i)));
        }

        return result;
    }

    public Matrix mult(Number b) {
        var copy = clone();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copy.set(x, y, get(x, y).mult(b));
            }
        }

        return copy;
    }

    public Matrix mult(ComplexNum b) {
        var copy = clone();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copy.set(x, y, get(x, y).mult(b));
            }
        }

        return copy;
    }

    public Matrix div(Number b) {
        var copy = clone();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copy.set(x, y, get(x, y).div(b));
            }
        }

        return copy;
    }

    public Matrix div(ComplexNum b) {
        var copy = clone();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copy.set(x, y, get(x, y).div(b));
            }
        }

        return copy;
    }

    public Matrix transp() {
        var newMatrix = createMatrix(height, width);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newMatrix.get(x).set(y, get(x, y));
            }
        }

        return new Matrix(height, width, newMatrix);
    }

    public ComplexNum getDeterminant() {
        if (width != height) throw new ArithmeticException("The amount of rows and columns are not equal");
        return calcDeterminant();
    }

    private ComplexNum calcDeterminant() {
        if (width == 1 && height == 1) return get(0, 0);

        ComplexNum result = ComplexNum.ZERO;

        for (int i = 0; i < width; i++) {
            Matrix subMatrix = getSubmatrix(i);
            ComplexNum part = get(i, 0).mult(subMatrix.getDeterminant());
            if (i % 2 == 1) {
                part = ComplexNum.ZERO.sub(part);
            }
            result = result.sum(part);
        }

        return result;
    }

    private Matrix getSubmatrix(int column) {
        Matrix res = new Matrix(width - 1, height - 1);

        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == column) continue;

                res.set(x - (x > column ? 1 : 0), y - 1, get(x, y));
            }
        }

        return res;
    }

    private List<List<ComplexNum>> createMatrix(int width, int height) {
        var mtrx = new ArrayList<List<ComplexNum>>();

        for (int i = 0; i < height; i++) {
            List<ComplexNum> line = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                line.add(ComplexNum.ZERO);
            }
            mtrx.add(line);
        }

        return mtrx;
    }

    @Override
    protected Matrix clone() {
        Matrix clone = new Matrix(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                clone.set(x, y, get(x, y));
            }
        }
        return clone;
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (int y = 0; y < height; y++) {
            result.append('|');
            for (int x = 0; x < width; x++) {
                result.append(String.format("%16s,", get(x, y).toString()));
            }
            result.deleteCharAt(result.length() - 1);
            result.append("|\n");
        }
        return result.toString();
    }
}
