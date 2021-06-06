package lab2.methods;

import lab2.functions.SquareFunction;

import java.io.IOException;

import static lab2.utils.MatrixUtils.*;

public class GradientDescentMethod extends AbstractGradientMethod {
    private double a;

    public GradientDescentMethod(final double step, final double epsilon, final boolean log, final String fileName) throws IOException {
        super(epsilon, log, fileName);
        this.a = step;
    }

    public GradientDescentMethod(final double step, final double epsilon) {
        super(epsilon);
        this.a = step;
    }

    @Override
    public double[] minimize(SquareFunction function, double[] x0) throws IOException {
        double[] prev = x0;
        double[] gradient = function.evalGradient(prev);

        double prevF = function.eval(x0);

        while (norm(gradient) > epsilon) {
            double[] next = subtract(prev, multiply(gradient, a));
            double nextF = function.eval(next);
            if (nextF < prevF) {
                prev = next;
                prevF = nextF;
                gradient = function.evalGradient(prev);
            } else {
                a /= 2;
            }
            log(prev, gradient);
        }
        return prev;
    }
}
