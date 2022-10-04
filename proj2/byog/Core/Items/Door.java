package byog.Core.Items;

import byog.Core.WorldGenerator;
import byog.Core.WorldManager;

public class Door  implements Interactable {
    public WorldGenerator.Position p;
    public Dir status;
    public boolean locked;
    public String ObjectType;

    @Override
    public WorldGenerator.Position getPosition() {
        return p;
    }

    @Override
    public String getTitle() {
        return ObjectType;
    }

    enum Dir{
        UPWARD,DOWNWARD,
    }

    public Door(int x, int y, Dir s){
        p = new WorldGenerator.Position(x,y);
        status = s;//up ward or downward
        locked = true;//initially locked. should place key somewhere.
        ObjectType = "Locked Door";
    }
    //should have some interface for
    @Override
    public void behavior(WorldManager wm){
        System.out.println("interacting with door!");
        System.out.print(status);
        System.out.println("");
        //check downward or upward!
        //check world exist or not, if not create one!
        int currentLayer = wm.getCurrentLayer();
        if(status==Dir.UPWARD){
            //Upward go back to previous map

            wm.setActive(currentLayer-1);
        }else{
            //downward
            if(wm.isLastOne(currentLayer)){
                //create new layer
                int newLayer = wm.createNewWorld();
                wm.setActive(newLayer);
            }else{
                wm.setActive(currentLayer+1);
            }
        }

    }
}
