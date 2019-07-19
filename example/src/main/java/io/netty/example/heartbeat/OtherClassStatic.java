package io.netty.example.heartbeat;

public class OtherClassStatic {
    public static void main(String[] args) {
        System.out.println(Parent.i);
    }
}

class Parent {
    public static final int i = -4;
}
