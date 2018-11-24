package main.java.bgu.spl.mics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class MessageBusTest {
    private MessageBusImpl Bustest ;
    private MicroService microTest1;

    @Before
    public void setUp() throws Exception {
        Bustest = new MessageBusImpl();

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
        Bustest.register(microTest1);


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