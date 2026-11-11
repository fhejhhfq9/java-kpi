package task3;

import java.io.*;

public class CipherService {

    public void encrypt(String inPath, String outPath, char key) throws IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(inPath));
             OutputStream out = new EncryptOutputStream(
                     new BufferedOutputStream(new FileOutputStream(outPath)), key)) {
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        }
    }

    public void decrypt(String inPath, String outPath, char key) throws IOException {
        try (InputStream in = new DecryptInputStream(
                new BufferedInputStream(new FileInputStream(inPath)), key);
             OutputStream out = new BufferedOutputStream(new FileOutputStream(outPath))) {
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        }
    }
}