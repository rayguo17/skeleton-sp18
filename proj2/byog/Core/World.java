package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class World {
    public int seed;
    public int layer;
    private TETile[][] map;
    public WorldGenerator wg;
    public WorldGenerator.Position playerPos;
    public static final int[][] dirArr = new int[][]{
            {-1,0},{1,0},{0,1},{0,-1},
    };

    public TETile[][] getCurrentWorld(){
        //put all player or object on map!

        TETile[][] renderedMap = TETile.copyOf(map);
        renderedMap[playerPos.x][playerPos.y] = Tileset.PLAYER;
        return renderedMap;
    }
    public World(int w,int h,int randSeed){
        wg = new WorldGenerator(w,h,randSeed);
        map = new TETile[w][h];
        layer =1;
        seed = randSeed;
        //player position should be generated by world generator.
        playerPos = wg.generateNLevel(map,layer);
    }
    public WorldGenerator.Position handleGetInitialWorld(){
    //return wg.generateNLevel(world,layer);
        return null;
    }

    public void handleMovement(char c){
        //decide movement array depends on user input.
        char[] cArr = {'A','D','W','S'};
        List<Character> dirLis = new ArrayList<>();
        for (int i=0;i<cArr.length;i++){
            dirLis.add(cArr[i]);
        }
        int[] targetDir = dirArr[dirLis.indexOf(c)];
        int newX = playerPos.x+targetDir[0];
        int newY = playerPos.y+targetDir[1];
        if(map[newX][newY]!= Tileset.WALL && map[newX][newY]!=Tileset.NOTHING){
            playerPos.x = newX;
            playerPos.y = newY;
        }

    }
    public void handleInteract(char c){

    }

    public static World newWorld(int randSeed){
        return null;
    }
}
