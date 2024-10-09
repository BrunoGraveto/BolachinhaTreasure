package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_MilkCup;

public class AssetSetter {
    
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Instancia os objetos no array de objetos
     */
    public void setObject() {
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 5 * gp.TILE_SIZE;
        gp.obj[0].worldY = 5 * gp.TILE_SIZE;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 7 * gp.TILE_SIZE;
        gp.obj[1].worldY = 41 * gp.TILE_SIZE;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 39 * gp.TILE_SIZE;
        gp.obj[2].worldY = 5 * gp.TILE_SIZE;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 29 * gp.TILE_SIZE;
        gp.obj[3].worldY = 31 * gp.TILE_SIZE;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 37 * gp.TILE_SIZE;
        gp.obj[4].worldY = 33 * gp.TILE_SIZE;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 37 * gp.TILE_SIZE;
        gp.obj[5].worldY = 38 * gp.TILE_SIZE;

        gp.obj[6] = new OBJ_MilkCup(gp);
        gp.obj[6].worldX = 5 * gp.TILE_SIZE;
        gp.obj[6].worldY = 2 * gp.TILE_SIZE;

        gp.obj[7] = new OBJ_Chest(gp);
        gp.obj[7].worldX = 37 * gp.TILE_SIZE;
        gp.obj[7].worldY = 41 * gp.TILE_SIZE;
    }

}
