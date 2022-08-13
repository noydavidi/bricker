package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Display a graphic object on the game window showing as many widgets as lives left.
 */
public class GraphicLifeCounter extends GameObject {

    private int currentNumberOfHearts;
    private final GameObjectCollection gameObjectCollection;
    private TextRenderable renderable;
    private final Counter livesCounter;
    private int numOfLives;
    private final GameObject[] livesArray;

    /**
     * Construct a new GraphicLifeCounter instance.
     * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
     *      *               Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions dimensions of widgets to be displayed.
     * @param livesCounter the Counter for lives left
     * @param widgetRenderable image to use for widgets.
     * @param gameObjectCollection global game object collection managed by game manager.
     * @param numOfLives global setting of number of lives a player will have in a game.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner,
                              Vector2 widgetDimensions,
                              Counter livesCounter,
                              Renderable widgetRenderable,
                              GameObjectCollection gameObjectCollection,
                              int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.livesArray = new GameObject[numOfLives];
        this.gameObjectCollection =gameObjectCollection;
        this.currentNumberOfHearts = numOfLives;
        fillLivesArray(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
    }

    /**
     * this method create the whole widget of lives.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *      *      *               Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions dimensions of widgets to be displayed.
     * @param widgetRerderable image to use for widget.
     */
    private void fillLivesArray(Vector2 topLeftCorner, Vector2 widgetDimensions, Renderable widgetRerderable){
        for(int i = 0; i < livesArray.length; i++){
            Vector2 position = new Vector2(20 +i*35, topLeftCorner.y()-50);
            livesArray[i] = new GameObject(position, widgetDimensions, widgetRerderable);
            gameObjectCollection.addGameObject(livesArray[i], Layer.UI);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(livesCounter.value() < currentNumberOfHearts){
            currentNumberOfHearts--;

            gameObjectCollection.removeGameObject(livesArray[currentNumberOfHearts], Layer.UI);
            livesArray[currentNumberOfHearts] = null;
        }
    }
}
