package lab2.utils;

public class MatrixUtils {
    public static double[] multiply(double[][] matrix, double[] vector) {
        int dimension = matrix.length;
        double[] result = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    public static double[] add(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; ++i) {
            result[i] = vector1[i] + vector2[i];
        }
        return result;
    }

    public static double[] subtract(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; ++i) {
            result[i] = vector1[i] - vector2[i];
        }
        return result;
    }

    public static double scalarMultiply(double[] vector1, double[] vector2) {
        double result = 0;

        for (int i = 0; i < vector1.length; ++i) {
            result += vector1[i] * vector2[i];
        }
        return result;
    }

    public static double[] multiply(double[] vector, double x) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * x;
        }
        return result;
    }

    public static double norm(double[] vector) {
        double ans = 0;
        for (double x : vector) {
            ans += x * x;
        }
        return Math.sqrt(ans);
    }
}
