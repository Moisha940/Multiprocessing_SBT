import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static volatile boolean pingTurn = true;
    private static final Object lock = new Object();
    private static final AtomicInteger iteration = new AtomicInteger(1);

    public static void main(String[] args) {
        int numberOfIterations = 10;

        Thread pingThread = new Thread(() -> {
            while (iteration.get() <= numberOfIterations) {
                synchronized (lock) {
                    if (pingTurn) {
                        System.out.println("PING: " + iteration.getAndIncrement());
                        pingTurn = false;
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });

        Thread pongThread = new Thread(() -> {
            while (iteration.get() <= 10) {
                synchronized (lock) {
                    if (!pingTurn) {
                        System.out.println("PONG: " + iteration.getAndIncrement());
                        pingTurn = true;
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });

        pingThread.start();
        pongThread.start();

        try {
            pingThread.join();
            pongThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
