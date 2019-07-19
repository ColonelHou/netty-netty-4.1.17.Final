package io.netty.example.chat;

/**
 * -XX:+TraceClassLoading 打印加载初始化类
 */
public class Test {
    public static void main(String[] args) {
        // 这样调用Son.static块不会打印, 因为Son没有被初始化
        System.out.println(Son.st);

//        System.out.println(Son.sstt);
    }
}

class Father {
    public static String st = "Hello";
    static {
        System.out.println("Father static");
    }
}

class Son extends Father {
    public static String sstt = "World";
    static {
        System.out.println("Son Static");
    }
}
