package lab1;

import java.util.List;
import java.util.function.Function;

/**
 * Abstract method for minimization one-dimensional <code>Function</code>
 * @author Vladislav Pavlov
 *
 */
public abstract class OneDimensionMethod {
    /**
     * <code>Function</code> for minimize in some range
     */
    protected final Function<Double, Double> function;


    /**
     * <code>key</code> for points
     */
    protected int key = 0;


    OneDimensionMethod(Function<Double, Double> function) {
        this.function = function;
    }


    /**
     * @return name of method in Russian locale
     */
    public abstract String getName();


    /**
     * Search <code>point</code>, where the function has minimal value.
     * @param series - empty list of points. It would filled after completion
     * @param leftBorder - minimal <code>x</code> of range
     * @param rightBorder - maximal <code>x</code> of range
     * @param sets - <code>Settings</code> for method
     * @return <code>x</code>, where function has minimal value
     */
    public double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder, Settings sets) {
        return minimize(series, leftBorder, rightBorder);
    }


    /**
     * Search <code>point</code>, where the function has minimal value.
     * @param series - empty list of points. It would filled after completion
     * @param leftBorder - minimal <code>x</code> of range
     * @param rightBorder - maximal <code>x</code> of range
     * @return <code>x</code>, where function has minimal value
     * @see #minimize
     */
    public abstract double minimize(List<StepFrame<Double>> series, double leftBorder, double rightBorder);


    /**
     * Add point(<code>x</code>, <code>y</code>) to <code>series</code>.
     * @param series - <code>series</code> of points
     * @param x - <code>x</code> argument of point to add
     * @param y - <code>y</code> argument of point to add
     * @param key - <code>key</code> of point
     */
    public void addPointToSeries(List<StepFrame<Double>> series, double x, double y, int key, double leftBorder, double rightBorder) {
        if (series == null) return;
        if (Double.compare(x, Double.NaN) == 0 || Double.isInfinite(x) ||
                Double.compare(y, Double.NaN) == 0 || Double.isInfinite(y)) {
            return;
        }
        series.add(new StepFrame<>(leftBorder, new Point<>(x, y, key), rightBorder));
    }


    /**
     * Clear <code>series</code> and set <code>key</code> = 0;
     * @param series - <code>series</code> of points
     */
    public void clear(List<StepFrame<Double>> series) {
        series.clear();
        key = 0;
    }

}
