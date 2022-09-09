package byog.Core;

import java.io.Serializable;

public class Player implements Interactable {
    public WorldGenerator.Position p;
    public String ObjectType;

    public Player(int x, int y){
        p = new WorldGenerator.Position(x,y);
        ObjectType = "Player";
    }
    public Player(WorldGenerator.Position p){
        this.p = p;
        ObjectType = "Player";
    }

    @Override
    public WorldGenerator.Position getPosition() {
        return p;
    }

    @Override
    public String getTitle() {
        return ObjectType;
    }

    @Override
    public void behavior(WorldManager wm) {
        System.out.println("player do something");
    }
}
