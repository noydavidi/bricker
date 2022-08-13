package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * this class represnts the strategy for moving the camera focus
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator{

    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private final CollisionStrategy toBeDecorated;
    private GameObject ball;
    private BallCollisionCountdownAgent ballCollisionCountdownAgent;

    /**
     * Counstruct for creating the strategy
     * @param toBeDecorated additional strategy
     * @param windowController Contains an array of helpful, self explanatory methods
     *                            concerning the window.
     * @param gameManager the Bricker game
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated, WindowController windowController, BrickerGameManager gameManager){
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
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
        toBeDecorated.onCollision(thisObj, otherObj, counter);
        if(otherObj instanceof Puck) return;
        if(otherObj instanceof Ball){
            super.onCollision(thisObj, otherObj, counter);
            this.ball = otherObj;
            this.ballCollisionCountdownAgent  = new BallCollisionCountdownAgent((Ball) ball, this, 4);
            getGameObjectCollection().addGameObject(ballCollisionCountdownAgent);
            gameManager.setCamera(new Camera(ball, Vector2.ZERO, windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()));
        }
    }

    /**
     * the method makes the strategy stop playing and set the camera back to normal.
     */
    public void turnOffCameraChange(){
        gameManager.setCamera(null);
        getGameObjectCollection().removeGameObject(ballCollisionCountdownAgent);
    }
}
