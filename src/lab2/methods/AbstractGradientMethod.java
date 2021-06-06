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
    protected final FileWriter out;

    public AbstractGradientMethod(double epsilon, boolean log, String fileName) throws IOException {
        this.epsilon = epsilon;
        this.log = log;
        this.out = new FileWriter(fileName);
    }

    public AbstractGradientMethod(double epsilon) {
        this.epsilon = epsilon;
        this.log = false;
        this.out = null;
    }

    double[] minimize(SquareFunction function, double[] x0) throws IOException {
        return new double[0];
    }

    protected void log(double[] x, double[] gradient) throws IOException {
        if(!log) return;
        assert out != null;
        out.write(Arrays.toString(x) + ":" + Arrays.toString(gradient) + "\n");
        out.flush();
    }
}
