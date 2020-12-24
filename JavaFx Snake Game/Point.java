/**
 * Class represents a point in the game of snake.
 */
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Provides access to x coord.
     */
    public int getX() {
        return x;
    }
    /**
     * Allows for modification of x coord.
     */
    public void setX(int x) {

        this.x = x;
    }
    /**
     * Provides access to the y coord.
     */
    public int getY() {
        return y;
    }
    /**
     * Allows for modification of the y coord.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Determines if two points are equal to each other.
     */
    public boolean equals(Object object) {

        Point other;
        if(object instanceof Point) {
            other = (Point) object;
        }else{
            return false;
        }

        return (this.x == other.x && this.y == other.y);
    } //equals

} //Point
