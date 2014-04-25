/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.util;

import com.FBLA.businesssim.level.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RAPHAEL
 */
public class QuestionTest {
    
    
    
    public QuestionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Question.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        fail("The test case is a prototype.");
        String[] args = null;
        Question.main(args);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of loadQuestionsFromFiles method, of class Question.
     * This tests whether loadQuestionsFromFiles works by testing the length of every question on every floor. If they're length are all at least 7, they're golden.
     */
    @Test
    public void testLoadQuestionsFromFiles() {
        System.out.println("loadQuestionsFromFiles");
        Question.loadQuestionsFromFiles();
        // TODO review the generated test code and remove the default call to fail.
        for(int floor = 0; floor < Level.levelAmount - 1; floor++) {
            for(int topic = 0; topic < Question.topicsPerFloor; topic++) {
                for(int question = 0; question < Question.questionsPerTopic; question++) {
                    System.out.println("Floor: " + (floor + 1) + " topic: " + (topic + 1) + " question: " + (question + 1) + " lines: " + Question.questions[floor][topic][question].length);
                    String[] lines = Question.questions[floor][topic][question];
                    if(lines.length < 7) {
                        fail("Bad number of lines on floor: " + (floor + 1) + " topic: " + (topic + 1) + " question: " + (question + 1) + " lines: " + Question.questions[floor][topic][question].length);
                    }
                }
            }
        }
    }

    /**
     * Test of getQuestion method, of class Question.
     */
    @Test
    public void testGetQuestion() {
        System.out.println("getQuestion");
        fail("The test case is a prototype.");
        String topic = "";
        int floor = 0;
        String[] expResult = null;
        String[] result = Question.getQuestion(topic, floor);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
}
