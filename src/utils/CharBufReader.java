package utils;

import http.constants.HttpConstants;

import java.nio.CharBuffer;


/**
 * The type Char buf reader.
 */
public final class CharBufReader {
    private final CharBuffer chrBuf;
    private int markPosition;

    /**
     * Instantiates a new Char buf reader.
     *
     * @param charBuffer the char buffer
     */
    public CharBufReader(CharBuffer charBuffer){
        this.chrBuf = charBuffer;
    }

    /**
     * Returns true if the underlying character buffer has remaining characters.
     *
     * @return the boolean
     */
    public boolean hasNext(){
        return getChrBuf().hasRemaining();
    }

    /**
     * Reads the next char in the underlying buffer and then rolls back the position.
     *
     * @return the int
     */
    public int peekNextChar(){
        if(!hasNext())
            return -1;

        char c = getChrBuf().get();
        getChrBuf().position(getChrBuf().position() - 1);
        return c;
    }


    /**
     * Skip forward.
     *
     * @param num the num
     */
    public void skipForward(int num){
        if(getPosition() + num > getChrBuf().limit())
            throw new IllegalArgumentException("Illegal argument");

        this.getChrBuf().position(this.getChrBuf().position() + num);
    }


    /**
     * Peek seperator.
     *
     * Attempts to find the seperator in the char buffer and returns the index of the seperator should it exist, else -1.
     *
     * @param c the c
     * @param resetPosition the reset position
     * @return the int
     */
    public int peek(char c, boolean resetPosition){
        if(!hasNext()) return -1;
        int startPos = getPosition();
        int place = -1;
        char readChar =  getCharAtPosition(0);
        while(hasNext()){
            if(readChar == c) {
                place =  getPosition();
                break;
            }
           readChar =  (char) readNext();
        }
        if(resetPosition) setPosition(startPos);
        return place;
    }


    public char getCharAtPosition(int n){
        if(n < 0 || n > chrBuf.limit())
            throw new IllegalArgumentException("Invalid args");

        return getChrBuf().get(n);
    }


    private final void setPosition(int n){
        getChrBuf().position(n);
    }


    /**
     * Get position.
     *
     * @return the int
     */
    public int getPosition(){
        return getChrBuf().position();
    }

    /**
     *
     *
     * @param high the high
     * @return the optional
     */
    public String extract(int high){
        return extract(getPosition(),high);
    }

    /**
     *
     *
     * @param low the low
     * @param high the high
     * @return the optional
     */
    public String extract(int low,int high){
        if(low < 0 || high > getChrBuf().limit() || high < low)
            throw new IllegalArgumentException("Illegal argument exception");

        getChrBuf().position(low);
        char[] buffer = new char[high-low];
        for(int i = 0; i < high-low;i++){
            buffer[i] = (char)readNext();
        }
        return new String(buffer);
    }

    /**
     *
     *
     * @param low the low
     * @param high the high
     * @return the optional
     */
    public String extract(int low,int high,boolean resetPos){
        int startPos = getPosition();
        String extractedString = extract(low,high);
        if(resetPos) setPosition(startPos);
        return extractedString;
    }


    /**
     * Current string.
     *
     * @return the string
     */
    public String currentString(){
        int startPos = getChrBuf().position();
        char[] buffer = new char[startPos];
        getChrBuf().position(0);
        for(int i = 0; i < startPos;i++){
            buffer[i] = (char)readNext();
        }
        getChrBuf().position(0);
        return new String(buffer);
    }

    /**
     * Read line.
     * @return the string
     */
    public String readLine() {
        return extract(peek(HttpConstants.NEWLINE_CHAR, false));
    }

    /**
     * Has next line.
     *
     * @return the boolean
     */
    public boolean hasNextLine(){
        return peek(HttpConstants.NEWLINE_CHAR, true) != -1;
    }

    /**
     * Has next.
     *
     * @param c the c
     * @return the boolean
     */
    public boolean hasNext(char c){
        return peek(c, true) != -1;
    }

    /**
     * Has next before.
     *
     * @param nextChar the next char
     * @param beforeChar the before char
     * @param resetPosition the reset position
     * @return the boolean
     */
    public boolean hasNextBefore(char nextChar,char beforeChar,boolean resetPosition){
        int startPos = getChrBuf().position();
        int beforePos = peek(beforeChar,false);
        int afterPos = peek(nextChar,false);
        if(resetPosition) setPosition(startPos);
        return (beforePos != -1 && afterPos != -1) && (beforePos < afterPos);
    }

    /**
     * Resets the readers position to the buffers mark
     * Reset position to mark.
     */
    public void resetPositionToMark(){
        getChrBuf().reset();
    }

    /**
     * Marks a place in the buffer.
     * Mark position.
     */
    public void markPosition(){
        setMarkPosition(getChrBuf().position());
        getChrBuf().mark();
    }

    /**
     * //Returns the index of the marked position
     *
     * @return the mark position
     */
    public int getMarkPosition() {
        return markPosition;
    }

    /**
     * Sets mark position.
     *
     * @param markPosition the mark position
     */
    public void setMarkPosition(int markPosition) {
        this.markPosition = markPosition;
    }

    /**
     * Reads the next char from the buffer.
     *
     * @return the int
     */
    public int readNext(){
        return getChrBuf().get();
    }

    /**
     * Reset to start.
     */
    public void resetToStart(){
        getChrBuf().position(0);
    }

    /**
     * Gets chr buf.
     *
     * @return the chr buf
     */
    public CharBuffer getChrBuf() {
        return chrBuf;
    }
}
