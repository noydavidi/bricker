package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * this class represnts the object for little ball that shows after
 * a certain brick got hit.
 */
public class Puck extends Ball{

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner   Position of the object, in window coordinates (pixels).
     *                        Note that (0,0) is the top-left corner of the window.
     * @param dimensions      Width and height in window coordinates.
     * @param renderable      The renderable representing the object. Can be null, in which case
     * @param collisionSounds the collision sound
     */
    public Puck(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable,
                Sound collisionSounds) {
        super(topLeftCorner, dimensions, renderable, collisionSounds);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }
}
