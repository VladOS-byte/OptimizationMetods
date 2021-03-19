package lab1;

import java.util.List;
import java.util.function.Function;

/**
 * Method for minimization one-dimensional <code>Function</code> 
 * by <a href="https://en.wikipedia.org/wiki/Brent%27s_method">Brent's combine method</a>
 * @author Vladislav Pavlov, Daniil Monahov
 */
public class CombineBrentMethod extends Method {
	
	private double epsilon = 0.0001;
	private final double K = (3 - Math.sqrt(5)) / 2;
	private ParabolicSection parabolic;

	CombineBrentMethod(Function<Double, Double> function) {
		super(function);
        parabolic = new ParabolicSection(function);
	}

	@Override
	public String getName() {
		return "Комбинированный метод Брента";
	}
	
	
	@Override
	public double minimize(List<Point<Double>> series, double leftBorder, double rightBorder, Settings sets) {
		epsilon = sets.epsilon;
		return minimize(series, leftBorder, rightBorder);
	}
	

	@Override
	public double minimize(List<Point<Double>> series, double leftBorder, double rightBorder) {
		clear(series);
		parabolic.clear(series);
		
		parabolic.addPointToSeries(series, leftBorder, function.apply(leftBorder), parabolic.key);
		parabolic.addPointToSeries(series, rightBorder, function.apply(rightBorder), parabolic.key++);
		
		double x = (leftBorder + rightBorder / 2), w = x, v = x, u = x;
		double fx = function.apply(x), fw = fx, fv = fx;
		double d = rightBorder - leftBorder, e = d;
		
		while (true) {
		    double g = e;
		    e = d;
		    double prevU = u;
		    u = parabolic.nextStep(series, v, x, w);
		    if (u > leftBorder + epsilon && u < rightBorder - epsilon && Math.abs(u - x) > g / 2) {
		        d = Math.abs(u - x);
		    } else {
		        // goldenCross.nextStep() or something like that
		        if (x < (rightBorder - leftBorder) / 2) {
		            u = x + K * (rightBorder - x);
		            d = rightBorder - x;
		        } else {
		            u = x - K * (x - leftBorder);
		            d = x - leftBorder;
		        }
		    }
		    if (u > prevU - epsilon && u < prevU + epsilon) break;
		
		    double fu = function.apply(u);
		    parabolic.addPointToSeries(series, u, fu, parabolic.key++);
		    if (fu < fx) {
		        if (u > x) {
		            leftBorder = x;
		        } else {
		            rightBorder = x;
		        }
		        v = w;
		        w = x;
		        x = u;
		        fv = fw;
		        fw = fx;
		        fx = fu;
		    } else {
		        if (u > x) {
		            rightBorder = u;
		        } else {
		            leftBorder = u;
		        }
		        if (fu <= fw || w > x - epsilon && w < x + epsilon) {
		            v = w;
		            w = u;
		            fv = fw;
		            fw = fu;
		        } else if (fu <= fv || v > x - epsilon && v < x + epsilon || v > w - epsilon && v < w + epsilon){
		            v = u;
		            fv = fu;
		        }
		    }
		}
		return u;
	}

}
