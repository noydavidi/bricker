package src;

import src.brick_strategies.BrickStrategyFactory;
import src.gameobjects.*;
import src.brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;


import java.util.Random;


public class BrickerGameManager extends  GameManager{

    public static final int BORDER_WIDTH = 20;

    private final float BALL_SPEED = 300;
    private final int amountOfBricksInRow = 8;
    private final int amountOfRows = 5;
    private final int amountOfLives = 4;
    private final Vector2 windowDimensions;
    private GameObject ball;
    private WindowController windowController;
    private Counter counterBricks = new Counter(0);
    private Counter livesCounter = new Counter(amountOfLives);
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private SoundReader soundReader;
    private GraphicLifeCounter graphicLives;
    private NumericLifeCounter numricLives;


    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;

        counterBricks.increment();

        createBackground();
        createWalls();
        createBall();
        createUserPaddle();
        createLives();
        createBricks();
    }

    /**
     * this method updates the game every deltaTime seconds.
     * it checks if the player won or lose and react on the relevant ways
     * @param deltaTime the time the game should update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if(livesCounter.value() == 0)
            prompt = "You lose! Play again?";

        if(counterBricks.value() == 0)
            prompt = "You won! Play again?";

        if(ballHeight > windowDimensions.y()){
            livesCounter.decrement();
            setCamera(null);
            gameObjects().removeGameObject(ball);
            createBall();
        }

        if(!(prompt.equals(""))){
            if(windowController.openYesNoDialog(prompt)){
                livesCounter = new Counter(amountOfLives);
                windowController.resetGame();
            }
            else
                windowController.closeWindow();
        }

    }

    /**
     * this method creates the lives icon and the lives text
     */
    private void createLives(){
        Vector2 dimensionsForNumeric = new Vector2(17,17);
        Vector2 dimensionsForGraphic = new Vector2(20,20);

        Renderable liveImage = imageReader.readImage("assets/heart.png", true);

        //create graphic counter
        this.graphicLives = new GraphicLifeCounter(new Vector2(windowDimensions.x(), windowDimensions.y()),
                dimensionsForGraphic, livesCounter, liveImage, gameObjects(), amountOfLives);
        gameObjects().addGameObject(graphicLives, Layer.UI);
        graphicLives.getCenter();

        //creates numeric counter
        this.numricLives = new NumericLifeCounter(livesCounter, new Vector2(0 , 0),
                dimensionsForNumeric, gameObjects());
        gameObjects().addGameObject(this.numricLives, Layer.STATIC_OBJECTS);
        numricLives.getCenter();

    }

    /**
     * this method creates the bricks for the game
     * organizing it by rows and columns
     */
    private void createBricks(){

        float length = (2*windowDimensions.x()/3) / amountOfBricksInRow;
        float width = (1*windowDimensions.y()/3)/ amountOfRows;

        float spaceBetweenBricks = 1*windowDimensions.x()/5/amountOfBricksInRow;
        float spaceBetweenRows = ((3*windowDimensions.y()) / 8) - (amountOfRows * width);

        for(int row = 0; row < amountOfRows; row++){
            for(int b = 0; b < amountOfBricksInRow; b++){
                createSingleBrick(new Vector2(b*length + (b+1)*spaceBetweenBricks, (row+1)*spaceBetweenRows + width*row),
                        new Vector2(length, width));
            }
        }
        counterBricks.decrement();
    }

    /**
     * this method create one single brick
     * @param topLeftCorner the place for the brick on the board
     * @param dimensions the size of the brick
     */
    private void createSingleBrick(Vector2 topLeftCorner, Vector2 dimensions){
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects(), this,
                imageReader, soundReader, inputListener
                ,windowController, windowDimensions);
        CollisionStrategy collisionStrategy = brickStrategyFactory.getStrategy();

        counterBricks.increment();
        GameObject brick = new Brick(topLeftCorner, dimensions, brickImage, collisionStrategy, counterBricks);
        gameObjects().addGameObject(brick);

    }

    /**
     * the method creates the main object, the ball of the game
     */
    private void createBall(){
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(300));

        ball.setCenter(windowDimensions.mult((float) 0.5));
        gameObjects().addGameObject(ball);

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX *= -1;
        }
        if(rand.nextBoolean()){
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * this method creates the walls of the game
     * so the ball can't escape the screen
     */
    private void createWalls(){
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()),null);
        gameObjects().addGameObject(leftWall);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(), 0),new Vector2(BORDER_WIDTH, windowDimensions.y()), null);
        gameObjects().addGameObject(rightWall);
        GameObject topWall = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDER_WIDTH),null);
        gameObjects().addGameObject(topWall);
    }

    /**
     * this method creates the paddle to play with
     */
    private void createUserPaddle(){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(200, 20), paddleImage, inputListener, windowDimensions, 20);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(userPaddle);
    }

    /**
     * the method creates the background
     */
    private void createBackground(){
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    public static void main(String[] args) {

        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
}
