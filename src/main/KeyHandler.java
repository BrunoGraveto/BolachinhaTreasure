
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    
    /**
     * Informa que os botões de movimento foram pressionados
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int codTecla = e.getKeyCode();
        
        if(codTecla == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(codTecla == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(codTecla == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(codTecla == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(codTecla == KeyEvent.VK_P) {
            if (gp.gameState == gp.PLAY_STATE) {
                gp.stopMusic();
                gp.gameState = gp.PAUSE_STATE;
            } else if (gp.gameState == gp.PAUSE_STATE) {
                gp.playMusic(0);
                gp.gameState = gp.PLAY_STATE;
            }
        }
    }

    /**
     * Informa que os botões de movimento foram soltados
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int codTecla = e.getKeyCode();
        
        if(codTecla == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(codTecla == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(codTecla == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(codTecla == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
    
    /**
     * Não necessário
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

}
