public class PingPong {
    private static final Object lock = new Object();
    private static volatile boolean pingTurn = true;

    public static void main(String[] args) {
        Thread pingThread = new Thread(new Ping());
        Thread pongThread = new Thread(new Pong());

        pingThread.start();
        pongThread.start();
    }

    static class Ping implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (!pingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.println("Ping");
                    pingTurn = false;
                    lock.notify();
                }
            }
        }
    }

    static class Pong implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (pingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.println("Pong");
                    pingTurn = true;
                    lock.notify();
                }
            }
        }
    }
}
