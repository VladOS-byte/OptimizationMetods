package lab3.solvers;

import lab3.matrix.ProfileMatrix;

public class LuSolver {
    private static void gaussForward(final ProfileMatrix matrix, final double[] b) {
        for (int i = 0; i < matrix.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                b[i] -= b[j] * matrix.getL(i, j);
            }
            b[i] /= matrix.getL(i, i);
        }
    }

    private static void gaussBackward(final ProfileMatrix matrix, final double[] y) {
        for (int i = matrix.size() - 1; i >= 0; --i) {
            for (int j = matrix.size() - 1; j > i; --j) {
                y[i] -= y[j] * matrix.getU(i, j);
            }
            y[i] /= matrix.getU(i, i);
        }
    }

    public static void solve(final ProfileMatrix matrix, final double[] b) {
        matrix.changeToLU();
        gaussForward(matrix, b);
        gaussBackward(matrix, b);
    }
}