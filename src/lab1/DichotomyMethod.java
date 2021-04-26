package lab1;

import java.util.List;
import java.util.function.Function;

/**
 * Method for minimization one-dimensional <code>Function</code> by 
 * <a href="https://ru.wikipedia.org/wiki/Дихотомия">Dichotomy search</a>
 * @author Vladislav Pavlov
 */
public class DichotomyMethod extends OneDimensionMethod {

	private double epsilon = 0.000_1, delta = 0.000_01, sigma = 0.000_000_001;

	public DichotomyMethod(Function<Double, Double> function) {
		super(function);
	}

	@Override
	public String getName() {
		return "Метод дихотмии";
	}

	@Override
	public String toString() {
		return "DichotomyMethod";
	}
	
	@Override
	public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder, Settings sets) {
		epsilon = Math.max(sets.epsilon, 4 * sets.delta);
		delta = sets.delta;
		sigma = sets.sigma;
		return minimize(series, leftBorder, rightBorder);
	}

	@Override
	public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder) {
		clear(series);
		
		while ((rightBorder - leftBorder) / 2 > epsilon) {
			double x1 = (leftBorder + rightBorder) / 2 - delta, x2 = (leftBorder + rightBorder) / 2 + delta;
			double f_x1 = function.apply(x1), f_x2 = function.apply(x2);
			addPointToSeries(series, x1, f_x1, key++, leftBorder, rightBorder);
			addPointToSeries(series, x2, f_x2, key++, leftBorder, rightBorder);
			if (f_x1 - f_x2 <= sigma) {
				rightBorder = x2;
			} else {
				leftBorder = x1;
			}
		}
		
		return (leftBorder + rightBorder) / 2;
	}

}
