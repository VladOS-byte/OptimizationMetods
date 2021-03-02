package lab1;

import java.util.List;
import java.util.function.Function;

/**
 * Method for minimization one-dimensional <code>Function</code> by 
 * <a href="https://en.wikipedia.org/wiki/Golden-section_search">Golden-section search</a>
 * @author Vladislav Pavlov, Danila Kuriabov
 */
public class GoldenCrossSection extends Method {

	private double epsilon = 0.0001;
	private final double goldenCrossSectionConstant  = 1.61803;
	
	
	GoldenCrossSection(Function<Double, Double> function) {
		super(function);
	}

	@Override
	public String getName() {
		return "Метод золотого сечения";
	}
	
	@Override
	public double minimize(List<Point<Double>> series, double leftBorder, double rightBorder, Settings sets) {
		epsilon = sets.epsilon;
		return minimize(series, leftBorder, rightBorder);
	}
	
	@Override
	public double minimize(List<Point<Double>> series, double leftBorder, double rightBorder) {
		clear(series);
		
		addPointToSeries(series, leftBorder, function.apply(leftBorder), key++);
		addPointToSeries(series, rightBorder, function.apply(rightBorder), key++);
		
		return solve(series, leftBorder, rightBorder);
	}
	
	
	/**
	 * Recursive search <code>point</code>, where the function has minimal value.
	 * @param series - empty list of points. It would filled after completion
	 * @param leftBorder - minimal <code>x</code> of range
	 * @param rightBorder - maximal <code>x</code> of range
	 * @return <code>x</code>, where function has minimal value
	 */
	private double solve(List<Point<Double>> series, double leftBorder, double rightBorder) {
		if (Math.abs(rightBorder - leftBorder) < epsilon) {
            return (leftBorder + rightBorder) / 2;
        } else {
            double x1 = rightBorder - (rightBorder - leftBorder) / goldenCrossSectionConstant; 
            double x2 = leftBorder + (rightBorder - leftBorder) / goldenCrossSectionConstant;
            double y1 = function.apply(x1);
            double y2 = function.apply(x2);
            
            addPointToSeries(series, x1, y1, key++);
            addPointToSeries(series, x2, y2, key++);
            
            if (y1 >= y2) {
                return solve(series, x1, rightBorder);
            } else {
                return solve(series, leftBorder, x2);
            }
        }
	}

}