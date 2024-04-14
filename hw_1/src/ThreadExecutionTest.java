import java.util.concurrent.CountDownLatch;

public class ThreadExecutionTest {

    public static void main(String[] args) {
        long startTimeNaive = System.currentTimeMillis();

        Thread thread1Naive = new Thread(() -> {
            System.out.println("Naive Thread 1 started");
        });

        Thread thread2Naive = new Thread(() -> {
            System.out.println("Naive Thread 2 started");
        });

        thread1Naive.start();
        thread2Naive.start();

        long endTimeNaive = System.currentTimeMillis();
        long timeElapsedNaive = endTimeNaive - startTimeNaive;
        System.out.println("Naive Timespan: " + timeElapsedNaive + "ms");

        long startTimeImproved = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(2);

        Thread thread1Improved = new Thread(() -> {
            System.out.println("Improved Thread 1 started");
            latch.countDown();
        });

        Thread thread2Improved = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Improved Thread 2 started");
        });

        thread1Improved.start();
        thread2Improved.start();

        long endTimeImproved = System.currentTimeMillis();
        long timeElapsedImproved = endTimeImproved - startTimeImproved;
        System.out.println("Improved Timespan: " + timeElapsedImproved + "ms");
    }
}

