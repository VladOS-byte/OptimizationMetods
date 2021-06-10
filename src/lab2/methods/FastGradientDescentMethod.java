package lab2.methods;

import lab1.*;
import lab2.functions.SquareFunction;
import lab2.utils.Mode;

import java.io.IOException;
import java.util.function.Function;

import static lab2.utils.MatrixUtils.*;

public class FastGradientDescentMethod extends AbstractGradientMethod {
    Mode mode = Mode.SQUARE;

    public FastGradientDescentMethod(final double epsilon) {
        super(epsilon);
    }

    public FastGradientDescentMethod(final double epsilon, Mode mode) {
        super(epsilon);
        this.mode = mode;
    }

    public FastGradientDescentMethod(final double epsilon, boolean log, String fileName) throws IOException {
        super(epsilon, log, fileName);
    }

    public FastGradientDescentMethod(final double epsilon, boolean log, String fileName, Mode mode) throws IOException {
        super(epsilon, log, fileName);
        this.mode = mode;
    }

    @Override
    public double[] minimize(SquareFunction function, double[] x0) throws IOException {
        double[] x = x0;
        double[] gradient = function.evalGradient(x);
        while (norm(gradient) > epsilon) {
            log(x, gradient);
            x = subtract(x, multiply(gradient, calculateStep(x, gradient, function)));
            gradient = function.evalGradient(x);
        }
        return x;
    }

    private double calculateStep(double[] x, double[] gradient, SquareFunction function) {
        if (mode == Mode.SQUARE) {
            double[] p = multiply(gradient, -1);
            return -(scalarMultiply(gradient, p) / scalarMultiply(multiply(function.getA(), p), p));
        }
        Function<Double, Double> f = alpha -> function.eval(subtract(x, multiply(gradient, alpha)));
        return newOneDimensionMethod(f).minimize(null, 0, 10, new Settings(epsilon));
    }

    private OneDimensionMethod newOneDimensionMethod(Function<Double, Double> f) {
        switch (mode) {
            case DICHOTOMY:
                return new DichotomyMethod(f);
            case GOLDEN_CROSS:
                return new GoldenCrossMethod(f);
            case PARABOLIC:
                return new ParabolicMethod(f);
            case FIBONACCI:
                return new FibonacciMethod(f);
            default:
                return new CombineBrentMethod(f);
        }
    }
}