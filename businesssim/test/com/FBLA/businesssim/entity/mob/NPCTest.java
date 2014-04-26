/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.graphics.Screen;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Test of the NPC Class
 * Created to verify that readFiles was working
 * As of April 13, 2014 only tests readFiles() and countLines()
 * 
 * @author RAPHAEL
 */
public class NPCTest {
    
    public NPCTest() {
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
     * Test of init method, of class NPC.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        //NPC.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readFiles method, of class NPC.
     */
    @Test
    public void testReadFiles() {
        System.out.println("readFiles");
        NPC.readFiles();
        
        //assertEquals("Is the first floor's first saying's 1st line's text correct?", NPC.sayings[0][0][0].equals("When I was a kid things weren't so easy."), true);
        //assertEquals("Is the third floor's first saying's 2nd line's text 'LINE2'?", NPC.sayings[2][0][1].equals("LINE2"), true);
        // assertEquals("Is the sixth floor's second saying's 3rd line's text 'LINE3'?", NPC.sayings[5][1][2].equals("LINE3"), true);
    }

    /**
     * Test of getSaying method, of class NPC.
     */
    @Test
    public void testGetSaying() {
        System.out.println("getSaying");
        //NPC instance = null;
        //String[] expResult = null;
        //String[] result = instance.getSaying();
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of speak method, of class NPC.
     */
    @Test
    public void testSpeak() {
        System.out.println("speak");
        //NPC instance = null;
        //instance.speak();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class NPC.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        //NPC instance = null;
        //instance.update();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of render method, of class NPC.
     */
    @Test
    public void testRender() {
        System.out.println("render");
        //Screen screen = null;
        //NPC instance = null;
        //instance.render(screen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
