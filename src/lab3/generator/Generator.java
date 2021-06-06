package lab3.generator;

import lab3.utils.MatrixUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Generator {
    public static final Random RANDOM = new Random();
    private static final String[] NAME_OF_FILES = {"au.txt", "al.txt", "ia.txt", "d.txt", "b.txt", "ja.txt"};

    public static double[][] generateOrdinaryMatrix(final int dimension, final int k) {
        final double[][] matrix = new double[dimension][dimension];
        final int[] profileOffset = new int[dimension];

        for (int i = 0; i < dimension; ++i) {
            profileOffset[i] = RANDOM.nextInt(i + 1);
        }

        for (int row = 0; row < dimension; ++row) {
            for (int col = profileOffset[row]; col < row; ++col) {
                matrix[row][col] = -RANDOM.nextInt(4) - 1;
            }
        }

        for (int col = 0; col < dimension; ++col) {
            for (int row = profileOffset[col]; row < col; ++row) {
                matrix[row][col] = -RANDOM.nextInt(4) - 1;
            }
        }

        for (int i = 0; i < dimension; ++i) {
            double sum = 0;
            for (int j = 0; j < dimension; ++j) {
                if (j != i) {
                    sum -= matrix[i][j];
                }
            }
            matrix[i][i] = sum;
            if (i == 0) {
                matrix[i][i] += Math.pow(0.1, k);
            }
        }

        return matrix;
    }

    public static double[][] generateDiagonalDominationMatrix(final int dimension, final int k) {
        final double[][] matrix = new double[dimension][dimension];
        final int[] profileOffset = new int[dimension];

        for (int i = 0; i < dimension; ++i) {
            profileOffset[i] = RANDOM.nextInt(i + 1);
        }

        for (int row = 0; row < dimension; ++row) {
            for (int col = profileOffset[row]; col < row; ++col) {
                int val = -RANDOM.nextInt(4) - 1;
                matrix[row][col] = val;
                matrix[col][row] = val;
            }
        }

        for (int i = 0; i < dimension; ++i) {
            double sum = 0;
            for (int j = 0; j < dimension; ++j) {
                if (j != i) {
                    sum -= matrix[i][j];
                }
            }
            matrix[i][i] = sum;
            if (i == 0) {
                matrix[i][i] += 1;
            }
        }

        return matrix;
    }

    public static double[][] generateHilbertMatrix(final int dimension) {
        final double[][] matrix = new double[dimension][dimension];
        for (int i = 1; i <= dimension; ++i) {
            for (int j = 1; j <= dimension; ++j) {
                matrix[i - 1][j - 1] = (double) 1 / (i + j - 1);
            }
        }


        return matrix;
    }

    public static void parseAndWrite(final double[][] matrix, final String... path) throws IOException {

        try {
            Files.createDirectories(Path.of(String.join(File.separator, path)));
        } catch (IOException e) {
            throw new IOException("Error in creating directories " + Path.of(String.join(File.separator, path) + ". " + e.getMessage()));
        }

        final int size = matrix.length;
        final double[] b = MatrixUtil.multiply(matrix, DoubleStream.iterate(1.0, x -> x + 1.0).limit(size).toArray());
        final double[] d = new double[size];
        final ArrayList<Double> al = new ArrayList<>();
        final ArrayList<Double> au = new ArrayList<>();
        final int[] ia = new int[size + 1];
        for (int i = 0; i < size; i++) {
            d[i] = matrix[i][i];
        }
        ia[0] = 0;
        ia[1] = 0;
        for (int i = 1; i < size; i++) {
            int ind = 0;
            while (ind < i && matrix[i][ind] == 0) {
                ind++;
            }
            while (ind < i) {
                al.add(matrix[i][ind]);
                au.add(matrix[ind][i]);
                ind++;
            }
            ia[i + 1] = al.size();
        }
        for (final String fileName : NAME_OF_FILES) {
            if (fileName.equals("ja.txt")) continue;
            try (final BufferedWriter out = Files.newBufferedWriter(Path.of(String.join(File.separator, path), fileName))) {
                switch (fileName) {
                    case "au.txt" -> out.write(au.stream().map(Object::toString).collect(Collectors.joining(" ")));
                    case "al.txt" -> out.write(al.stream().map(Object::toString).collect(Collectors.joining(" ")));
                    case "ia.txt" -> out.write(Arrays.stream(ia).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "d.txt" -> out.write(Arrays.stream(d).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "b.txt" -> out.write(Arrays.stream(b).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                }
            }
        }
    }

    public static void parserAndWriterOnLineColumn(final double[][] matrix, final String... path) throws IOException {

        try {
            Files.createDirectories(Path.of(String.join(File.separator, path)));
        } catch (IOException e) {
            throw new IOException("Error in creating directories " + Path.of(String.join(File.separator, path) + ". " + e.getMessage()));
        }

        final int size = matrix.length;
        final double[] b = MatrixUtil.multiply(matrix, DoubleStream.iterate(1.0, x -> x + 1.0).limit(size).toArray());
        final double[] d = new double[size];
        final ArrayList<Double> al = new ArrayList<>();
        final ArrayList<Double> au = new ArrayList<>();
        final ArrayList<Integer> ja = new ArrayList<>();
        final int[] ia = new int[size + 1];
        for (int i = 0; i < size; i++) {
            d[i] = matrix[i][i];
        }
        ia[0] = 0;
        ia[1] = 0;
        for (int i = 1; i < size; i++) {
            int ind = 0;
            while (ind < i && matrix[i][ind] == 0) {
                ind++;
            }
            while (ind < i) {
                if (matrix[i][ind] != 0) {
                    al.add(matrix[i][ind]);
                    au.add(matrix[ind][i]);
                    ja.add(ind);
                }
                ind++;
            }
            ia[i + 1] = al.size();
        }
        for (final String fileName : NAME_OF_FILES) {
            try (final BufferedWriter out = Files.newBufferedWriter(Path.of(String.join(File.separator, path), fileName))) {
                switch (fileName) {
                    case "au.txt" -> out.write(au.stream().map(Object::toString).collect(Collectors.joining(" ")));
                    case "al.txt" -> out.write(al.stream().map(Object::toString).collect(Collectors.joining(" ")));
                    case "ia.txt" -> out.write(Arrays.stream(ia).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "d.txt" -> out.write(Arrays.stream(d).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "b.txt" -> out.write(Arrays.stream(b).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "ja.txt" -> out.write(ja.stream().map(Object::toString).collect(Collectors.joining(" ")));
                }
            }
        }
    }

}