public class ComplexNum{
    public static final ComplexNum ZERO = new ComplexNum(0 ,0);
    final double real;
    final double img;

    public ComplexNum(double real, double img) {
        this.real = real;
        this.img = img;
    }

    @Override
    public String toString() {
        return real + img + "i";
    }
}