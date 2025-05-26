package GameEnvironment;

import Collidables.Block;
import Collidables.Collidable;
import GameFlow.BallRemover;
import GameFlow.BlockRemover;
import Shapes.Point;
import Sprites.Ball;
import Sprites.Paddle;
import Sprites.Sprite;
import Sprites.SpriteCollection;
import Sprites.Velocity;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

/**
 * GameEnvironment.Game class.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;

    /**
     * Adding collidable to game environment.
     * @param c collidable object.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Adding object sprite to Sprites.Sprite collection.
     * @param s sprite object.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    // Initialize a new game: create the Blocks and Sprites.Ball (and Sprites.Paddle)
    // and add them to the game.

    /**
     * Initializing game.
     */
    public void initialize() {
  //      Sprites.Ball[] ball = new Sprites.Ball[1000];
        Counter counter = new Counter(0);
        BlockRemover remover = new BlockRemover(this, counter);
        BallRemover ballRemover = new BallRemover(this);
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", 800, 600);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        this.sleeper = new Sleeper();
        //Velocity v2 = Velocity.fromAngleAndSpeed(150, 4);
        Paddle paddle = new Paddle(new Point(35, 560), 20, 80, keyboard);
        paddle.addToGame(this);
//        for (int i = 0; i < 100; i++) {
//            ball[i] = new Sprites.Ball(new Shapes.Point(6 + i * 4, 420 + i * 3), 5, Color.GREEN, this.environment);
//            ball[i].addToGame(this);
//            ball[i].setVelocity(v2.getDx() + i * 0.5, v2.getDy() + i * 0.6);
//            paddle.addBalls(ball[i]);
//        }
        paddle.addSleeper(sleeper);
        Block blockWallLeft = new Block(20.2, 580, new Point(0, 20), Color.GRAY);
        Block blockWallRight = new Block(20, 580, new Point(780, 19), Color.GRAY);
        Block blockWallUp = new Block(800, 20, new Point(0, 0), Color.GRAY);
        Block blockWallDown = new Block(760, 20, new Point(19, 580), Color.GRAY);
        blockWallLeft.addToGame(this);
        blockWallRight.addToGame(this);
        blockWallUp.addToGame(this);
        blockWallDown.addToGame(this);
        Random rand = new Random();
        Ball ball1 = new Ball(new Point(330, 400), 4,
                Color.ORANGE, this.environment);
        Ball ball2 = new Ball(new Point(69, 420), 5,
                Color.PINK, this.environment);
        ball1.addToGame(this);
        ball2.addToGame(this);
        paddle.addBalls(ball1);
        paddle.addBalls(ball2);
        for (int i = 0; i < 6; i++) {
            int x = rand.nextInt(25); //for randomizing colors
            int y = rand.nextInt(127);
            int z = rand.nextInt(51);
            for (int j = 11; j > i; j--) {
                Block block = new Block(60, 30, new Point(71 + j * 59, 140 + i * 29),
                        new Color(255 - x * 10, 255 - y * 2, 255 - z * 5));
                block.addToGame(this);
                counter.increase(1);
                block.addHitListener(remover);
            }
        }
    }

    // Run the game -- start the animation loop.

    /**
     * Running game.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long currentTimeMillis = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.BLUE);
            d.drawRectangle(0, 0, 800, 600);
            d.fillRectangle(0, 0, 800, 600);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - currentTimeMillis;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

}
