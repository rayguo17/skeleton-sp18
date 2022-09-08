package byog.Core;

import java.util.Random;

public class Room{
    public WorldGenerator.Position p;
    public int width;
    public int height;
    enum Direction{
        LEFT,RIGHT,UP,DOWN
    }
    public static final int[][] dirArr = new int[][]{
            {-1,0},{1,0},{0,1},{0,-1}
    };
    public Room(WorldGenerator.Position p, int w, int y){
        this.p = p;
        this.width = w;
        this.height = y;
    }
    public static class PosDir{
        WorldGenerator.Position start;
        Direction d;
        WorldGenerator.Position end;
        public PosDir(WorldGenerator.Position s, WorldGenerator.Position e, Direction d){
            start=s;
            end=e;
            this.d=d;
        }
    }
    public WorldGenerator.Position  randomWall(Random random){

        int dirNum = RandomUtils.uniform(random,0,Direction.values().length);
        int x=0;
        int y=0;
        switch (Direction.values()[dirNum]){
            case UP:
                x = RandomUtils.uniform(random,p.x,p.x+width);
                y=p.y+height;
                break;
            case DOWN:
                x = RandomUtils.uniform(random,p.x,p.x+width);
                y = p.y;
                break;
            case LEFT:
                x = p.x;
                y = RandomUtils.uniform(random,p.y,p.y+height);
                break;
            case RIGHT:
                x = p.x+width;
                y = RandomUtils.uniform(random,p.y,p.y+height);
        }
        return new WorldGenerator.Position(x,y);
    }
    public WorldGenerator.Position randPos(Random rand){
        int randX = RandomUtils.uniform(rand,p.x+1,p.x+width-1);
        int randY = RandomUtils.uniform(rand,p.y+1,p.y+height-1);
        return new WorldGenerator.Position(randX,randY);
    }
    public PosDir isCollapse(Room o,Random rand){
        //vertical

        int verBottom = Math.max(p.y,o.p.y);
        int verUpper = Math.min(p.y+height-1,o.p.y+o.height-1);
        Direction d;
        if (verUpper>=verBottom){
            //randomly choose between min and max
            int chosenY;
            if(verBottom==verUpper){
                chosenY=verBottom;
            }else{
                chosenY=RandomUtils.uniform(rand,verBottom==0?verBottom+1:verBottom,verUpper);
            }

            //tell left or right?
            int chosenX;
            int endX;

            if(o.p.x>this.p.x){
                //right
                //find the right boundary of this
                chosenX = p.x + width - 2;
                endX = o.p.x+1;
                d=Direction.RIGHT;
            }else{
                //left
                chosenX = p.x+1;
                endX = o.p.x+o.width-2;
                d=Direction.LEFT;
            }
            WorldGenerator.Position start = new WorldGenerator.Position(chosenX,chosenY);
            WorldGenerator.Position end = new WorldGenerator.Position(endX,chosenY);
            return new PosDir(start,end,d);
        }
        //horizontal
        int horLeft = Math.max(p.x,o.p.x);
        int horRight = Math.min(p.x+width-1,o.p.x+o.width-1);
        if(horLeft<=horRight){
            //choose between min and max
            int chosenX;
            if(horLeft==horRight){
                chosenX=horLeft;
            }else{
                chosenX = RandomUtils.uniform(rand,horLeft,horRight);
            }

            int chosenY;
            int endY;
            if(o.p.y>this.p.y){
                //up
                chosenY = this.p.y+height-2;
                endY = o.p.y+1;
                d=Direction.UP;
            }else{
                //down
                d=Direction.DOWN;
                endY=o.p.y+o.height-2;
                chosenY = this.p.y+1;
            }
            WorldGenerator.Position start = new WorldGenerator.Position(chosenX,chosenY);
            WorldGenerator.Position end = new WorldGenerator.Position(chosenX,endY);
            return new PosDir(start,end,d);
        }
        return null;
    }


}