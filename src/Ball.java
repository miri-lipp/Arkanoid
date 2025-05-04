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
    public void timePassed(double dt) {
        this.moveOneStep(dt);
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
     * @param dt delta time parameter for bugs.
     */
    public void moveOneStep(double dt) {
        double offsetX = 0;
        double offsetY = 0;
        if (this.v == null) {
            return;
        }
        if (dt < 0.001) {
            dt = 0.016; //60 fps
        }  else if (dt > 0.05) {
            dt = 0.05; // avoid large physics jumps
        }
        Point nextCenter = new Point(this.center.getX() + v.getDx(),
                this.center.getY() + v.getDy()); //next center of ball with current velocity
        Line trajectory = new Line(this.center, nextCenter); //trajectory line
        CollisionInfo collisionInfo = gameEnv.getClosestCollision(trajectory);
        if (collisionInfo != null) {
            Point p = collisionInfo.collisionPoint();
            double dx = this.v.getDx();
            double dy = this.v.getDy();
            double buffer = 0.1; //small step to prevent tunneling
            double length = Math.sqrt(dx * dx + dy * dy);
            if (length != 0) {
                 offsetX = dx / length * buffer;
                 offsetY = dy / length * buffer;
            } else {
                 offsetX = dx / buffer;
                 offsetY = dy / buffer;
            }
            this.center = new Point(p.getX() - offsetX, p.getY() - offsetY); //set new center with offset
            this.v = collisionInfo.collisionObject().hit(p, this.v);
            this.setVelocity(this.v);
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
     * Adding Ball to game environment.
     * @param g game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}
