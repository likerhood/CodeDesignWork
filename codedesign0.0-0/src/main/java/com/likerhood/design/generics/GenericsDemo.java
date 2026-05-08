package com.likerhood.design.generics;
import java.util.ArrayList;
import java.util.List;

public class GenericsDemo {

    public static void main(String[] args) {
        System.out.println("=== 1. 测试：为什么引入泛型 ===");
        testWhyGenerics();

        System.out.println("\n=== 2. 测试：泛型类 (Box) ===");
        testGenericClass();

        System.out.println("\n=== 3. 测试：类型参数约束 (MathBox) ===");
        testBounds();

        System.out.println("\n=== 4. 测试：泛型方法 ===");
        testGenericMethods();

        System.out.println("\n=== 5. 测试：通配符与 PECS ===");
        testWildcards();
    }

    /* =========================================================
     * 第一部分：实体类与基础定义
     * ========================================================= */
    static class Animal { public void eat() { System.out.println("Animal eating"); } }
    interface Flyable { void fly(); }

    static class Fruit {
        public String getName() { return this.getClass().getSimpleName(); }
    }
    static class Apple extends Fruit {}
    static class RedApple extends Apple {}
    static class Banana extends Fruit {}

    /* =========================================================
     * 第二部分：泛型类及工具类的定义
     * ========================================================= */

    // 1. 普通泛型类
    static class Box<T> {
        private T value;
        public Box(T value) { this.value = value; }
        public T getValue() { return value; }
        public void setValue(T value) { this.value = value; }
    }

    // 2. 带上界的泛型类
    static class MathBox<T extends Number> {
        private T number;
        public MathBox(T number) { this.number = number; }
        public double sqrt() {
            return Math.sqrt(number.doubleValue());
        }
    }

    // 3. 多重限定的泛型类
    static class FlyableAnimal<T extends Animal & Flyable> {
        T creature;
        public FlyableAnimal(T creature) { this.creature = creature; }
        public void action() {
            creature.eat();
            creature.fly();
        }
    }

    // 4. 包含静态泛型方法的类 (防坑演示)
    static class TestClass<T> {
        public void print(T item) { System.out.println(item); }
        // 静态方法必须声明独立的泛型 <E>，不能使用类的 <T>
        public static <E> void staticPrint(E item) { System.out.println(item); }
    }

    // 5. 返回泛型类型的工具类
    static class Converter {
        public static <T> List<T> arrayToList(T[] array) {
            List<T> list = new ArrayList<>();
            for (T element : array) {
                list.add(element);
            }
            return list;
        }
    }

    // 6. 多重泛型参数工具类
    static class PairUtils {
        public static <K, V> void printPair(K key, V value) {
            System.out.println("Key: " + key + " (Type: " + key.getClass().getSimpleName() + ")");
            System.out.println("Value: " + value + " (Type: " + value.getClass().getSimpleName() + ")");
        }
    }

    // 7. 带有边界约束的泛型方法
    static class MathUtils {
        public static <T extends Comparable<T>> T findMax(T a, T b) {
            return (a.compareTo(b) > 0) ? a : b;
        }
    }

    /* =========================================================
     * 第三部分：测试逻辑的实现
     * ========================================================= */

    private static void testWhyGenerics() {
        // 没有泛型的痛苦 (危险操作已注释)
        List oldList = new ArrayList();
        oldList.add("hello");
        oldList.add(123);
        String s = (String) oldList.get(0);
        // String err = (String) oldList.get(1); // 运行时抛出 ClassCastException

        // 泛型带来的改变
        List<String> genericList = new ArrayList<>();
        genericList.add("hello");
        // genericList.add(123); // 取消注释看报错：IDE 直接红线阻止混入 Integer
        String s2 = genericList.get(0);
        System.out.println("泛型集合取值 (无需强转): " + s2);
    }

    private static void testGenericClass() {
        Box<String> stringBox = new Box<>("hello generics");
        System.out.println("String Box 存放内容: " + stringBox.getValue());

        Box<Integer> intBox = new Box<>(100);
        System.out.println("Integer Box 存放内容: " + intBox.getValue());
    }

    private static void testBounds() {
        MathBox<Integer> intBox = new MathBox<>(10);
        System.out.println("MathBox 传入 10 的开方: " + intBox.sqrt());

        MathBox<Double> doubleBox = new MathBox<>(3.14);
        System.out.println("MathBox 传入 3.14 的开方: " + doubleBox.sqrt());

        // 取消注释看报错：String 不是 Number 的子类，不允许传入
        // MathBox<String> stringBox = new MathBox<>("error");
    }

    private static void testGenericMethods() {
        TestClass.staticPrint("--> 执行静态泛型方法");

        String[] strArr = {"Apple", "Banana"};
        List<String> list = Converter.arrayToList(strArr);
        System.out.println("--> Converter 工具类转化后的 List: " + list);

        System.out.println("--> 测试 PairUtils:");
        PairUtils.printPair(100, "Success");

        System.out.println("--> 测试 MathUtils (Comparable 限定):");
        Integer maxInt = MathUtils.findMax(10, 20);
        String maxStr = MathUtils.findMax("Alice", "Bob");
        System.out.println("10 和 20 的最大值: " + maxInt);
        System.out.println("Alice 和 Bob 的最大值: " + maxStr);

        // 取消注释看报错：Object 没有实现 Comparable，无法进行比较
        // MathUtils.findMax(new Object(), new Object());
    }

    private static void testWildcards() {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple());

        List<Banana> bananas = new ArrayList<>();
        bananas.add(new Banana());

        // 1. 测试 <? extends Fruit> (Producer)
        System.out.println("--> 测试 <? extends Fruit> (只能读):");
        checkFruits(apples);
        checkFruits(bananas);

        // 2. 测试 <? super Apple> (Consumer)
        System.out.println("--> 测试 <? super Apple> (只能写):");
        List<Fruit> fruits = new ArrayList<>();
        List<Object> objects = new ArrayList<>();

        addApples(apples);
        addApples(fruits);
        addApples(objects);
        System.out.println("成功向各种类型的箱子中写入 Apple 对象。");
        // addApples(bananas); // 取消注释看报错：香蕉筐不能当做苹果收集箱
    }

    /* =========================================================
     * 第四部分：通配符测试专属辅助方法
     * ========================================================= */

    // 上界通配符：只能读，绝对不能写！
    public static void checkFruits(List<? extends Fruit> basket) {
        if (!basket.isEmpty()) {
            Fruit f = basket.get(0);
            System.out.println("读取到一颗: " + f.getName());
        }
        // 取消注释看报错：不知道具体是哪种水果筐，禁止盲目写入
        // basket.add(new Apple());
    }

    // 下界通配符：安全写入，读出来全当 Object 处理
    public static void addApples(List<? super Apple> basket) {
        basket.add(new Apple());
        basket.add(new RedApple());

        // 取消注释看报错：因为箱子可能是 List<Object>，所以取出来的东西无法保证是苹果
        // Apple a = basket.get(0);
    }
}