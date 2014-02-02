/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.entity.items;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.entity.Entity;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.sound.MusicPlayer;
import com.FBLA.businesssim.util.Vector2d;

/**
 *
 * @author Tripp
 */
public class HuntObject extends Entity {

    protected Screen screen;
    protected String[] pickupText;

    public HuntObject(Vector2d v, Sprite s, Screen sc, String[] pickupText) {
        super(v);
        sprite = s;
        screen = sc;
        this.pickupText = pickupText;
    }

    public HuntObject(int x, int y, Sprite s, Screen sc, String[] pickupText) {
        this(new Vector2d(x, y), s, sc, pickupText);
    }

    public void render() {
        screen.renderRaisedMob(v.getiX(), v.getiY(), sprite);
    }

    public void event() {
        remove();

        BusinessSim.bs.screen.updateText(new String[]{"Woah! You picked up a skill!", "Let's see what it is.."});
        BusinessSim.bs.screen.updateText(pickupText);
        // play a sound, write a message
        MusicPlayer m = new MusicPlayer();
        m.init();
        m.changeTrack(5);
    }
}
