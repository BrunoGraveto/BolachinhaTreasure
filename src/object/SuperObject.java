package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
    
    // Vars objeto
    public BufferedImage image;
    public String name;
    public int worldX, worldY;
    
    // Colisão
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = solidArea.x;
    public int solidAreaDefaultY = solidArea.y;
    public boolean collision = false;
    
    // Ferramenta
    UtilityTool uTool = new UtilityTool();

    /**
     * Desenha os objetos no mundo
     * @param g2
     * @param gp 
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X
                && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X
                && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y
                && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {

            g2.drawImage(image, screenX, screenY, null);
        }
    }
    
    /**
     * Pega a imagem do objeto já escalada
     * @param gp
     * @param objectName 
     */
    public void getImage(GamePanel gp, String objectName) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/" + objectName + ".png"));
            image = uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a imagem " + objectName + " na classe SuperObject.");
        }
    }

}
