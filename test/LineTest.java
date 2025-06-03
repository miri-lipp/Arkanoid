import Shapes.Line;
import Shapes.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LineTest {
    @Test
    public void testLength() {
        Line line = new Line(new Point(0, 0), new Point(3, 4));
        assertEquals(5.0, line.length(), 0.0001);
    }
    @Test
    public void compareLengthAndDistance() {
        Line line = new Line(new Point(0, 0), new Point(3, 4));
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        assertEquals(p1.distance(p2), line.length(), 0.0001);
    }
    @Test
    public void testMiddle() {
        Line line = new Line(new Point(0, 0), new Point(4, 4));
        Point mid = line.middle();
        assertEquals(2.0, mid.getX(), 0.0001);
        assertEquals(2.0, mid.getY(), 0.0001);
    }
    @Test
    public void startPoint() {
        Line line = new Line(new Point(0, 0), new Point(3, 4));
        Point p1 = new Point(0, 0);
        assertTrue(p1.equals(line.start()));
    }
    @Test
    public void endPoint() {
        Line line = new Line(new Point(0, 0), new Point(3, 4));
        Point p1 = new Point(3, 4);
        assertTrue(p1.equals(line.end()));
    }
    @Test
    public void testIsIntersecting_parallelLines() {
        Line l1 = new Line(new Point(0, 0), new Point(1, 1));
        Line l2 = new Line(new Point(0, 1), new Point(1, 2)); // parallel with same slope but different intercept
        assertFalse(l1.isIntersecting(l2));
    }
    @Test
    public void testIsIntersecting_intersectingLines() {
        Line l1 = new Line(new Point(0,0), new Point(2,2));
        Line l2 = new Line(new Point(0,2), new Point(2,0));
        assertTrue(l1.isIntersecting(l2));
    }
    @Test
    public void testEquals_reversedPoints() {
        Line l1 = new Line(new Point(0,0), new Point(1,1));
        Line l2 = new Line(new Point(1,1), new Point(0,0));
        assertTrue(l1.equals(l2));
    }
    @Test
    public void testCalculateIntersectionPoint_verticalLine() {
        Line l1 = new Line(new Point(1,0), new Point(1,2)); // vertical
        Line l2 = new Line(new Point(0,1), new Point(2,1));
        Point intersection = l1.calculateIntersectionPoint(l2);
        assertTrue(new Point(1,1).equals(intersection));
    }
    @Test
    public void testCalculateIntersectionPoint_horizontalLine() {
        Line l1 = new Line(new Point(1,0), new Point(1,2));
        Line l2 = new Line(new Point(0,1), new Point(2,1));
        Point intersection = l1.calculateIntersectionPoint(l2);
        assertTrue(new Point(1,1).equals(intersection));
    }
    @Test
    public void testIsPointWithinLine() {
        Line l1 = new Line(new Point(0,0), new Point(2,4));
        assertTrue(l1.isWithin(0.7, 1.4, l1));
    }
}
