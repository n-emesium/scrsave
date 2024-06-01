import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class ScreenSaverG extends JPanel {
    private static final char DEFAULT_CHAR = '*';
    private static final int DEFAULT_LENGTH = 5, DEFAULT_WIDTH = 5;
    private static final int SQUARE_SIZE = 20;
    private static final int TIMER_DELAY = 250;
    private char[][] screen;
    private int length;
    private int width;
    private char obj;
    private int[] objPosition;
    private int[] movement;
    public ScreenSaverG() {
        this(DEFAULT_LENGTH, DEFAULT_WIDTH, DEFAULT_CHAR);
    }
    public ScreenSaverG(int length, int width) {
        this(length, width, DEFAULT_CHAR);
    }
    public ScreenSaverG(int length, int width, char obj) {
        this.length = length;
        this.width = width;
        this.obj = obj;
        this.screen = new char[length][width];
        initialize();
    }
    private void initialize() {
        fillScreen();
        placeRandom();
        objPosition = findChar();
        movement = generateRandomMovement();
        Timer timer = new Timer(TIMER_DELAY, e -> {
            updateScreen();
            repaint();
        });
        timer.start();
    }
    private void fillScreen() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                screen[i][j] = 'o';
            }
        }
    }
    private void placeRandom() {
        int[] coords = generateRandomPosition();
        screen[coords[0]][coords[1]] = obj;
    }
    private int[] generateRandomPosition() {
        Random rand = new Random();
        int x = rand.nextInt(length);
        int y = rand.nextInt(width);
        return new int[]{x, y};
    }
    private int[] generateRandomMovement() {
        int[][] possibilities = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        Random rand = new Random();
        return possibilities[rand.nextInt(possibilities.length)];
    }
    private int[] findChar() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (screen[i][j] == obj) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    private void updateScreen() {
        int x = objPosition[0];
        int y = objPosition[1];
        screen[x][y] = 'o';
        x += movement[0];
        y += movement[1];
        if (x < 0 || x >= length) {
            movement[0] *= -1;
            x += movement[0] * 2;
        }
        if (y < 0 || y >= width) {
            movement[1] *= -1;
            y += movement[1] * 2;
        }
        objPosition[0] = x;
        objPosition[1] = y;
        screen[x][y] = obj;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (screen[i][j] == obj) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ScreenSaver");
        ScreenSaverG screenSaver = new ScreenSaverG(10, 10);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(screenSaver);
        frame.setSize(screenSaver.width * SQUARE_SIZE + 50, screenSaver.length * SQUARE_SIZE + 50);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScreenSaverG::createAndShowGUI);
    }
}
