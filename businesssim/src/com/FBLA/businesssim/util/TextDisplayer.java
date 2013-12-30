/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.util;

import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.input.Keyboard;

/**
 *
 * @author Tripp
 */
public class TextDisplayer extends Thread implements Runnable {

    private static String[] lines;
    private static Keyboard keys;
    private static Screen screen;
    public static boolean isUpdated = false;

    public TextDisplayer(TextDisplayer runnable) {
        super(runnable);
    }

    public TextDisplayer(Screen screen, Keyboard k) {
        
        keys = k;
        this.screen = screen;
        System.out.println(this.screen);
        System.out.println(keys);
    }

    @Override
    public void run() {
        if (isUpdated) {
            System.out.println(keys);
            System.out.println(screen);
            screen.displayText(lines, keys);
            isUpdated = false;
        }
    }

    public void setText(String[] s) {
        if (isUpdated) {
            return;
        }
        lines = s;
        isUpdated = true;
    }

    /**
     * Same as setText, but allows an override of the isUpdated boolean so that
     * it's possible to override mistakes in queuing of the text
     *
     * @param s the array of the text to be displayed
     * @param override allows for a bypass of the isUpdated boolean, which is
     * used to keep text from being displayed over and over
     */
    public void setText(String[] s, boolean override) {
        if (!override & isUpdated) {
            return;
        }
        lines = s;
        isUpdated = true;
    }
}
