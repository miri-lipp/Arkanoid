package GameFlow;
import Collidables.Block;
import Sprites.Ball;

/**
 * Hit Listener.
 */
public interface HitListener {
    // This method is called whenever the beingHit object is hit.
    // The hitter parameter is the Ball that's doing the hitting.

    /**
     * Hit event.
     * @param beingHit block object.
     * @param hitter ball object.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
