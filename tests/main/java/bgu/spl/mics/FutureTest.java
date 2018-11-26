package main.java.bgu.spl.mics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FutureTest {
    private Future<String> test ;


    @Before
    public void setUp() throws Exception {
        test = new Future<>();
    }

    @Test
    public void get() {
        assertNull(test.get());
        test.resolve("let's perform a test");
        assertEquals("let's perform a test", test.get());
    }

    @Test
    public void resolve() {
        test.resolve("test check");
        assertEquals("test check",test.get());
        test.resolve(null);
        assertNull(test.get());
    }

    @Test
    public void isDone() {
        assertFalse(test.isDone());
        test.resolve("I love testing");
        assertTrue(test.isDone());
        test.resolve(null);
        assertFalse(test.isDone());

    }

    @Test
    public void get1() {
        assertNull(test.get(0,TimeUnit.MICROSECONDS));
        assertNull(test.get(500,TimeUnit.MICROSECONDS));
        test.resolve("I love Guy Ofeck");
        assertEquals("I love Guy Ofeck",test.get(500,TimeUnit.MICROSECONDS));
    }

    @After
    public void tearDown() throws Exception {
    }

}