
package main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        // Janela
        JFrame janela = new JFrame();
        janela.setTitle("Bolachinha Treasure");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        
        // Panel do Jogo
        GamePanel gp = new GamePanel();
        janela.add(gp);
        
        // Vizibiliza a janela
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
        
        // Inicia o jogo
        gp.setupGame();
        gp.startGameThread();
    }
    
}
