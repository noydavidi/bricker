package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Display a graphic object on the game window showing a numeric count of lives left.
 */
public class NumericLifeCounter extends GameObject {
    private final Counter livesCounter;
    private final TextRenderable renderable;

    /**
     * Construct a new NumericLifeCounter instance.
     * @param livesCounter  global lives counter of game.
     * @param topLeftCorner top left corner of renderable
     * @param dimensions dimensions of renderable
     * @param gameObjectCollection global game object collection
     */

    public NumericLifeCounter(Counter livesCounter,
                              Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {

        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        int currentLivesCounter = livesCounter.value();
        String textString = Integer.toString(currentLivesCounter);
        this.renderable = new TextRenderable("Your lives: " + textString);
        this.renderable.setColor(Color.WHITE);
        GameObject text = new GameObject(topLeftCorner, dimensions, renderable);
        gameObjectCollection.addGameObject(text, Layer.UI);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderable.setString("Your lives: " + livesCounter.value());

    }
}
