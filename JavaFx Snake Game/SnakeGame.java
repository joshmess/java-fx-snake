import javafx.application.Platform;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javax.swing.JOptionPane;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Provides the main gameplay structure for Snake and makes GUI.
 */
public class SnakeGame extends Application {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 500;
    private GraphicsContext gc;
    private Grid grid;
    private Snake snake;
    private AnimationTimer animationTimer;
    private Button start;
    private Text text;
    private Text hiLabel;
    private Timeline timeline;
    private boolean inProgress = false;
    private boolean gameOver = false;
    private int highScore = 0;
    private Button exit;
    private Button snakeButton;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public SnakeGame(int hi, Button sb) {
        highScore = hi;
        snakeButton = sb;
    }
    /**
     * Creates GUI.
     */
    public void start(Stage stage) {

        stage.setTitle("CS1302 Snake");
        Canvas canvas = new Canvas(WIDTH,HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        grid = new Grid(WIDTH,HEIGHT);
        snake = new Snake(WIDTH,HEIGHT);

        drawGrid();

        exit = new Button("Exit");
        exit.setOnAction(e -> {

                stage.close();
            });
        text = new Text("Level: 1");
        hiLabel = new Text("     High Score: " + highScore + "   ");
        text.setFill(Color.WHITE);
        hiLabel.setFill(Color.WHITE);
        start = new Button("Start!");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(start,text,hiLabel,exit);

        start.setOnAction(e -> {

                inProgress = true;
                gameOver = false;

                //start over after game ended
                if(timeline == null) {

                    grid = new Grid(WIDTH,HEIGHT);
                    snake = new Snake(WIDTH, HEIGHT);
                    text.setText("Level: 1");
                    timeline = createTimeline();
                    timeline.play();
                    animationTimer.start();
                }


            });

        root.getChildren().add(hBox);

        //initialize arrow keys
        scene.setOnKeyPressed(e -> {

                switch(e.getCode()){

                case UP:
                    snake.setDir(Direction.UP);
                    break;
                case DOWN:
                    snake.setDir(Direction.DOWN);
                    break;
                case RIGHT:
                    snake.setDir(Direction.RIGHT);
                    break;
                case LEFT:
                    snake.setDir(Direction.LEFT);
                    break;
                default:
                    break;

                } //switch
            });

        stage.setScene(scene);

        stage.sizeToScene();
        stage.show();

        //uses AnimationTimer class to make dynamic changes to GUI
        animationTimer = new AnimationTimer() {

                //implement abstract method
                public void handle(long now) {

                    if(inProgress){
                        drawGrid();
                        drawSnake();
                        drawFood();
                    }else if(gameOver) {
                        animationTimer.stop();

                    }
                }//handle
            };

        timeline = createTimeline();

        animationTimer.start();
        timeline.play();
    } //start

    /**
     * Helper method to make timeline that updates snake and food.
     */
    private Timeline createTimeline() {

        EventHandler<ActionEvent> handler = event -> {

            if(inProgress){
                snake.updatePos();

                if(snake.collision()){
                    gameOver();
                }
                if(grid.hitFood(snake)) {
                    snake.addToBody();
                    if(snake.getLength() > highScore) {
                        highScore = snake.getLength();
                        snakeButton.setText("Snake (High Score: " + highScore + ")");
                        hiLabel.setText("     High Score: " + highScore + "   ");
                    }

                    text.setText("Level: " + snake.getLength());
                    grid.spawnFood();
                }
            }
        };

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0/8.0),handler);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);

        return timeline;
    } //createTimeline

    /**
     * Helper method to alert player the game is over.
     */
    private void gameOver() {

        gameOver = true;
        inProgress = false;
        timeline.stop();
        timeline = null;
        JOptionPane.showMessageDialog(null, "Game Over!");
    }

    /**
     * Helper method uses GraphicsContext to draw grid.
     */
    private void drawGrid() {

        //makes a big rectangle
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0,WIDTH, HEIGHT);

    } //drawGrid


    /**
     * Helper used GraphicsContext to fill certain areas with color for snake.
     */
    private void drawSnake() {

        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(snake.getHead().getX(), snake.getHead().getY(), 10,10);
        for (Point sect : snake.getBody()) {
            gc.fillRect(sect.getX(), sect.getY(), 10,10);
        }
    } //drawSnake

    /**
     * Helper uses GraphicsContext to paint food onto grid.
     */
    private void drawFood() {

        gc.setFill(Color.RED);
        int x = grid.getFood().getFoodLoc().getX();
        int y = grid.getFood().getFoodLoc().getY();
        gc.fillRect(x,y,10,10);
    } //drawFood
    /**
     * Provides access to the highscore.
     */
    public int highScore(){
        return highScore;
    }

} //snakeGame
