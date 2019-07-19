package io.netty.example.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;

// 泛型显示我client-server之间传输的是字符串
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 将连接好的客户端放到此对象中
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息 " + msg + "\n");
            } else {
                ch.writeAndFlush("自己 " + msg + "\n");
            }
        });
//        ctx.channel().writeAndFlush("from server " + UUID.randomUUID());
    }

    /**
     * 表示连接建立; 广播给其他客户端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 先广播, 再加入
        channelGroup.writeAndFlush("服务器 - " + channel.remoteAddress() + " 加入\n");
        channelGroup.add(channel);
    }

    /**
     * 客户端断开连接
     * 如果客户端进入飞行模式, 这块不会被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("服务器 - " + channel.remoteAddress() + "断线\n");
        System.out.println("在线客户端数量剩: " + channelGroup.size());
    }

    /**
     * 连接处于活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线");
    }

    /**
     * 客户端连接下线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 出现异常把服务器关掉
        ctx.close();
    }
}
