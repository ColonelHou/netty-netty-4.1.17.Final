package io.netty.example.io.decorator;

public class ComcreteDecorator1 extends Decorator{

    public ComcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }


    private void doAnotherThing() {
        System.out.println("功能A");
    }
}
