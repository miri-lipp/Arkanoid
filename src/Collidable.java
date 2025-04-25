/**
 * Collidable interface.
 */
public interface Collidable {
    // Return the "collision shape" of the object.

    /**
     * Can only collide with rectangles.
     * @return rectangle object that ball has collided with.
     */
    Rectangle getCollisionRectangle();

    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).

    /**
     * Velocity change.
     * @param collisionPoint collision points of ball with rectangle.
     * @param currentVelocity current Velocity.
     * @return new Velocity of ball.
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}
