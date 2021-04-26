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

    public double run(double[] x) {
        return scalarMultiply(multiply(A, x), x) - scalarMultiply(B, x) + C;
    }

    public double[] runGradient(double[] x) {
        return add(multiply(A, x), B);
    }

    public double[][] getA() {
        return A;
    }
}