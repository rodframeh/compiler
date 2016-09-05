
package org.ulasalle.lexical.analizer;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AnalyzerTest {
    
    public AnalyzerTest() {
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
     * Test of getTokens method, of class Analyzer.
     */
    @Test
    public void testGetTokens() {
        System.out.println("getTokens");
        Analyzer instance = new Analyzer();
    }

    /**
     * Test of getLexicalErrors method, of class Analyzer.
     */
    @Test
    public void testGetLexicalErrors() {
        System.out.println("getLexicalErrors");
        Analyzer instance = new Analyzer();
    }

    /**
     * Test of getLine method, of class Analyzer.
     */
    @Test
    public void testGetLine() {
        System.out.println("getLine");
        Analyzer instance = new Analyzer();
    }

    /**
     * Test of setLine method, of class Analyzer.
     */
    @Test
    public void testSetLine() {
        System.out.println("setLine");
        String line = "";
        Analyzer instance = new Analyzer();
    }

    /**
     * Test of start method, of class Analyzer.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        Analyzer instance = new Analyzer();
        instance.setLine("Programa() { Entero variable=12; EscribirPantalla(\"holamundo\"); }");
        instance.start();
        instance.show();
    }
    
}
