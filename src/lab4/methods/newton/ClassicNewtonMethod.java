package lab4.methods.newton;

import lab4.utils.MatrixUtil;
import lab4.solvers.GaussSolver;
import lab4.functions.Function;
import lab4.methods.Method;
import lab4.solvers.Solver;

public class ClassicNewtonMethod implements Method {
    private final Solver solver;
    private final Double epsilon;

    public ClassicNewtonMethod(Solver solver, double epsilon){
        this.solver = solver;
        this.epsilon = epsilon;
    }

    public ClassicNewtonMethod(){
        this.solver = new GaussSolver();
        this.epsilon = 0.000001;
    }

    @Override
    public double[] findMinimum(Function function, double[] x0) {
        double[] curX = x0;
        double diff;
        do {
            double[] prevX = curX;
            double[] p = solver.solve(function.runHessian(prevX), MatrixUtil.multiply(function.runGradient(prevX), -1), epsilon);
            curX = MatrixUtil.add(prevX, p);
            diff = MatrixUtil.norm(MatrixUtil.subtract(curX, prevX));
        } while(diff > epsilon);
        return curX;
    }


}
