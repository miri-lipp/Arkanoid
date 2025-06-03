import GameEnvironment.GameEnvironment;
import Shapes.*;
import Shapes.Point;
import Sprites.Ball;
import Sprites.Velocity;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class BallTest {
    @Test
    public void testBallConstructorAndGetters() {
        Point center = new Point(50, 50);
        Color color = Color.RED;
        GameEnvironment dummyEnv = new GameEnvironment(); // Use a mock or dummy if needed
        Ball ball = new Ball(center, 10, color, dummyEnv);

        assertEquals(50, ball.getX());
        assertEquals(50, ball.getY());
        assertEquals(10, ball.getSize());
        assertEquals(color, ball.getColor());
    }
    @Test
    public void testSetColor() {
        Ball ball = new Ball(new Point(0, 0), 5, Color.RED, new GameEnvironment());
        ball.setColor(Color.BLUE);
        assertEquals(Color.BLUE, ball.getColor());
    }
    @Test
    public void testSetAndGetVelocity() {
        Ball ball = new Ball(new Point(0, 0), 5, Color.RED, new GameEnvironment());
        Velocity v = new Velocity(1, 2);
        ball.setVelocity(v);
        assertEquals(v, ball.getVelocity());
    }
    @Test
    public void testMoveOneStepNoCollision() {
        GameEnvironment env = mock(GameEnvironment.class);
        when(env.getClosestCollision(any(Line.class))).thenReturn(null);

        Ball ball = new Ball(new Point(0, 0), 5, Color.RED, env);
        ball.setVelocity(1, 1);
        ball.moveOneStep();

        assertEquals(1, ball.getX());
        assertEquals(1, ball.getY());
    }
}
