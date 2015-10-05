package http.header;

import http.constants.HttpConstants;

import java.nio.CharBuffer;
import java.util.Optional;


/**
 * The type Char buf reader.
 */
final class CharBufReader {
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
     * Returns true if the char buffer has more characters remaining.
     *
     * @return the boolean
     */
    public boolean hasNext(){
        return chrBuf.hasRemaining();
    }

    /**
     * Reads the next char in the buffer and then rolls back the position
     *
     * @return the int
     */
    public int peekNextChar(){
        if(!hasNext())
            return -1;

        char c = chrBuf.get();
        chrBuf.position(chrBuf.position()-1);
        return c;
    }


    /**
     * Skip forward.
     *
     * @param num the num
     */
    public void skipForward(int num){
        this.chrBuf.position(this.chrBuf.position()+num);
    }


    /**
     * Peek seperator.
     *
     * Attempts to find the seperator in the char buffer and returns the index of the seperator should it exist, else -1.
     *
     * @param c the c
     * @return the int
     */
    public int peekSeperator(char c, boolean withinLine,boolean resetPosition){
        int startPos = chrBuf.position();
        while(hasNext()){
            char readChar = (char) readNext();
            if(readChar == c)
                return chrBuf.position();
            if(c == HttpConstants.NEWLINE.charAt(0) && withinLine)
                break;
        }
        if(resetPosition)
            chrBuf.position(startPos);

        return -1;
    }


    /**
     * Extract optional.
     *
     * @param low the low
     * @param high the high
     * @return the optional
     */
    public Optional<String> extract(int low,int high){
        if(low < 0 || high > chrBuf.limit())
            return Optional.empty();

        System.out.println(high);
        System.out.println(low);
        chrBuf.position(low);
        char[] buffer = new char[50];
        for(int i = 0; i < high;i++){
            buffer[i] = (char)readNext();
        }
        return Optional.of(new String(buffer));
    }

    /**
     * Resets the readers position to the buffers mark
     * Reset position to mark.
     */
    public void resetPositionToMark(){
        chrBuf.reset();
    }

    /**
     * Marks a place in the buffer.
     * Mark position.
     */
    public void markPosition(){
        markPosition = chrBuf.position();
        chrBuf.mark();
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
        return chrBuf.get();
    }
}