import java.io.DataInputStream;
import java.io.InputStream;

public class ReadHandler implements  Runnable{

    private final DataInputStream is;
    private final Callback callback;


    public ReadHandler(DataInputStream is, Callback callback) {
        this.is = is;
        this.callback = callback;
    }

    @Override
    public void run() {
    try {
        while (true) {
            String msg = is.readUTF();
            callback.call(msg);
            }
    } catch (Exception e) {
        System.err.println("Exception on reading!");
        }
    }
}
