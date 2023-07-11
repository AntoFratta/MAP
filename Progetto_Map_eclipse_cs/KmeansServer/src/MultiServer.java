import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


    public class MultiServer {
        int port = 8080;

        public static void main(String[] args) throws IOException {
            MultiServer mt = new MultiServer(8080);
        }

        public MultiServer(int port) throws IOException {
            this.port = port;
            run();
        }

        public void run() throws IOException {
            ServerSocket sSocket = null;
            System.out.println("Avvio del server sulla porta " + port);
            try {
                sSocket = new ServerSocket(port);
                while (true) {
                    System.out.println("In attesa di connessione ");
                    Socket socket = sSocket.accept();
                    try {
                        new ServerOneClient(socket);
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            } finally {
                if (sSocket != null) {
                    sSocket.close();
                }
            }
        }

    }


