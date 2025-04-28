import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * Paddle class.
 */
public class Paddle implements Collidable, Sprite {
    private final biuoop.KeyboardSensor keyboard;
    private final double height;
    private final double width;
    private Point upperLeft;
    static final int RADIUS = 5;
    /**
     * Paddle constructor.
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
        double x = this.upperLeft.getX() - 5;
        double y = this.upperLeft.getY();
        if (x + this.width < 0) {
            x = 800;
        }
        this.upperLeft = new Point(x, y);
    }

    /**
     * Moving paddle right.
     */
    public void moveRight() {
        double x = this.upperLeft.getX() + 5;
        double y = this.upperLeft.getY();
        if (x - this.width > 800) {
            x = 0 - this.width;
        }
        this.upperLeft = new Point(x, y);
    }

    // Sprite
    @Override
    public void timePassed() {
        if (this.keyboard.isPressed("a") || this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (this.keyboard.isPressed("d") || this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.YELLOW);
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
    }

    // Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.upperLeft, this.width, this.height);
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double region = this.width / 5;
        double x = collisionPoint.getX();
        double paddle = this.upperLeft.getX();
        if (MathChecker.doubleEquals(collisionPoint.getY(), this.upperLeft.getY())) {
            if (x <= paddle + region) {
                return Velocity.fromAngleAndSpeed(150, currentVelocity.getSpeed());
            } else if (x < paddle + region * 2) {
                return Velocity.fromAngleAndSpeed(120, currentVelocity.getSpeed());
            } else if (x < paddle + region * 3) {
                return Velocity.fromAngleAndSpeed(90, currentVelocity.getSpeed());
            } else if (x < paddle + region * 4) {
                return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
            } else {
                return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
            }
        }
        for (Line side : getCollisionRectangle().getSides()) {
            if (side.isPointInside(collisionPoint)) {
                if (side.isPointInside(collisionPoint)) {
                    double dx = currentVelocity.getDx();
                    double dy = currentVelocity.getDy();
                    boolean horizontal = MathChecker.doubleEquals(side.start().getY(), side.end().getY());
                    boolean vertical = MathChecker.doubleEquals(side.start().getX(), side.end().getX());
                    if (horizontal) {
                        return new Velocity(dx, -dy);
                    }
                    if (vertical) {
                        return new Velocity(-dx, dy);
                    }
                }
            }
        }
        return currentVelocity;
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
}
