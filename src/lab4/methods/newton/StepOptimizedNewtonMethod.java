package lab4.methods.newton;

import lab1.CombineBrentMethod;
import lab4.methods.Method;
import lab4.functions.Function;
import lab4.solvers.Solver;
import lab4.utils.MatrixUtil;
import lab4.solvers.GaussSolver;

/**
 * методо Ньютона с использованием метода Брента для вычисления длины шага
 */
public class StepOptimizedNewtonMethod implements Method {
    private final Solver solver;
    private final Double epsilon;
    
    public StepOptimizedNewtonMethod(Solver solver, double epsilon) {
        this.solver = solver;
        this.epsilon = epsilon;
    }
    
    public StepOptimizedNewtonMethod() {
        this.solver = new GaussSolver();
        this.epsilon = 0.000001;
    }
    
    @Override
    public double[] findMinimum(Function function, double[] x0) {
        double[] prevX = x0;
        double[] p = solver.solve(function.runHessian(prevX), MatrixUtil.multiply(function.runGradient(prevX), -1), epsilon);

        double[] curX = MatrixUtil.add(prevX, p);

        while (MatrixUtil.norm(MatrixUtil.subtract(curX, prevX)) > epsilon && MatrixUtil.norm(p) > epsilon) {
            prevX = curX;
            p = solver.solve(function.runHessian(prevX), MatrixUtil.multiply(function.runGradient(prevX), -1), epsilon);

            double[] finalPrevX = prevX;
            double[] finalP = p;
            java.util.function.Function<Double, Double> fun = v -> function.run(MatrixUtil.add(finalPrevX, MatrixUtil.multiply(finalP, v)));
            double a = new CombineBrentMethod(fun).minimize(-100, 100, epsilon);
            curX = MatrixUtil.add(prevX, MatrixUtil.multiply(p, a));
        }

        return curX;
    }
}
