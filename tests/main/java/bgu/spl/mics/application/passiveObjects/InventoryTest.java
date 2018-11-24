package main.java.bgu.spl.mics.application.passiveObjects;

import main.java.bgu.spl.mics.Future;
import static main.java.bgu.spl.mics.application.passiveObjects.OrderResult.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



public class InventoryTest {
    private Inventory test;
    private BookInventoryInfo[] toLoad ;

    @Before
    public void setUp() throws Exception {
        test = new Inventory();
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
        assertEquals(test.checkAvailabiltyAndGetPrice("Matrix "+0),-1);
        for(int i = 1 ; i < toLoad.length ; i++){
            assertEquals(test.checkAvailabiltyAndGetPrice("Matrix "+i),i+1);
            assertEquals(test.checkAvailabiltyAndGetPrice("Game Of Thrones"),-1);
        }
    }

    @Test
    public void take() {
        assertEquals(test.take("Game Of Thrones"),NOT_IN_STOCK);
        assertEquals(test.take("Matrix "+0),NOT_IN_STOCK);
        for(int i=1 ; i<toLoad.length;i++){
            for(int j=0 ; j<i ; j++) {
                assertEquals(test.take("Matrix "+i),SUCCESSFULLY_TAKEN);
            }
            assertEquals(test.take("Matrix "+i),NOT_IN_STOCK);
        }
    }

    @Test
    public void checkAvailabiltyAndGetPrice() {
        assertEquals(test.checkAvailabiltyAndGetPrice("Matrix "+0),-1);
        for(int i=1;i<toLoad.length;i++){
            assertEquals(test.checkAvailabiltyAndGetPrice("Matrix "+i),i+1);
        }
        for(int i=1 ; i<toLoad.length;i++) {
            for (int j = 0; j < i; j++) {
                test.take("Matrix " + i);
            }
            assertEquals(test.checkAvailabiltyAndGetPrice("Matrix "+i),-1);
        }


    }

    @Test
    public void printInventoryToFile() {
    }

    @After
    public void tearDown() throws Exception {
    }

}