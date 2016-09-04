/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ulasalle.lexical.analizer;

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
public class AutomataTest {
    
    public AutomataTest() {
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
     * Test of build method, of class Automata.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        Automata instance = new Automata();
        instance.build();

    }

    /**
     * Test of getLengthStates method, of class Automata.
     */
    @Test
    public void testGetLengthStates() {
        System.out.println("getLengthStates");
        Automata instance = new Automata();

    }

    /**
     * Test of getLengthTransitions method, of class Automata.
     */
    @Test
    public void testGetLengthTransitions() {
        System.out.println("getLengthTransitions");
        Automata instance = new Automata();

    }

    /**
     * Test of getAlphabet method, of class Automata.
     */
    @Test
    public void testGetAlphabet() {
        System.out.println("getAlphabet");
        Automata instance = new Automata();

    }

    /**
     * Test of getTransitionsAccepted method, of class Automata.
     */
    @Test
    public void testGetTransitionsAccepted() {
        System.out.println("getTransitionsAccepted");
        Automata instance = new Automata();

    }

    /**
     * Test of getTransitions method, of class Automata.
     */
    @Test
    public void testGetTransitions() {
        System.out.println("getTransitions");
        Automata instance = new Automata();

    }
    
    @Test
    public void testShowTransitions(){
        Automata automata = new Automata();
        automata.build();
        automata.showTransitions();
    }
    
}
