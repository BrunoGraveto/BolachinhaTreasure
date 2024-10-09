package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    // Sistema
    GamePanel gp;
    
    // Array dos Tiles e outro muldimensional com seus index
    public Tile[] tiles;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        mapTileNum = new int[gp.WORLD_COLS][gp.WORLD_ROWS];
        tiles = new Tile[16];

        getTileImage();
        loadMap("/maps/world.txt");
    }

    /**
     * Pega as imagens dos tiles
     */
    public void getTileImage() {
        setup(0, "agua", true);
        setup(1, "areia", false);
        setup(2, "arvore", true);
        setup(3, "grama-2", false);
        setup(4, "grama", false);
        setup(5, "tabua", false);
        setup(6, "terra", false);
        setup(7, "tijolo", true);
    }

    /**
     * Escala as imagens e configuram a colisão
     * @param index
     * @param imageName
     * @param collision 
     */
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gp.TILE_SIZE, gp.TILE_SIZE);
            tiles[index].collision = collision;
        } catch (IOException e) {
            System.out.println("Erro ao carregar a imagem " + imageName + " na classe TileManager.");
        }
    }

    /**
     * Carrega um mapa de um arquivo .txt
     * @param path 
     */
    public void loadMap(String path) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(inputStreamReader);

            for (int row = 0; row < gp.WORLD_ROWS; row++) {
                String line = buffReader.readLine();

                for (int col = 0; col < gp.WORLD_COLS; col++) {
                    String[] nums = line.split(" ");
                    mapTileNum[col][row] = Integer.parseInt(nums[col]);
                }
            }

            buffReader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desenha os tiles na tela
     * @param g2 
     */
    public void draw(Graphics2D g2) {
        for (int row = 0; row < gp.WORLD_ROWS; row++) {
            for (int col = 0; col < gp.WORLD_COLS; col++) {
                int worldX = col * gp.TILE_SIZE;
                int worldY = row * gp.TILE_SIZE;

                int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
                int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

                if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X
                        && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X
                        && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y
                        && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
                    g2.drawImage(tiles[mapTileNum[col][row]].image, screenX, screenY, null);
                }
            }
        }
    }

}
