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
import com.FBLA.businesssim.util.Question;
import com.FBLA.businesssim.util.Vector2d;

/**
 *  This class is used to represented the objects that can be picked up during the
 * game
 */
public class HuntObject extends Entity {

    protected Screen screen;
    protected String[] pickupText;
    private String[] question;
    private String reason;
    private int correctAnswerIndex = 2;

    /**
     * The constructor of the hunt objects 
     * @param v The placement vector
     * @param s the sprite to be drawn
     * @param sc where to draw
     * @param pickupText the 
     * @param questionText 
     */
    public HuntObject(Vector2d v, Sprite s, Screen sc, String[] pickupText, int floor){//, int questionTopic) {
        super(v);
        sprite = s;
        screen = sc;
        String questionTopic = pickupText[0];//The first line
        question = Question.getQuestion(questionTopic, floor);
        reason = question[question.length - 1];
        String[] temp = question;
        question = new String[question.length - 1];
        System.arraycopy(temp, 0, question, 0, question.length);
        this.pickupText = pickupText;
        
    }

    public HuntObject(int x, int y, Sprite s, Screen sc, String[] pickupText, int floor){//, String[] questionText) {
        this(new Vector2d(x, y), s, sc, pickupText, floor);//, questionText);
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
        String[] correctString = {"","The correct answer was: ", question[correctAnswerIndex], reason};
        BusinessSim.bs.td.addLines(correctString, TextDisplayer.TEXT, correctAnswerIndex);
        
        // play a sound, write a message
        MusicPlayer m = new MusicPlayer();
        m.init();
        m.changeTrack(5);
    }
}
