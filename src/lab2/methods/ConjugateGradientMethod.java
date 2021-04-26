package lab2.methods;

import lab2.functions.SquareFunction;


import static lab2.utils.MatrixUtils.*;

public class ConjugateGradientMethod {
    private final double epsilon;

    public ConjugateGradientMethod(double epsilon) {
        this.epsilon = epsilon;
    }

    /**
     * Finds minimum
     * 
     * @param fun         function, minimum to find
     * @param startValues start point
     * @return the coordinates of the minimum
     */
    public double[] findMinimum(SquareFunction fun, double[] startValues) {
        return iterate(fun, startValues.clone());
    }

    public double[] iterate(SquareFunction squareFunction, double[] x0) {
        double[] gradient = squareFunction.runGradient(x0);
        double gradientNorma = norm(gradient);
        double[] prevP = multiply(gradient, -1);
        for (int i = 1; i < x0.length && gradientNorma >= epsilon; i++) {
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
//            log(x0, gradient);
        }
        return x0;
    }
}