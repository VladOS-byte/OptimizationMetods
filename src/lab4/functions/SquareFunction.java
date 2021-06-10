package lab4.functions;

import lab4.utils.MatrixUtil;

public class SquareFunction implements Function {
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
     */
    public SquareFunction(double[][] a, double[] b, double c) {
        A = a;
        B = b;
        C = c;
    }

    @Override
    public double run(double[] x) {
        double[] a = MatrixUtil.multiply(A, x);
        double quad = MatrixUtil.scalar(x, a) / 2;
        double one = MatrixUtil.scalar(B, x);

        return quad - one + C;
    }

    @Override
    public double[] runGradient(double[] x) {
        return MatrixUtil.subtract(MatrixUtil.multiply(A, x), B);
    }

    @Override
    public double[] multiply(double[] x) {
        return MatrixUtil.multiply(A, x);
    }

    @Override
    public double[][] runHessian(double[] x) {
        return A;
    }
}
