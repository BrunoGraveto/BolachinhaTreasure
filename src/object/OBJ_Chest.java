package object;

import main.GamePanel;

public class OBJ_Chest extends SuperObject {
    
    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {
        this.gp = gp;
        name = "Chest";
        getImage(gp, "bau");
    }

}
