package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * this class represents the paddle the user plays with
 */
public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300;
    private final int minDistanceFromEdge;
    private final Vector2 windowDimensions;
    private final Vector2 dimensions;
    private final UserInputListener inputListener;

    /**
     * constructor for the paddle, making the object
     * @param topLeftCorner the place for the object in the begining of the game
     * @param dimensions the size of the paddle
     * @param renderable  The renderable representing the object. Can be null, in which case the GameObject will not be rendered.
     * @param inputListener the object we use for noticing if a key was pressed
     * @param windowDimensions The width and height of the game window
     * @param minDistanceFromEdge the distance the paddle will keep from the walls.
     */
    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener inputListener,
                  Vector2 windowDimensions,
                  int minDistanceFromEdge) {

        super(topLeftCorner, dimensions, renderable);

        this.inputListener = inputListener;
        this.dimensions = dimensions;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newDimensions;
        if(other instanceof WidePaddleDefine){
            newDimensions = new Vector2((float) (dimensions.x() + 100), dimensions.y());
            setDimensions(newDimensions);
        }
        if(other instanceof NarrowPaddleDefine){
            newDimensions = new Vector2((float) (dimensions.x() - 50), dimensions.y());
            setDimensions(newDimensions);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDirection = Vector2.ZERO;

        if((inputListener.isKeyPressed(KeyEvent.VK_LEFT)) &&  (minDistanceFromEdge < getTopLeftCorner().x())){
            movementDirection = movementDirection.add(Vector2.LEFT);
        }
        if((inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) && (getTopLeftCorner().x() < windowDimensions.x() - minDistanceFromEdge - dimensions.x())){
            movementDirection = movementDirection.add(Vector2.RIGHT);
        }
        setVelocity(movementDirection.mult(MOVEMENT_SPEED));
    }
}
