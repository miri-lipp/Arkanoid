package GameFlow;

import Collidables.Block;
import GameEnvironment.Counter;
import GameEnvironment.Game;
import Sprites.Ball;

public class BallRemover implements HitListener{
    private final Game game;
    private final Counter counter;
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
