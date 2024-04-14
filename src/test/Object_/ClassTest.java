package test.Object_;

/**
 * java.lang.Object#getClass()
 */
public class ClassTest {
    public static void main(String[] args) {
        // 创建不同类型的对象
        Object obj = new Object();

        // 使用 getClass() 获取类信息
        System.out.println("The class of obj is: " + obj.getClass());
    }
}

