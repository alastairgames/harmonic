package harmonic.riff;

import java.io.DataInput;
import java.io.IOException;
import java.util.Stack;

/** Implementation class for reading RIFF files.
 */

/*
 * The RIFF reader is implemented with a flyweight pattern to represent
 * the different elements of the riff file and a stack to track the hiearchy
 * of the chunks.
 */

/*The MIT License

Copyright (c) 2012 Luke Petrolekas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/

final class RiffReaderImpl implements RiffReader{
    
    private static final int RIFF_CHUNK = 0x52494646;
    private static final int LIST_CHUNK = 0x4C495354;
    
    private Stack<RiffChunk> stack;
    private RiffChunk chunk;
    private DataInput d;

    RiffReaderImpl(DataInput d) throws IOException {
        stack = new Stack<>();
        this.d = d;
        stack.push(createChunk());
        
        if (chunk.fourCC != RIFF_CHUNK){
            stack.pop();
            throw new IllegalArgumentException("Given data is not RIFF data.");
        }
        
        chunk = stack.peek();
    }
    
    @Override
    public boolean push() throws IOException{
         if (chunk.bitsRemaining == 0)
            return false;
         
       switch(chunk.fourCC){
           case RIFF_CHUNK:
           case LIST_CHUNK:
               stack.push(createChunk());
               chunk = stack.peek();
               return true;
           default:
               return false;
       }
    }
    
    @Override
    public boolean pop() throws IOException{
        if (chunk.bitsRemaining > 0)
            return false;
        
        RiffChunk poppedChunk = null;
        
        //skip pad byte
        if (chunk.len % 2 != 0)
            d.readByte();
        
        if (!stack.empty())
            poppedChunk = stack.pop();
        
        if (!stack.empty()){ //note how pop reduces size by 1 and we will have a poppedChunk.
            chunk = stack.peek();
            chunk.bitsRemaining -= poppedChunk.len;
        }else //in this case the RIFF chunk has been completely read.
            chunk = null;
        
        return true;
    }
            
    private static int readFourCC(byte[] b){
        if (b.length != 4)
            throw new IllegalArgumentException("Byte array entered is not length 4.");    
        return b[0] << 24 | b[1] << 16 |
                b[2] << 8 | b[3];
    }
    
    private RiffChunk createChunk() throws IOException{
        byte[] temp = new byte[4];
        d.readFully(temp);
        
        int fourcc = readFourCC(temp);
        int len = d.readInt();
        int type = -1;
        int header = 8;
        //if it is of type LIST, then there is a type associated with it
        if (fourcc == RIFF_CHUNK || fourcc == LIST_CHUNK){       
             d.readFully(temp);
            type = readFourCC(temp);
            len -= 4; //subtract the cost of reading the type, which is incl.
            header = 12;
        }

        return new RiffChunk(fourcc, len, type, header);
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        assertEnoughData(b.length * Byte.SIZE);
        chunk.bitsRemaining -= b.length;
        d.readFully(b);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        assertEnoughData(len * Byte.SIZE);
        chunk.bitsRemaining -= len;
        d.readFully(b, off, len);
    }

    @Override
     public int skipBytes(int n) throws IOException {
        assertEnoughData(n * Byte.SIZE);
        int skippedBytes = d.skipBytes(n);
        
        if (skippedBytes > 0)
            chunk.bitsRemaining -= skippedBytes * Byte.SIZE;
        
        return skippedBytes;
     }

    @Override
    public boolean readBoolean() throws IOException {
        assertEnoughData(Byte.SIZE);
        chunk.bitsRemaining -= Byte.SIZE;
        return d.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        assertEnoughData(Byte.SIZE);
        chunk.bitsRemaining -= Byte.SIZE;
        return d.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        assertEnoughData(Byte.SIZE);
        chunk.bitsRemaining -= Byte.SIZE;
        return d.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        assertEnoughData(Short.SIZE);
        chunk.bitsRemaining -= Short.SIZE;
        return d.readShort();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        assertEnoughData(Short.SIZE);
        chunk.bitsRemaining -= Short.SIZE;
        return d.readUnsignedShort();
    }

    @Override
    public char readChar() throws IOException {
        assertEnoughData(Character.SIZE);
        chunk.bitsRemaining -= Character.SIZE;
        return d.readChar();
    }

    @Override
    public int readInt() throws IOException {
        assertEnoughData(Integer.SIZE);
        chunk.bitsRemaining-= Integer.SIZE;
        return d.readInt();
    }

    @Override
    public long readLong() throws IOException {
        assertEnoughData(Long.SIZE);
        chunk.bitsRemaining -= Long.SIZE;
        return d.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        assertEnoughData(Float.SIZE);
        chunk.bitsRemaining -= Float.SIZE;
        return d.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        assertEnoughData(Double.SIZE);
        chunk.bitsRemaining -= Double.SIZE;
        return d.readFloat();
    }

    @Override
    public String readLine() throws IOException {
        throw new UnsupportedOperationException("Not a supported operation.");
    }

    @Override
    public String readUTF() throws IOException {
        throw new UnsupportedOperationException("Not a supported operation.");
    }
    
    @Override
    public int getFourCC() {
        return chunk.fourCC;
    }

    @Override
    public int getType(){
        return chunk.type;
    }

    private void assertEnoughData(int bits) {
        if (bits  > chunk.bitsRemaining)
            throw new RuntimeException("Not enough data in this chunk.");
    }
          
    private class RiffChunk {
        int fourCC;
        int len;
        int type;
        int header;
        long bitsRemaining;

        public RiffChunk(int fourCC, int len, int type, int header) {
            this.fourCC = fourCC;
            this.len = len;
            this.type = type;
            this.header = header;
            this.bitsRemaining = len * Byte.SIZE;
        }
         
    }
}