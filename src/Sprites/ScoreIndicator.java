package Sprites;

import GameEnvironment.Counter;
import GameEnvironment.Game;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private final Counter score;

    /**
     * Constructor.
     * @param score object.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }
    @Override
    public void drawOn(DrawSurface d) {
        String score = "SCORE: " + this.score.getValue();
        d.setColor(Color.WHITE);
        d.drawText(360, 15, score, 15);
    }

    @Override
    public void timePassed() {

    }

    /**
     * Adding to game.
     * @param g game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
