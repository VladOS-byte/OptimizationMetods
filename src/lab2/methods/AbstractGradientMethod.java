package lab2.methods;

import lab2.functions.SquareFunction;

import java.io.*;
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
    protected final BufferedWriter out;

    /**
     * Full constructor
     * @param epsilon {@link #epsilon}
     * @param log {@link #log}
     * @param fileName output file for {@link #out}
     * @throws FileNotFoundException
     */
    public AbstractGradientMethod(double epsilon, boolean log, String fileName) throws FileNotFoundException {
        this.epsilon = epsilon;
        this.log = log;
        this.out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
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
     * @param function explores function
     * @param x0 point for the start of exploring
     * @return minimum of the function
     * @throws IOException exception when log from function computing throw exception
     */
    double[] findMinimum(SquareFunction function, double[] x0) throws IOException {
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
