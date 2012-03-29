package harmonic.io;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * LittleEndianDataInputStream
 *
 * Allows reading data that is organized by byte in little endian order.
 * 
 * Otherwise it is functionally equivalent to DataInputStream.
 * 
 * @author Luke Petrolekas
 */

/*The MIT License

Copyright (c) 2010 Luke Petrolekas

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

public final class LittleEndianDataInputStream extends InputStream implements DataInput{

    private DataInputStream dataInputStream;

    public LittleEndianDataInputStream(InputStream in){
        dataInputStream = new DataInputStream(in);
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        dataInputStream.readFully(b, off, len);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        return dataInputStream.skipBytes(n);
    }

    @Override
    public boolean readBoolean() throws IOException {
        return dataInputStream.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return dataInputStream.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return dataInputStream.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        short value = dataInputStream.readShort();
        return (short)((value & 0xFF) << 8 | (value & 0xFF00) >>> 8);
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return readShort() & 0xFFFF;
    }

    @Override
    public char readChar() throws IOException {
        return (char) readShort();
    }

    @Override
    public int readInt() throws IOException {
        int value = dataInputStream.readInt();
        return (value & 0xFF) << 24 | (value & 0xFF00) << 8
                | (value & 0xFF0000) >>> 8 | (value & 0xFF000000) >>> 24;
    }

    @Override
    public long readLong() throws IOException {
        long value = dataInputStream.readLong();
        return (value & 0xFFL) << 56
                | (value & 0xFF00L) << 40
                | (value & 0xFF0000L) << 24
                | (value & 0xFF000000L) << 8
                | (value & 0xFF00000000L) >>> 8
                | (value & 0xFF0000000000L) >>> 24
                | (value & 0xFF000000000000L) >>> 40
                | (value & 0xFF00000000000000L) >>> 56;
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @Override
    public String readLine() throws IOException {
        return dataInputStream.readLine();
    }

    @Override
    public String readUTF() throws IOException {
        return dataInputStream.readUTF();
    }

    @Override
    public int read() throws IOException {
        return dataInputStream.read();
    }

    @Override
    public int available() throws IOException {
        return dataInputStream.available();
    }

    @Override
    public void close() throws IOException {
        dataInputStream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        dataInputStream.mark(readlimit);
    }

    @Override
    public boolean markSupported() {
        return dataInputStream.markSupported();
    }

    @Override
    public synchronized void reset() throws IOException {
        dataInputStream.reset();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return dataInputStream.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        return dataInputStream.read(b);
    }

    @Override
    public long skip(long n) throws IOException {
        return dataInputStream.skip(n);
    }

    @Override
    public String toString() {
        return dataInputStream.toString();
    }

}
