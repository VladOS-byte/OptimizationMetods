package lab2.methods;

import lab2.functions.SquareFunction;

import java.io.FileNotFoundException;
import java.io.IOException;

import static lab2.utils.MatrixUtils.*;

/**
 * предоставляет возможность искать минимум квадратичных функций методом градиентного спуска
 */
public class GradientDescentMethod extends AbstractGradientMethod {
    /**
     * step of Gradient method 
     */
    private double a;

    /**
     * Full constructor
     * @param step {@link #a}
     * @param epsilon {@link #epsilon}
     * @param log {@link AbstractGradientMethod#log}
     * @param fileName output file for {@link AbstractGradientMethod#out}
     * @throws FileNotFoundException if specified output file was not found
     * @see AbstractGradientMethod#AbstractGradientMethod(double, boolean, String)
     */
    public GradientDescentMethod(final double step, final double epsilon, final boolean log, final String fileName) throws FileNotFoundException {
        super(epsilon, log, fileName);
        this.a = step;
    }

    /**
     * Standard constructor
     * @param step {@link #a}
     * @param epsilon {@link AbstractGradientMethod#epsilon}
     * @see AbstractGradientMethod#AbstractGradientMethod(double) 
     */
    public GradientDescentMethod(final double step, final double epsilon) {
        super(epsilon);
        this.a = step;
    }

    @Override
    public double[] findMinimum(SquareFunction function, double[] x0) throws IOException {
        double[] prev = x0;
        double[] gradient = function.runGradient(prev);

        double prevF = function.run(x0);

        while (norm(gradient) > epsilon) {
            double[] next = subtract(prev, multiply(gradient, a));
            double nextF = function.run(next);
            if (nextF < prevF) {
                prev = next;
                prevF = nextF;
                gradient = function.runGradient(prev);
            } else {
                a /= 2;
            }
            log(prev, gradient);
        }
        return prev;
    }
}
