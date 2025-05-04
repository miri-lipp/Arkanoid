import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * Game class.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    static final Color[] COLORS = {
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.MAGENTA,
            Color.ORANGE,
            Color.CYAN,
            Color.PINK,
    };

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
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", 800, 600);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        this.sleeper = new Sleeper();
        Velocity v = Velocity.fromAngleAndSpeed(90, 5);
        Paddle paddle = new Paddle(new Point(35, 560), 20, 80, keyboard);
        paddle.addToGame(this);
        Ball ball1 = new Ball(new Point(330, 400), 4, Color.ORANGE, this.environment);
        Ball ball2 = new Ball(new Point(69, 420), 5, Color.PINK, this.environment);
        ball1.addToGame(this);
        ball2.addToGame(this);
        ball1.setVelocity(v);
        ball2.setVelocity(v);
        Block blockWallLeft = new Block(20, 580, new Point(0, 20), Color.GRAY);
        Block blockWallRight = new Block(20, 580, new Point(780, 20), Color.GRAY);
        Block blockWallUp = new Block(800, 20, new Point(0, 0), Color.GRAY);
        Block blockWallDown = new Block(760, 20, new Point(20, 580), Color.GRAY);
        blockWallLeft.addToGame(this);
        blockWallRight.addToGame(this);
        blockWallUp.addToGame(this);
        blockWallDown.addToGame(this);
        for (int i = 0; i < 6; i++) {
            for (int j = 11; j > i; j--) {
                Block block = new Block(60, 30, new Point(60 + j * 60, 140 + i * 31), COLORS[i]);
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
        long startTime = System.currentTimeMillis();
        while (true) {
            long currentTimeMillis = System.currentTimeMillis(); // timing
            double dt = (currentTimeMillis - startTime) / 1000.0; //getting delta time for better fps and less bugging
            startTime = currentTimeMillis;
            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.BLUE);
            d.drawRectangle(0, 0, 800, 600);
            d.fillRectangle(0, 0, 800, 600);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed(dt);

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
