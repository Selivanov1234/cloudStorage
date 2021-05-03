package io;

import java.io.*;

public class toUtils {
    public static void main(String[] args) throws IOException {

        InputStream is = new FileInputStream("wow.jpg");
        OutputStream os = new FileOutputStream("copy.jpg");

        byte[] buffer = new byte[256];

        int read;

        while (true) {
            read = is.read(buffer);
            if (read == -1) {
                break;
            }
            os.write(buffer, 0, read);
        }
        os.flush();
        os.close();
    }
}
