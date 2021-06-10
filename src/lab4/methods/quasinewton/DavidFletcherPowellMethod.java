package lab4.methods.quasinewton;

import lab4.functions.Function;

import java.util.Arrays;

import static lab4.utils.MatrixUtil.*;

public class DavidFletcherPowellMethod extends AbstractQuasiMethod {

    public DavidFletcherPowellMethod(double eps) {
        super(eps);
    }

    @Override
    public double[] findMinimum(final Function function, final double[] x0) {
        double[][] G = createI(x0.length);
        double[] w = negate(function.runGradient(x0));
        double[] p = Arrays.copyOf(w, w.length);
        double[] x = findNextX(function, x0, p);
        double[] deltaX = subtract(x, x0);

        while (norm(deltaX) < eps) {
            double[] prevW = Arrays.copyOf(w, w.length);
            w = negate(function.runGradient(x));
            double[] deltaW = subtract(w, prevW);

            double[] v = multiply(G, deltaW);
            G = nextG(G, deltaX, deltaW, v);

            p = multiply(G, w);
            double[] prevX = Arrays.copyOf(x, x.length);
            x = findNextX(function, x, p);
            deltaX = subtract(x, prevX);
        }
        return x;
    }

    /**
     * вычисляет следующие приближение матрицы G
     */
    private double[][] nextG(final double[][] G, final double[] deltaX, final double[] deltaW, double[] v) {
        double[][] first = multiply(
                multiply(deltaX, deltaX),
                1 / scalar(deltaW, deltaX));
        double[][] second = multiply(
                multiply(v, v),
                1 / scalar(v, deltaW));
        return subtract(subtract(G, first), second);
    }
}