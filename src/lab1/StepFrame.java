package lab1;

public class StepFrame<E extends Number> {
    public final Point<E> x;
    public final E leftBorder, rightBorder;

    public StepFrame(E leftBorder, Point<E> x, E rightBorder) {
        this.leftBorder = leftBorder;
        this.x = x;
        this.rightBorder = rightBorder;
    }
}
