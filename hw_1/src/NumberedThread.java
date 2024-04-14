
public class NumberedThread extends Thread implements  Runnable {

    private final int number;
    final Runnable task;

    public NumberedThread(int number, Runnable task) {
        this.task = task;
        this.number = number;
    }

    @Override
    public void run() {
        if (task != null) {
            System.out.println("Thread " + number + " started");
        }
    }

    public static void main(String[] args)  {
        int numThreads = 5;
        for (int i = 1; i <= numThreads; i++) {
            NumberedThread thread = new NumberedThread(i, () -> System.out.println("Executing lambda function"));
            thread.start();
        }
    }
}