package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * this class represents the ball of the game
 */
public class Ball extends GameObject {
    private final Sound collisionSounds;
    private int amountOfCollisions;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSounds) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSounds = collisionSounds;
        amountOfCollisions = 0;
    }

    /**
     * Called on the first frame of a collision.
     * @param other The GameObject with which a collision occurred.
     * @param collision in formation regarding this collision.
     *                 A reasonable elastic behavior can be achieved with:
     *                 setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(other instanceof StatusDefines){
            return;
        }
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSounds.play();
        amountOfCollisions++;
    }

    public int getCollisionCount(){
        return amountOfCollisions;
    }

}
