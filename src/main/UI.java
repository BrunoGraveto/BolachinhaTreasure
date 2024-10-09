package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import object.OBJ_Key;

public class UI {

    // Váriavel de Sistema
    GamePanel gp;
    
    // Fontes
    Font arial_40, arial_80B;
    
    // Chave
    BufferedImage keyImage;
    
    // Mensagem
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    
    // Parabéns ao jogador
    public boolean gameFinished = false;
    boolean congratulations = true;
    int counterCong = 0;
    
    // Animação final.
    BufferedImage[] finalImageAnimation;
    int spriteCounter = 0;
    int spriteNum = 0;
    boolean fimAnimation = false;
    
    // Vars. auxiliar ao DrawString
    String text;
    int x;
    int y;

    // Temporizador
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
        
        finalImageAnimation = new BufferedImage[17];
        getImage();
    }
    
    /**
     * Pega as imagens da animação final
     */
    public void getImage() {
        try {
            for (int i = 0; i < finalImageAnimation.length; i++) {
                finalImageAnimation[i] = ImageIO.read(getClass().getResourceAsStream("/final/fim-" + (i+1) + ".png"));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar imagem na classe IU.");
        }
    }

    /**
     * Mostra uma mensagem na tela
     */
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    /**
     * Desenha o temporizador, as mensagens, quantidade
     * de chaves que o jogador possui, e a animação final
     */
    public void draw(Graphics2D g2) {
        if (gameFinished) {
            if (congratulations) {
                g2.setFont(arial_80B);
                g2.setColor(Color.YELLOW);
                setDrawString("Parabéns!", gp.WINDOW_HEIGHT / 2 - (gp.TILE_SIZE * 3), g2);
                g2.drawString(text, x, y);
                
                g2.setFont(arial_40);
                g2.setColor(Color.WHITE);
                setDrawString("Você encontrou o Tesouro!", gp.WINDOW_HEIGHT / 2 - (gp.TILE_SIZE * 2), g2);
                g2.drawString(text, x, y);
                setDrawString("Seu tempo foi de: " + dFormat.format(playTime) + " segundos!", gp.WINDOW_HEIGHT / 2 + (gp.TILE_SIZE * 4), g2);
                g2.drawString(text, x, y);
                
                counterCong++;
                if(counterCong > 6 * 60){
                    congratulations = false;
                }
            } else {
                g2.drawImage(finalImageAnimation[spriteNum], 0, 0, null);

                if (fimAnimation) {
                    g2.setFont(arial_80B);
                    g2.setColor(Color.RED);
                    setDrawString("The end.", gp.WINDOW_HEIGHT / 2, g2);
                    g2.drawString(text, x, y);
                    gp.gameThread = null;
                } else {
                    spriteCounter++;
                    if (spriteCounter >= 5) {
                        spriteCounter = 0;
                        spriteNum++;

                        if (spriteNum >= finalImageAnimation.length) {
                            spriteNum--;
                            fimAnimation = true;
                        }
                    }
                }
            }
        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gp.TILE_SIZE / 2, gp.TILE_SIZE / 2, gp.TILE_SIZE, gp.TILE_SIZE, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);

            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString(message, gp.TILE_SIZE / 2, gp.TILE_SIZE * 3);

                messageCounter++;

                if (messageCounter >= 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
            
            g2.setFont(arial_40);
            g2.drawString("Tempo: " + dFormat.format(playTime), gp.TILE_SIZE * 10, 65);
            
            if (gp.gameState == gp.PAUSE_STATE) {
                g2.setColor(Color.GRAY);
                g2.setFont(arial_80B);
                setDrawString("PAUSE", gp.WINDOW_HEIGHT / 2, g2);
                g2.drawString(text, x, y);
            } else {
                playTime += (double) 1 / gp.FPS;
            }
        }
    }
    
    /**
     * Passa valor para as variaveis para escrever na tela
     * @param text
     * @param y
     * @param g2 
     */
    public void setDrawString(String text, int y, Graphics2D g2) {
        this.text = text;
        this.x = (gp.WINDOW_WIDTH - (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth()) / 2;
        this.y = y;
    }

}
