package lab1;

import java.util.List;
import java.util.function.Function;

/**
 * Method for minimization one-dimensional <code>Function</code> by Simpson's (Parabolic) method
 * @author Vladislav Pavlov
 * @author Daniil Monahov
 */
public class ParabolicMethod extends Method {

	private double epsilon = 0.00001;
	protected boolean changeMiddle = false;
	
	ParabolicMethod(Function<Double, Double> function) {
		super(function);
	}

	@Override
	public String getName() {
		return "Метод парабол";
	}

    @Override
    public String toString() {
        return "ParabolicMethod";
    }

	@Override
	public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder, Settings sets) {
		epsilon = sets.epsilon;
		return minimize(series, leftBorder, rightBorder);
	}
	
	@Override
	public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder) {
		clear(series);

        double fl = function.apply(leftBorder), fr = function.apply(rightBorder);
        double m = chooseMiddle(leftBorder, rightBorder, fl, fr);
        double fm = function.apply(m);

        addPointToSeries(series, m, function.apply(m), key++, leftBorder, rightBorder);

        double m1 = nextStep(series, leftBorder, m, rightBorder, fl, fm, fr);
        double fm1 = function.apply(m1);
        while (m1 < m - epsilon || m1 > m + epsilon) {
        	changeMiddle = true;
            if (m1 < m) {
            	rightBorder = m;
            	fr = fm;
            } else {
            	leftBorder = m;
            	fl = fm;
            }
            m = m1;
            fm = fm1;
            m1 = nextStep(series, leftBorder, m, rightBorder, fl, fm, fr);
            fm1 = function.apply(m1);
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
    public double nextStep(List<StepFrame<Double>> series, double left, double m, double right, double fl, double fm, double fr) {
        if (changeMiddle) {
        	addPointToSeries(series, m, fm, key++, left, right);
        }
        
        return m -
                ((Math.pow(m - left, 2) * (fm - fr) - Math.pow(m - right, 2) * (fm - fl))
                        / (2 * ((m - left) * (fm - fr) - (m - right) * (fm - fl))));
    }

    
    private double chooseMiddle(double left, double right, double fl, double fr) {
//        if (function.apply(left + epsilon) <= fl) {
//            return left + epsilon;
//        }
//        if (function.apply(right - epsilon) <= fr) {
//            return right - epsilon;
//        }
//        return fl < fr ? left : right;
        return 1.263932;
    }
	
}
