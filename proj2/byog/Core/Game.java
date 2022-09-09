package byog.Core;

import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.List;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int xOffset = 3;
    public static final int yOffset = 3;
    public static final int TILE_SIZE = 16;
    public WorldManager wm;
    public Renderer renderer;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StdDraw.enableDoubleBuffering();
        renderer = new Renderer(WIDTH,HEIGHT,xOffset,yOffset);
        //should use renderer to print main page as well
        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                switch(c) {
                    case 'n':
                        System.out.println("game start");
                        handleNew();
                        break;
                    case 'q':
                        System.out.println("QUIT");
                        System.exit(0);
                    case 'l':
                        System.out.println("Load Game");
                        if(!handleLoad()){
                            continue;
                        };
                        break;
                    default:
                        int b = c;
                        System.out.printf("%d",b);
                        continue;
                }
                System.out.println(c);
                break;
            }
            drawMain();
        }
        while(StdDraw.hasNextKeyTyped()){
            StdDraw.nextKeyTyped();
        }
        StdDraw.clear(StdDraw.BLACK);
        boolean quitFlag = false;
        while(true){
            //System.out.println("inside while loop");
            if(StdDraw.hasNextKeyTyped()){

                char c = StdDraw.nextKeyTyped();
                System.out.println(c);
                switch (c){
                    case 'W':
                    case 'A':
                    case 'S':
                    case 'D':
                        quitFlag=false;
                        wm.handleMovement(c);
                        break;
                    case 10 :
                        quitFlag=false;
                        wm.handleInteract();
                        break;
                    case ':':
                        quitFlag=true;
                        break;
                    case 'Q':
                        if(quitFlag) {
                            //should save the world.
                            handleSave();
                            System.exit(0);
                        }
                    default:
                }
            }
            //should use other as well, or world should have some status information.
            renderer.drawWorld(wm.getCurrentWorld(),StdDraw.mouseX(),StdDraw.mouseY());


            StdDraw.pause(200);
        }
    }
    public boolean handleLoad(){
        File f = new File("./world.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                WorldManager loadWorld = (WorldManager) os.readObject();
                os.close();
                wm = loadWorld;
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return false;
    }
    public void handleSave(){
        //save the world!!!!
        File f = new File("./world.txt");
        try{
            if(!f.exists()){
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(wm);
            os.close();
        }catch(FileNotFoundException e){
            System.out.println("file not found");
            System.exit(0);
        }catch (IOException e){
            System.out.println(e);
            System.exit(0);
        }
    }
    public void handleNew(){
        while(StdDraw.hasNextKeyTyped()){
            StdDraw.nextKeyTyped();
        }

        StringBuilder stb = new StringBuilder();
        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                if(c>='0' && c<='9'){
                    stb.append(c);

                }
                if(c==10){
                    System.out.println("input done!");
                    break;
                }
            }
            drawInput(stb.toString());
        }
        int seed = Integer.parseInt(stb.toString());
        wm = new WorldManager(WIDTH,HEIGHT,seed);



    }
    public void drawInput(String seed){
        StdDraw.clear(StdDraw.BLACK);
        Font headerFont = new Font("Arial",Font.TRUETYPE_FONT,30);
        StdDraw.setFont(headerFont);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5*(WIDTH), 0.8*HEIGHT, "CS61B: THE GAME");
        Font bodyFont = new Font("Sans Serif", Font.TRUETYPE_FONT,20);
        StdDraw.setFont(bodyFont);
        StdDraw.text(0.5*WIDTH, 0.5*HEIGHT, "input random seed:");
        StdDraw.text(0.5*WIDTH, 0.45*HEIGHT, seed);
        StdDraw.show();
    }
    public void drawMain(){
        StdDraw.clear(StdDraw.BLACK);
        Font headerFont = new Font("Arial",Font.TRUETYPE_FONT,30);
        StdDraw.setFont(headerFont);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5*WIDTH, 0.8*HEIGHT, "CS61B: THE GAME");
        Font bodyFont = new Font("Sans Serif", Font.TRUETYPE_FONT,20);
        StdDraw.setFont(bodyFont);
        StdDraw.text(0.5*WIDTH, 0.5*HEIGHT, "New Game (N)");
        StdDraw.text(0.5*WIDTH, 0.45*HEIGHT, "Load Game (L)");
        StdDraw.text(0.5*WIDTH, 0.40*HEIGHT, "Quit (Q)");
        StdDraw.show();
        //StdDraw.pause(100);

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
