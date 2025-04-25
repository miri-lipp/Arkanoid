import biuoop.GUI;
import biuoop.DrawSurface;
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
//    /**
//     * Game constructor.
//     * @param sprites sprite collection.
//     * @param environment game environment.
//     */
//    public Game(SpriteCollection sprites, GameEnvironment environment) {
//        this.sprites = sprites;
//        this.environment = environment;
//    }

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
        this.gui = new GUI("Game", 800, 600);
        this.sleeper = new Sleeper();
        Velocity v = Velocity.fromAngleAndSpeed(90, 4);
        Ball ball = new Ball(new Point(300, 400), 5, Color.ORANGE, this.environment);
        ball.addToGame(this);
        ball.setVelocity(v);
        for (int i = 0; i < 4; i++) {
            Block block = new Block(120, 60, new Point(i * 20, i * 30 + 5 * i));
            block.addToGame(this);
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
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
    public static void main(String[] args) {
        Game g = new Game();
        g.initialize();
        g.run();
    }
}
