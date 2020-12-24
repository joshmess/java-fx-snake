import java.util.Random;

/**
 * Provides a template for grids in Snake application.
 */
public class Grid {

    private int width;
    private int height;
    private Food food;
    private Random rGen = new Random();

    public Grid(int w, int h){

        width = w;
        height = h;
        food = new Food(width/2,height/2);
    }

    /**
     * Places food at random loc
     */
    public void spawnFood() {

        int x = 0, y = 0;
        //make sure not same loc
        do{

            x = rGen.nextInt(width);
            y = rGen.nextInt(height);

            //make sure the food can be hit by the snake (moves increments of ten)
            x = Math.round(x / 10) *10;
            y = Math.round(y / 10) *10;

        }while(x == food.getFoodLoc().getX() && y == food.getFoodLoc().getY());

        food = new Food(x,y);
    }

    /**
     * Determines whether snake is on food
     */
    public boolean hitFood(Snake s) {

        if(s.getHead().equals(food.getFoodLoc())){
            return true;
        }
        return false;
    }

    /**
     * Returns the food on the grid.
     */
    public Food getFood() {
        return food;
    }

} //Grid
