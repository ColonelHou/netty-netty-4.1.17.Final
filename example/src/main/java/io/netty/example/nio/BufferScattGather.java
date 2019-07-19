package io.netty.example.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 分散与聚集
 */
public class BufferScattGather {
    public static void main(String[] args) throws IOException {
        // 一个channel中的数据读到多个buffer中 Scattering


        // 顺序把多个buffer中数据写到一个channel中 Gathering


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        int messageLen = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int byteSRead = 0;
            while (byteSRead < messageLen) {
                long r = socketChannel.read(buffers);
                byteSRead += r;
                System.out.println("bytesRead : " + byteSRead);

                Arrays.asList(buffers).stream().map(
                        buffer -> "position: " + buffer.position() +
                        ", limit: " + buffer.limit() +
                        ", capacity: " + buffer.capacity()).forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(
                    buffer -> buffer.flip());
            long bytesWriten = 0;
            while (bytesWriten < messageLen) {
                long r = socketChannel.write(buffers);
                bytesWriten += r;
            }
            Arrays.asList(buffers).forEach(
                    buffer -> buffer.clear());

            System.out.println("byteSRead : "+ byteSRead + ", bytesWriten: " + bytesWriten + ", meesageLen: " + messageLen);

        }
    }
}
