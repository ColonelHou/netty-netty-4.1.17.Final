package io.netty.example.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.example.protobuf.netty.MyDataInfo;

import java.util.Random;

//public class ProtocClientHandler extends SimpleChannelInboundHandler<MyDataInfo> {
public class ProtocClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*DataInfo.Student student = DataInfo.Student
                .newBuilder().setName("阿大")
                .setAge(25)
                .setAddress("北京").build();
        ctx.writeAndFlush(student);
        */

        int random = new Random().nextInt(3);

        MyDataInfo.MyMessage myMessage = null;
        if (random == 0) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                            .setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder()
                            .setName("阿大")
                            .setAge(25)
                            .setAddress("北京").build()).build();
        } else if (random == 1) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder()
                            .setName("一只狗")
                            .setAge(25).build()).build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder()
                            .setName("一只猫")
                            .setCity(25).build()).build();
        }
        ctx.writeAndFlush(myMessage);
    }
}
