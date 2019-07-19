package io.netty.example.io.decorator;

/**
 * 动态给对象添加新的职责, 而不会影响其他对象
 */
public class Main {
    public static void main(String[] args) {
        Component component = new ComcreteDecorator1(new ComcreteDecorator2(new ConcreteComponent()));
        component.doSomething();
    }
}
