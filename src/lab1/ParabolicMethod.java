package lab1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Method for minimization one-dimensional <code>Function</code> by Simpson's (Parabolic) method
 * @author Vladislav Pavlov
 * @author Daniil Monahov
 */
public class ParabolicMethod extends OneDimensionMethod {

	private double epsilon = 0.00001;
	protected boolean changeMiddle = false;
	public Map<Integer, List<Double>> parabols = new HashMap<>();
	
	public ParabolicMethod(Function<Double, Double> function) {
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
		this.epsilon = sets.epsilon;
		return minimize(series, leftBorder, rightBorder);
	}
	
	@Override
	public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder) {
		clear(series);
		this.parabols.clear();

        double fl = this.function.apply(leftBorder), fr = this.function.apply(rightBorder);
        addPointToSeries(series, leftBorder, fl, this.key++, leftBorder, rightBorder);
        addPointToSeries(series, rightBorder, fr, this.key++, leftBorder, rightBorder);
        
        Point<Double> p = chooseMiddle(series, leftBorder, rightBorder, fl, fr);
        double m = p.x;
        double fm = p.y;

        double m1 = nextStep(series, leftBorder, m, rightBorder, fl, fm, fr);
        double fm1 = this.function.apply(m1);
        while (m1 < m - this.epsilon || m1 > m + this.epsilon) {
        	this.changeMiddle = true;
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
            fm1 = this.function.apply(m1);
        }
        addPointToSeries(series, m1, fm1, this.key++, leftBorder, rightBorder);
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
        if (this.changeMiddle) {
        	addPointToSeries(series, m, fm, this.key++, left, right);
        }
        
        double a, b, c;
        a = ((fl - fm) / (left - m) - (fl - fr) / (left - right)) / (m - right);
        b = (fl - fm) / (left - m) - a * (left + m);
        c = fm - a * Math.pow(m, 2) - b * m;
        
        this.parabols.put(this.key - 1, List.of(a, b, c));
        
        return m - ((Math.pow(m - left, 2) * (fm - fr) - Math.pow(m - right, 2) * (fm - fl))
                        / (2 * ((m - left) * (fm - fr) - (m - right) * (fm - fl))));
    }

    
    private Point<Double> chooseMiddle(List<StepFrame<Double>> series, double left, double right, double fl, double fr) {
    	double fme = this.function.apply(left + this.epsilon);
    	addPointToSeries(series, left + this.epsilon, fme, this.key++, left, right);
        if (fme <= fl) {
            return new Point<>(left + this.epsilon, fme);
        }
        fme = this.function.apply(right - this.epsilon);
    	addPointToSeries(series, right - this.epsilon, fme, this.key++, left, right);
        if (fme <= fr) {
            return new Point<>(right - this.epsilon, fme);
        }
        return fl < fr ? new Point<>(left, fl) : new Point<>(right, fr);
    }
	
}
