package lab1;

public class Point<E extends Number> {
    public final E x, y;
    public final int key;


    Point(E x, E y) {
        this.x = x;
        this.y = y;
        this.key = 0;
    }


    Point(E x, E y, int key) {
        this.x = x;
        this.y = y;
        this.key = key;
    }

}
