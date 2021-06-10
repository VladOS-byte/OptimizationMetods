package lab4.methods.newton;

import lab1.CombineBrentMethod;
import lab4.utils.MatrixUtil;
import lab4.solvers.GaussSolver;
import lab4.functions.Function;
import lab4.methods.Method;
import lab4.solvers.Solver;

import static lab4.utils.MatrixUtil.*;

/**
 * метод Ньютона с выбором направления спуска и вычислением шага методом Брента
 */
public class DescentDirectionNewtonMethod implements Method {
    private final Solver solver;
    private final Double epsilon;

    public DescentDirectionNewtonMethod(Solver solver, double epsilon){
        this.solver = solver;
        this.epsilon = epsilon;
    }

    public DescentDirectionNewtonMethod() {
        this.solver = new GaussSolver();
        this.epsilon = 0.000001;
    }

    @Override
    public double[] findMinimum(Function function, double[] x0) {
        double[] d0 = multiply(function.runGradient(x0), -1);
        java.util.function.Function<Double, Double> f0 = alpha -> function.run(add(x0, multiply(d0, alpha)));
        double alpha0 = new CombineBrentMethod(f0).minimize(-100, 100, epsilon);
        double[] nextX = add(x0, multiply(d0, alpha0));
        double diff = norm(MatrixUtil.subtract(nextX, x0));

        while(diff > epsilon) {
            double[] prevX = nextX;
            double[] gradient = function.runGradient(prevX);
            double[] antiGradient = multiply(gradient, -1);
            double[] p = solver.solve(function.runHessian(prevX), antiGradient, epsilon);

            final double[] direction = MatrixUtil.scalar(p, gradient) >= 0 ? antiGradient : p;

            java.util.function.Function<Double, Double> f = alpha -> function.run(MatrixUtil.add(prevX, MatrixUtil.multiply(direction, alpha)));
            double alpha = new CombineBrentMethod(f0).minimize(-100, 100, epsilon);
            nextX = add(prevX, multiply(direction, alpha));

            diff = MatrixUtil.norm(MatrixUtil.subtract(nextX, prevX));
        }

        return nextX;
    }
}
