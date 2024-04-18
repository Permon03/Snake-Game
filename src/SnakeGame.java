import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private final int windowWidth;
    private final int windowHeight;
    private static final int tileSize = 25;



    private class Tile {
        int x;
        int y;

        Tile (int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private Tile snakeHead;
    private final int startPosX = 5;
    private final int startPosY = 5;
    private ArrayList<Tile> snakeBody;
    private Tile food;
    private Random random;
    private Timer gameLoop;
    private int velocityX;
    private int velocityY;
    private boolean gameOver = false;

    public SnakeGame (int windowWidth, int windowHeight){
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(startPosX, startPosY);
        snakeBody = new ArrayList<>();
        food = new Tile (10, 10);
        random = new Random();
        placeFood();
        velocityX = 1;
        velocityY = 0;

        gameLoop = new Timer (100, this);
        gameLoop.start();
    }

    public void paintComponent (Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw (Graphics g){

        // Food
        g.setColor(Color.RED);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        // Snake Head
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        // Snake Body
        for(Tile snakeTile : snakeBody){
            g.fillRect(snakeTile.x*tileSize, snakeTile.y*tileSize, tileSize, tileSize);
        }

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over: " + snakeBody.size() + " -- Press R to restart", tileSize-16, tileSize);
        } else {
            g.drawString("Score: " + snakeBody.size(), tileSize-16, tileSize);
        }

    }

    public void placeFood(){
        food.x = random.nextInt(windowWidth/tileSize);
        food.y = random.nextInt(windowHeight/tileSize);
    }

    public void move(){

        if (collison(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        for (int i = snakeBody.size()-1; i>=0 ; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile snakePrev = snakeBody.get(i-1);
                snakePart.x = snakePrev.x;
                snakePart.y = snakePrev.y;
            }
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for (Tile snakePart : snakeBody){
            if (collison(snakeHead, snakePart)){
                gameOver = true;
            }
        }
        if ((snakeHead.x < 0) || (snakeHead.y < 0) || (snakeHead.x*tileSize > windowWidth) || (snakeHead.y * tileSize > windowHeight)){
            gameOver = true;
        }
    }

    public boolean collison (Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void reset() {
        gameOver = false;
        snakeHead = new Tile(startPosX, startPosY);
        snakeBody = new ArrayList<>();
        velocityX = 1;
        velocityY = 0;
        placeFood();
        gameLoop.restart();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver){
            gameLoop.stop();
        }
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                if (velocityY != 1){
                    velocityY = -1;
                    velocityX = 0;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (velocityY != -1){
                    velocityY = 1;
                    velocityX = 0;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (velocityX != 1){
                    velocityX = -1;
                    velocityY = 0;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (velocityX != -1){
                    velocityY = 0;
                    velocityX = 1;
                }
                break;
            case KeyEvent.VK_R:
                reset();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
