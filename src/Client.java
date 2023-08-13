import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private GUI gui;

    private RSA rsa;


    public Client(Socket s,GUI gui) {
        try {
            this.socket = s;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.gui=gui;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessages(String Message) {
        try {

            try {
                bufferedWriter.write(Message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println(Message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void receiveMessages() {
        new Thread(() -> {
            String Message;
            while (socket.isConnected()) {
                try {
                    Message = bufferedReader.readLine();
                    gui.setReceived(Message);
                    System.out.println(Message);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
        }).start();
    }


    public static void main(String[] args) {

    }
}
