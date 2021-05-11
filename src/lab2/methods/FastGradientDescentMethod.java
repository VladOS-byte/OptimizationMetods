package lab2.methods;

import lab1.*;
import lab2.functions.SquareFunction;
import lab2.utils.Mode;

import java.io.IOException;
import java.util.function.Function;

import static lab2.utils.MatrixUtils.*;

/**
 * Class of Fast Gradient realisation
 */
public class FastGradientDescentMethod extends AbstractGradientMethod {
    Mode mode = Mode.SQUARE;

    /**
     * Standard constructor
     *
     * @param epsilon {@link AbstractGradientMethod#epsilon}
     * @see AbstractGradientMethod#AbstractGradientMethod(double)
     */
    public FastGradientDescentMethod(final double epsilon) {
        super(epsilon);
    }

    /**
     * Standard constructor with mode
     *
     * @param epsilon {@link AbstractGradientMethod#epsilon}
     * @param mode    OneDimensional optimization mode
     * @see AbstractGradientMethod#AbstractGradientMethod(double)
     */
    public FastGradientDescentMethod(final double epsilon, Mode mode) {
        super(epsilon);
        this.mode = mode;
    }

    /**
     * Full constructor with default mode
     *
     * @param epsilon  {@link #epsilon}
     * @param log      {@link AbstractGradientMethod#log}
     * @param fileName output file for {@link AbstractGradientMethod#out}
     * @throws IOException if specified output file was not found
     * @see AbstractGradientMethod#AbstractGradientMethod(double, boolean, String)
     */
    public FastGradientDescentMethod(final double epsilon, boolean log, String fileName) throws IOException {
        super(epsilon, log, fileName);
    }

    /**
     * Full constructor
     *
     * @param epsilon  {@link #epsilon}
     * @param log      {@link AbstractGradientMethod#log}
     * @param fileName output file for {@link AbstractGradientMethod#out}
     * @param mode     OneDimensional optimization mode
     * @throws IOException if specified output file was not found
     * @see AbstractGradientMethod#AbstractGradientMethod(double, boolean, String)
     */
    public FastGradientDescentMethod(final double epsilon, boolean log, String fileName, Mode mode) throws IOException {
        super(epsilon, log, fileName);
        this.mode = mode;
    }


    @Override
    public double[] findMinimum(SquareFunction function, double[] x0) throws IOException {
        double[] x = x0;
        double[] gradient = function.runGradient(x);
        while (norm(gradient) > epsilon) {
            log(x, gradient);
            x = subtract(x, multiply(gradient, calculateStep(x, gradient, function)));
            gradient = function.runGradient(x);
        }
        return x;
    }

    /**
     * Function for computing of the step.
     *
     * @param x        start point
     * @param gradient gradient of the fucntion
     * @param function explored function
     * @return step for {@link #findMinimum(SquareFunction, double[])}
     */
    private double calculateStep(double[] x, double[] gradient, SquareFunction function) {
        if (mode == Mode.SQUARE) {
            double[] p = multiply(gradient, -1);
            return -(scalarMultiply(gradient, p) / scalarMultiply(multiply(function.getA(), p), p));
        }
        Function<Double, Double> f = alpha -> function.run(subtract(x, multiply(gradient, alpha)));
        return newOneDimensionMethod(f).minimize(null, 0, 10, new Settings(epsilon));
    }

    /**
     * Function to return instance of one dimension optimization class
     *
     * @param f function to optimize
     * @return instance of one dimension optimization class optimizing  {@code f}
     */
    private OneDimensionMethod newOneDimensionMethod(Function<Double, Double> f) {
        switch (mode) {
            case DICHOTOMY:
                return new DichotomyMethod(f);
            case GOLDEN_CROSS:
                return new GoldenCrossSection(f);
            case PARABOLIC:
                return new ParabolicMethod(f);
            case FIBONACCI:
                return new FibonacciMethod(f);
            default:
                return new CombineBrentMethod(f);
        }
    }
}