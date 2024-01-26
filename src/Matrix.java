import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Matrix<T> {
    private final Operator<T> operator;
    private List<List<T>> matrix;
    private int width;
    private int height;

    public Matrix(Operator<T> operator, int width, int height) {
        this.width = width;
        this.height = height;
        this.operator = operator;
        this.matrix = createMatrix(width, height);
    }

    public void fill(Supplier<T> randSup) {
        for (int i = 0; i < height; i++) {
            List<T> line = matrix.get(i);
            for (int j = 0; j < width; j++) {
                line.set(j, randSup.get());
            }
        }
    }

    public Matrix<T> sum(Matrix<T> b) {
        if (this.height != b.height || this.width != b.width) {
            throw new RuntimeException("Matrices have to be same size");
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                addAtIndex(x, y, b.get(x, y));
            }
        }

        return this;
    }

    public Matrix<T> sub(Matrix<T> b) {
        if (this.height != b.height || this.width != b.width) {
            throw new RuntimeException("Matrices have to have same size");
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                subAtIndex(x, y, b.get(x, y));
            }
        }

        return this;
    }

    public Matrix<T> mult(Matrix<T> b) {
        if (this.height != b.width || this.width != b.height) {
            throw new ArithmeticException("Matrices have to have reversed size");
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                set(x, y, multAndSumLines(matrix.get(y), b, x));
            }
        }

        return this;
    }

    public Matrix<T> transp() {
        var newMatrix = createMatrix(height, width);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newMatrix.get(x).set(y, get(x, y));
            }
        }

        int temp = height;
        height = width;
        width = temp;

        this.matrix = newMatrix;

        return this;
    }

    private List<List<T>> createMatrix(int width, int height) {
        var mtrx = new ArrayList<List<T>>();

        for (int i = 0; i < height; i++) {
            List<T> line = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                line.add(operator.zero());
            }
            mtrx.add(line);
        }

        return mtrx;
    }

    private T multAndSumLines(List<T> line, Matrix<T> b, int x) {
        T result = null;
        for (int i = 0; i < width; i++) {
            result = operator.sum(result, operator.mult(line.get(x), b.get(x, i)));
        }

        return result;
    }

    public T get(int x, int y) {
        return matrix.get(y).get(x);
    }

    public void set(int x, int y, T value) {
        matrix.get(y).set(x, value);
    }

    private void addAtIndex(int x, int y, T value) {
        matrix.get(y).set(x, operator.sum(get(x, y), value));
    }

    private void subAtIndex(int x, int y, T value) {
        matrix.get(y).set(x, operator.sub(get(x, y), value));
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for(int y = 0; y < height; y++) {
            result.append('|');
            for(int x = 0; x < width; x++) {
                result.append(String.format("%12s,", get(x, y).toString()));
            }
            result.append("|\n");
        }
        return result.toString();
    }
}
