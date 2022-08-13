package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

/**
 * this class represents the additional paddle created when a brick brake
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{

    private final ImageReader imageReader;
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
    private final CollisionStrategy toBeDecorated;
    private Counter collisionsCounter;


    /**
     * Construct a AddPaddleStrategy object extends from RemoveBrickStrategyDecorator
     * @param toBeDecorated the additional strategy to use
     * @param imageReader the object to read the path to the image
     * @param inputListener the object we use for noticing if a key was pressed
     * @param windowDimensions The width and height of the game window
     *
     **/
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions){
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.windowDimensions = windowDimensions;
        this.inputListener = inputListener;
        this.collisionsCounter = new Counter(3);
        this.toBeDecorated = toBeDecorated;

    }

    /**
     * this method creates another paddle in case there is no one
     * @param thisObj the object brick
     * @param otherObj what the brick collided with
     * @param counter the brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        toBeDecorated.onCollision(thisObj, otherObj, counter);
//        new RemoveBrickStrategy(getGameObjectCollection()).onCollision(thisObj, otherObj, counter);
        if(!MockPaddle.isInstantiated)
            createAdditionalPaddle();
    }


    /**
     * this method creates the additional object paddle
     */
    private void createAdditionalPaddle(){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject additionalPaddle = new MockPaddle(new Vector2(0, windowDimensions.y() / 2),
                new Vector2(200, 20), paddleImage, inputListener, windowDimensions, getGameObjectCollection(), 21, 3);
        additionalPaddle.setCenter(new Vector2(windowDimensions.x() / 2, 3*windowDimensions.y()/5));
        getGameObjectCollection().addGameObject(additionalPaddle);
    }
}
