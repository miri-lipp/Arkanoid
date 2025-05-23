import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

/**
 * Game class.
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
     * Adding object sprite to Sprite collection.
     * @param s sprite object.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.

    /**
     * Initializing game.
     */
    public void initialize() {
  //      Ball[] ball = new Ball[1000];
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", 800, 600);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        this.sleeper = new Sleeper();
        Velocity v2 = Velocity.fromAngleAndSpeed(150, 4);
        Paddle paddle = new Paddle(new Point(35, 560), 20, 80, keyboard);
        paddle.addToGame(this);
        Ball ball1 = new Ball(new Point(330, 400), 4, Color.PINK, this.environment);
        Ball ball2 = new Ball(new Point(69, 420), 5, Color.CYAN, this.environment);
//        for (int i = 0; i < 100; i++) {
//            ball[i] = new Ball(new Point(6 + i * 4, 420 + i * 3), 5, Color.GREEN, this.environment);
//            ball[i].addToGame(this);
//            ball[i].setVelocity(v2.getDx() + i * 0.5, v2.getDy() + i * 0.6);
//            paddle.addBalls(ball[i]);
//        }
        ball1.addToGame(this);
        ball2.addToGame(this);
        paddle.addBalls(ball1);
        paddle.addBalls(ball2);
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
        for (int i = 0; i < 6; i++) {
            int x = rand.nextInt(25); //for randomizing colors
            int y = rand.nextInt(127);
            int z = rand.nextInt(51);
            for (int j = 11; j > i; j--) {
                Block block = new Block(60, 30, new Point(71 + j * 59, 140 + i * 29),
                        new Color(255 - x * 10, 255 - y * 2, 255 - z * 5));
                block.addToGame(this);
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
//        long startTime = System.currentTimeMillis();
        while (true) {
            long currentTimeMillis = System.currentTimeMillis(); // timing
//            double dt = (currentTimeMillis - startTime) / 1000.0; //getting delta time for better fps and less bugging
//            startTime = currentTimeMillis;
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

//    /**
//     * Main.
//     * @param args string.
//     */
//    public static void main(String[] args) {
//        Game g = new Game();
//        g.initialize();
//        g.run();
//    }
}
