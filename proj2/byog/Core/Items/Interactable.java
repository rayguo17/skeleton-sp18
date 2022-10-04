package byog.Core.Items;


import byog.Core.WorldGenerator;
import byog.Core.WorldManager;

import java.io.Serializable;

public interface Interactable extends Serializable {
    public WorldGenerator.Position getPosition();
    public String getTitle();
    public void behavior(WorldManager wm);
}
