import java.util.ArrayList;

/**
 * Provides a template for snakes in the game of snake.
 */
public class Snake {

    private Point head;
    private ArrayList<Point> body;
    private int length;
    private Direction dir;
    private boolean onEdge = false;
    private int gridWidth;
    private int gridHeight;

    public Snake(int w, int h) {

        gridWidth = w;
        gridHeight = h;
        head = new Point(0,0);
        body = new ArrayList<Point>();
        length = 1;
        dir = Direction.DOWN;
    }

    /**
     * Changes the place of snake on grid. (Only modifies IV's)
     */
    public void updatePos() {

        //remove lsat spot and add to front
        if(!body.isEmpty()) {
            body.remove(body.size()-1);
            body.add(0, new Point(head.getX(),head.getY()));
        }

        //move head
        switch(dir) {

        case UP:
            head.setY(head.getY() - 10);
            if(head.getY() < 0){
                onEdge = true;
            }
            break;

        case DOWN:
            head.setY(head.getY() + 10);
            if(head.getY() > gridHeight) {
                onEdge = true;
            }
            break;
        case LEFT:
            head.setX(head.getX() - 10);
            if(head.getX() < 0){
                onEdge = true;
            }
            break;
        case RIGHT:
            head.setX(head.getX() + 10);
            if(head.getX() > gridWidth){
                onEdge = true;
            }
            break;

        } //switch
    } //updatePos

    /**
     * Determines whether the snake ran into itself.
     */
    public boolean collision() {

        return body.contains(head) || onEdge;

    } //collision

    /**
     * Adds a point to the body of the snake.
     */
    public void addToBody() {

        body.add(0, new Point( head.getX(),head.getY()));
        length++;
    } //addToBody

    /**
     * Returns the point loc of the head of the snake.
     */
    public Point getHead() {

        return head;
    } //getHead

    /**
     * Returns the body of the snake as an ArrayList.
     */
    public ArrayList<Point> getBody() {

        return body;
    } //getBody

    /**
     * Allows for the direction to be changed.
     */
    public void setDir(Direction d) {

        dir = d;
    } //setDir

    /**
     * Returns the length of the snake.
     */
    public int getLength() {
        return length;
    }


} //snake
