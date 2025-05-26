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
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * Adding of sprite.
     * @param s sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }
    // call timePassed() on all sprites.

    /**
     * Notifier that time passed.
     */
    public void notifyAllTimePassed() {
        for (Sprite sprite : this.sprites) {
            sprite.timePassed();
        }
    }

    // call drawOn(d) on all sprites.

    /**
     * Drawing of all Sprites.
     * @param d Drawsurface object.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }
    }
}
