package test.Object_;

public class CloneTest {
    public static void main(String[] args) {
        Address address = new Address("New York", "USA");
        Person original = new Person("John Doe", 30, address);

        try {
            Person cloned = (Person) original.clone();
            System.out.println("Original: " + original);
            System.out.println("Cloned: " + cloned);

            // 修改克隆后的地址
            cloned.getAddress().setCity("Los Angeles");
            System.out.println("修改后:");
            System.out.println("Original: " + original);
            System.out.println("Cloned: " + cloned);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

class Person implements Cloneable {
    private String name;
    private int age;
    private Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // 打断点 1: 检查进入 clone 方法
        Person cloned = (Person) super.clone(); // 浅复制
        cloned.address = (Address) address.clone(); // 深复制
        // 打断点 2: 检查完成深复制后的状态
        return cloned;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", address=" + address + '}';
    }
}

class Address implements Cloneable {
    private String city;
    private String country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // 打断点 3: 检查 Address 的 clone 方法
        return super.clone();
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{city='" + city + "', country='" + country + "'}";
    }
}
