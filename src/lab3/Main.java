package lab3;

import lab3.conjugate.ConjugateGradientMethod;
import lab3.solvers.LuSolver;
import lab3.utils.MatrixUtil;
import lab3.generator.MatrixGenerator;
import lab3.matrix.LineColumnMatrix;
import lab3.matrix.ProfileMatrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


public class Main {

    private static void solve(final String[] args) {
        //args: path [hilbert $dimension | ordinary $dimension $ordinary | bonus]
        checkArgs(args);
        boolean bonus = false;
        if (args.length > 1) {
            try {
                switch (args[1]) {
                    case "hilbert" -> MatrixGenerator.parseAndWrite(MatrixGenerator.generateHilbertMatrix(Integer.parseInt(args[2])), args[0]);
                    case "ordinary" -> MatrixGenerator.parseAndWrite(MatrixGenerator.generateOrdinaryMatrix(Integer.parseInt(args[2]), Integer.parseInt(args[3])), args[0]);
                    case "bonus" -> bonus = true;
                }
            } catch (final IOException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
        final double[] b;
        if (bonus) {
            try {
                b = solveBonus(args[0]);
            } catch (final IOException unreachable) {
                return;
            }
        } else {
            b = readB(args[0]);
            final ProfileMatrix matrix = new ProfileMatrix(args[0]);
            matrix.showByGetters();
            LuSolver.solve(matrix, b);
        }
        try (final BufferedWriter writer = Files.newBufferedWriter(Path.of(args[0], "ans.txt"))) {
            writer.write(Arrays.stream(b).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
        } catch (final IOException e) {
            System.err.println("Error in writing answer. " + e.getMessage());
        }
    }

    private static double[] solveBonus(final String path) throws IOException {
        final LineColumnMatrix matrix = new LineColumnMatrix(path);
        final ConjugateGradientMethod m = new ConjugateGradientMethod(0.000001);
        final double[] xSolved = m.findMinimum(matrix, new double[matrix.size()]);
        final double[] xReal = DoubleStream.iterate(1.0, x -> x + 1.0).limit(xSolved.length).toArray();

        final double miss = MatrixUtil.norm(MatrixUtil.subtract(xReal, xSolved));

        System.out.println(miss);
        System.out.println(miss / MatrixUtil.norm(xReal));

        final double[] f = matrix.getB();
        final double missF = MatrixUtil.norm(MatrixUtil.subtract(f, matrix.multiply(xSolved)));

        System.out.println((miss / MatrixUtil.norm(xReal)) / (missF / MatrixUtil.norm(f)));

        return xSolved;
    }

    private static void checkArgs(final String[] args) {
        if (args == null || Arrays.stream(args).anyMatch(Objects::isNull) || args.length == 0) {
            throw new IllegalArgumentException("Args shouldn't be null and have length > 0");
        }
        Path.of(args[0]);
        if (args.length > 1) {
            final String message = "If you give more than 1 args, second must be hilbert $dimension | ordinary $dimension $ordinary";
            args[1] = args[1].toLowerCase();
            if (args[1].equals("hilbert") && args.length == 3) {
                try {
                    Integer.parseInt(args[2]);
                } catch (final NumberFormatException ignored) {
                    throw new IllegalArgumentException(message);
                }
            } else if (args[1].equals("ordinary") && args.length == 4) {
                try {
                    Integer.parseInt(args[2]);
                    Integer.parseInt(args[3]);
                } catch (final NumberFormatException ignored) {
                    throw new IllegalArgumentException(message);

                }
            } else if (args[1].equals("bonus")) {
                return;
            } else {
                throw new IllegalArgumentException(message);
            }
        }
    }

    private static double[] readB(final String path) {
        try (final BufferedReader reader = Files.newBufferedReader(Path.of(path, "b.txt"))) {
            return Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
        } catch (final IOException e) {
            throw new IllegalArgumentException("Error in getting vector b from 'Ax = b' " + e.getMessage());
        }
    }

    public static void main(final String[] args) {
        solve(args);
    }
}
