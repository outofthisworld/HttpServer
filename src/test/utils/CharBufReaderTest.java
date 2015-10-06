package test.utils;

import utils.CharBufReader;

import java.nio.CharBuffer;

import static org.junit.Assert.*;

public class CharBufReaderTest {
    private CharBufReader charBufReader;
    private String testString;


    @org.junit.Before
    public void setUp() throws Exception {
        CharBuffer charBuffer = CharBuffer.allocate(1000);
        charBuffer.put(testString = "The quick brown fox jumped over the lazy moon\n" + "from there");
        charBuffer.flip();
        charBufReader = new CharBufReader(charBuffer);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        charBufReader = null;
        testString = null;
    }

    @org.junit.Test
    public void testHasNext() throws Exception {
        assertTrue(charBufReader.hasNext());
        int count = 0;
        for( ;charBufReader.hasNext();count++){
            charBufReader.readNext();
        }
        assertEquals(count,testString.length());
        assertFalse(charBufReader.hasNext());
    }

    @org.junit.Test
    public void testPeekNextChar() throws Exception {

    }

    @org.junit.Test
    public void testSkipForward() throws Exception {
        int pos = charBufReader.getPosition();
        int skip = 2;
        charBufReader.skipForward(skip);
        assertEquals(charBufReader.getPosition(),pos+skip);
    }

    @org.junit.Test
    public void testPeek() throws Exception {
        assertEquals(charBufReader.peek(testString.charAt(0), true), testString.indexOf(testString.charAt(0)));
        assertEquals(charBufReader.getPosition(), 0);
        assertEquals(charBufReader.peek('&', false), testString.indexOf(testString.indexOf('&')));
        assertEquals(charBufReader.getPosition(), testString.length());
    }

    @org.junit.Test
    public void testGetPosition() throws Exception {
        assertEquals(charBufReader.getPosition(),0);
        charBufReader.readNext();
        assertEquals(charBufReader.getPosition(), 1);
    }

    @org.junit.Test
    public void testExtract() throws Exception {
        assertEquals(charBufReader.extract(0, 2),testString.substring(0,2));
        assertEquals(charBufReader.getPosition(),2);
    }

    @org.junit.Test
    public void testExtract1() throws Exception {
        assertEquals(charBufReader.extract(0, 2,true),testString.substring(0,2));
        assertEquals(charBufReader.getPosition(),0);
    }

    @org.junit.Test
    public void testExtract2() throws Exception {

    }

    @org.junit.Test
    public void testCurrentString() throws Exception {

    }

    @org.junit.Test
    public void testReadLine() throws Exception {

    }

    @org.junit.Test
    public void testHasNextLine() throws Exception {

    }

    @org.junit.Test
    public void testHasNext1() throws Exception {

    }

    @org.junit.Test
    public void testHasNextBefore() throws Exception {

    }

    @org.junit.Test
    public void testResetPositionToMark() throws Exception {

    }

    @org.junit.Test
    public void testMarkPosition() throws Exception {

    }

    @org.junit.Test
    public void testGetMarkPosition() throws Exception {

    }

    @org.junit.Test
    public void testSetMarkPosition() throws Exception {

    }

    @org.junit.Test
    public void testReadNext() throws Exception {

    }

    @org.junit.Test
    public void testResetToStart() throws Exception {

    }

    @org.junit.Test
    public void testGetChrBuf() throws Exception {

    }
}