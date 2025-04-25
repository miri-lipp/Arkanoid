import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

public class TestGui {
    public void create() {
        GUI gui = new GUI("Fuck", 800, 600);
        Sleeper sleeper = new Sleeper();
        Random random = new Random();
        GameEnvironment gameEnv = new GameEnvironment();
        Ball ball = new Ball(new Point(200, 40), 5, Color.GREEN, gameEnv);
        Velocity v = Velocity.fromAngleAndSpeed(90, 4);
        ball.setVelocity(v);
        Block block = new Block(300, 100, new Point(400, 200));
        Block block1 = new Block(100, 100, new Point(20, 10));
        Block block2 = new Block(100, 30, new Point(20, 300));
        gameEnv.addCollidable(block);
        gameEnv.addCollidable(block1);
        gameEnv.addCollidable(block2);
        while (true) {
            ball.moveOneStep(800, 600, 0, 0);
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            block.drawOn(d);
            block1.drawOn(d);
            block2.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
    public static void main(String[] args) {
        TestGui gui = new TestGui();
        gui.create();
    }
}
