package bgu.spl.mics.application.passiveObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import bgu.spl.mics.application.passiveObjects.OrderResult.*;

import static org.junit.Assert.*;


public class InventoryTest {
    private Inventory test;
    private BookInventoryInfo[] toLoad ;

    @Before
    public void setUp() throws Exception {
        test = Inventory.getInstance();
        toLoad = new BookInventoryInfo[5];
        for(int i = 0 ;  i < toLoad.length ; i++){
            toLoad[i] = new BookInventoryInfo("Matrix "+i, i+1 , i);
        }
        test.load(toLoad);

    }


    @Test
    public void getInstance() {

    }

    @Test
    public void load() {
        assertEquals(-1,test.checkAvailabiltyAndGetPrice("Matrix "+0));
        for(int i = 1 ; i < toLoad.length ; i++){
            assertEquals(i+1,test.checkAvailabiltyAndGetPrice("Matrix "+i));

            assertEquals(-1,test.checkAvailabiltyAndGetPrice("Game Of Thrones"));
        }
    }


    @Test
    public void take() {
        assertEquals(OrderResult.NOT_IN_STOCK,test.take("Game Of Thrones"));
        assertEquals(OrderResult.NOT_IN_STOCK,test.take("Matrix "+0));
        for(int i=1 ; i<toLoad.length;i++){
            for(int j=0 ; j<i ; j++) {
                assertEquals(OrderResult.SUCCESSFULLY_TAKEN,test.take("Matrix "+i));
            }
            assertEquals(OrderResult.NOT_IN_STOCK,test.take("Matrix "+i));

        }
    }

    @Test
    public void checkAvailabiltyAndGetPrice() {
        assertEquals(-1,test.checkAvailabiltyAndGetPrice("Matrix "+0));
        for(int i=1;i<toLoad.length;i++){
            assertEquals(i+1,test.checkAvailabiltyAndGetPrice("Matrix "+i));
        }
        for(int i=1 ; i<toLoad.length;i++) {
            for (int j = 0; j < i; j++) {
                test.take("Matrix " + i);
            }
            assertEquals(-1,test.checkAvailabiltyAndGetPrice("Matrix "+i));
        }


    }

    @Test
    public void printInventoryToFile() {
    }

    @After
    public void tearDown() throws Exception {
    }

}