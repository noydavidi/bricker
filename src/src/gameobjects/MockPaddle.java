package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * this class represents the additional paddle created by
 * a certain hit brick.
 */
public class MockPaddle extends Paddle{

    public static boolean isInstantiated;

    private final GameObjectCollection gameObjectCollection;
    private int numCollisionsToDisappear;

    /**
     * constructor for the paddle, making the object
     *
     * @param topLeftCorner       the place for the object in the begining of the game
     * @param dimensions          The size of the paddle
     * @param renderable          The renderable representing the object. Can be null, in which case the GameObject will not be rendered.
     * @param inputListener       The object we use for noticing if a key was pressed
     * @param windowDimensions    The width and height of the game window
     * @param gameObjectCollection The game objects collection
     */
    public MockPaddle(Vector2 topLeftCorner,
                      Vector2 dimensions,
                      Renderable renderable,
                      UserInputListener inputListener,
                      Vector2 windowDimensions,
                      GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge,
                      int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.gameObjectCollection = gameObjectCollection;
        isInstantiated = true;
    }

    /**
     * when the paddle was hit, the counter go down by one
     * @param other the other object the paddle got hit with
     * @param collision the reaction for the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other instanceof Ball){
            numCollisionsToDisappear--;
            if(numCollisionsToDisappear == 0){
                gameObjectCollection.removeGameObject(this);
                isInstantiated = false;
            }
        }
    }
}
