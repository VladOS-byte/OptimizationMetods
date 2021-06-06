package lab2;

import lab2.functions.SquareFunction;
import lab2.methods.ConjugateGradientMethod;
import lab2.methods.FastGradientDescentMethod;
import lab2.methods.GradientDescentMethod;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        final List<SquareFunction> functions = new ArrayList<>();
        functions.add(new SquareFunction(new double[][]{{2, 2}, {2, 2}}, new double[]{-1, -1}, 0.0));
        functions.add(new SquareFunction(new double[][]{{40, 2}, {2, 20}}, new double[]{-1, -1}, 0.0));
        functions.add(new SquareFunction(new double[][]{{400, 2}, {2, 100}}, new double[]{-1, -1}, 0.0));


        test(functions);
    }


    /**
     * Test optimization methods on several provided functions
     * @param functions {@link List} of {@link SquareFunction} to test on
     * @throws IOException when it's impossible to create logging file
     */
    public static void test(List<SquareFunction> functions) throws IOException {
        for (int i = 0; i < functions.size(); i++) {
            System.out.println("======Function:" + (i + 1) + "\n");
            test(functions.get(i));
        }
    }

    /**
     * Test optimization methods on provided function
     * @param function {@link SquareFunction} to test on
     * @throws IOException when it's impossible to create logging file
     */
    public static void test(SquareFunction function) throws IOException {
        double[] x0 = new double[]{0, 0};
        double epsilon = 0.00001;

        GradientDescentMethod m1 = new GradientDescentMethod(2, epsilon, true, "grad.txt");
        FastGradientDescentMethod m2 = new FastGradientDescentMethod(epsilon, true, "fast.txt");
        ConjugateGradientMethod m3 = new ConjugateGradientMethod(epsilon, true, "conj.txt");

        double[] x;

        System.out.println("==Gradient Descent method for : " + Arrays.toString(x0) + " \n");
        x = m1.minimize(function, x0);
        System.out.println("min: " + Arrays.toString(x) + "\n" + "\n");

        System.out.println("==Fast Gradient Descent method for : " + Arrays.toString(x0) + " \n");
        x = m2.minimize(function, x0);
        System.out.println("min: " + Arrays.toString(x) + "\n" + "\n");

        System.out.println("==Conjugate Gradient method for : " + Arrays.toString(x0) + " \n");
        x = m3.minimize(function, x0);
        System.out.println("min: " + Arrays.toString(x) + "\n" + "\n");
    }
}