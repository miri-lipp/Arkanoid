package GameFlow;

import Collidables.Block;
import GameEnvironment.Game;
import Sprites.Ball;

public class BallRemover implements HitListener{
    Game game;
    public BallRemover(Game game){
        this.game = game;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
    }
}
