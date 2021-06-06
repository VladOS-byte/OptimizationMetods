package lab2.functions;

import static lab2.utils.MatrixUtils.*;

public class SquareFunction {
    /**
     * Matrix of square terms. A_{ij}=A_{ji}, A_{ii} = 2 * x{i}^2
     */
    final double[][] A;
    /**
     * Vector of one-dimensional terms
     */
    final double[] B;
    /**
     * Constant of function
     */
    final double C;

    public SquareFunction(double[][] a, double[] b, double c) {
        A = a;
        B = b;
        C = c;
    }

    public double eval(double[] x) {
        return scalarMultiply(multiply(A, x), x) / 2 - scalarMultiply(B, x) + C;
    }

    public double[] evalGradient(double[] x) {
        return subtract(multiply(A, x), B);
    }

    public double[][] getA() {
        return A;
    }
}