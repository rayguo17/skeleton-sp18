package byog.Core;

import byog.Core.Items.Door;
import byog.Core.Items.Interactable;
import byog.Core.Items.Player;
import byog.Core.Utils.RandomUtils;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.io.Serializable;
import java.util.*;

public class WorldGenerator implements Serializable {
    private  int WIDTH;
    private  int HEIGHT;
    public static double ratio = 0;
    public int RANDOM_SEED;
    public static Random rand;
    public static int filled = 0;
    public static boolean connMat[][];
    public static List<Room> rooms;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        WorldGenerator wg = new WorldGenerator(80,30,1234);
        ter.initialize(wg.WIDTH,wg.HEIGHT);
        TETile[][] tiles = new TETile[wg.WIDTH][wg.HEIGHT];
        wg.generateNLevel(tiles,1);

        ter.renderFrame(tiles);
        StdDraw.pause(5000);
        wg.generateNLevel(tiles,2);
        ter.renderFrame(tiles);
        StdDraw.pause(5000);
    }

    public WorldGenerator(int w,int h, int seed){
        WIDTH = w;
        HEIGHT = h;
        rand = new Random(seed);
        RANDOM_SEED = seed;
    }
    //world generate and item generate should have different random? or could be
    public List<Interactable> generateNLevel(TETile[][] world, int n){
        rand= new Random(RANDOM_SEED);
        List<Interactable> layerList = null;
        for(int i=0;i<n+1;i++){
            layerList = new LinkedList<>();
            generateEntry(world);
            //place door and place player
            Door downward = placeDoor(world,true);
            if(i>=1){
                //layer larger than 2 should have two door one downward, one upward.
                //player should be spawn in upward door place!

                Door upward = placeDoor(world,false);
                layerList.add(upward);
                layerList.add(new Player(upward.getPosition().copyPos()));
            }else{
                layerList.add(playerStartPos());
            }
            layerList.add(downward);
            //should have two different door for n>1;
            //world[p.x][p.y] = Tileset.PLAYER;
        }
        return layerList;
        //generate door and other object
    }
    //should have a method return the TileMap
    public void generateEntry(TETile[][] world){

        clearTiles(world);
        while(true){
            worldGenerator(world);
            connectAll(world);
            if(checkConnect()){
                System.out.println("All rooms are connected!");
                break;
            }else {
                System.out.println("Not all rooms are connected...");
                clearTiles(world);
            }
        }

    }
    private void clearTiles(TETile[][] world){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    public static void printConnMat(){
        for(int i=0;i<connMat.length;i++){
            System.out.printf("[ ");
            for(int j=0;j<connMat[i].length;j++){
                System.out.printf("%d ",connMat[i][j]?1:0);
            }
            System.out.println("]");
        }
    }
    private static boolean checkConnect(){
        boolean[] connectedSeq = new boolean[rooms.size()];
        int connected = 0;
        Stack<Integer> waitList = new Stack<>();
       waitList.push(0);

       while(waitList.size()>0){
           int target = waitList.pop();
           if(!connectedSeq[target]){
               connectedSeq[target]=true;
               connected++;
           }else{
               continue;
           }

           for(int i=0;i<connMat[target].length;i++){
               if(connMat[target][i]&& !connectedSeq[i]){
                   waitList.push(i);
               }
           }
       }

       return connected==rooms.size();
    }
    public static class Position implements Serializable{
        public int x;
        public int y;
        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
        public boolean equals(Position p) {
            return p.x==x && p.y==y;
        }
        public Position copyPos(){
            return new Position(x,y);
        }
    }

    public Player playerStartPos(){
        Room target = rooms.get(RandomUtils.uniform(rand,rooms.size()-1));
        //should mark it in tile?
        Player p = new Player(target.randPos(rand));
        return p;
    }
    private void worldGenerator(TETile[][] world){
        //calculate
        //each time get a new position:
        rooms = new LinkedList<>();

        ratio=0;
        filled=0;
        while(ratio<0.4){
            int x = RandomUtils.uniform(rand,0,WIDTH);
            int y = RandomUtils.uniform(rand,0,HEIGHT);
            int width = RandomUtils.uniform(rand,5,15);
            int height = RandomUtils.uniform(rand,5,15);
            Position p = new Position(x,y);
            if (!willCollapse(p,width,height,world)){
                addRoom(p,width,height,world);
                Room room = new Room(p,width,height);
                rooms.add(room);
                filled += width*height;
                ratio = ((double)filled)/(WIDTH*HEIGHT);
            }
            //System.out.println(ratio);
        }
        connMat = new boolean[rooms.size()][rooms.size()];
        //System.out.println(rooms.size());



    }
    private static void addRoom(Position p, int width, int height, TETile[][] world){
        //check collapse
        //room size 2-10
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                if(i==0||i==width-1||j==0||j==height-1){
                    world[p.x+i][p.y+j]=Tileset.WALL;
                }else{
                    world[p.x+i][p.y+j]= Tileset.FLOOR;
                }

            }
        }


    }
    private static boolean willCollapse(Position p, int width,int height, TETile[][] world){
        for(int i=0;i<width;i++){
            for(int j =0;j<height;j++){
                if (p.x+i>=world.length){
                    return true;
                }
                if(p.y+j>=world[p.x+i].length){
                    return true;
                }
                if(world[p.x+i][p.y+j]!=Tileset.NOTHING){
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean connect(Room a, Room b,TETile[][] world ){
        //collapse or not,
        Room.PosDir p = a.isCollapse(b,rand);
        if (p!=null){
            Position start = p.start;
            Position end = p.end;
            p2pConnect(start,end,p.d,world);
            int i =rooms.indexOf(a);
            int j = rooms.indexOf(b);
            connMat[i][j]=true;
            connMat[j][i]=true;
            return true;
        }
        return false;
    }
    private static void p2pConnect(Position start, Position end, Room.Direction d, TETile[][]world){
        int[] dirArr = Room.dirArr[d.ordinal()];
        int tmpX = start.x-dirArr[0];
        int tmpY = start.y-dirArr[1];
        if(world[tmpX][tmpY]==Tileset.NOTHING){
            world[tmpX][tmpY]=Tileset.WALL;
        }
        int tsideX1 = tmpX+dirArr[1];
        int tsideY1 = tmpY+dirArr[0];
        if(world[tsideX1][tsideY1]==Tileset.NOTHING){
            world[tsideX1][tsideY1]=Tileset.WALL;
        }

        //right/top
        int tsideX2 = tmpX-dirArr[1];
        int tsideY2 = tmpY-dirArr[0];
        if(world[tsideX2][tsideY2]==Tileset.NOTHING){
            world[tsideX2][tsideY2]=Tileset.WALL;
        }
        while(tmpX != end.x || tmpY!=end.y){
            tmpX+=dirArr[0];
            tmpY+=dirArr[1];

            world[tmpX][tmpY]=Tileset.FLOOR;
            //wall of hallway
            //left/bottom
            int sideX1 = tmpX+dirArr[1];
            int sideY1 = tmpY+dirArr[0];
            if(world[sideX1][sideY1]==Tileset.NOTHING){
                world[sideX1][sideY1]=Tileset.WALL;
            }

            //right/top
            int sideX2 = tmpX-dirArr[1];
            int sideY2 = tmpY-dirArr[0];
            if(world[sideX2][sideY2]==Tileset.NOTHING){
                world[sideX2][sideY2]=Tileset.WALL;
            }


        }
        tmpX+=dirArr[0];
        tmpY+=dirArr[1];
        if(world[tmpX][tmpY]==Tileset.NOTHING){
            world[tmpX][tmpY]=Tileset.WALL;
        }
        int sideX1 = tmpX+dirArr[1];
        int sideY1 = tmpY+dirArr[0];
        if(world[sideX1][sideY1]==Tileset.NOTHING){
            world[sideX1][sideY1]=Tileset.WALL;
        }

        //right/top
        int sideX2 = tmpX-dirArr[1];
        int sideY2 = tmpY-dirArr[0];
        if(world[sideX2][sideY2]==Tileset.NOTHING){
            world[sideX2][sideY2]=Tileset.WALL;
        }


    }
    private static class IndexDis implements Comparable<IndexDis>{
        public int Dis;
        public int index;
        @Override
        public int compareTo(IndexDis indexDis) {
            return Dis-indexDis.Dis;
        }
    }
    private  void connectAll(TETile[][] world){
        Random random = new Random(RANDOM_SEED);
        for (int i=0;i<rooms.size();i++){
            PriorityQueue<IndexDis> pd = new PriorityQueue<>();
            for(int j=0;j<rooms.size();j++){
                if(i==j){
                    continue;
                }
                int dis = calDis(rooms.get(i),rooms.get(j));
                IndexDis d = new IndexDis();
                d.Dis=dis;
                d.index=j;
                pd.add(d);
            }
            int cnt=0;
            while(cnt<2){
                IndexDis target = pd.remove();
                int j = target.index;
                if(!connMat[i][j]){
                    connect(rooms.get(i),rooms.get(j),world);

                }
                cnt++;
            }
        }
    }
    private Door placeDoor(TETile[][] world,boolean downward){
        //randomly choose a room, and then randomly choose a wall if it is wall,replace it with door.

        int roomNumber = RandomUtils.uniform(rand,0,rooms.size()-1);
        Room selected = rooms.get(roomNumber);
        while(true){
            Position p = selected.randomWall(rand);
            //System.out.printf("x: %d, y: %d\n",p.x,p.y);
            if(world[p.x][p.y]==Tileset.WALL && validPos(p.x+1,p.y) && validPos(p.x-1,p.y) && validPos(p.x,p.y+1) && validPos(p.x, p.y-1)){
                //first up/down
                if((world[p.x+1][p.y]==Tileset.WALL && world[p.x-1][p.y]==Tileset.WALL && (world[p.x][p.y+1]==Tileset.FLOOR || world[p.x][p.y-1]==Tileset.FLOOR)) ||( world[p.x][p.y+1]==Tileset.WALL&&world[p.x][p.y-1]==Tileset.WALL && (world[p.x+1][p.y]==Tileset.FLOOR || world[p.x-1][p.y]==Tileset.FLOOR)) ){
                    world[p.x][p.y]=Tileset.LOCKED_DOOR;
                    return new Door(p.x,p.y,downward?Door.Dir.DOWNWARD: Door.Dir.UPWARD);
                    //don't change the world, instead place it in world!
                }

            }
        }
    }
    private boolean validPos(int x, int y){
        if(x<0 || x>WIDTH || y<0 || y>HEIGHT){
            return false;
        }
        return true;
    }
    private static int calDis(Room a, Room b){
        Room.PosDir p = a.isCollapse(b,rand);
        if (p!=null){
            switch (p.d){
                case UP:
                    return p.end.y-p.start.y;

                case DOWN:
                    return p.start.y-p.end.y;
                case LEFT:
                    return p.start.x-p.end.x;
                case RIGHT:
                    return p.end.x-p.start.x;

            }
        }
        return 100;
    }

    public static void testConnect(TETile[][] world){
        //connect up and bottom
        Position p1 = new Position(10,5);
        Room r1 = new Room(p1,10,5);
        addRoom(p1,10,5,world);

        Position p2 = new Position(19,13);
        Room r2 = new Room(p2,3,5);
        addRoom(p2,10,5,world);

        connect(r1,r2,world);

        //connect left and right
        Position p3 = new Position(25,5);
        Room r3 = new Room(p3,5,20);
        addRoom(p3,5,20,world);

        Position p4 = new Position(32,10);
        Room r4 = new Room(p4,3,8);
        addRoom(p4,3,8,world);

        connect(r3,r4,world);
    }
    public static void testEnum(){
        System.out.println(Room.Direction.UP.ordinal());
    }





}
