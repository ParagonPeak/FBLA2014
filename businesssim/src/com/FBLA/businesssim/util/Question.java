package com.FBLA.businesssim.util;

import com.FBLA.businesssim.graphics.SpriteSheet;
import com.FBLA.businesssim.level.Level;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Question {
    //questions[floor][topic][QuestionNumber][displaytext]
    public static String[][][][] questions = new String[5][][][];
    public static final int topicsPerFloor = 5;
    public static final int questionsPerTopic = 3;
    public static final int reasonIndex = 6;

    public static void main(String args[]) {
        loadQuestionsFromFiles();
        for (String[][][] floor : questions) {
            System.out.println("FLOOR CHANGE");
            for (String[][] topic : floor) {
                System.out.println("NEXT TOPIC:");
                for (String[] questionNum : topic) {
                    System.out.println("NEXT QUESTION:");
                    for (String display : questionNum) {
                        System.out.println(display);
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * Given a file location, returns the lines of text (and the lines of text
     * in those lines of text) in the file First index is question number,
     * Second index is line number
     */
    public static void loadQuestionsFromFiles() {
        String path = "";
        for (int floor = 0; floor < Level.levelAmount - 1; floor++) {
            path = "/Text/Questions/floor" + (floor + 1) + ".txt";
            try {
                InputStream in = Question.class.getClass().getResourceAsStream(path);
                BufferedReader input = new BufferedReader(new InputStreamReader(in));
                String[][][] floorQuestions = new String[topicsPerFloor][questionsPerTopic][];
                String wholeLine;
                int totalQuestions = 0;
                while ((wholeLine = input.readLine()) != null) {
                    floorQuestions[(int)(totalQuestions / questionsPerTopic)][totalQuestions++ % questionsPerTopic] = wholeLine.split("#");
                }
                questions[floor] = floorQuestions;
                //Save Data
            } catch (Exception e) {
                Logger.getLogger(SpriteSheet.class.getName()).log(java.util.logging.Level.SEVERE, "Could not load level questions at " + path, e);
            }
        }
    }
    
    public static String[] getQuestion(String topic, int floor) {
        for(int topicNum = 0; topicNum < questions[floor].length; topicNum++)
        {
            if(questions[floor][topicNum][0][0].equalsIgnoreCase(topic))
                return(questions[floor][topicNum][(int)(Math.random() * questions[floor][topicNum].length)]);
        }
        return new String[]{"Error"};
    }
}
