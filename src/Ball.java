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
     * @param center of class Point.
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
    };

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
        if (this.v == null) {
            return;
        }
        Point nextCenter = new Point(this.center.getX() + v.getDx(), this.center.getY() + v.getDy());
        Line trajectory = new Line(this.center, nextCenter);
        CollisionInfo collisionInfo = this.gameEnv.getClosestCollision(trajectory);
        if (collisionInfo != null) {
            Point p = collisionInfo.collisionPoint();
            this.v = collisionInfo.collisionObject().hit(p, this.v);
            this.setVelocity(this.v);
            this.center = this.v.applyToPoint(this.center);
        } else {
            this.center = nextCenter;
        }
//        double remainingDistance = this.v.getSpeed(); // magnitude of velocity
//        Point currentPos = this.center;
//
//        while (remainingDistance > 0.01) {
//            Point nextPos = this.v.applyToPoint(currentPos);
//            double dx = nextPos.getX() - currentPos.getX();
//            double dy = nextPos.getY() - currentPos.getY();
//            double length = Math.sqrt(dx * dx + dy * dy);
//
//            if (length == 0) {
//                break;
//            }
//
//            dx /= length;
//            dy /= length;
//
//            Point extendedNext = new Point(
//                    nextPos.getX() + dx * this.r,
//                    nextPos.getY() + dy * this.r
//            );
//
//            Line trajectory = new Line(currentPos, extendedNext);
//            CollisionInfo collision = this.gameEnv.getClosestCollision(trajectory);
//            if (collision == null) {
//                this.center = nextPos;
//                break;
//            } else {
//                Point collisionPoint = collision.collisionPoint();
//                // Move just before collision point to avoid overlap
//                currentPos = new Point(
//                        collisionPoint.getX() - dx * 0.01,
//                        collisionPoint.getY() - dy * 0.01
//                );
//                this.v = collision.collisionObject().hit(collisionPoint, this.v);
//                remainingDistance -= currentPos.distance(this.center);
//                this.center = currentPos;
//            }
//        }
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
     * Adding Ball to game environment.
     * @param g game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
