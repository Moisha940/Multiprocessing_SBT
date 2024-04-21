public class VolatileRealization {
    private static volatile int count = 1;
    static final int numberOfIteration = 100;

    public static void main(String[] args) {
        new MyPingThread().start();
        new MyPongThread().start();
    }

    static private class MyPongThread extends Thread {
        @Override
        public void run() {
            while (count <= numberOfIteration) {
                if (count % 2 == 0) {
                    System.out.println("PONG: " + count);
                    count++;
                }
                Thread.yield();
            }
        }
    }

    static private class MyPingThread extends Thread {
        @Override
        public void run () {
            while (count <= numberOfIteration) {
                if (count % 2 == 1) {
                    System.out.println("PING: " + count);
                    count++;
                }
                Thread.yield();
            }
        }
    }
}
