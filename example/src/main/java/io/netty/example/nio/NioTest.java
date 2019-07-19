package io.netty.example.nio;

import io.netty.buffer.ByteBuf;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

public class NioTest {
    public static void main(String[] args)throws Exception {
        directBuffer();
    }
    public static void directBuffer() throws IOException {
        readWrite(true);
    }


    public static void readBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        ByteBuffer readBuffer = buffer.asReadOnlyBuffer();
        readBuffer.position(2);
        readBuffer.put((byte)23); // 这里异常
    }

    public static void testBufferAll() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(15);
        byteBuffer.putLong(1000000l);
        byteBuffer.putDouble(12.2345);
        byteBuffer.putShort((short) 2);
        byteBuffer.putChar('你');
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getChar());

        /**
         * 内存只保留一份, 多个buffer共享一块内存
         */
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i ++) {
            buffer.put((byte)i);
        }
        buffer.position(2);
        buffer.limit(6);

        ByteBuffer newBuffer = buffer.slice();
        for (int i = 0; i < newBuffer.capacity(); i ++) {
            byte b = newBuffer.get(i);
            b *= 2;
            newBuffer.put(i , b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }

    public static void readWrite(boolean isDirect) throws IOException {
        String currentDir = NioTest.class.getClassLoader().getResource("").getPath()
                + NioTest.class.getPackage().getName().replace(".", "/");
        File inFile = new File("/Users/houningning/Documents/opensource/netty/my/netty-4.0/example/src/main/java/io/netty/example/nio/input.txt" );
        File outFile = new File("/Users/houningning/Documents/opensource/netty/my/netty-4.0/example/src/main/java/io/netty/example/nio/output.txt");
        FileInputStream in = new FileInputStream(inFile);
        FileOutputStream fos = new FileOutputStream(outFile);

        FileChannel inputChannel = in.getChannel();
        FileChannel outputChannel = fos.getChannel();
        ByteBuffer buffer = null;
        if (isDirect) {
            // Buffer中有个变量long address;
            buffer = ByteBuffer.allocateDirect(1024);
        } else {
            buffer = ByteBuffer.allocate(1024);
        }


        while (true) {
            buffer.clear();
            int read = inputChannel.read(buffer);
            System.out.println("read = " + read);
            if (-1 == read) {
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }

        inputChannel.close();
        outputChannel.close();

    }

    public static void testChannel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("");
        FileChannel channel = fileInputStream.getChannel();
        // -----------------读
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        channel.read(byteBuffer);
        byteBuffer.flip();
        while (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            System.out.println(b);
        }
        fileInputStream.close();
        // ----------------写
        FileOutputStream outputStream = new FileOutputStream("");
        FileChannel outChannel = outputStream.getChannel();
        ByteBuffer byteBufferWrite = ByteBuffer.allocate(512);
        byte[] msg = "Hello world".getBytes();
        for (int i = 0; i < msg.length; i ++) {
            byteBufferWrite.put(msg[i]);
            System.out.println("position: " + byteBufferWrite.position());
            System.out.println("limit : " + byteBufferWrite.limit());
            System.out.println("capacity: " + byteBufferWrite.capacity());
        }
        byteBufferWrite.flip();
        outChannel.write(byteBufferWrite);
        outputStream.close();
    }

    public static void testBuffer() {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i ++) {
            int r = new SecureRandom().nextInt(20);
            buffer.put(r);
        }
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
