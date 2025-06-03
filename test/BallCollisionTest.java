import Collidables.Collidable;
import Collidables.CollisionInfo;
import GameEnvironment.GameEnvironment;
import Shapes.Point;
import Sprites.Ball;
import Sprites.Velocity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.awt.Color;

public class BallCollisionTest {

    @Test
    public void testBallCollisionChangesVelocity() {
        // Create mocks
        GameEnvironment mockEnv = mock(GameEnvironment.class);
        Collidable mockCollidable = mock(Collidable.class);

        // Create Ball
        Ball ball = new Ball(new Point(0, 0), 5, Color.RED, mockEnv);
        Velocity originalVelocity = new Velocity(5, 0);
        ball.setVelocity(originalVelocity);

        // Define collision point and new velocity after collision
        Point collisionPoint = new Point(2.5, 0); // halfway on path
        Velocity newVelocity = new Velocity(-5, 0); // reflect horizontally

        // Prepare mocked behavior
        when(mockEnv.getClosestCollision(any())).thenReturn(
                new CollisionInfo(collisionPoint, mockCollidable)
        );
        when(mockCollidable.hit(eq(collisionPoint), eq(originalVelocity), eq(ball)))
                .thenReturn(newVelocity);

        // Act
        ball.moveOneStep();

        // Assert: Ball should now have the new velocity
        assertEquals(newVelocity, ball.getVelocity(), "Ball should update velocity after collision");

        // Assert: Ball position should be just before the collision point
        Point newCenter = new Point(ball.getX(), ball.getY());
        assertTrue(newCenter.getX() < collisionPoint.getX(),
                "Ball should be slightly before collision point on X-axis");
    }
}
