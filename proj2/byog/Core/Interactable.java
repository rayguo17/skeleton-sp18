package byog.Core;


import java.io.Serializable;

public interface Interactable extends Serializable {
    public WorldGenerator.Position getPosition();
    public String getTitle();
    public void behavior(WorldManager wm);
}
