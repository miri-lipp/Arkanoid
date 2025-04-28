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
        Point nextCenter = this.getVelocity().applyToPoint(this.center);
        // EXTEND the trajectory by radius
        double dx = nextCenter.getX() - this.center.getX();
        double dy = nextCenter.getY() - this.center.getY();
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            // Normalize direction vector
            dx = dx / length;
            dy = dy / length;
        }
        // Create a new point slightly "ahead" by radius
        Point extendedNextCenter = new Point(
                nextCenter.getX() + dx * this.r,
                nextCenter.getY() + dy * this.r
        );
        // Create a new trajectory line
        Line trajectory = new Line(this.center, extendedNextCenter);

        CollisionInfo collisionInfo = this.gameEnv.getClosestCollision(trajectory);
        if (collisionInfo != null) {
            Point p = collisionInfo.collisionPoint();
            this.v = collisionInfo.collisionObject().hit(p, this.v);
            this.center = this.v.applyToPoint(this.center);
        } else {
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
     * Adding Ball to game environment.
     * @param g game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
