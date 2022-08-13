package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.NarrowPaddleDefine;
import src.gameobjects.WidePaddleDefine;

import java.util.Random;

/**
 * this class represents the strategy to wide and narrow the main paddle
 */
public class WideNarrowPaddleStrategy extends RemoveBrickStrategyDecorator{

    private final CollisionStrategy toBeDecorated;
    private Vector2 topLeftCorner;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private final Random rand = new Random();


    /**
     * constructor to build the strategy object
     * @param windowDimensions the window dimensions
     * @param gameObjectCollection the game objects collection
     * @param imageReader the object to read the path to the image
     */
    public WideNarrowPaddleStrategy(CollisionStrategy toBeDecorated,
                                    Vector2 windowDimensions,
                                    GameObjectCollection gameObjectCollection, ImageReader imageReader) {
        super(toBeDecorated);
        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.toBeDecorated = toBeDecorated;
    }

    /**
     * when there is a collision, makes the strategy happen
     * @param thisObj the object which got collided
     * @param otherObj the object which collides
     * @param counter the bricks counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        this.toBeDecorated.onCollision(thisObj, otherObj, counter);
        this.topLeftCorner = thisObj.getTopLeftCorner();
        if(rand.nextBoolean())
            createStatusDefineWide();
        else
            createStatusDefineNarrow();
    }


    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

    /**
     * this method create the icon and the object WidePaddleDefine
     * which makes the paddle wider
     */
    private void createStatusDefineWide(){
        Renderable statusDefineWideImage = imageReader.readImage("assets/buffWiden.png", false);
        GameObject statusDefineWide = new WidePaddleDefine(topLeftCorner, new Vector2(30, 30), statusDefineWideImage,
                windowDimensions, gameObjectCollection);
        statusDefineWide.setVelocity(Vector2.DOWN.mult(300));
        gameObjectCollection.addGameObject(statusDefineWide);
    }

    /**
     * this method create the icon and the object NarrowPaddleDefine
     * which makes the paddle narrower
     */
    private void createStatusDefineNarrow(){
        Renderable statusDefineNarrowImage = imageReader.readImage("assets/buffNarrow.png", false);
        GameObject statusDefineNarrow = new NarrowPaddleDefine(topLeftCorner, new Vector2(30, 30), statusDefineNarrowImage,
                windowDimensions, gameObjectCollection);
        statusDefineNarrow.setVelocity(Vector2.DOWN.mult(300));
        gameObjectCollection.addGameObject(statusDefineNarrow);
    }


}
