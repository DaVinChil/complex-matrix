import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        int width = 3;
        int height = 3;

        Matrix matrix1 = new Matrix(width, height);
        matrix1.fill(RandomComplex::rand);

        Matrix matrix2 = new Matrix(width, height);
        matrix2.fill(RandomComplex::rand);

        System.out.println("---------   TRANSPOSITION   ---------\n");
        System.out.println(matrix1);
        System.out.println("                  |                  ");
        System.out.println("                  |                  ");
        System.out.println("                  |                  ");
        System.out.println("                  |                  ");
        System.out.println("                  v                  \n");
        System.out.println(matrix1.transp());

        System.out.println("---------        SUM        ---------\n");
        System.out.println(matrix1);
        System.out.println("                  +                  \n");
        System.out.println(matrix2);
        System.out.println("                  ||                 \n");
        System.out.println(matrix1.sum(matrix2));

        System.out.println("---------        SUB        ---------\n");
        System.out.println(matrix1);
        System.out.println("                  -                  \n");
        System.out.println(matrix2);
        System.out.println("                  ||                 \n");
        System.out.println(matrix1.sub(matrix2));

        System.out.println("---------        MULT        ---------\n");
        System.out.println(matrix1);
        System.out.println("                  *                  \n");
        System.out.println(matrix2);
        System.out.println("                  ||                 \n");
        System.out.println(matrix1.mult(matrix2));


        System.out.println("---------        DET        ---------\n");
        System.out.println(matrix1);
        System.out.println("                  d                  \n");
        System.out.println(matrix1.getDeterminant());
    }
}
