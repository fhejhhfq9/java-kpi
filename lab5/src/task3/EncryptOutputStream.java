package task3;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EncryptOutputStream extends FilterOutputStream {
    private final int key;

    public EncryptOutputStream(OutputStream out, char key) {
        super(out);
        this.key = key;
    }

    @Override
    public void write(int b) throws IOException {
        super.write((b + key) & 0xFF);
    }

    @Override
    public void write(byte[] buff, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(buff[i]);
        }
    }
}
