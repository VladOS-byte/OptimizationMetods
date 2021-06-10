package lab4.methods.gradient;

import lab3.matrix.LineColumnMatrix;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public abstract class AbstractGradientMethod {
    protected final double epsilon;
    protected final boolean log;
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

    double[] findMinimum(LineColumnMatrix matrix, double[] x0) throws IOException {
        return new double[0];
    }

    protected void log(double[] x, double[] gradient) throws IOException {
        if(!log) return;
        assert out != null;
        out.write(Arrays.toString(x) + ":" + Arrays.toString(gradient) + "\n");
        out.flush();
    }
}
