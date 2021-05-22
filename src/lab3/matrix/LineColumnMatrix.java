package lab3.matrix;

import lab3.utils.MatrixUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class LineColumnMatrix {
    private static final String[] NAME_OF_FILES = {"au.txt", "al.txt", "ia.txt", "d.txt", "ja.txt", "b.txt"};

    private double[] al, au, d, b;
    private int[] ia, ja;

    public LineColumnMatrix(final String pathOfMatrixAndVector) {
        for (final String fileName : NAME_OF_FILES) {
            try (final BufferedReader reader = Files.newBufferedReader(Path.of(pathOfMatrixAndVector + File.separator + fileName))) {
                switch (fileName) {
                    case "au.txt" -> au = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "al.txt" -> al = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "ia.txt" -> ia = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                    case "ja.txt" -> ja = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                    case "d.txt" -> d = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "b.txt" -> b = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int size() {
        return d.length;
    }

    public double getIJ(final int i, final int j) {
        if (i == j) {
            return d[i];
        }
        if (i > j) {
            return getFromTriangle(i, j, true);
        } else {
            return getFromTriangle(j, i, false);
        }
    }

    private double getFromTriangle(final int i, final int j, final boolean low) {
        final int realInJA = ia[i + 1] - ia[i];
        int offset = 0;
        for (; offset < realInJA; offset++) {
            if (ja[ia[i] + offset] == j) {
                break;
            } else if (ja[ia[i] + offset] > j) {
                return 0;
            }
        }
        if (offset == realInJA) {
            return 0;
        }
        if (low) {
            return al[ia[i] + offset];
        } else {
            return au[ia[i] + offset];
        }
    }

    public double run(final double[] x) {
        final double[] a = multiply(x);
        final double quad = MatrixUtil.dotProduct(x, a) / 2;
        final double one = MatrixUtil.dotProduct(b, x);

        return quad - one;
    }

    public double[] runGradient(final double[] x) {
        return MatrixUtil.subtract(multiply(x), b);
    }

    public double[] multiply(final double[] x) {
        final double[] ans = new double[size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                ans[i] += getIJ(i, j) * x[j];
            }
        }
        return ans;
    }

    public double[] getB() {
        return b;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LineColumnMatrix{");
        sb.append(System.lineSeparator());
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                sb.append(getIJ(i, j)).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("}");
        return sb.toString();
    }
}
