package Http;

import utils.Delimiters;

/**
 * Created by daleappleby on 3/10/15.
 */
class HeaderReader {
    private String[] headerLines;
    private char[][] chars;

    //The position
    private int pos = 0;

    public HeaderReader(String string){
        parseHeader(string);
    }

    private final void parseHeader(String header) {
        headerLines = header.split(Delimiters.NEWLINE);
        chars = new char[headerLines.length][];
        for(int i = 0; i < headerLines.length;i++){
            chars[i] = headerLines[i].toCharArray();
        }
    }

    public final String[] getAndSplit(String delim) throws IndexOutOfBoundsException{
        return headerLines[pos++].split(delim);
    }

    public boolean hasNextLine(){
        return pos < headerLines.length - 1;
    }

    public int getLineLength(){
        return headerLines[pos].length();
    }

    public char readChar(int charIndex){
        return chars[pos][charIndex];
    }

    public char readChar(int pos,int charIndex) throws IndexOutOfBoundsException{
        return chars[pos][charIndex];
    }

    public String readLine() throws IndexOutOfBoundsException{
        return headerLines[pos++];
    }

    public String goBack() throws IndexOutOfBoundsException{
        return headerLines[--pos];
    }

    public int getNumberOfLines(){
        return headerLines.length;
    }

    public void setPos(int pos){
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
