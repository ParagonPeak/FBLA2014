/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.entity.items;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.entity.Entity;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.graphics.TextDisplayer;
import com.FBLA.businesssim.sound.MusicPlayer;
import com.FBLA.businesssim.util.Vector2d;

/**
 *
 * @author Tripp
 */
public class HuntObject extends Entity {

    protected Screen screen;
    protected String[] pickupText;
    private String[] question;
    private int correctAnswerIndex = 2;

    public HuntObject(Vector2d v, Sprite s, Screen sc, String[] pickupText, String[] questionText) {
        super(v);
        sprite = s;
        screen = sc;
        this.pickupText = pickupText;
        this.question = questionText;
    }

    public HuntObject(int x, int y, Sprite s, Screen sc, String[] pickupText, String[] questionText) {
        this(new Vector2d(x, y), s, sc, pickupText, questionText);
    }

    public void render() {
        screen.renderRaisedMob(v.getiX(), v.getiY(), sprite);
    }

    public void event() {
        remove();

        BusinessSim.bs.td.addLines(new String[]{"Woah! You picked up a skill!", "Let's see what it is.."}, TextDisplayer.TEXT);
        BusinessSim.bs.td.addLines(pickupText, TextDisplayer.TEXT);
        
        // swap correct answer location with a different one
        int newAnswerIndex = (int) (Math.random() * 4 + 2);
        String correct = question[correctAnswerIndex];
        String incorrect = question[newAnswerIndex];
        question[correctAnswerIndex] = incorrect;
        question[newAnswerIndex] = correct;
        correctAnswerIndex = newAnswerIndex;
        
        BusinessSim.bs.td.addLines(question, TextDisplayer.MULTIPLE_CHOICE, correctAnswerIndex);
        
        String[] correctString = {"The correct answer was: ", question[correctAnswerIndex]};
        BusinessSim.bs.td.addLines(correctString, TextDisplayer.MULTIPLE_CHOICE, correctAnswerIndex);
        
        
        // play a sound, write a message
        MusicPlayer m = new MusicPlayer();
        m.init();
        m.changeTrack(5);
    }
}
