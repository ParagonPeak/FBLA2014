package com.FBLA.businesssim.sound;

public class MusicPlayer {

    public static MusicPlayer mp = new MusicPlayer();
    public Thread musicThread;
    private int currentTrack;
    private Sound[] sounds;

    public MusicPlayer() {
        Sound[] tracks = {Sound.mainMenuMusic, Sound.floorEvenMusic};//, Sound.floorOddMusic, Sound.pauseMusic, Sound.creditsMusic};
        sounds = tracks.clone();
        currentTrack = -1;
//        this.sounds[currentTrack].start();
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
        System.out.println(currentTrack);
        sounds[currentTrack = i].start(); //I think this works...
        System.out.println(currentTrack);

    }
}
