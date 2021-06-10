package lab4.methods.marquardt;

import lab1.CombineBrentMethod;
import lab1.OneDimensionMethod;
import lab4.functions.Function;
import lab4.solvers.GaussSolver;
import lab4.solvers.Solver;

import java.util.Arrays;

import static lab4.utils.MatrixUtil.*;

/**
 * класс для поиска минимума функции методом Марквардта без использования разложения Холецкого
 */
public class GaussMarquardtMethod extends AbstractMarquardt {

    public GaussMarquardtMethod() {
        super(new GaussSolver(), 0.000001, 0.5, 1000000000000000D);
    }

    public GaussMarquardtMethod(final Solver solver, final double epsilon, final double lambda, final double beta) {
        super(solver, epsilon, beta, lambda);
    }

    @Override
    public double[] findMinimum(final Function function, final double[] x0) {
        double[][] I = getI(x0.length);
        double[] x = x0;
        double step = lambda;

        double[] optimalDirection;
        do {
            double[] antiGradient = multiply(function.runGradient(x), -1);
            double[][] hessian = function.runHessian(x);
            double[] direction = solver.solve(add(hessian, multiply(I, step)), antiGradient, epsilon);

            double alpha = new CombineBrentMethod(getOptimizedFunction(function, x, direction)).minimize(-100000, 100000, epsilon);
            optimalDirection = multiply(direction, alpha);

            x = add(x, optimalDirection);
            step *= beta;
        } while (norm(optimalDirection) > epsilon);

        return x;
    }

    private java.util.function.Function<Double, Double> getOptimizedFunction(final Function f, final double[] x, final double[] p) {
        return alpha -> f.run(add(Arrays.copyOf(x, x.length), multiply(Arrays.copyOf(p, p.length), alpha)));
    }
}
