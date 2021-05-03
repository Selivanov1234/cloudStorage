import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    public ListView<String> listView;
    public TextField input;
    private DataInputStream is;
    private DataOutputStream os;


    public void send(ActionEvent actionEvent) throws IOException {
        os.writeUTF(input.getText());
        os.flush();
        input.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());
            ReadHandler handler = new ReadHandler(is,
                    msg -> Platform.runLater(
                            () -> listView.getItems().add(msg)
                    )
            );
            Thread readThread = new Thread(handler);
            readThread.setDaemon(true);
            readThread.start();
        } catch (Exception e) {
            System.err.println("Socket error");
        }

    }
}
