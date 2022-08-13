package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * this class represnts the RemoveStrategy, makes the brick to be removes from the game
 */
public class RemoveBrickStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjectCollection;

    /**
     * Consturct the Strategy
     * @param gameObjectCollection the game objects collection
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * when there is a collision, makes the strategy happen
     * @param thisObj the object which got collided
     * @param otherObj the object which collides
     * @param counter the bricks counter
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if(gameObjectCollection.removeGameObject(thisObj))
            counter.decrement();
    }

    /**
     * this method returns the game objects colletion
     * @return
     */
    public GameObjectCollection getGameObjectCollection(){
        return gameObjectCollection;
    }
}

