import java.util.Objects;
import java.util.function.BinaryOperator;

public class ComplexNumOperator implements Operator<ComplexNum> {

    @Override
    public ComplexNum sum(ComplexNum a, ComplexNum b) {
        return new ComplexNum(a.real + b.real, a.img + b.img);
    }

    @Override
    public ComplexNum sub(ComplexNum a, ComplexNum b) {
        return new ComplexNum(a.real - b.real, a.img - b.img);
    }

    @Override
    public ComplexNum mult(ComplexNum a, ComplexNum b) {
        return new ComplexNum(a.real * b.real - a.img * b.img, a.real * b.img + a.img * b.real);
    }

    @Override
    public ComplexNum div(ComplexNum a, ComplexNum b) {
        if (a.equals(ComplexNum.ZERO)) return ComplexNum.ZERO;
        else if (b.equals(ComplexNum.ZERO)) throw new ArithmeticException("Zero division!");

        double divider = b.real * b.real + b.img * b.img;
        return new ComplexNum((a.real * b.real + a.img * b.img) / divider, (a.img * b.real - a.real * b.img) / divider);
    }

    @Override
    public ComplexNum zero() {
        return ComplexNum.ZERO;
    }
}

class ComplexNumOperatorDecorator extends ComplexNumOperator {

    @Override
    public ComplexNum sum(ComplexNum a, ComplexNum b) {
        return operation(a, b, super::sum);
    }

    @Override
    public ComplexNum sub(ComplexNum a, ComplexNum b) {
        return operation(a, b, super::sub);
    }

    @Override
    public ComplexNum mult(ComplexNum a, ComplexNum b) {
        return operation(a, b, super::mult);
    }

    @Override
    public ComplexNum div(ComplexNum a, ComplexNum b) {
        return operation(a, b, super::div);
    }

    private ComplexNum operation(ComplexNum a, ComplexNum b, BinaryOperator<ComplexNum> op) {
        a = Objects.requireNonNullElse(a, ComplexNum.ZERO);
        b = Objects.requireNonNullElse(b, ComplexNum.ZERO);

        return op.apply(a, b);
    }
}