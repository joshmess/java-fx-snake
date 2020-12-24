/**
 * Class provides a template for food objects in Snake application.
 */
public class Food {

    private Point coordinates;

    public Food(int x, int y) {
        coordinates = new Point(x,y);
    }

    /**
     * Provides access to the coordinates of a Food object.
     */
    public Point getFoodLoc() {
        return coordinates;
    }



