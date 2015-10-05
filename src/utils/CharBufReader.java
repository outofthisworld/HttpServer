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
        int startPos = getPosition();
        while(hasNext()){
            char readChar = (char) readNext();
            if(readChar == c) {
                return getPosition();
            }
        }
        if(resetPosition)
            getChrBuf().position(startPos);

        return -1;
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
     * Extract optional.
     *
     * @param high the high
     * @return the optional
     */
    public String extract(int high){
        if(high > getChrBuf().limit() || high < getChrBuf().position())
            throw new IllegalArgumentException("Illegal argument exception");

        char[] buffer = new char[high- getChrBuf().position()];
        for(int i = 0; i < high- getChrBuf().position();i++){
            buffer[i] = (char)readNext();
        }
        return new String(buffer);
    }

    /**
     * Extract optional.
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
     * Current string.
     *
     * @return the string
     */
    public String currentString(){
        char[] buffer = new char[chrBuf.position()];
        for(int i = 0; i < getChrBuf().position();i++){
            buffer[i] = (char)readNext();
        }
        return new String(buffer);
    }

    /**
     * Read line.
     */
    public void readLine(){
        peek(HttpConstants.NEWLINE_CHAR, false);
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

    public boolean hasNextBefore(char nextChar,char beforeChar ){
        return true;
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
     * Gets chr buf.
     *
     * @return the chr buf
     */
    public CharBuffer getChrBuf() {
        return chrBuf;
    }
}
