import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PORT1 = 666;


    public static class ServerThread implements Runnable {


        private static final List<ServerThread> Clients=new ArrayList<>();

        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;



        public ServerThread(Socket s) {
            try {
                this.socket = s;
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                Clients.add(this);
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void run() {

            String Message;
            while (socket.isConnected())
            {
                try{
                    Message=bufferedReader.readLine();
                    if(Message==null)
                    {
                        continue;
                    }
                    for(ServerThread client : Clients )
                        if(client !=this)
                    {
                        client.bufferedWriter.write(Message);
                        client.bufferedWriter.newLine();
                        client.bufferedWriter.flush();
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    break;
                }
            }

        }
    }

    public static void main(String[] args) {
                try (ServerSocket serverSocket = new ServerSocket(PORT1)) {
                    while (!serverSocket.isClosed()) {
                        new Thread(new ServerThread(serverSocket.accept())).start();
                    }
                } catch (IOException e) {
                    System.out.println("Error happened when creating the ServerSocket.. error:" + e.getMessage());
                }


    }
}
