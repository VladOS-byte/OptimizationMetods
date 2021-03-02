package lab1;

import java.util.List;
import java.util.function.Function;

/**
 * Method for minimization one-dimensional <code>Function</code> by Simpson's (Parabolic) method
 * @author Vladislav Pavlov
 * @author Daniel Manahov
 */
public class ParabolicSection extends Method {

	private double epsilon = 0.00001;
	protected boolean changeMiddle = false;
	
	ParabolicSection(Function<Double, Double> function) {
		super(function);
	}

	@Override
	public String getName() {
		return "Метод парабол";
	}
	
	@Override
	public double minimize(List<Point<Double>> series, double leftBorder, double rightBorder, Settings sets) {
		epsilon = sets.epsilon;
		return minimize(series, leftBorder, rightBorder);
	}
	
	@Override
	public double minimize(List<Point<Double>> series, double leftBorder, double rightBorder) {
		clear(series);
		
		double m = chooseMiddle(series, leftBorder, rightBorder);
		addPointToSeries(series, rightBorder, function.apply(rightBorder), key);
		
        double m1 = nextStep(series, leftBorder, m, rightBorder);
        while (m1 < m - epsilon || m1 > m + epsilon) {
        	changeMiddle = true;
            if (m1 < m) {
            	rightBorder = m;
            } else {
            	leftBorder = m;
            }
            m = m1;
            m1 = nextStep(series, leftBorder, m, rightBorder);
        }
        return m1;
	}

	
    /**
     * @param series - <code>list</code> of points
     * @param left - minimal <code>x</code> of range
     * @param m - last medium <code>x</code>
     * @param right - maximal <code>x</code> of range
     * @return next medium <code>x</code>
     */
    protected double nextStep(List<Point<Double>> series, double left, double m, double right) {
        double f1 = function.apply(left), f2 = function.apply(m), f3 = function.apply(right);
        
        if (changeMiddle) {
        	addPointToSeries(series, m, f2, key++);
        }
        
        return m -
                ((Math.pow(m - left, 2) * (f2 - f3) - Math.pow(m - right, 2) * (f2 - f1))
                        / (2 * ((m - left) * (f2 - f3) - (m - right) * (f2 - f1))));
    }

    
    private double chooseMiddle(List<Point<Double>> series, double left, double right) {
        double f1 = function.apply(left), f3 = function.apply(right);
        double step = (right - left) / 100;
        double m;
        for (m = left; m < right; m += step) {
            double f2 = function.apply(m);
            if (f2 < f1 && f2 < f3) {
            	key++;
                return m;
            }
            addPointToSeries(series, m, f2, key);
        }
        key++;
        return f1 < f3 ? left : right;
    }
	
}
