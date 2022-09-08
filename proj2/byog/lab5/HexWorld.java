package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    public static void addHexagon(Position p, int s, TETile[][] world,TETile t){
        for(int i= p.c;i<p.c+s*2;i++){
            if(i>=world[0].length || i<0){
                continue;
            }
            int layer = i-p.c;
            if(layer>=s){
                layer = 2*s-1-layer;
            }
            for(int j=p.r-layer;j<p.r+s+layer;j++){
                if(j>=world.length||j<0){
                    continue;
                }
                world[j][i]=t;
            }
        }


    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.FLOOR;
            case 4: return Tileset.SAND;
            default: return Tileset.GRASS;
        }
    }
    public static void printHexagon(TETile[][] world){
        //world .length
        int length = world.length;
        int s = lengthToSize(length);
        Position startPos = getStartPos(length,s);
        for (int i=0;i<5;i++){
            int layer =i;
            int newR = startPos.r+layer*(2*s-1);
            if(layer >2){
                layer = 6-layer-2;
            }
            int newC = startPos.c-layer*s;
            Position newPos = new Position(newR,newC);
            printRowHex(world,newPos,layer+3,s);


        }
    }
    private static void printRowHex(TETile[][] world, Position startingPos,int nums,int s ){
        for(int i=0;i<nums;i++){
            int newC = startingPos.c+i*2*s;
            Position newPos = new Position(startingPos.r,newC);
            TETile t = randomTile();
            addHexagon(newPos,s,world,t);
        }
    }
    private static Position getStartPos(int length,int s){
        int center = length/2;
        Position p = new Position(center-4*s,center-(3*s));
        return p;
    }
    private static int lengthToSize(int length){
        int size = length/10;
        return size;
    }
    public static void testAddHexagon(){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        for( int i=0;i<4;i++){
            TETile t = randomTile();
            Position newPos = new Position(5, 10*i);
            addHexagon(newPos,i+2,tiles,t);
        }
        ter.renderFrame(tiles);
    }
    public static void testPrintHexagon(){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        printHexagon(tiles);
        ter.renderFrame(tiles);
    }
    public static void testAxis(){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        for (int x=0;x<WIDTH-5;x+=1){
            tiles[x][0]=randomTile();
        }
        ter.renderFrame(tiles);
    }
    public static void main(String[] args) {
        testPrintHexagon();
    }
}
