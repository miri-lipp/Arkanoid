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
        double length = 2 * Math.PI * r;
        return (int) length;
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
        this.moveOneStep(800, 600, 0, 0);
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
     * @param heightMax max height.
     * @param widthMax max width.
     * @param heightMin minimum height.
     * @param widthMin minimum width.
     */
    public void moveOneStep(int heightMax, int widthMax, int heightMin, int widthMin) {
        if (this.v == null) {
            return;
        }
        Point nextCenter = this.getVelocity().applyToPoint(this.center);
        Line trajectory = new Line(this.center, nextCenter);
        if (this.gameEnv.getClosestCollision(trajectory) != null) {
            Point p = this.gameEnv.getClosestCollision(trajectory).collisionPoint();
            this.v = this.gameEnv.getClosestCollision(trajectory).collisionObject().hit(p, this.v);
        }
        if (nextCenter.getX() + this.r > heightMax || nextCenter.getX() - this.r < heightMin) {
            this.v = new Velocity(-this.v.getDx(), this.v.getDy());
        }
        if (nextCenter.getY() + this.r > widthMax || nextCenter.getY() - this.r < widthMin) {
            this.v = new Velocity(this.v.getDx(), -this.v.getDy());
        }
        this.center = this.getVelocity().applyToPoint(this.center);
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
