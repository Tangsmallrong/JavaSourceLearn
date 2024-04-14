package test.Object_;

/**
 * java.lang.Object#wait(long)
 */
public class WaitTest {
    private static final Object sharedResource = new Object();
    private static boolean dataReady = false;

    public static void main(String[] args) {
        Thread consumerThread = new Thread(() -> {
            synchronized (sharedResource) {
                while (!dataReady) {
                    try {
                        System.out.println("消费者正在等待数据准备好...");
                        // 如果在5000毫秒内生产者准备数据并调用 notify(), 它将被唤醒; 否则, 它将因超时而唤醒
                        sharedResource.wait(5000);  // 等待通知或超时
                        if (dataReady) {
                            System.out.println("消费者拿到数据！");
                        } else {
                            System.out.println("消费者超时！ 无可用数据");
                        }
                    } catch (InterruptedException e) {
                        System.out.println("消费者被中断");
                    }
                }
            }
        });

        Thread producerThread = new Thread(() -> {
            synchronized (sharedResource) {
                try {
                    Thread.sleep(1000); // 准备数据需要 1 秒
                } catch (InterruptedException e) {
                    System.out.println("生产者在数据准备期间被中断");
                }
                dataReady = true;
                System.out.println("生产者已生产数据");
                sharedResource.notify();  // 通知消费者数据已准备好
            }
        });

        consumerThread.start();
        producerThread.start();
    }
}
