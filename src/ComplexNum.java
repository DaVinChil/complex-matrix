public class ComplexNum {
    public static final ComplexNum ZERO = new ComplexNum(0, 0);
    final double real;
    final double img;

    public ComplexNum(double real, double img) {
        this.real = real;
        this.img = img;
    }

    public ComplexNum sum(ComplexNum b) {
        return new ComplexNum(real + b.real, img + b.img);
    }

    public ComplexNum sum(Number b) {
        return new ComplexNum(real + b.doubleValue(), img);
    }

    public ComplexNum sub(ComplexNum b) {
        return new ComplexNum(real - b.real, img - b.img);
    }

    public ComplexNum sub(Number b) {
        return new ComplexNum(real - b.doubleValue(), img);
    }

    public ComplexNum mult(ComplexNum b) {
        return new ComplexNum(real * b.real - img * b.img, real * b.img + img * b.real);
    }

    public ComplexNum mult(Number b) {
        return new ComplexNum(real * b.doubleValue(), img * b.doubleValue());
    }

    public ComplexNum div(ComplexNum b) {
        if (equals(ComplexNum.ZERO)) return ComplexNum.ZERO;
        else if (b.equals(ComplexNum.ZERO)) throw new ArithmeticException("Zero division!");

        double divider = b.real * b.real + b.img * b.img;
        return new ComplexNum((real * b.real + img * b.img) / divider, (img * b.real - real * b.img) / divider);
    }

    public ComplexNum div(Number b) {
        if (equals(ComplexNum.ZERO)) return ComplexNum.ZERO;
        else if (Math.abs(b.doubleValue()) <= 0.000000001) throw new ArithmeticException("Zero division!");

        return new ComplexNum(real / b.doubleValue(), img / b.doubleValue());
    }

    @Override
    public String toString() {
        if (Math.abs(img) <= 0.000000001) {
            return String.format("%.2f", real);
        } else {
            return String.format("%.2f", real) + (img > 0 ? "+" : "") + String.format("%.2f", img) + "i";
        }
    }
}