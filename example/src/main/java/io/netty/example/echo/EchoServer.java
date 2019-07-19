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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import sun.misc.SignalHandler;

import java.util.ArrayList;

/**
 * Echoes back any received data from a client.
 * 服务端与客户端交互发数据
 */
public final class EchoServer {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(EchoServer.class);
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        logger.error("+++++++++++++++++++");
        // Configure SSL.
        /*final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }*/

        // 事件循环组, 底层是一个死循环
        // Configure the server.
        //线程池: boss线程池的线程负责处理请求的accept事件
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //线程池: work线程池负责请求的read和write事件; 由对应的Handler处理
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 方法链的调用
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // 通道
             .option(ChannelOption.SO_BACKLOG, 100)
             .handler(new LoggingHandler(LogLevel.INFO))   // 针对boss
             .childHandler(new ChannelInitializer<SocketChannel>() { // 针对worker

                 /**
                  * 1. ChannelInitializer实现被注册到ServerBootstrap
                  * 2. initChannel被调用时, 将在ChannelPipline中安装一组自定义的ChannelHandler
                  * 3. ChannelInitializer将它自己从ChannelPipline中移除
                  * (链中ChannelHandler执行顺序就是添加顺序)
                  * @param ch            the {@link Channel} which was registered.
                  * @throws Exception
                  */
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     /*if (sslCtx != null) {
                         p.addLast(sslCtx.newHandler(ch.alloc()));
                     }*/
//                     p.addLast(new LoggingHandler(LogLevel.INFO));
                     p.addLast(new EchoServerHandler());
                 }
             });

            // Start the server. 用同步的方式绑定服务器监听端口
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the server socket is closed.
            // 通过jstack看到main线程被阻塞到closeFuture(), 等待channel关闭
//            f.channel().closeFuture().sync();
            System.out.println("abc");
            f.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("链路关闭");
                    // 链路关闭时再释放线程池和连接句柄
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }
            });
        } finally {
            // Shut down all event loops to terminate all threads.
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
    }
}
