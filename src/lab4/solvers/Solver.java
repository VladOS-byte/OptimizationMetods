package lab4.solvers;

public interface Solver {

    /**
     * решить СЛАУ
     */
    double[] solve(double[][] A, double[] B, double epsilon);

}
