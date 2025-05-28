package GameEnvironment;

/**
 * Counter.
 */
public class Counter {
    private int counter;
    /**
     * Constructor.
     * @param counter object.
     */
    public Counter(int counter) {
        this.counter = counter;
    }

    /**
     * add number to current count.
     * @param number number.
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * subtract number from current count.
     * @param number number.
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * get current count.
     * @return current count.
     */
    public int getValue() {
        return this.counter;
    }
}
