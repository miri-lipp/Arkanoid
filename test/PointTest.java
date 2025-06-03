import Shapes.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PointTest {
    @Test
    public void testDistance() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        assertEquals(5.0, p1.distance(p2), 0.0001);
    }
    @Test
    public void testEqualsSameCoordinates() {
        Point p1 = new Point(1.0, 2.0);
        Point p2 = new Point(1.0, 2.0);
        assertTrue(p1.equals(p2)); // using your custom method
    }

    @Test
    public void testEqualsDifferentCoordinates() {
        Point p1 = new Point(1.0, 2.0);
        Point p2 = new Point(2.0, 3.0);
        assertFalse(p1.equals(p2)); // using your custom method
    }

    @Test
    public void testEqualsWithPrecision() {
        Point p1 = new Point(1.00000001, 2.0);
        Point p2 = new Point(1.00000002, 2.0);
        assertTrue(p1.equals(p2)); // assuming MathChecker.doubleEquals handles precision tolerance
    }

    @Test
    public void testEqualsNull() {
        Point p1 = new Point(1.0, 2.0);
        assertFalse(p1.equals(null)); // should avoid NullPointerException!
    }

}
