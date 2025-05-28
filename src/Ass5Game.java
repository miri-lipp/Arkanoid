import GameEnvironment.Game;

/**
 * GameEnvironment.Game class.
 * ID 336239652
 */
public class Ass5Game {

    /**
     * Start game method.
     */
    public void startGame() {
        Game game = new Game();
        game.initialize();
        game.run();
    }

    /**
     * Main.
     * @param args string.
     */
    public static void main(String[] args) {
        Ass5Game g = new Ass5Game();
        g.startGame();
    }
}
