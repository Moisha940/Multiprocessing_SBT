import java.util.concurrent.CountDownLatch;

public class ThreadExecution {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1 started");
            latch.countDown(); // Уменьшаем счетчик после завершения работы потока 1
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2 waiting");
            try {
                latch.await(); // Поток 2 ждет, пока счетчик достигнет 0
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2 started");
        });

        thread1.start();
        thread2.start();
    }
}