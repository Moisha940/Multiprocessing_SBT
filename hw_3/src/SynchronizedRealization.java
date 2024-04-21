public class SynchronizedRealization  {
    private static int i = 0;
    private static boolean pingTurn = true;
    private static final int numberOfIteration = 100;
    private static final Object lock = new Object(); // на этом объекте будеым вызвать synchronized

    public static void main(String[] args) {
        new MyPingThread().start();
        new MyPongThread().start();
    }

    static public class MyPingThread extends Thread  {
        @Override
        public void run () {
            while (i < numberOfIteration) {
                synchronized (lock) {
                    while (pingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("PING: " + i);
                    pingTurn = true;
                    ++i;
                    lock.notify();
                }
            }
        }
    }

    static public class MyPongThread extends Thread {
        @Override
        public void run() {
            while (i < numberOfIteration) {
                synchronized (lock) {
                    while (!pingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("PONG: " + i);
                    pingTurn = false;
                    ++i;
                    lock.notify();
                }
            }
        }

    }
}
