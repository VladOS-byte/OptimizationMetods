package lab4.functions;

public interface Function {
    /**
     * вычисляет значение функции в указанной точке
     */
    double run(double[] x);

    /**
     * вычисляет градиент функции в указанной точке
     */
    double[] runGradient(double[] x);

    /**
     * вычисляет значение {@code A * x}
     */
    double[] multiply(double[] x);

    /**
     * Вычисляет значение матрицы Гессе в указанной точке
     */
    double[][] runHessian(double[] x);
}
