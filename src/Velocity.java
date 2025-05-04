/**
 * Velocity class.
 */
public class Velocity {
    // constructor
    private final double dx;
    private final double dy;

    /**
     * Velocity constructor.
     * @param dx double.
     * @param dy double.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Velocity constructor.
     * @param angle angle.
     * @param speed speed of object.
     * @return new velocity in coordinates x and y.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = speed * Math.cos(angle);
        double dy = -speed * Math.sin(angle);
        if (Math.abs(dx) < 0.01) {
            dx = 0.05;
        }
        return new Velocity(dx, dy);
    }

    /**
     * Getter of speed.
     * @return speed.
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     * @param p Point.
     * @param dt delta time.
     * @return Point.
     */
    public Point applyToPoint(Point p, double dt) {
        return new Point(p.getX() + this.dx * dt, p.getY() + this.dy * dt);
    }

    /**
     * Accessor of dx.
     * @return x coordinates of velocity.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Accessor of dy.
     * @return y coordinate of velocity.
     */
    public double getDy() {
        return dy;
    }
}
