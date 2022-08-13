package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

/**
 * this class represents the PuckStrategy. it creates puck balls.
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator {

    private final SoundReader soundReader;
    private final ImageReader imageReader;
    private final CollisionStrategy toBeDecorated;
    private final Random rand = new Random();
    private Vector2 topLeftCornerOfBrick;
    private float size;


    /**
     * Creating a puck strategy
     * @param toBeDecorated additional strategy
     * @param imageReader the object to read the path to the image
     * @param soundReader the object for reading the sounds
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader){
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
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
        this.topLeftCornerOfBrick = thisObj.getTopLeftCorner();
        this.size = thisObj.getDimensions().x()/3;
        createPuckBall();
    }

    /**
     * this method creates a puck ball where the brick was
     */
    private void createPuckBall() {
        Renderable ballImage = imageReader.readImage("assets/mockBall.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        GameObject ball = new Puck(topLeftCornerOfBrick, new Vector2(size, size), ballImage, collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(300));

//        ball.setCenter(topLeftCornerOfBrick.mult((float) 0.5));
        getGameObjectCollection().addGameObject(ball);

        float ballVelX = 300;
        float ballVelY = 300;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

}
