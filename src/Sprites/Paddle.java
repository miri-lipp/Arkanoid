package Sprites;

import Collidables.Collidable;
import Shapes.MathChecker;
import Shapes.Point;
import Shapes.Rectangle;
import GameEnvironment.Game;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Sprites.Paddle class.
 */
public class Paddle implements Collidable, Sprite {
    private Sleeper sleeper;
    public static final int SPEED = 5;
    public static final int WIDTH = 800;
    private final biuoop.KeyboardSensor keyboard;
    private final double height;
    private final double width;
    private Point upperLeft;
    private ArrayList<Ball> balls; // an array of balls to check if any ball is inside the paddle.

    /**
     * Sprites.Paddle constructor.
     * @param upperLeft starting point.
     * @param height height.
     * @param width width.
     * @param keyboard keyboard.
     */
    public Paddle(Point upperLeft, double height, double width, KeyboardSensor keyboard) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
        this.keyboard = keyboard;
    }

    /**
     * Moving paddle left.
     */
    public void moveLeft() {
        double x = this.upperLeft.getX() - SPEED;
        double y = this.upperLeft.getY();
        if (x + this.width < 0) {
            x = WIDTH;
        }
        this.upperLeft = new Point(x, y);
    }

    /**
     * Moving paddle right.
     */
    public void moveRight() {
        double x = this.upperLeft.getX() + SPEED;
        double y = this.upperLeft.getY();
        if (x - this.width > WIDTH) {
            x = 0 - this.width;
        }
        this.upperLeft = new Point(x, y);
    }

    // Sprites.Sprite
    @Override
    public void timePassed() { //after passing time or left or right key pressed
        boolean moved  = false;
        if (this.keyboard.isPressed("a") || this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
            this.sleeper.sleepFor(1); //sleeper after input
            moved = true;
        } else if (this.keyboard.isPressed("d") || this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
            this.sleeper.sleepFor(1);
            moved = true;
        }
        if (moved) {
            isWIthinPaddle();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.YELLOW);
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
    }

    // Collidables.Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.upperLeft, this.width, this.height);
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        double region = this.width / 5; //hit on 5 areas of paddle
        double x = collisionPoint.getX();
        double paddle = this.upperLeft.getX();
        if (MathChecker.doubleEquals(collisionPoint.getY(), this.upperLeft.getY())) {
            if (x <= paddle + region) {
                return Velocity.fromAngleAndSpeed(150, currentVelocity.getSpeed());
            } else if (x <= paddle + region * 2) {
                return Velocity.fromAngleAndSpeed(120, currentVelocity.getSpeed());
            } else if (x <= paddle + region * 3) {
                return Velocity.fromAngleAndSpeed(90, currentVelocity.getSpeed());
            } else if (x <= paddle + region * 4) {
                return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
            } else if (x <= paddle + region * 5) {
                return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
            }
        } //hit on edges of paddle
        double rightX = paddle + this.width;
        if (MathChecker.doubleEquals(collisionPoint.getX(), paddle)
                || MathChecker.doubleEquals(collisionPoint.getX(), rightX)) {
            // Reflect horizontally
            //System.out.println("Horizontally x:" + -currentVelocity.getDx() + " y:" + currentVelocity.getDy());
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        //System.out.println("Vertically x:" + currentVelocity.getDx() + " y:" + -currentVelocity.getDy());
        return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
    }

    // Add this paddle to the game.

    /**
     * Adding paddle to the game.
     * @param g game object.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Adding a ball to array of balls.
     * @param ball Sprites.Ball.
     */
    public void addBalls(Ball ball) {
        if (this.balls == null) {
            this.balls = new ArrayList<>();
        }
        this.balls.add(ball);
    }

    /**
     * Getter of balls.
     * @return balls.
     */
    public ArrayList<Ball> getBalls() {
        return this.balls;
    }

    /**
     * Adding sleeper object.
     * @param sleep sleeper.
     */
    public void addSleeper(Sleeper sleep) {
        this.sleeper = sleep;
    }

    private void isWIthinPaddle() {
        //System.out.println("Total balls: " + getBalls().size());
        for (Ball b : getBalls()) {
            if (b == null) {
                continue;
            }
            Point rectPoint = getCollisionRectangle().getUpperLeft();
            boolean insideX = b.getX() > rectPoint.getX() && b.getX() < rectPoint.getX() + this.width;
            boolean insideY = b.getY() > rectPoint.getY() && b.getY() < rectPoint.getY() + this.height;
            if (insideX && insideY) { //is inside paddle
                double dx = b.getVelocity().getDx();
                double dy = b.getVelocity().getDy();
                // Compute paddle center
                double paddleCenterX = rectPoint.getX() + this.width / 2;
                double paddleCenterY = rectPoint.getY() + this.height / 2;
                // Vector from paddle center to ball center
                double vectorX = b.getX() - paddleCenterX;
                double vectorY = b.getY() - paddleCenterY;
                // Normalize the vector
                double magnitude = Math.sqrt(vectorX * vectorX + vectorY * vectorY);
                if (magnitude == 0) {
                    magnitude = 1; // prevent division by zero
                }
                vectorX /= magnitude;
                vectorY /= magnitude;
                // Push ball out slightly in that direction
                double pushDistance = b.getSize() + SPEED;
                double newX = b.getX() + vectorX * pushDistance;
                double newY = b.getY() + vectorY * pushDistance;
                b.setCenter(new Point(newX, newY));
                if (newX <= 20 || newX >= 780 || newY <= 20 || newY >= 580) { //if paddle pushed ball outside of borders
                    b.setVelocity(Velocity.fromAngleAndSpeed(90, b.getVelocity().getSpeed()));
                    b.setCenter(new Point(69, 420));
                }
                // Adjust velocity to match new direction (reflect away)
                b.setVelocity(new Velocity(Math.signum(vectorX) * Math.abs(dx),
                        Math.signum(vectorY) * Math.abs(dy)));
                // Move once after correction to avoid re-collision
                b.moveOneStep();
            }
        }
    }
}
