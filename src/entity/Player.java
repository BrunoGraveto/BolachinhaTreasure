package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

    // Sistema e controle
    GamePanel gp;
    KeyHandler keyHandler;

    // Vars. posição fixa do jogador na tela
    public final int SCREEN_X;
    public final int SCREEN_Y;

    // Vars. direção
    public final int UP = 0;
    public final int DOWN = 1;
    public final int LEFT = 2;
    public final int RIGHT = 3;

    // Qtd de chaves que o jogador possui
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        SCREEN_X = (gp.WINDOW_WIDTH - gp.TILE_SIZE) / 2;
        SCREEN_Y = (gp.WINDOW_HEIGHT - gp.TILE_SIZE) / 2;

        image = new BufferedImage[4][3];
        direction = 1;

        solidArea = new Rectangle(gp.ORIGINAL_TILE_SIZE, gp.ORIGINAL_TILE_SIZE / 2, gp.ORIGINAL_TILE_SIZE, gp.ORIGINAL_TILE_SIZE * 2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getPlayerImage();
        loadPlayer();
    }

    /**
     * Carrega os sprites do player
     */
    public void getPlayerImage() {
        image[0][0] = setup("player-tras-1");
        image[0][1] = setup("player-tras-2");
        image[0][2] = setup("player-tras-3");
        image[1][0] = setup("player-frente-1");
        image[1][1] = setup("player-frente-2");
        image[1][2] = setup("player-frente-3");
        image[2][0] = setup("player-esq-1");
        image[2][1] = setup("player-esq-2");
        image[2][2] = setup("player-esq-3");
        image[3][0] = setup("player-dir-1");
        image[3][1] = setup("player-dir-2");
        image[3][2] = setup("player-dir-3");
    }

    /**
     * Escala as imagens pré-definidamente para melhor o renderizamento
     *
     * @param imageName
     * @return
     */
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage spriteImage = null;

        try {
            spriteImage = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            spriteImage = uTool.scaleImage(spriteImage, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a imagem " + imageName + " na classe Player.");
        }

        return spriteImage;
    }

    /**
     * Define a velocidade e posição inicial no mundo do jogador
     */
    public void loadPlayer() {
        worldX = 1000;
        worldY = 1000;
        speed = 4;
    }

    /**
     * Atualiza e verifica a movimentação do jogador
     */
    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            updateDirection();

            // Verifica os tiles
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Verifica os objetos
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            move();
            updateSprite();
        } else {
            spriteNum = 0;
        }
    }

    /**
     * Atualiza a direção do jogador
     */
    public void updateDirection() {
        if (keyHandler.upPressed) {
            direction = UP;
        }
        if (keyHandler.downPressed) {
            direction = DOWN;
        }
        if (keyHandler.leftPressed) {
            direction = LEFT;
        }
        if (keyHandler.rightPressed) {
            direction = RIGHT;
        }
    }

    /**
     * Movimentação do jogador
     */
    public void move() {
        if (!collisionOn) {
            switch (direction) {
                case UP -> worldY -= speed;
                case DOWN -> worldY += speed;
                case LEFT -> worldX -= speed;
                case RIGHT -> worldX += speed;
            }
        }
    }

    /**
     * Atualiza o sprite
     */
    public void updateSprite() {
        spriteCounter++;

        if (spriteCounter >= 10) {
            spriteCounter = 0;
            spriteNum++;

            if (spriteNum > 2) {
                spriteNum = 0;
            }
        }
    }

    /**
     * Dá a devida ação á certa interação com algum objeto do mundo
     *
     * @param index
     */
    public void pickUpObject(int index) {
        if (index != 999) {
            String objectName = gp.obj[index].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.playSE(1);
                    gp.ui.showMessage("Você encontrou uma chave!");
                    gp.obj[index] = null;

                    break;
                case "Door":
                    if (hasKey > 0) {
                        hasKey--;
                        gp.playSE(3);
                        gp.ui.showMessage("Você abriu uma porta!");
                        gp.obj[index] = null;
                    } else {
                        gp.ui.showMessage("Você precisa de uma chave para abrir a porta!");
                    }

                    break;
                case "MilkCup":
                    speed += 3;
                    gp.playSE(2);
                    gp.ui.showMessage("Você encontrou um Copo de Leite, ele te deixará mais rápido!");
                    gp.obj[index] = null;
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    /**
     * Desenha o jogador na tela
     *
     * @param g2
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(image[direction][spriteNum], SCREEN_X, SCREEN_Y, null);
    }

}
