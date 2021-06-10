package lab4.utils;

public final class MatrixUtil {

	private MatrixUtil() {
	}

	public static void transposeMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = i; j < matrix.length; ++j) {
				double temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
	}

	public static double[] add(double[] vector1, double[] vector2) {
		double[] result = new double[vector1.length];

		for (int i = 0; i < vector1.length; ++i) {
			result[i] = vector1[i] + vector2[i];
		}
		return result;
	}

	public static double[][] add(double[][] matrix1, double[][] matrix2) {
		double[][] result = new double[matrix1.length][matrix1[0].length];

		for (int i = 0; i < matrix1.length; ++i) {
			for (int j = 0; j < matrix1.length; ++j) {
				result[i][j] = matrix1[i][j] + matrix2[i][j];
			}
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

	public static double[][] subtract(double[][] matrix1, double[][] matrix2) {
		double[][] result = new double[matrix1.length][matrix1[0].length];
		for (int i = 0; i < matrix1.length; ++i) {
			for (int j = 0; j < matrix1.length; ++j) {
				result[i][j] = matrix1[i][j] - matrix2[i][j];
			}
		}
		return result;
	}

	public static double scalar(double[] vector1, double[] vector2){
		int dimention = vector1.length;
		double result = 0;
		for(int i = 0; i < dimention; i++){
			result += vector1[i]*vector2[i];
		}
		return result;
	}

	public static double[] multiply(double[] vector, double number) {
		double[] result = new double[vector.length];
		for (int i = 0; i < vector.length; i++) {
			result[i] = vector[i] * number;
		}
		return result;
	}

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

	public static double norm(double[] vector) {
		double norm;
		double sum = 0;
		for (final double v : vector) {
			sum += Math.pow(v, 2);
		}
		norm = Math.sqrt(sum);
		return norm;
	}

	public static double norm(double[][] matrix) {
		double norm = 0;
		for (final double[] doubles : matrix) {
			double sum = 0;
			for (int j = 0; j < matrix.length; j++) {
				sum += Math.abs(doubles[j]);
			}
			norm = Math.max(norm, sum);
		}
		return norm;
	}

	public static double[][] negate(double[][] matrix) {
		int dim1 = matrix.length;
		int dim2 = matrix[0].length;
		double[][] resultMatrix = new double[dim1][dim2];
		for (int i = 0; i < dim1; i++) {
			for (int j = 0; j < dim2; j++) {
				resultMatrix[i][j] = - matrix[i][j];
			}
		}
		return resultMatrix;
	}

	public static double[] negate(double[] vector) {
		int dim = vector.length;
		double[] v = new double[dim];
		for (int i = 0; i < dim; i++) {
			v[i] = - vector[i];
		}
		return v;
	}
}
