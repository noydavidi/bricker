package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import src.BrickerGameManager;

/**
 * this class reprents a decor pattern, allows making doubled or triple strategy
 */
public abstract class RemoveBrickStrategyDecorator implements  CollisionStrategy{

    private final CollisionStrategy toBeDecorated;

    /**
     * Construct the strategy
     * @param toBeDecorated the additional strategy
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){
        this.toBeDecorated = toBeDecorated;
    }

    /**
     * when there is a collision, makes the strategy happen
     * @param thisObj the object which got collided
     * @param otherObj the object which collides
     * @param counter the bricks counter
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter){
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    }

    /**
     * this method return the game objects collection
     * @return
     */
    public GameObjectCollection getGameObjectCollection(){
        return toBeDecorated.getGameObjectCollection();
    }
}
