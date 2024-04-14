package test.Object_;

/**
 * java.lang.Object#notify()
 */
public class NotifyTest {
    private static Object lock = new Object();
    private static int[] buffer;
    private static int count;

    static class Producer {
        void produce() {
            synchronized (lock) {
                while (isFull(buffer)) {
                    try {
                        lock.wait();  // 等待消费者消费数据
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[count++] = 1;  // 生产一个元素
                lock.notify();  // 唤醒一个等待的（消费者）线程
            }
        }
    }

    static class Consumer {
        void consume() {
            synchronized (lock) {
                while (isEmpty(buffer)) {
                    try {
                        lock.wait();  // 等待生产者生产数据
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[--count] = 0;  // 消费一个元素
                lock.notify();  // 唤醒一个等待的（生产者）线程
            }
        }
    }

    static boolean isFull(int[] buffer) {
        return count == buffer.length;
    }

    static boolean isEmpty(int[] buffer) {
        return count == 0;
    }

    public static void main(String[] args) {
        buffer = new int[10];
        count = 0;
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        // 创建线程执行生产和消费
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                producer.produce();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                consumer.consume();
            }
        }).start();
    }
}
