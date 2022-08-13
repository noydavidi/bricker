package src.brick_strategies;


import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;

import src.*;

import java.util.Random;

/**
 * this class represents the object of a factory for choosing a strategy
 */
public class BrickStrategyFactory {

    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;
    private final BrickerGameManager gameManger;

    private Random rand = new Random();

    /**
     * @param gameObjectCollection the game objects collection
     * @param gameManager the Bricker game
     * @param imageReader the object to read the path to the image
     * @param soundReader the object for reading the sounds
     * @param inputListener the object for reading the user moves on the keyboard
     * @param windowController Contains an array of helpful, self explanatory methods
     *      *                         concerning the window.
     * @param windowDimensions The width and height of the game window
     */
    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                ImageReader imageReader,
                                SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController,
                                Vector2 windowDimensions) {
        this.gameManger = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
    }

    /**
     * this methods choose a strategy by the required chances
     * @return the chosen strategy
     */
    public CollisionStrategy getStrategy() {
        int random = rand.nextInt(6);
        switch (random){
            case 0:
                return (new RemoveBrickStrategy(gameObjectCollection));
            case 1:
                return getSingleStrategy();
            case 2:
                return getSingleStrategy();
            case 3:
                return getSingleStrategy();
            case 4:
                return getSingleStrategy();
            case 5:
                return getDoubledStrategy(-1);
        }
        return new RemoveBrickStrategy(gameObjectCollection);
    }


    /**
     * this methods is a helper for getting a random single Strategy
     * @return A strategy collided with the Remove strategy
     */
    private CollisionStrategy getSingleStrategy(){
        int random = rand.nextInt(4);
        switch (random) {
            case 0:
                return (new AddPaddleStrategy(new RemoveBrickStrategy(gameObjectCollection), imageReader, inputListener, windowDimensions));
            case 1:
                return (new PuckStrategy(new RemoveBrickStrategy(gameObjectCollection), imageReader, soundReader));
            case 2:
                return (new WideNarrowPaddleStrategy(new RemoveBrickStrategy(gameObjectCollection), windowDimensions, gameObjectCollection, imageReader));
            case 3:
                return (new ChangeCameraStrategy(new RemoveBrickStrategy(gameObjectCollection), windowController, gameManger));
        }
        return new RemoveBrickStrategy(gameObjectCollection);
    }

    /**
     * this method is a helper, it gets a random doubled strategy
     * @param i if i -1 so it will get any random strategy
     *          otherwise it will choose the strategy by i
     * @return the chosen doubled strategy
     */
    private CollisionStrategy getDoubledStrategy(int i){
        int random;
        if(i < 0) random = rand.nextInt(5);
        else random = i;
        CollisionStrategy additionalCollision = getSingleStrategy();
        switch (random) {
            case 0:
                return (new AddPaddleStrategy(additionalCollision, imageReader, inputListener, windowDimensions));
            case 1:
                return (new PuckStrategy(additionalCollision, imageReader, soundReader));
            case 2:
                return (new WideNarrowPaddleStrategy(additionalCollision, windowDimensions, gameObjectCollection, imageReader));
            case 3:
                return (new ChangeCameraStrategy(additionalCollision, windowController, gameManger));
            case 4:
                getTripleStrategy();
        }
        return new RemoveBrickStrategy(gameObjectCollection);
    }

    /**
     * this method get a random strategy which has 3 strategies
     * @return the choosen strategy
     */
    private CollisionStrategy getTripleStrategy(){
        int random = rand.nextInt(4);
        CollisionStrategy additionalCollision = getDoubledStrategy(random);
        random = rand.nextInt(4);
        switch (random) {
            case 0:
                return (new AddPaddleStrategy(additionalCollision, imageReader, inputListener, windowDimensions));
            case 1:
                return (new PuckStrategy(additionalCollision, imageReader, soundReader));
            case 2:
                return (new WideNarrowPaddleStrategy(additionalCollision, windowDimensions, gameObjectCollection, imageReader));
            case 3:
                return (new ChangeCameraStrategy(additionalCollision, windowController, gameManger));
        }
        return new RemoveBrickStrategy(gameObjectCollection);
    }

}

