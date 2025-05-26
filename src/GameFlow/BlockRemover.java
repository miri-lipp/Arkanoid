package GameFlow;

import Collidables.Block;
import GameEnvironment.Counter;
import GameEnvironment.Game;
import Sprites.Ball;

public class BlockRemover implements HitListener{
    private final Game g;
    private final Counter remainingBlocks;
    public BlockRemover(Game g, Counter remainingBlocks) {
        this.g = g;
        this.remainingBlocks = remainingBlocks;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(this.g);
        beingHit.removeHitListener(this);
        hitter.setColor(beingHit.getColor());
        this.remainingBlocks.decrease(1);
       // hitter.removeFromGame(this.g);
    }
}
