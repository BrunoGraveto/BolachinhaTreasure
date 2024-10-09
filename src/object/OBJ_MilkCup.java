package object;

import main.GamePanel;

public class OBJ_MilkCup extends SuperObject {
    
    GamePanel gp;

    public OBJ_MilkCup(GamePanel gp) {
        this.gp = gp;
        name = "MilkCup";
        getImage(gp, "copo-leite");
    }

}
