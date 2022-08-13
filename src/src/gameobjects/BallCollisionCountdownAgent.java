package src.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * An object of this class is instantiated on
 * collision of ball with a brick with a change camera strategy.
 */
public class BallCollisionCountdownAgent extends GameObject {
    private final ChangeCameraStrategy owner;
    private final int countDownValue;
    private final Ball ball;
    private final int currentNumberCollisions;


    /**
     * Counstruct a BallCollisionCountdownAgent extends GameObject
     * @param ball the ball the camera should follow
     * @param owner the specific change camera strategy for the brick
     * @param countDownValue the counter to use for make the strategy stop
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue){
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.owner = owner;
        this.countDownValue = countDownValue;
        this.currentNumberCollisions = ball.getCollisionCount();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(ball.getCollisionCount() >= countDownValue + currentNumberCollisions){
            owner.turnOffCameraChange();
        }
    }

}
