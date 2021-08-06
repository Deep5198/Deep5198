/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import highwaymonitor.Highway;
import highwaymonitor.InvalidEnteranceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HighwayClassTest {
    
    public HighwayClassTest() {
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
	 * TO DO add test methods here.
	 *
	 ** The methods must be annotated with annotation @Test. For example:
	 */
     @Test
     public void testTotalVehicleInEnterance() {
         Highway h;
        try {
            h = highwaymonitor.Highway.getInstance();
            assertEquals(7,Highway.getVehicals().size());  
        } catch (InvalidEnteranceException ex) {
            Logger.getLogger(HighwayClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     
     }
}
