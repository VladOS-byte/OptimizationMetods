package lab3.conjugate;

import lab3.matrix.LineColumnMatrix;

import java.io.IOException;

import static lab2.utils.MatrixUtils.*;

public class ConjugateGradientMethod extends AbstractGradientMethod {

    /**
     * Standard constructor
     *
     * @param epsilon {@link #epsilon}
     */
    public ConjugateGradientMethod(double epsilon) {
        super(epsilon);
    }

    /**
     * Full constructor
     *
     * @param epsilon  {@link #epsilon}
     * @param log      {@link AbstractGradientMethod#log}
     * @param fileName output file for {@link AbstractGradientMethod#out}
     * @throws IOException if specified output file was not found
     */
    public ConjugateGradientMethod(final double epsilon, final boolean log, final String fileName) throws IOException {
        super(epsilon, log, fileName);
    }

    @Override
    public double[] findMinimum(LineColumnMatrix matrix, double[] startValues) throws IOException {
        return iterate(matrix, startValues.clone());
    }

    /**
     * Iterative method of ConjugateGradient.
     *
     * @param matrix explored fucntion
     * @param x0 point of start for current iterative launch
     * @return temporary result of minimum
     * @throws IOException exception from {@link #log(double[], double[])}
     */
    public double[] iterate(LineColumnMatrix matrix, double[] x0) throws IOException {
        double[] gradient = matrix.runGradient(x0);
        double gradientNorma = norm(gradient);
        double[] prevP = multiply(gradient, -1);
        for (int i = 1; gradientNorma >= epsilon; i++) {
            double[] mulApPrev = matrix.multiply(prevP);
            double aPrev = gradientNorma * gradientNorma / scalarMultiply(mulApPrev, prevP);
            double[] nextX = add(x0, multiply(prevP, aPrev));
            double[] nextGrad = add(gradient, multiply(mulApPrev, aPrev));
            double normNextGrad = norm(nextGrad);
            double b = normNextGrad * normNextGrad / gradientNorma / gradientNorma;

            prevP = add(multiply(nextGrad, -1), multiply(prevP, b));
            gradient = nextGrad;
            x0 = nextX;
            gradientNorma = normNextGrad;
            log(x0, gradient);
        }
        return x0;
    }
}