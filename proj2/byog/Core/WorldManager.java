package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class WorldManager implements Serializable {
    public int WIDTH;
    public int HEIGHT;
    public WorldGenerator wg;
    public World currentWorld;
    public List<World> archiveWorld;
    public int RANDOM_SEED;
    //will only be invocated when generating new game!
    public WorldManager(int w, int h, int seed){
        wg = new WorldGenerator(w,h,seed);
        RANDOM_SEED = seed;
        currentWorld = new World(w,h,0,wg);
        archiveWorld = new LinkedList<>();
        archiveWorld.add(currentWorld);
        WIDTH = w;
        HEIGHT = h;
    }

    public TETile[][] getCurrentWorld() {
        return currentWorld.getCurrentWorld();
    }
    public void handleMovement(char c){
        currentWorld.handleMovement(c);
    }
    public void handleInteract(){
        currentWorld.handleInteract(this);
    }

    public int createNewWorld(){
        //create next layer. add it into list.
        int layer = archiveWorld.size();
        World newWorld = new World(WIDTH,HEIGHT,layer,wg);
        archiveWorld.add(newWorld);
        return archiveWorld.size()-1;
    }
    public int getCurrentLayer(){
        return currentWorld.layer;
    }
    public boolean setActive(int layer){
        try {
            currentWorld = archiveWorld.get(layer);
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean isLastOne(int layer){
        return (archiveWorld.size()-1)==layer;
    }
}
