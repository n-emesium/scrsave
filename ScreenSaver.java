public class ScreenSaver {
    private static final char defChar = '*';
    private static final int DEFAULT_LENGTH = 5, DEFAULT_WIDTH = 5;
    private char[][] screen;
    private int length;
    private int width;
    private char obj;
    public ScreenSaver() {
        obj = defChar;
        length = DEFAULT_LENGTH;
        width =  DEFAULT_WIDTH;
        initialize();
    }
    public ScreenSaver(int length, int width) {
        obj = defChar;
        this.length = length;
        this.width = width;
        initialize();
    }
    public ScreenSaver(int length, int width, char obj) {
        this.length = length;
        this.width = width;
        this.obj = obj;
        initialize();
    }
    private void initialize() {
        screen = new char[length][width];
        fillScr();
        placeRandom();
        printScr();
    }
    private void fillScr() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                screen[i][j] = 'o';
            }
        }
    }
    private void placeRandom() {
        int[] coords = generateRandom();
        screen[coords[0]][coords[1]] = obj;
    }
    private int[] generateRandom() {
        int x = (int) (Math.random() * (length - 2) + 1);
        int y = (int) (Math.random() * (width - 2) + 1);
        return new int[]{x,y};
    }
    private void printScr() {
        System.out.println("\n");
        for (char[] c : screen) {
            for (char d : c) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
    public void scrSave() { //only public and usable function
        int[] start = genRandMov();
        while (!endScreen()) { //default boolean flag here;
            hitFrame(start);
            mv(start);
            printScr();
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void mv(int[] mvs) { //move command
        //current position:
        int[] coordinates = findChar();
        screen[coordinates[0] + mvs[0]][coordinates[1] + mvs[1]] = obj;
        screen[coordinates[0]][coordinates[1]] = 'o';
    }
    private int[] findChar() {
        for (int i = 0; i < screen.length; i++) {
            for (int j = 0; j < screen[0].length; j++) {
                if (screen[i][j] == obj) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }
    private int[] genRandMov() {
        int[][] possibilities = {{1,1},{1,-1},{-1,1},{-1,-1}};
        int[] choose = possibilities[((int)(Math.random() * possibilities.length))]; 
        return choose;   
    }
    private boolean endScreen() {
        if (screen[0][screen[0].length-1] == obj || screen[screen.length-1][0] == obj || screen[0][0] == obj || screen[screen.length - 1][screen[0].length - 1] == obj) {
            return true;
        }
        return false;
    }
    private void hitFrame(int[] movements) {
        int xv = movements[0];
        int yv = movements[1];
        int[] coordinates = findChar();
        int x = coordinates[0];
        int y = coordinates[1];
        if (x == 0 || x == screen.length - 1) {
            xv *= -1; // Reverse horizontal movement
        }
        // Check for collisions with the top or bottom frame
        if (y == 0 || y == screen[0].length - 1) {
            yv *= -1; // Reverse vertical movement
        }
    
        movements[0] = xv;
        movements[1] = yv;
    }
}