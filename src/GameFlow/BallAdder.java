package GameFlow;

import Collidables.Block;
import GameEnvironment.Counter;
import GameEnvironment.Game;
import Shapes.Point;
import Sprites.Ball;

public class BallAdder implements HitListener{
    private final Game game;
    private final Counter counter;
    public BallAdder(Game game, Counter counter) {
        this.game = game;
        this.counter = counter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        Ball ball = new Ball(new Point(hitter.getX(), hitter.getY()), hitter.getSize(),
                hitter.getColor(), this.game.getEnvironment());
        ball.addToGame(this.game);
        this.counter.increase(1);
    }
}
