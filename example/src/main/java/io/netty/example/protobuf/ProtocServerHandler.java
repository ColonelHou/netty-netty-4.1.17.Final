package io.netty.example.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.example.protobuf.netty.MyDataInfo;

// 泛型显示我client-server之间传输的是字符串
//public class ProtocServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Student> {
public class ProtocServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        /**
         * 这块相当于springmvc中的DispatcherServlet
         */
        MyDataInfo.MyMessage.DataType type = msg.getDataType();
        if (type == MyDataInfo.MyMessage.DataType.StudentType) {
            MyDataInfo.Student st = msg.getStudent();
            System.out.println(st.getName() + ", " + st.getAge() + ", " + st.getAddress());
        } else if (type == MyDataInfo.MyMessage.DataType.DogType) {
            MyDataInfo.Dog dog = msg.getDog();
            System.out.println(dog.getName() + ", " + dog.getAge());
        } else {
            MyDataInfo.Cat cat = msg.getCat();
            System.out.println(cat.getName() + ", " + cat.getCity());
        }
    }
}
