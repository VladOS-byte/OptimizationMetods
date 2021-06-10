package lab4.methods.marquardt;

import lab4.functions.Function;
import lab4.solvers.CholeskySolver;

import static lab4.utils.MatrixUtil.*;

/**
 * класс для поиска минимума функции методом Марквардта с использованием разложения Холецкого
 */
public class CholeskyMarquardMethod extends AbstractMarquardt {

    public CholeskyMarquardMethod() {
        super(new CholeskySolver(), 0.000001, 2, 0);
    }

    public CholeskyMarquardMethod(final double epsilon, final double beta) {
        super(new CholeskySolver(), epsilon, beta, 0);
    }

    @Override
    public double[] findMinimum(Function function, double[] x0) {
        double[][] I = getI(x0.length);
        double[] x = x0;
        double step = lambda;

        while (true) {
            double[] antiGradient = multiply(function.runGradient(x), -1);
            double[][] hessian = function.runHessian(x);

            double[] direction;
            do {
                direction = solver.solve(add(hessian, multiply(I, step)), antiGradient, epsilon);
                if (direction != null) {
                    break;
                }
                step = Math.max(1, beta * step);
            } while (true);

            x = add(x, direction);

            if (norm(direction) <= epsilon) {
                break;
            }
        }
        return x;
    }
}
