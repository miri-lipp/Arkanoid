import java.util.ArrayList;
import java.util.List;

/**
 * Game environment class.
 */
public class GameEnvironment {
    private final List<Collidable> collidables;

    /**
     * Game environment constructor.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }
    // add the given collidable to the environment.

    /**
     * Adding of collidable object to game environment list.
     * @param c collidable object.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }
    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.

    /**
     * getting closest Collision info.
     * @param trajectory of the object.
     * @return Collision info.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double minDistance = Double.MAX_VALUE;
        for (Collidable collidable : this.collidables) {
            Point p = trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
            if (trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle()) != null) {
                double distance = trajectory.start().distance(p);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCollision = new CollisionInfo(p, collidable);
                }
            }
        }
        return closestCollision;
    }
}
