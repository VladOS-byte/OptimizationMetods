package lab4;

import lab4.functions.Function;
import lab4.functions.SquareFunction;
import lab4.methods.Method;
import lab4.methods.marquardt.CholeskyMarquardMethod;
import lab4.methods.marquardt.GaussMarquardtMethod;
import lab4.methods.newton.ClassicNewtonMethod;
import lab4.methods.newton.DescentDirectionNewtonMethod;
import lab4.methods.newton.StepOptimizedNewtonMethod;
import lab4.methods.quasinewton.DavidFletcherPowellMethod;
import lab4.methods.quasinewton.PowellMethod;

import java.util.Arrays;

public class Main {
    private static final double EPS = 0.0000001;

    public static void main(String[] args) {
        Method classicNewtonMethod = new ClassicNewtonMethod();
        Method optimizedNewtonMethod = new StepOptimizedNewtonMethod();
        Method directionNewtonMethod = new DescentDirectionNewtonMethod();

        test("Classic Newton", classicNewtonMethod, new Function1(), new double[]{4, 1});
        test("Classic Newton", classicNewtonMethod, new Function5(), new double[]{-1.2, 1});
        test("Classic Newton", classicNewtonMethod, new Function2(), new double[]{-10, -10});
        test("Classic Newton", classicNewtonMethod, new Function3(), new double[]{-10, -10});
        test("Classic Newton", classicNewtonMethod, new Function4(), new double[]{-10, -10});
        test("Step Optimixed Newton", optimizedNewtonMethod, new Function1(), new double[]{4, 1});
        test("Step Optimixed Newton", optimizedNewtonMethod, new Function5(), new double[]{-1.2, 1});
        test("Step Optimixed Newton", optimizedNewtonMethod, new Function2(), new double[]{-10, -10});
        test("Step Optimixed Newton", optimizedNewtonMethod, new Function3(), new double[]{-10, -10});
        test("Step Optimixed Newton", optimizedNewtonMethod, new Function4(), new double[]{-10, -10});
        test("Descent Direction Newton", directionNewtonMethod, new Function1(), new double[]{4, 1});
        test("Descent Direction Newton", directionNewtonMethod, new Function5(), new double[]{-1.2, 1});
        test("Descent Direction Newton", directionNewtonMethod, new Function2(), new double[]{-10, -10});
        test("Descent Direction Newton", directionNewtonMethod, new Function3(), new double[]{-10, -10});
        test("Descent Direction Newton", directionNewtonMethod, new Function4(), new double[]{-10, -10});

        double[] bonusStartPoint = new double[100];
        Arrays.fill(bonusStartPoint, 5);

        Method choleskyMarquardMethod = new CholeskyMarquardMethod();
        Method gaussMarquardtMethod = new GaussMarquardtMethod();
        test("Cholesky Marquard", choleskyMarquardMethod, new BonusFunction(), bonusStartPoint);
        test("Gauss Marquard", gaussMarquardtMethod, new BonusFunction(), bonusStartPoint);

        Method dfp = new DavidFletcherPowellMethod(EPS);
        Method powell = new PowellMethod(EPS);
//        test("David Fletcher Powell", dfp, new BonusFunction(), bonusStartPoint);
        test("Powell", powell, new BonusFunction(), bonusStartPoint);
    }

    private static void test(String methodName, Method method, Function function, double[] start) {
        System.out.println(methodName + ": " + Arrays.toString(method.findMinimum(function, start)));
    }

    private static double pow(double x, int degree) {
        double res = x;
        for (int i = 1; i < degree; i++) {
            res *= x;
        }
        return res;
    }

    private static class Function1 implements Function {
        private static final Function function = new SquareFunction(new double[][]{{2, 1.2}, {1.2, 2}}, new double[]{0, 0}, 0);

        @Override
        public double run(double[] x) {
            return function.run(x);
        }

        @Override
        public double[] runGradient(double[] x) {
            return function.runGradient(x);
        }

        @Override
        public double[] multiply(double[] x) {
            return function.multiply(x);
        }

        @Override
        public double[][] runHessian(double[] x) {
            return function.runHessian(x);
        }
    }

    private static class Function2 implements Function {
        @Override
        public double run(double[] x) {
            return Math.pow(x[0] - x[1], 4) + pow(x[0] + x[1], 2);
        }

        @Override
        public double[] runGradient(double[] x) {
            return new double[]{
                    2 * (2 * pow(x[0] - x[1], 3) + x[0] + x[1]),
                    2 * (-2 * pow(x[0] - x[1], 3) + x[0] + x[1])
            };
        }

        @Override
        public double[] multiply(double[] x) {
            return null;
        }

        @Override
        public double[][] runHessian(double[] x) {
            return new double[][]{
                    {12 * pow(x[0] - x[1], 2) + 2, -12 * pow(x[0] - x[1], 2) + 2},
                    {-12 * pow(x[0] - x[1], 2) + 2, 12 * pow(x[0] - x[1], 2) + 2}
            };
        }
    }

