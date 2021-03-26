package lab1;

import java.util.List;
import java.util.function.Function;

/**
 * Method for minimization one-dimensional <code>Function</code> by 
 * <a href="https://en.wikipedia.org/wiki/Fibonacci_search_technique">Fibonacci search technique</a>
 * @author Vladislav Pavlov, Danila Kuriabov
 */
public class FibonacciMethod extends Method {

    private int iterationDegree = 0;
	private double[] fibonacci;


	FibonacciMethod(Function<Double, Double> function) {
		super(function);
		iterationDegree = 50;
	}

	@Override
	public String getName() {
		return "Метод Фиббоначи";
	}

    @Override
    public String toString() {
        return "FibonacciMethod";
    }
	
	/**
	 * Fill array by fibonacci numbers
	 * @param n - length of fibonacci numbers <code>array</code> 
	 */
	private void calcFibonacci(int n) {
        fibonacci = new double[n + 1];
        fibonacci[1] = 1;
        fibonacci[2] = 1;

        for (int i = 3; i <= n; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
    }

	@Override
	public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder) {
		clear(series);
		
		for (int i = 2;;i++) {
			calcFibonacci(i);
			if (fibonacci[i] > (rightBorder - leftBorder) / sets.epsilon) {
				iterationDegree = i;
				break;
			}
		}
		
		return minimize(series, leftBorder, rightBorder);
	}

	@Override
	public double minimize(List<Point<Double>> series, double leftBorder, double rightBorder) {
		int iterationCounter = iterationDegree;
		

        double x1 = leftBorder + (rightBorder - leftBorder) * fibonacci[iterationCounter - 2] / fibonacci[iterationCounter];
        double x2 = leftBorder + (rightBorder - leftBorder) * fibonacci[iterationCounter - 1] / fibonacci[iterationCounter];
        double y1 = function.apply(x1);
        double y2 = function.apply(x2);
        addPointToSeries(series, x1, y1, key, leftBorder, rightBorder);
        addPointToSeries(series, x2, y2, key++, leftBorder, rightBorder);

        while (iterationCounter != 1) {
            iterationCounter--;
            if (y1 >= y2) {
                leftBorder = x1;
                x1 = x2;
                x2 = rightBorder - (x1 - leftBorder);
                y1 = y2;
                y2 = function.apply(x2);
                addPointToSeries(series, x2, y2, key++, leftBorder, rightBorder);
            } else {
                rightBorder = x2;
                x2 = x1;
                x1 = leftBorder + (rightBorder - x2);
                y2 = y1;
                y1 = function.apply(x1);
                addPointToSeries(series, x1, y1, key++, leftBorder, rightBorder);
            }
        }
		
        return (leftBorder + rightBorder) / 2;
	}
}
