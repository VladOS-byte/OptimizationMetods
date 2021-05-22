package lab3.utils;

/**
 * Утилитарный класс для работы с матрицами
 */
public final class MatrixUtil {

	private MatrixUtil() {
	}

	/**
	 * Функция сложения двух векторов
	 */
	public static double[] add(double[] vector1, double[] vector2) {
		double[] result = new double[vector1.length];

		for (int i = 0; i < vector1.length; ++i) {
			result[i] = vector1[i] + vector2[i];
		}
		return result;
	}

	/**
	 * Функция вычитания двух векторов
	 */
	public static double[] subtract(double[] vector1, double[] vector2) {
		double[] result = new double[vector1.length];

		for (int i = 0; i < vector1.length; ++i) {
			result[i] = vector1[i] - vector2[i];
		}
		return result;
	}

	/**
	 * Функция скалярного произведение векторов
	 */

	public static double dotProduct(double[] vector1, double[] vector2) {
		int dimention = vector1.length;
		double result = 0;
		for (int i = 0; i < dimention; i++) {
			result += vector1[i] * vector2[i];
		}
		return result;
	}

	/**
	 * Функция произведения вектора на число
	 */
	public static double[] multiply(double[] vector, double number) {
		double[] result = new double[vector.length];
		for (int i = 0; i < vector.length; i++) {
			result[i] = vector[i] * number;
		}
		return result;
	}

	/**
	 * Функция произведения вектора на вектор
	 */
	public static double[][] multiply(double[] vector1, double[] vector2) {
		int dimension = vector1.length;
		double[][] result = new double[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				result[i][j] += vector1[i] * vector2[j];
			}
		}
		return result;
	}

	/**
	 * Функция произведения матрицы на число
	 */
	public static double[][] multiply(double[][] matrix, double number) {
		int dimension = matrix.length;
		double[][] result = new double[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				result[i][j] += matrix[i][j] * number;
			}
		}
		return result;
	}

	/**
	 * Функция произведения матрицы на вектор
	 */
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

	/**
	 * Функция произведения двух матриц
	 */
	public static double[][] multiply(double[][] matrixA, double[][] matrixB) {
		int dimension = matrixA.length;
		double[][] resultMatrix = new double[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				for (int k = 0; k < dimension; k++) {
					resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
				}
			}
		}
		return resultMatrix;
	}


	/**
	 * Функция подсчёта нормы вектора
	 */
	public static double norm(double[] vector) {
		double norm;
		double sum = 0;
		for (final double v : vector) {
			sum += Math.pow(v, 2);
		}
		norm = Math.sqrt(sum);
		return norm;
	}
}
