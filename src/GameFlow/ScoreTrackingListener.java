package GameFlow;

import Collidables.Block;
import GameEnvironment.Counter;
import Sprites.Ball;

/**
 * Score tracker.
 */
public class ScoreTrackingListener implements HitListener {
    static final int BLOCK_KILLING = 5;
    private final Counter currentScore;

    /**
     * Constructor.
     * @param scoreCounter object.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Getter of current score.
     * @return score.
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(BLOCK_KILLING);
    }
}
