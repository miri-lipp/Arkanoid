package Sprites;

import biuoop.DrawSurface;

/**
 * Sprites.Sprite inteface.
 */
public interface Sprite {
    // draw the sprite to the screen

    /**
     * Draws object on surface.
     * @param d DrawSurface object.
     */
    void drawOn(DrawSurface d);
    // notify the sprite that time has passed

    /**
     * Time Passed notifier function.
     */
    void timePassed();
}
