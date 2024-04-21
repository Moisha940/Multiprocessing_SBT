import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentRealization {

    private static int i = 0;
    private static boolean pingTurn = true;
    private static final int numberOfIteration = 98;
    private static final Lock lock = new ReentrantLock();
    private static final Condition pingCondition = lock.newCondition();
    private static final Condition pongCondition = lock.newCondition();

    public static void main(String[] args) {
        new MyPingThread().start();
        new MyPongThread().start();
    }

    static private class MyPingThread extends Thread {
        @Override
        public void run() {
            while (i < numberOfIteration) {
                lock.lock();
                try {
                    while (!pingTurn) {
                        pingCondition.await();
                    }
                    System.out.println("PING: " + ++i);
                    pingTurn = false;
                    pongCondition.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static private class MyPongThread extends Thread {
        @Override
        public void run () {
            while (true) {
                lock.lock();
                try {
                    while (pingTurn) {
                        pongCondition.await();
                    }
                    System.out.println("PONG: " + ++i);
                    pingTurn = true;
                    pingCondition.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
