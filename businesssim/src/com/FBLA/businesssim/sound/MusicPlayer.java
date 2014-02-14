package com.FBLA.businesssim.sound;

public class MusicPlayer {

    public static MusicPlayer mp = new MusicPlayer();
    public Thread musicThread;
    private int currentTrack;
    private Sound[] sounds;
    private float volume = .85f;

    /**
     * Used to play music in the game
     */
    public MusicPlayer() {
        sounds = new Sound[]{Sound.mainMenuMusic, Sound.floorEvenMusic, Sound.floorOddMusic, Sound.pauseMusic, Sound.creditsMusic, Sound.sfx_elevatorDing};
        currentTrack = -1;
    }
    
    /**
     * Decrease the volume of the sounds that will play
     */
    public void decreaseVolume()
    {
        volume -= .05f;
        if(volume < 0)
            volume = 0;
        sounds[currentTrack].setVolumeByPercent(volume);
    }
    
    /**
     * Increase the volume of the sounds playing
     */
    public void increaseVolume()
    {
        volume += .05f;
        if(volume > 1)
            volume = 1;
        sounds[currentTrack].setVolumeByPercent(volume);
    }
    
    /**
     * Initialize all of the music and sounds in the game
     */
    public static void init() {
        Sound.init();
        mp = new MusicPlayer();
    }

    /**
     * Switch what's playing to a new sound
     * @param i the track number
     */
    public void changeTrack(int i) {
        if (i == currentTrack | i < 0 | i >= sounds.length) {
            return;
        }
        if (currentTrack >= 0 && currentTrack < sounds.length) {
            if (sounds[currentTrack].isRunning()) {
                sounds[currentTrack].stop();
            }
        }
        sounds[currentTrack = i].start(); //I think this works...
        sounds[currentTrack].setVolumeByPercent(volume);
    }
}
