import Shapes.Line;
import Shapes.Point;
import Shapes.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTests {
    @Test
    public void testIntersectionPoints() {
        Rectangle rectangle = new Rectangle(new Point(10, 10),10,10);
        Line line = new Line (new Point (11, 6), new Point (15, 21));
        List<Point> intersections = rectangle.intersectionPoints(line);
        assertEquals(2, intersections.size(), "Should have two intersection points");
        assertTrue(intersections.stream().anyMatch(p -> Math.abs(p.getX() - 12.0667) < 1e-3 && Math.abs(p.getY() - 10) < 1e-3));
        assertTrue(intersections.stream().anyMatch(p -> Math.abs(p.getX() - 14.7333) < 1e-3 && Math.abs(p.getY() - 20) < 1e-3));
    }
    @Test
    public void testIntersectionPointsAtCorners() { //this is the one i'm having troubles with
        Rectangle rectangle = new Rectangle(new Point(10, 10), 10, 10);
        Line line = new Line(new Point(5, 5), new Point(20, 20));

        List<Point> intersections = rectangle.intersectionPoints(line);

        assertEquals(2, intersections.size(), "Should intersect at two corners");

        assertTrue(intersections.stream().anyMatch(p -> Math.abs(p.getX() - 10) < 1e-6 && Math.abs(p.getY() - 10) < 1e-6),
                "Expected intersection at (10,10)");

        assertTrue(intersections.stream().anyMatch(p -> Math.abs(p.getX() - 20) < 1e-6 && Math.abs(p.getY() - 20) < 1e-6),
                "Expected intersection at (20,20)");
    }
    @Test
    public void testIntersectionPointsEmpty() {
        Rectangle rectangle = new Rectangle(new Point(10, 10),10,10);
        Line line = new Line (new Point (5, 5), new Point (3, 1));
        List<Point> intersections = rectangle.intersectionPoints(line);
        assertEquals(0, intersections.size(), "Should have zero intersection points");
    }
    @Test
    public void testClosestIntersectionToTheStartOfLine() {
        Rectangle rectangle = new Rectangle(new Point(15, 5),5,5);
        Line line = new Line (new Point (0, 0), new Point (20, 10));
        Point expected = new Point(15, 7.5);
        Point actual = line.closestIntersectionToStartOfLine(rectangle);
        assertNotNull(actual);
        assertTrue(expected.equals(actual), "Expected intersection at (15, 7.5)");
    }
}
