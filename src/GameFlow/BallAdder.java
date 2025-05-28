package GameFlow;

import Collidables.Block;
import GameEnvironment.Counter;
import GameEnvironment.Game;
import Shapes.Point;
import Sprites.Ball;
import Sprites.Paddle;

/**
 * Adds new ball to game.
 */
public class BallAdder implements HitListener {
    private final Game game;
    private final Counter counter;
    private final Paddle paddle;

    /**
     * Ball adder constructor.
     * @param game Game object.
     * @param counter Counter object.
     * @param paddle Paddle object.
     */
    public BallAdder(Game game, Counter counter, Paddle paddle) {
        this.game = game;
        this.counter = counter;
        this.paddle = paddle;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        Ball ball = new Ball(new Point(hitter.getX(), hitter.getY()), hitter.getSize(),
                hitter.getColor(), this.game.getEnvironment());
        ball.addToGame(this.game);
        this.counter.increase(1);
        this.paddle.addBalls(ball);
    }
}
