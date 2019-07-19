package io.netty.example.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.example.protobuf.gen.DataInfo;

public class ProtocTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student
                .newBuilder().setName("阿大")
                .setAge(25)
                .setAddress("北京").build();
        byte[] byteStu = student.toByteArray();
        DataInfo.Student ss = DataInfo.Student.parseFrom(byteStu);
        System.out.println(ss.getAddress());
        System.out.println(ss.getName());
        System.out.println(ss.getAge());
    }
}
