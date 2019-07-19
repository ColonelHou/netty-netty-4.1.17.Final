/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.echo;

import io.netty.buffer.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * Handler implementation for the echo server.
 * ChannelHandler处理入/出站数据的应用程序逻辑的容器, 格式转换, 异常处理
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter { // SimpleChannelInboundHandler

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf bf = (ByteBuf) msg;
        ByteBuf byteBuf = Unpooled.buffer(bf.readableBytes());
        ByteBuf directBuf = Unpooled.directBuffer(bf.readableBytes());
        ByteBuf wrapperBuf = Unpooled.wrappedBuffer("".getBytes());
//        PooledByteBufAllocator
        Unpooled.unreleasableBuffer(bf);
        bf.readBytes(byteBuf);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ByteBuf resp = Unpooled.copiedBuffer("Send to Client".getBytes());
        System.out.println("接收到客户端消息: " + new String(byteBuf.array()) + new Date().toString());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
