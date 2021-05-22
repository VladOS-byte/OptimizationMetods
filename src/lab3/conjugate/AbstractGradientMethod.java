package lab3.conjugate;

import lab3.matrix.LineColumnMatrix;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Abstract class for Gradients methods
 */
public abstract class AbstractGradientMethod {
    /**
     * Calculation accuracy
     */
    protected final double epsilon;
    /**
     * Should log result or not
     */
    protected final boolean log;
    /**
     * Writer for the log
     */
    protected final FileWriter out;

    /**
     * Full constructor
     * @param epsilon {@link #epsilon}
     * @param log {@link #log}
     * @param fileName output file for {@link #out}
     * @throws FileNotFoundException
     */
    public AbstractGradientMethod(double epsilon, boolean log, String fileName) throws IOException {
        this.epsilon = epsilon;
        this.log = log;
        this.out = new FileWriter(fileName);
    }

    /**
     * Standard constructor
     * @param epsilon {@link #epsilon}
     */
    public AbstractGradientMethod(double epsilon) {
        this.epsilon = epsilon;
        this.log = false;
        this.out = null;
    }

    /**
     * Search minimum of the function with start point x0
     *
     * @param matrix explores function
     * @param x0 point for the start of exploring
     * @return minimum of the function
     * @throws IOException exception when log from function computing throw exception
     */
    double[] findMinimum(LineColumnMatrix matrix, double[] x0) throws IOException {
        return new double[0];
    }

    /**
     * Function for logging method
     * @param x current point
     * @param gradient current gradient
     * @throws IOException exception throwed by {@link #out}
     */
    protected void log(double[] x, double[] gradient) throws IOException {
        if(!log) return;
        assert out != null;
        out.write(Arrays.toString(x) + ":" + Arrays.toString(gradient) + "\n");
        out.flush();
    }
}
