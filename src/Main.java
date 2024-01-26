public class Main {
    public static void main(String[] args) {
        int width = 9;
        int height = 5;
        Operator<ComplexNum> operator = new ComplexNumOperatorDecorator();

        Matrix<ComplexNum> matrix1 = new Matrix<>(operator, width, height);
        matrix1.fill(RandomComplex::rand);

        Matrix<ComplexNum> matrix2 = new Matrix<>(operator, width, height);
        matrix2.fill(RandomComplex::rand);


    }
}