    private static class Function3 implements Function {
        @Override
        public double run(double[] x) {
            return Math.pow((2 * x[0] + x[1] + 2), 4) + pow(x[0] - 6 * x[1], 2);
        }

        @Override
        public double[] runGradient(double[] x) {
            return new double[]{
                    2 * (x[0] - 6 * x[1] + 4 * pow(2 + 2 * x[0] + x[1], 3)),
                    4 * (-3 * (x[0] - 6 * x[1]) + pow(2 + 2 * x[0] + x[1], 3))
            };
        }

        @Override
        public double[] multiply(double[] x) {
            return null;
        }

        @Override
        public double[][] runHessian(double[] x) {
            return new double[][]{
                    {48 * pow(2 * x[0] + x[1] + 2, 2) + 2, 24 * pow(2 * x[0] + x[1] + 2, 2) - 12},
                    {24 * pow(2 * x[0] + x[1] + 2, 2) - 12, 12 * pow(2 * x[0] + x[1] + 2, 2) + 72}
            };
        }
    }

    private static class Function4 implements Function {
        @Override
        public double run(double[] x) {
            return 3 * pow(x[0] - 1, 2) + 5 * x[1] * x[1] + 6 * x[0] * x[1] + 2;
        }

        @Override
        public double[] runGradient(double[] x) {
            return new double[]{
                    6 * (x[0] + x[1] - 1),
                    6 * x[0] + 10 * x[1]
            };
        }

        @Override
        public double[] multiply(double[] x) {
            return new double[0];
        }

        @Override
        public double[][] runHessian(double[] x) {
            return new double[][]{{6, 6}, {6, 10}};
        }
    }

    private static class Function5 implements Function {
        @Override
        public double run(double[] x) {
            return 100 * pow(x[1] - pow(x[0], 2), 2) + pow(1 - x[0], 2);
        }

        @Override
        public double[] runGradient(double[] x) {
            double[] ans = new double[2];
            ans[0] = findGrad1(x);
            ans[1] = findGrad2(x);
            return ans;
        }

        private double findGrad2(double[] x) {
            return 200 * (x[1] - pow(x[0], 2));
        }

        private double findGrad1(double[] x) {
            return 2 * (200 * pow(x[0], 3) - 200 * x[0] * x[1] + x[0] - 1);
        }

        @Override
        public double[] multiply(double[] x) {
            return null;
        }

        @Override
        public double[][] runHessian(double[] x) {
            double[][] H = new double[2][2];
            H[0][0] = findH11(x);
            H[0][1] = H[1][0] = findH12(x);
            H[1][1] = findH22(x);
            return H;
        }

        private double findH22(double[] x) {
            return 200;
        }

        private double findH12(double[] x) {
            return -400 * x[0];
        }

        private double findH11(double[] x) {
            return 1200 * pow(x[0], 2) - 400 * x[1] + 2;
        }
    }

    private static class BonusFunction implements Function {
        @Override
        public double run(double[] x) {
            double ans = 0;
            for (int i = 0; i < 99; i++) {
                ans += 100 * pow(x[i + 1] - pow(x[i], 2), 2) + pow(1 - x[i], 2);
            }
            return ans;
        }

        @Override
        public double[] runGradient(double[] x) {
            double[] ans = new double[100];
            ans[0] = -400 * (x[1] - pow(x[0], 2)) * x[0] - 2 * (1 - x[0]);
            for (int i = 1; i < 98; i++) {
                ans[i] = -400 * (x[i + 1] - pow(x[i], 2)) * x[i] - 2 * (1 - x[i]) + 200 * (x[i] - pow(x[i - 1], 2));
            }
            ans[99] = 200 * (x[99] - pow(x[98], 2));
            return ans;
        }

        @Override
        public double[] multiply(double[] x) {
            return null;
        }

        @Override
        public double[][] runHessian(double[] x) {
            double[][] H = new double[100][100];
            H[0][0] = 1200 * x[0] * x[0] - 400 * x[1] + 2;
            H[1][0] = H[0][1] = -400 * x[0];
            for (int i = 1; i < 99; i++) {
                H[i][i - 1] = findIJLess(x, i);
                H[i][i] = findII(x, i);
                H[i][i + 1] = findIJMore(x, i);
            }
            H[98][99] = H[99][98] = -400 * x[98];
            H[99][99] = 200;
            return H;
        }

        private double findIJMore(double[] x, int ind) {
            return -400 * x[ind];
        }

        private double findII(double[] x, int ind) {
            return 202 + 1200 * x[ind] * x[ind] - 400 * x[ind + 1];
        }

        private double findIJLess(double[] x, int ind) {
            return -400 * x[ind - 1];
        }
    }
}
