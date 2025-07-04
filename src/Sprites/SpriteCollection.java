package Sprites;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * Sprites.Sprite class.
 */
public class SpriteCollection {
    private final List<Sprite> sprites;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adding of sprite.
     * @param s sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Removes sprite from the game.
     * @param s Sprite object.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> sprites = new ArrayList<>(this.sprites);
        for (Sprite sprite : sprites) {
            sprite.timePassed();
        }
    }

    /**
     * Drawing of all Sprites.
     * @param d Draw surface object.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }
    }
}
