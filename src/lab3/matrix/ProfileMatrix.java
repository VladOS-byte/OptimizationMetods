package lab3.matrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ProfileMatrix {

    public double[] al, au, d;
    public int[] ia;
    boolean isLU;

    private static final String[] NAME_OF_FILES = {"au.txt", "al.txt", "ia.txt", "d.txt"};

    public ProfileMatrix(final String pathOfMatrix) {
        this(pathOfMatrix, false);
    }

    public ProfileMatrix(final String pathOfMatrix, final boolean isLU) {
        this.isLU = isLU;
        readFromPath(pathOfMatrix);

    }

    private void readFromPath(final String pathOfMatrix) {
        for (final String fileName : NAME_OF_FILES) {
            try (final BufferedReader reader = Files.newBufferedReader(Path.of(pathOfMatrix + File.separator + fileName))) {
                switch (fileName) {
                    case "au.txt" -> au = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "al.txt" -> al = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "ia.txt" -> ia = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                    case "d.txt" -> d = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public double get(final int i, final int j) {
        if (isLU) {
            throw new IllegalStateException("Matrix is LU. Use getU, getL");
        }
        return getIJ(i, j);
    }

    private double getIJ(final int i, final int j) {
        if (i == j) {
            return d[i];
        } else if (i < j) {
            return profileGet(j, i, au);
        } else {
            return profileGet(i, j, al);
        }
    }

    public int size() {
        return d.length;
    }

    private double profileGet(final int i, final int j, final double[] a) {
        if (i == j) {
            throw new IllegalStateException("Coordinate from diag " + i + " " + j);
        }
        if (j > i) {
            return 0;
        }
        final int realCount = ia[i + 1] - ia[i];
        final int imagineCount = i - realCount;
        if (j < imagineCount) {
            return 0;
        } else {
            return a[ia[i] + j - imagineCount];
        }
    }

    public double getL(final int i, final int j) {
        checkLU();
        if (i == j) {
            return d[i];
        } else {
            return profileGet(i, j, al);
        }
    }

    public double getU(final int i, final int j) {
        checkLU();
        if (i == j) {
            return 1;
        } else {
            return profileGet(j, i, au);
        }
    }

    public void setL(final int i, final int j, final double value) {
        checkLU();
        if (i == j) {
            d[i] = value;
            return;
        }
        if (i < j) {
            return;
        }
        setProfile(i, j, value, al);
    }

    public void setU(final int i, final int j, final double value) {
        checkLU();
        if (i >= j) {
            return;
        }
        setProfile(j, i, value, au);
    }

    private void setProfile(final int i, final int j, final double value, final double[] profile) {
        final int realCount = ia[i + 1] - ia[i];
        final int imagineCount = i - realCount;
        if (j >= imagineCount) {
            profile[ia[i] + j - imagineCount] = value;
        }
    }

    private void checkLU() {
        if (!isLU) {
            throw new IllegalStateException("Matrix isn't in LU");
        }
    }

    public void changeToLU() {
        if (isLU) {
            return;
        }
        isLU = true;

        setL(0, 0, getIJ(0, 0));

        for (int i = 1; i < size(); i++) {
            //setting L_ij
            for (int j = 0; j < i; j++) {
                double substract = 0;
                for (int k = 0; k < j; k++) {
                    substract += getL(i, k) * getU(k, j);
                }
                setL(i, j, getIJ(i, j) - substract);
            }

            //setting U_ji
            for (int j = 0; j < i; j++) {
                double substract = 0;
                for (int k = 0; k < j; k++) {
                    substract += getL(j, k) * getU(k, i);
                }
                setU(j, i, (getIJ(j, i) - substract) / getL(j, j));
            }

            //setting L_ii
            double substract = 0;
            for (int k = 0; k < i; k++) {
                substract += getL(i, k) * getU(k, i);
            }
            setL(i, i, getIJ(i, i) - substract);

            //setting U_ii
            setU(i, i, 1);
        }
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "al=" + Arrays.toString(al) +
                ", au=" + Arrays.toString(au) +
                ", d=" + Arrays.toString(d) +
                ", ia=" + Arrays.toString(ia) +
                '}';
    }

    public void showByGetters() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }
}
