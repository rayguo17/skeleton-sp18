package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class Renderer {
    private TERenderer ter;
    public int WIDTH;
    public int HEIGHT;
    public int xOffset;
    public int yOffset;


    public Renderer(int w, int h, int x, int y){
            ter = new TERenderer();
            WIDTH = w;
            HEIGHT = h;
            xOffset = x;
            yOffset = y;
            ter.initialize(w+x,h+y,0,0);
    }
    public void drawWorld(TETile[][] world,double mouseX,double mouseY){
        ter.renderFrame(world);
        if(mouseX>WIDTH || mouseY>HEIGHT){

        }else{
            String des = world[(int) mouseX][(int)mouseY].description();
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textRight(WIDTH+xOffset-1,HEIGHT+yOffset-1,des);
            StdDraw.show();
        }

    }

    public void renderMain(){

    }
}
