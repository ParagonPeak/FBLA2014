package com.FBLA.businesssim.sound;

public class MusicPlayer {

    public static MusicPlayer mp = new MusicPlayer();
    public Thread musicThread;
    private int currentTrack;
    private Sound[] sounds, soundEffects;
    private float volume = .85f;

    public MusicPlayer() {
        sounds = new Sound[]{Sound.mainMenuMusic, Sound.floorEvenMusic, Sound.floorOddMusic, Sound.pauseMusic, Sound.creditsMusic, Sound.sfx_elevatorDing};
        soundEffects = new Sound[]{ Sound.sfx_elevatorDing};
        currentTrack = -1;
    }
    
    public void decreaseVolume()
    {
        volume -= .05f;
        if(volume < 0)
            volume = 0;
        sounds[currentTrack].setVolumeByPercent(volume);
    }
    
    public void increaseVolume()
    {
        volume += .05f;
        if(volume > 1)
            volume = 1;
        sounds[currentTrack].setVolumeByPercent(volume);
    }
    
    public static void init() {
        Sound.init();
        mp = new MusicPlayer();
    }

    public void changeTrack(int i) {
        if (i == currentTrack | i < 0 | i >= sounds.length) {
            return;
        }
        if (currentTrack >= 0 & currentTrack < sounds.length) {
            if (sounds[currentTrack].isRunning()) {
                sounds[currentTrack].stop();
            }
        }
        sounds[currentTrack = i].start(); //I think this works...
        sounds[currentTrack].setVolumeByPercent(volume);
    }
}
