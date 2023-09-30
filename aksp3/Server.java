import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static List<ClientHandler> clients = new ArrayList<>();
    private static List<String> messages = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        new Thread(new MessageSender()).start();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final PrintWriter out;

        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try (Scanner in = new Scanner(clientSocket.getInputStream())) {
                while (in.hasNextLine()) {
                    String message = in.nextLine();
                    synchronized (messages) {
                        messages.add(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }

    private static class MessageSender implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (messages) {
                    if (!messages.isEmpty()) {
                        for (ClientHandler client : clients) {
                            for (String message : messages) {
                                client.sendMessage(message);
                            }
                        }
                        messages.clear();
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
