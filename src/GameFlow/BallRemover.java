package GameFlow;

import Collidables.Block;
import GameEnvironment.Counter;
import GameEnvironment.Game;
import Sprites.Ball;

/**
 * Removes ball from game.
 */
public class BallRemover implements HitListener {
    private final Game game;
    private final Counter counter;

    /**
     * Constructor.
     * @param game game object.
     * @param counter counter object.
     */
    public BallRemover(Game game, Counter counter) {
        this.game = game;
        this.counter = counter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.counter.decrease(1);
    }
}
