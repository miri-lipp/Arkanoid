import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class block implements Collidable.
 */
public class Block implements Collidable, Sprite {
    private final double width;
    private final double height;
    private final Point uperleft;

    /**
     * Block constructor.
     * @param width of the rectangle.
     * @param height of the rectangle.
     * @param uperleft Point of the rectangle.
     */
    public Block(double width, double height, Point uperleft) {
        this.width = width;
        this.height = height;
        this.uperleft = uperleft;
    }

//    private void setBounds(int minX, int minY, int maxX, int maxY, Ball ball, int radius) {
//        Velocity v = ball.getVelocity();
//        double nextX = ball.getX() + v.getDx();
//        double nextY = ball.getY() + v.getDy();
//        boolean hitX = false;
//        boolean hitY = false;
//        if (nextX + radius > minX && nextX - radius < maxX) {
//            if ((ball.getY() < minY && nextY + radius > minY && v.getDy() > 0)
//                    || (ball.getY() > maxY && nextY - radius < maxY && v.getDy() < 0)) {
//                hitY = true;
//            }
//        }
//        if (nextY + radius > minY && nextY - radius < maxY) {
//            if ((ball.getX() < minX && nextX + radius > minX && v.getDx() > 0)
//                    || (ball.getX() > maxX && nextX - radius <= maxX && v.getDx() < 0)) {
//                hitX = true;
//            }
//        }
//        double dx = v.getDx();
//        double dy = v.getDy();
//        if (hitX) {
//            dx = -dx;
//        }
//        if (hitY) {
//            dy = -dy;
//        }
//        if (hitX || hitY) {
//            ball.setVelocity(dx, dy);
//        }
//    }
    @Override
    public void timePassed() {
        return;
    }

    /**
     * Draws Blocks.
     * @param surface Drawsurface object.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.BLUE);
        surface.drawRectangle((int) this.uperleft.getX(), (int) this.uperleft.getY(),
                (int) this.width, (int) this.height);
        surface.fillRectangle((int) this.uperleft.getX(), (int) this.uperleft.getY(),
                (int) this.width, (int) this.height);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.uperleft, this.width, this.height);
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        for (Line side : getCollisionRectangle().getSides()) {
            if (side.isPointInside(collisionPoint)) {
                double dx = currentVelocity.getDx();
                double dy = currentVelocity.getDy();
                boolean horizontal = MathChecker.doubleEquals(side.start().getY(), side.end().getY());
                boolean vertical = MathChecker.doubleEquals(side.start().getX(), side.end().getX());
                if (horizontal) {
                    return new Velocity(dx, -dy);
                }
                if (vertical) {
                    return new Velocity(-dx, dy);
                }
            }
        }
        return currentVelocity;
    }

    /**
     * Adding block to game environment.
     * @param g game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
