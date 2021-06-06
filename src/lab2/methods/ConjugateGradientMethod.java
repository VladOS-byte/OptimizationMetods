package lab2.methods;

import lab2.functions.SquareFunction;

import java.io.IOException;

import static lab2.utils.MatrixUtils.*;

public class ConjugateGradientMethod extends AbstractGradientMethod {

    public ConjugateGradientMethod(double epsilon) {
        super(epsilon);
    }

    public ConjugateGradientMethod(final double epsilon, final boolean log, final String fileName) throws IOException {
        super(epsilon, log, fileName);
    }

    @Override
    public double[] minimize(SquareFunction fun, double[] startValues) throws IOException {
        return iterate(fun, startValues.clone());
    }

    public double[] iterate(SquareFunction squareFunction, double[] x0) throws IOException {
        double[] gradient = squareFunction.evalGradient(x0);
        double gradientNorma = norm(gradient);
        double[] prevP = multiply(gradient, -1);
        for (int i = 1; gradientNorma >= epsilon; i++) {
            double[] mulApPrev = multiply(squareFunction.getA(), prevP);
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