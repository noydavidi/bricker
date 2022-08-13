package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class StatusDefines extends GameObject {
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Construct a new StatusDefines instance extends from GameObject
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param windowDimensions The width and height of the game window
     * @param gameObjectCollection the game objects collection
     */
    public StatusDefines(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 windowDimensions,
                         GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable);
        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other instanceof Paddle){
            gameObjectCollection.removeGameObject(this);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(getTopLeftCorner().y() > windowDimensions.y()){
            gameObjectCollection.removeGameObject(this);
        }
    }

}
