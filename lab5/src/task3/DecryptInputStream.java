
package task3;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DecryptInputStream extends FilterInputStream {

    private final int key;

    public DecryptInputStream(InputStream in, char key) {
        super(in);
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int b = in.read();
        if (b == -1) {
            return -1;
        }
        return (b - key) & 0xFF;
    }

    @Override
    public int read(byte[] buff, int off, int len) throws IOException {
        int count = in.read(buff, off, len);
        if (count == -1) {
            return -1;
        }
        for (int i = off; i < off + count; i++) {
            buff[i] = (byte) ((buff[i] - key) & 0xFF);
        }

        return count;
    }
}
