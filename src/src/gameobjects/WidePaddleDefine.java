package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * this class represents the object that makes the paddle wider
 */
public class WidePaddleDefine extends StatusDefines {
    /**
     * Construct a new WidePaddleDefine instance extends from StatusDefines
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     * @param windowDimensions The width and height of the game window
     * @param gameObjectCollection the game objects collection
     */
    public WidePaddleDefine(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 windowDimensions, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable, windowDimensions, gameObjectCollection);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }
}
