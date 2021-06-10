package lab4.methods.quasinewton;

import lab1.CombineBrentMethod;
import lab4.functions.Function;
import lab4.methods.Method;

import static lab4.utils.MatrixUtil.add;
import static lab4.utils.MatrixUtil.multiply;


public abstract class AbstractQuasiMethod implements Method {
    protected final double eps;
    protected int iterations = 0;

    protected AbstractQuasiMethod(double eps) {
        this.eps = eps;
    }

    protected AbstractQuasiMethod(double eps, String path) {
        this(eps);
    }

    /**
     * создание единичной матрицы указанной размерности
     */
    protected double[][] createI(int length) {
        double[][] ans = new double[length][length];
        for (int i = 0; i < length; i++) {
            ans[i][i] = 1;
        }
        return ans;
    }

    /**
     * вычисление следующего приближения
     */
    protected double[] findNextX(Function function, double[] x0, double[] p) {
        double a = findLinearMinimum(function, x0, p);
        p = multiply(p, a);
        return add(x0, p);
    }

    /**
     * вычисление величины шага с использованием метода брента
     * @param x начальная точка
     * @param p вектор направления
     */
    protected double findLinearMinimum(Function function, double[] x, double[] p) {
        java.util.function.Function<Double, Double> f = a -> function.run(add(x, multiply(p, a)));
        return new CombineBrentMethod(f).minimize(0, 10, eps);
    }
}
