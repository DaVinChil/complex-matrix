import java.util.Random;

class RandomComplex {
    private static final Random RANDOM = new Random();
    private static final Double MAX_VALUE = 100.0;

    public static ComplexNum rand() {
        return new ComplexNum(randDouble(), randDouble());
    }

    @org.jetbrains.annotations.NotNull
    private static Double randDouble() {
        boolean isNegative = RANDOM.nextBoolean();
        return RANDOM.nextDouble(MAX_VALUE) * (isNegative ? -1 : 1);
    }
}