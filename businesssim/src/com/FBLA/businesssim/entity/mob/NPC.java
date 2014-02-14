package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.util.Vector2d;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Used as a random mob to wander the halls with interactive text
 */
public class NPC extends Mob{
    
    protected String[] speak;
    
    public NPC(Vector2d v, String name, Screen sc)
    {
        super(v, name);
    }
    
    /**
     * Sets the script from an array of text
     * @param speak What we want the character to say
     */
    public void setSpeak(String[] speak)
    {
        this.speak = speak;
    }
    /**
     * Loads text from a specific file
     * @param url Name of the file
     */
    public void setSpeak(String url)
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(BusinessSim.bs.getClass().getClassLoader().getResource("/storyBoard/"+name+"/"+url).toString())));
            String text = "";
            String line;
            do{
                line = br.readLine();
                text += line;
            }while(line != null);
            speak = text.split("/n");
        } catch (FileNotFoundException ex) {
            System.out.println("Problem loading text");
        } catch (IOException ex) {
            System.out.println("Problem reading text");
        }
    }
    
    public void speak()
    {
//        BusinessSim.bs.screen.speakPrompt(v.getiX(), v.getiY(), speak, BusinessSim.bs.getGraphics());
    }
}
