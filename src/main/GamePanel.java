package main;

import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Configurações da tela
    public final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile
    public final int SCALE = 3;

    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 x 48 tile
    public final int SCREEN_COLS = 16;
    public final int SCREEN_ROWS = 12;
    public final int WINDOW_WIDTH = TILE_SIZE * SCREEN_COLS; // 768 px
    public final int WINDOW_HEIGHT = TILE_SIZE * SCREEN_ROWS; // 576 px

    final int FPS = 60;

    // Configurações do mundo
    public final int WORLD_COLS = 50;
    public final int WORLD_ROWS = 50;
    public final int WORLD_WIDTH = WORLD_COLS * TILE_SIZE;
    public final int WORLD_HEIGHT = WORLD_ROWS * TILE_SIZE;

    // Vars. de Sistema
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // Vars. das entidades e objetos
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[10];
    
    // Game State
    public int gameState;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    /**
     * Chamado pela classe main, instancia os objetos e inicia a música 
     */
    public void setupGame() {
        aSetter.setObject();
        playMusic(0);
        gameState = PLAY_STATE;
    }

    /**
     * Chamado pela classe main, inicia a thread do jogo
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    /**
     * Thread do jogo, a qual chama o metodo update e repinta a tela
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Atualização do player e entidades
    */
    public void update() {
        if (gameState == PLAY_STATE) {
            player.update();
        }
        if (gameState == PAUSE_STATE) {
            
        }
    }

    /**
     * Desenha tudo o que aparece na tela
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Tile
        tileManager.draw(g2);

        // Objeto
        for (SuperObject object : obj) {
            if (object != null) {
                object.draw(g2, this);
            }
        }

        // Player
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose();
    }

    /**
     * Toca a música
     * @param index 
     */
    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    /**
     * Para a música
     */
    public void stopMusic() {
        music.stop();
    }

    /**
     * Toca o efeito sonoro
     * @param index 
     */
    public void playSE(int index) {
        se.setFile(index);
        se.play();
    }

}
