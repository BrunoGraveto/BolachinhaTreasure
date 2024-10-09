
package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    // Velocidade e localização no mundo
    public int worldX, worldY;
    public int speed;
    
    // Array multidimensional que guarda os diferentes sprites
    public BufferedImage[][] image;
    
    // Vars. auxiliar dos sprites
    public int direction;
    public int spriteCounter = 0;
    public int spriteNum = 0;
    
    // Colisão
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    
}
