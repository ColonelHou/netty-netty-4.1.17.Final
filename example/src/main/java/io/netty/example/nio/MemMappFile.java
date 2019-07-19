package io.netty.example.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class MemMappFile {
    public static void main(String[] args) throws IOException {
        testFileLock();
    }

    public static void testFileLock() throws IOException {
        String file = "/Users/houningning/Documents/opensource/netty/my/netty-4.0/example/src/main/java/io/netty/example/nio/MMPFile.txt";
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        FileLock fileLock = fileChannel.lock(3, 6, true);
        System.out.println("valid : " + fileLock.isValid());
        System.out.println("lock type : " + fileLock.isShared());
        fileLock.release();
    }

    /**
     * 内存映射文件, 对文件的修改全是在内存中, 修改完后, 由OS去完成磁盘的读写
     * @throws IOException
     */
    public static void testMappedByteBuffer() throws IOException {
        String file = "/Users/houningning/Documents/opensource/netty/my/netty-4.0/example/src/main/java/io/netty/example/nio/MMPFile.txt";
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte)'a'); // 对内存中修改了
        mappedByteBuffer.put(3, (byte)'b');
        randomAccessFile.close();
    }
}
