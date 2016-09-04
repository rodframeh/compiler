/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.lexical.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author francisco
 */
public class ReaderTest {
    
    public ReaderTest() {
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
     * Test of setCharacters method, of class Reader.
     */
    @Test
    public void testSetCharacters() {
        System.out.println("setCharacters");
        String characters = "";
        Reader instance = new Reader();
        instance.setCharacters(characters);

    }

    /**
     * Test of boot method, of class Reader.
     */
    @Test
    public void testBoot() {
        System.out.println("boot");
        Reader instance = new Reader();
        instance.boot();

    }

    /**
     * Test of getReadStatus method, of class Reader.
     */
    @Test
    public void testGetReadStatus() {
        System.out.println("getReadStatus");
        Reader instance = new Reader();
        ReadStatus expResult = null;
        ReadStatus result = instance.getReadStatus();
        assertEquals(expResult, result);

    }

    /**
     * Test of next method, of class Reader.
     */
    @Test
    public void testNext() {
        try {
            System.out.println("next");
            Reader reader = new Reader();
            List<String> list=new ArrayList<>();
            list.add("-");
            list.add(";");
            list.add(".");
            list.add("test1");
            list.add("teSt1");
            list.add("tEST2");
            list.add("TEST33");
            list.add("TEST");
            list.add("$");
            list.add("_");
            list.add("SsSsS");
            list.add("SSS,");
            list.add("*=");
            list.add("==");
            list.add("&&");
            
            for(String a:list){
                reader.boot();
                reader.setCharacters(a);
                for(int i=0;i<a.length();i++){
                    reader.next();
                }
                System.out.println(a+" - "+reader.getReadStatus());
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
}
