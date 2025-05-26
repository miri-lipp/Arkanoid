package Sprites;

import Shapes.Line;
import Shapes.Point;
import GameEnvironment.GameEnvironment;
import GameEnvironment.Game;
import Collidables.CollisionInfo;
import biuoop.DrawSurface;

/**
 * Class Balls.
 */
public class Ball implements Sprite {
    static final double MAX_SPEED = 100;
    // constructor
    private final int r;
    private Point center;
    private final java.awt.Color color;
    private Velocity v;
    private final GameEnvironment gameEnv;
    /**
     * Constructor.
     * @param center of class Shapes.Point.
     * @param r radius.
     * @param color color of ball.
     * @param gameEnv game environment reference.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnv) {
        this.center = new Point(center.getX(), center.getY());
        this.r = r;
        this.color = color;
        this.gameEnv = gameEnv;
    }

    /**
     * Accessor to x.
     * @return x.
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Accessor to y.
     * @return y.
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Size.
     * @return size of ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Color.
     * @return color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Draws ball on the surface.
     * @param surface class DrawSurface.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(getColor());
        surface.drawCircle(this.getX(), this.getY(), this.r);
        surface.fillCircle(this.getX(), this.getY(), this.r);
    }
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Sets velocity.
     * @param v velocity.
     */
    public void setVelocity(Velocity v) {
        this.v = v;
    }

    /**
     * Sets velocity.
     * @param dx x of velocity.
     * @param dy y of velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Accessor of velocity.
     * @return velocity.
     */
    public Velocity getVelocity() {
        return v;
    }

    /**
     * Moves one step.
     */
    public void moveOneStep() {
        double offsetX;
        double offsetY;
        if (this.v == null) {
            return;
        }
        Point nextCenter = this.v.applyToPoint(this.center); //next center of ball with current velocity
        Line trajectory = new Line(this.center, nextCenter); //trajectory line
//        Shapes.Line trajectoryHelper = new Shapes.Line(this.center, this.v.applyToPoint(nextCenter));
        CollisionInfo collisionInfo = gameEnv.getClosestCollision(trajectory);
        if (collisionInfo != null) { //if there is a collision
            Point p = collisionInfo.collisionPoint();
            System.out.println("collision point x: " + p.getX() + " y: " + p.getY());
            System.out.println(collisionInfo.collisionObject().getCollisionRectangle().toString());
            double dx = this.v.getDx();
            double dy = this.v.getDy();
            System.out.println("dx: " + dx + " dy: " + dy);
            double buffer = 5.0; //small step to prevent tunneling
            double length = Math.sqrt(dx * dx + dy * dy);
 //           System.out.println("length: " + length);
            if (length != 0) {
                 offsetX = dx / length * buffer;
                 offsetY = dy / length * buffer;
            } else {
                 offsetX = dx / buffer;
                 offsetY = dy / buffer;
            }
            this.center = new Point(p.getX() - offsetX, p.getY() - offsetY); //set new center with offset
            System.out.println("center x: " + this.center.getX() + " y: " + this.center.getY());
            this.v = collisionInfo.collisionObject().hit(p, this.v); //getting new velocity after hit
            System.out.println("New Sprites.Velocity x: " + this.v.getDx() + " y: " + this.v.getDy());
            this.center = new Point(this.center.getX() + Math.signum(this.v.getDx()) * 0.1,
                    this.center.getY() + Math.signum(this.v.getDy()) * 0.1);
            if (this.center.getX() <= 20 || this.center.getX() >= 780
                    || this.center.getY() <= 20 || this.center.getY() >= 580) { //if ball outside of borders
                this.setVelocity(Velocity.fromAngleAndSpeed(90, this.getVelocity().getSpeed()));
                this.setCenter(new Point(69, 420));
            }
        } else { //next point if no collisions
            this.center = nextCenter;
        }
    }

    /**
     * Setting velocity for ball.
     * @param ball ball object.
     */
    public void setVelocity(Ball ball) {
        Velocity v = Velocity.fromAngleAndSpeed(90, MAX_SPEED / (ball.getSize() + 1));
        if (ball.getSize() >= 50) {
            v = Velocity.fromAngleAndSpeed(90, 1);
            ball.setVelocity(v);
        }
        ball.setVelocity(v);
    }

    /**
     * Adding Sprites.Ball to game environment.
     * @param g game object.
     */
    public void addToGame(Game g) {
        this.setVelocity(Velocity.fromAngleAndSpeed(90, 4));
        g.addSprite(this);
    }

    /**
     * Setter of center.
     * @param center point.
     */
    public void setCenter(Point center) {
        this.center = center;
    }
}
