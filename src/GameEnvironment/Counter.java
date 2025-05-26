package GameEnvironment;

public class Counter {
    private int counter;
    // add number to current count.
    public Counter(int counter) {
        this.counter = counter;
    }
    public void increase(int number) {
        this.counter += number;
    }
    // subtract number from current count.
    public void decrease(int number) {
        this.counter -= number;
    }
    // get current count.
    public int getValue() {
        return this.counter;
    }
}
