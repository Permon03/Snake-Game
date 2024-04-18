import javax.swing.*;

public class Main {
    private static final int windowWidth = 600;
    private static final int windowHeight = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(windowWidth, windowHeight);

        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();

    }
}