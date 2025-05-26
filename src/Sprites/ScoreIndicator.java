package Sprites;

import GameEnvironment.Counter;
import GameEnvironment.Game;
import biuoop.DrawSurface;

import java.awt.Color;

public class ScoreIndicator implements Sprite {
    private final Counter score;

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

    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
