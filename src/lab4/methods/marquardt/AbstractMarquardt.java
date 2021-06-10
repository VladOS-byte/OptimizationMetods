package lab4.methods.marquardt;

import lab4.methods.Method;
import lab4.solvers.Solver;

import java.util.stream.IntStream;

public abstract class AbstractMarquardt implements Method {
    protected final Solver solver;
    protected final double epsilon;
    /**
     * величина для изменения {@code lambda}
     */
    protected final double beta;
    /**
     * величина, на которую будет увеличиваться диагональ матрицы Гессе
     */
    protected final double lambda;


    AbstractMarquardt(final Solver solver, final double epsilon, final double beta, final double lambda) {
        this.solver = solver;
        this.epsilon = epsilon;
        this.beta = beta;
        this.lambda = lambda;
    }

    /**
     * создаёт единичную матрицу указанного размера
     */
    protected double[][] getI(final int dimension) {
        return IntStream.range(0, dimension).mapToObj(i -> {
            double[] line = new double[dimension];
            line[i] = 1;
            return line;
        }).toArray(double[][]::new);
    }
}
