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
    //player should be the last one to be rendered.
    public void drawWorld(TETile[][] world,double mouseX,double mouseY,int layer){
        ter.renderFrame(world);
        //item
        StdDraw.setPenColor(StdDraw.WHITE);
        if(mouseX>WIDTH || mouseY>HEIGHT){

        }else{
            String des = world[(int) mouseX][(int)mouseY].description();

            StdDraw.textRight(WIDTH+xOffset-1,HEIGHT+yOffset-1,des);

        }
        //layer
        String layerDes = "layer: "+layer;
        StdDraw.textLeft(0,HEIGHT+yOffset-1,layerDes);
        StdDraw.show();
    }

    public void renderMain(){

    }
}
