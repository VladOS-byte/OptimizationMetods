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

    /**
     * Standard constructor of matrix representation of function
     * @param a {@link #A}
     * @param b {@link #B}
     * @param c {@link #C}
     */
    public SquareFunction(double[][] a, double[] b, double c) {
        A = a;
        B = b;
        C = c;
    }

    /**
     * Evaluates function in x
     * @param x point to evaluate in
     * @return function value in point x
     */
    public double run(double[] x) {
        return scalarMultiply(multiply(A, x), x) / 2 - scalarMultiply(B, x) + C;
    }

    /**
     * Evaluates function's gradient in x
     * @param x point to evaluate in
     * @return function's gradient in point x
     */
    public double[] runGradient(double[] x) {
        return subtract(multiply(A, x), B);
    }

    public double[][] getA() {
        return A;
    }
}