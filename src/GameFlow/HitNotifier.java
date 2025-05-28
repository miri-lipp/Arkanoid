package GameFlow;

/**
 * Hit Notifier.
 */
public interface HitNotifier {

    /**
     * Add hl as a listener to hit events.
     * @param hl Hit listener object.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl hit listener object.
     */
    void removeHitListener(HitListener hl);
}
