package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int TILE_SIZE = 16;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StdDraw.enableDoubleBuffering();
        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                switch(c) {
                    case 'n':
                        System.out.println("game start");
                        break;
                    case 'q':
                        System.out.println("QUIT");
                        System.exit(0);
                    case 'l':
                        System.out.println("Load Game");
                        break;
                    default:
                }
                System.out.println(c);
            }
            drawMain();
        }
    }
    public void drawMain(){
        StdDraw.clear(StdDraw.BLACK);
        Font headerFont = new Font("Arial",Font.TRUETYPE_FONT,30);
        StdDraw.setFont(headerFont);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5, 0.8, "CS61B: THE GAME");
        Font bodyFont = new Font("Sans Serif", Font.TRUETYPE_FONT,20);
        StdDraw.setFont(bodyFont);
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.45, "Load Game (L)");
        StdDraw.text(0.5, 0.40, "Quit (Q)");
        StdDraw.show();
        StdDraw.pause(100);

    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
