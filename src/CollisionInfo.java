/**
 * Collision info class.
 */
public class CollisionInfo {
    private final Point p;
    private final Collidable c;

    /**
     * CollisionInfo constructor.
     * @param p collision point.
     * @param c collidable object.
     */
    public CollisionInfo(Point p, Collidable c) {
        this.p = p;
        this.c = c;
    }
    // the point at which the collision occurs.

    /**
     * Getter of Collision point.
     * @return point.
     */
    public Point collisionPoint() {
        return this.p;
    }

    // the collidable object involved in the collision.

    /**
     * Getter of Collision object.
     * @return collidable object.
     */
    public Collidable collisionObject() {
        return this.c;
    }
}
