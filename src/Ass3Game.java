/**
 * Game class.
 * ID 336239652
 */
public class Ass3Game {
    private Game game;

    /**
     * Start game method.
     */
    public void startGame() {
        this.game = new Game();
        game.initialize();
        game.run();
    }

    /**
     * Main.
     * @param args string.
     */
    public static void main(String[] args) {
        Ass3Game g = new Ass3Game();
        g.startGame();
    }
}
