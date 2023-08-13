import java.io.IOException;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws Exception {
        try(Socket socket = new Socket("localhost", 666)) {
            RSA rsa = new RSA();
            GUI gui = new GUI(rsa);
            Client client = new Client(socket,gui);
            gui.setClient(client);
            System.out.println("Connected to server");
            client.receiveMessages();
            while (true) {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
