import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        new Thread(new Receiver(socket)).start();

        try (Scanner scanner = new Scanner(System.in);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                out.println(message);
            }
        }
    }

    private static class Receiver implements Runnable {
        private final Socket socket;

        public Receiver(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (Scanner in = new Scanner(socket.getInputStream())) {
                while (in.hasNextLine()) {
                    String message = in.nextLine();
                    System.out.println("Received: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
