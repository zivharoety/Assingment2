package bgu.spl.mics;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.services.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class MessageBusTest {
    private MessageBusImpl Bustest ;
    private MicroService serviceTest1;
    private MicroService apiTest1;
    private MicroService inventoryTest1;
    private MicroService logisticTest1;
    private MicroService timeTest1;
    private MicroService resourceTest1;

    @Before
    public void setUp() throws Exception {
        //Bustest = new MessageBusImpl();
     //   serviceTest1 = new SellingService();
       // apiTest1 = new APIService();
        inventoryTest1 = new InventoryService();
        logisticTest1 = new LogisticsService();
        timeTest1 = new TimeService();
        resourceTest1 = new ResourceService();


    }


    @Test
    public void subscribeEvent() {

    }

    @Test
    public void subscribeBroadcast() {
    }

    @Test
    public void complete() {
    }

    @Test
    public void sendBroadcast() {
    }

    @Test
    public void sendEvent() {
    }

    @Test
    public void register() {
        Bustest.register(serviceTest1);
        Bustest.register(apiTest1);
        Bustest.register(inventoryTest1);


    }

    @Test
    public void unregister() {
    }

    @Test
    public void awaitMessage() {
    }

    @After
    public void tearDown() throws Exception {
    }

}