import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
        private static final Lock lock = new ReentrantLock();
        private static final Condition pingCondition = lock.newCondition();
        private static final Condition pongCondition = lock.newCondition();
        private static int iteration = 0;
        private static boolean isPing = true;

        public static void main(String[] args) {
            Thread thread1 = new Thread(() -> {
                while (true) {
                    lock.lock();
                    try {
                        while (!isPing) {
                            pingCondition.await();
                        }
                        System.out.println("PING #" + ++iteration);
                        isPing = false;
                        pongCondition.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });

            Thread thread2 = new Thread(() -> {

            });

            thread1.start();
            thread2.start();
        }
    }