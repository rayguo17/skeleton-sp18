package byog.lab6;

import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);

        MemoryGame game = new MemoryGame(40, 40,seed);
        //game.flashSequence("hello");
        //game.solicitNCharsInput(5);
        game.startGame();
    }

    public MemoryGame(int width, int height,int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);

    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder stb = new StringBuilder();
        for(int i=0;i<n;i++){
            int k = RandomUtils.uniform(rand,0,CHARACTERS.length);
            stb.append(CHARACTERS[k]);
        }
        return stb.toString();
    }

    public void drawFrame(String s) {
        StdDraw.clear(StdDraw.BLACK);
        Font headFont = new Font("Sans Serif",Font.TRUETYPE_FONT,20);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(headFont);
        StdDraw.textLeft(0,0.97*height,String.format("Round: %d",round));
        StdDraw.text(0.5*width,0.97*height,playerTurn?"Type!":"Watch!");
        StdDraw.textRight(width,0.97*height,ENCOURAGEMENT[0]);
        StdDraw.line(0,0.95*height,width,0.95*height);
        Font bodyFont = new Font("Sans Serif", Font.TRUETYPE_FONT,30);
        StdDraw.setFont(bodyFont);
        StdDraw.text(0.5*width,0.5*width,s);
        StdDraw.show();
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for(int i=0;i<letters.length();i++){
            drawFrame(letters.substring(i,i+1));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        while(StdDraw.hasNextKeyTyped()){
            StdDraw.nextKeyTyped();
            //clear all buffer key!
        }
        StringBuilder stb = new StringBuilder();
        while(stb.length()<n){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                stb.append(c);

            }
            drawFrame(stb.toString());
        }
        System.out.println(stb);
        return stb.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Game loop
        round=1;
        while(true){
            playerTurn=false;
            drawFrame(String.format("Round: %d",round));
            StdDraw.pause(1000);
            String str = generateRandomString(round);
            flashSequence(str);
            playerTurn=true;
            String ans = solicitNCharsInput(str.length());
            if(!ans.equals(str)){
                gameOver=true;
                drawFrame(String.format("Game Over! You made it to round: %d",round));
                StdDraw.pause(1000);
                break;
            }
            round++;

        }

    }

}
