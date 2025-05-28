package GameEnvironment;

import Collidables.Block;
import Collidables.Collidable;
import GameFlow.BallAdder;
import GameFlow.BallRemover;
import GameFlow.BlockRemover;
import GameFlow.ScoreTrackingListener;
import Shapes.Point;
import Sprites.Ball;
import Sprites.Paddle;
import Sprites.ScoreIndicator;
import Sprites.Sprite;
import Sprites.SpriteCollection;
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
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 600;
    static final int COUNTER_START = 0;
    static final int BLOCK_WALLS = 4;
    static final int CLEAR_LEVEL = 100;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Counter counterBlocks;
    private Counter counterBalls;
    private BlockRemover blockRemover;
    private KeyboardSensor keyboardSensor;
    private Random rand;
    private Ball[] balls;
    private ScoreTrackingListener scoreTracker;
    private ScoreIndicator scoreIndicator;
    private Counter score;
    private Paddle paddle;

    /**
     * Adding collidable to game environment.
     * @param c collidable object.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adding object sprite to Sprites.Sprite collection.
     * @param s sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Getter of GameEvnironment.
     * @return game environment.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }
    private void initializeGameEnv() {
        this.counterBlocks = new Counter(COUNTER_START);
        this.score = new Counter(COUNTER_START);
        this.blockRemover = new BlockRemover(this, counterBlocks);
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.scoreTracker = new ScoreTrackingListener(this.score);
        this.scoreIndicator = new ScoreIndicator(this.scoreTracker.getCurrentScore());
    }
    private void initializeGui() {
        this.gui = new GUI("Arkanoid", SCREEN_WIDTH, SCREEN_HEIGHT);
        this.keyboardSensor = gui.getKeyboardSensor();
        this.sleeper = new Sleeper();
        this.rand = new Random();
    }

    private void initializeWalls() {
        Block[] blockWalls = new Block[BLOCK_WALLS];
        BallRemover ballRemover = new BallRemover(this, counterBalls);
        blockWalls[0] = new Block(20.2, 580, new Point(0, 20), Color.GRAY);
        blockWalls[1] = new Block(20, 580, new Point(780, 19), Color.GRAY);
        blockWalls[2] = new Block(800, 20, new Point(0, 0), Color.GRAY);
        blockWalls[3] = new Block(760, 10, new Point(19, 590), Color.GRAY);
        blockWalls[3].addHitListener(ballRemover);
        for (Block b : blockWalls) {
            b.addToGame(this);
        }
        this.scoreIndicator.addToGame(this);
    }

    private void initializePaddle() {
        this.paddle = new Paddle(new Point(35, 560), 20, 80, this.keyboardSensor);
        this.paddle.addToGame(this);
        this.paddle.addSleeper(this.sleeper);
        for (Ball b : this.balls) {
            this.paddle.addBalls(b);
        }
    }

    private void initializeBalls() {
        this.counterBalls = new Counter(COUNTER_START);
        this.balls = new Ball[2];
//        for(int i = 0; i < 20; i++){
//            int x = 300 - (20 / 2) * 20 + i * 20;
//            this.balls[i] = new Ball(new Point(x, 400), 4, Color.PINK, this.environment);
//        }
        this.balls[0] = new Ball(new Point(330, 400), 4,
                Color.ORANGE, this.environment);
        this.balls[1] = new Ball(new Point(69, 420), 5,
                Color.PINK, this.environment);
        for (Ball b : this.balls) {
            b.addToGame(this);
            this.counterBalls.increase(1);
        }
    }

    private void initializePlayableBlocks() {
        BallAdder ballAdder = new BallAdder(this, this.counterBalls, this.paddle);
        Block[] blocks = new Block[56];
        int blockIndex = 0;
        for (int i = 0; i < 6; i++) {
            int x = this.rand.nextInt(25); //for randomizing colors
            int y = this.rand.nextInt(127);
            int z = this.rand.nextInt(51);
            for (int j = 11; j > i; j--) {
                Block block = new Block(60, 30, new Point(71 + j * 59, 140 + i * 29),
                        new Color(255 - x * 10, 255 - y * 2, 255 - z * 5));
                block.addToGame(this);
                this.counterBlocks.increase(1);
                block.addHitListener(this.blockRemover);
                block.addHitListener(this.scoreTracker);
                blocks[blockIndex++] = block;
            }
        }
        int luckyBlock = this.rand.nextInt(blockIndex); //adding lucky block that adds balls on field
        blocks[luckyBlock].addHitListener(ballAdder);
    }

    // Initialize a new game: create the Blocks and Sprites.Ball (and Sprites.Paddle)
    // and add them to the game.

    /**
     * Initializing game.
     */
    public void initialize() {
        initializeGameEnv();
        initializeGui();
        initializeBalls();
        initializePaddle();
        initializeWalls();
        initializePlayableBlocks();
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
            DrawSurface d = this.gui.getDrawSurface();
            d.setColor(Color.BLUE);
            d.drawRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            d.fillRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - currentTimeMillis;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (this.counterBlocks.getValue() == 0) {
                this.score.increase(CLEAR_LEVEL);
                System.out.println("You win!");
                System.out.println("Your score is: " + scoreTracker.getCurrentScore().getValue());
                this.gui.close();
                return;
            }
            if (this.counterBalls.getValue() == 0) {
                System.out.println("Game Over.");
                System.out.println("Your score is: " + scoreTracker.getCurrentScore().getValue());
                this.gui.close();
                return;
            }
        }
    }

    /**
     * Removes Collidable from game.
     * @param c Collidable.
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * Removes Sprites from the game.
     * @param s Sprite.
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

}
