package object;

import main.GamePanel;

public class OBJ_Door extends SuperObject {
    
    GamePanel gp;

    public OBJ_Door(GamePanel gp) {
        this.gp = gp;
        name = "Door";
        getImage(gp, "porta");
        collision = true;
    }

}
