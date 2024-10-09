
package main;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    // Váriaveis de controle do som
    Clip clip;
    URL[] soundURL = new URL[30];
    
    public Sound() {
        soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
    }
    
    /**
     * Seleciona o arquivo de áudio
     * @param index
     */
    public void setFile(int index) {
        try {   
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);   
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Inicia o audio
     */
    public void play() {
        clip.start();
    }
    
    /**
     * Da loop ao audio
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    /**
     * Para o audio
     */
    public void stop() {
        clip.stop();
    }
    
}

