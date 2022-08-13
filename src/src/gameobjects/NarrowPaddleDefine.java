package src.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * this class represents the object that makes the paddle narrower
 */
public class NarrowPaddleDefine extends StatusDefines{
    /**
     * Construct a new NarrowPaddleDefine instance extends StatusDefines.
     *
     * @param topLeftCorner        Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height in window coordinates.
     * @param renderable           The renderable representing the object. Can be null, in which case
     * @param windowDimensions     The width and height of the game window
     * @param gameObjectCollection The game objects collection
     */
    public NarrowPaddleDefine(Vector2 topLeftCorner,
                              Vector2 dimensions,
                              Renderable renderable,
                              Vector2 windowDimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable, windowDimensions, gameObjectCollection);
    }
}
