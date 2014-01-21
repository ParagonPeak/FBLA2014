/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.sound;

import com.FBLA.businesssim.BusinessSim;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    public static Sound mainMenuMusic,
            floorEvenMusic,
            floorOddMusic,
            pauseMusic,
            creditsMusic,
            sfx_elevatorDing;
    private AudioInputStream ais;
    private int loopCount;
    private Clip clip;
    public boolean isRunning = false;
    private FloatControl volume;

    public static void init() {
        mainMenuMusic = new Sound("MainTheme.wav", Clip.LOOP_CONTINUOUSLY);
        floorEvenMusic = new Sound("GameMusic.wav", Clip.LOOP_CONTINUOUSLY);
        floorOddMusic = new Sound("GameMusic.wav", Clip.LOOP_CONTINUOUSLY);
        pauseMusic = new Sound("back1.wav", Clip.LOOP_CONTINUOUSLY);
        creditsMusic = new Sound("back1.wav");
        sfx_elevatorDing = new Sound("sfx/Elevator_Ding.wav");
    }

    public Sound(String url) {
        this(url, 1);
    }

    public Sound(String url, int timesToLoop) {
        try {
            ais = AudioSystem.getAudioInputStream(BusinessSim.class.getClassLoader().getResource("Audio/" + url));
            clip = AudioSystem.getClip();
            loopCount = timesToLoop;
        } catch (Exception ex) {
            System.out.println("Trouble Loading Track");

        }
    }

    public void stop() {
        if (clip.isActive()) {
            clip.stop();
            isRunning = false;
        }

    }

    public void start() {
        try {
            clip.open(ais);
            clip.start();
            clip.loop(loopCount);
            isRunning = true;
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception ex) {
            System.out.println("Trouble Playing Track");
        }
    }

    public boolean isRunning() {
        return clip.isActive();
    }
    
    /**
     * 
     * @param vol 0 to -80
     */
    public void setVolume(float vol)
    {
        volume.setValue(vol);
    }
    
    /**
     * 
     * @param percent 1 to 0
     */
    public void setVolumeByPercent(float percent)
    {
        if(percent > 1) percent = 1f;
        if(percent < 0) percent = 0f;
        setVolume((1f - percent) * volume.getMinimum());
    }
}
