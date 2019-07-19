package io.netty.example.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * ASCII 7位 128
 * ISO-8859-1 8位即一个字节(byte), 来表示一个字符, 共计可以表示256个字符
 * gb2312 国标, 用两个字节表示一个汉字
 * gbk 国标的扩展, 把一些生偏字又拿入进来
 * gb18030 最完整的汉字表示
 * big5 湾湾
 * ========>问题, 每个国家出现一堆字符, 全球怎么统一
 * unicode来统一全球所有字符;  用两个字节表示一个字符(相对于欧美, 原来一个字节现在变成两个字节, 存储空间具大的膨胀; 不适合英文国家的编码)
 * utf-8 Unicode Translation Format; unicode是编码方式, UTF存储格式; 但UTF是Unicode的实现方式之一;
 *       变长字节表示形式; 三个字节表示一个中文; 最多可以用6个字节表示一个字符
 *    -16  -16LE（litte endian小端）-BE(big endian大端) 0xFEFF开头的文件(BE) 0xFFFE开头的文件是小端(LE)
 *    -32
 *    -63
 * BOM(Byte Order Mark)字节序, win下有BOM头,
 *
 */
public class EncoderDecoderTest {
    public static void main(String[] args) throws Exception {
        String input = "/Users/houningning/Documents/opensource/netty/my/netty-4.0/example/src/main/java/io/netty/example/nio/input.txt";
        String output = "/Users/houningning/Documents/opensource/netty/my/netty-4.0/example/src/main/java/io/netty/example/nio/input_coder.txt";

        RandomAccessFile inputRandomFile = new RandomAccessFile(input, "r");
        RandomAccessFile outputRandomFile = new RandomAccessFile(output, "rw");

        long inputLen = new File(input).length();

        FileChannel inputFileChannel = inputRandomFile.getChannel();
        FileChannel outputFileChannel = outputRandomFile.getChannel();

        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLen);


        System.out.println("=====================");
        Charset.availableCharsets().forEach((k, v) -> {
            System.out.println(k + ", " + v);
        });

//        Charset charset = Charset.forName("utf-8");
        Charset charset = Charset.forName("iso-8859-1"); // 设置成iso为什么结果文件正常显示utf-8?
        /**
         * 三个字节存储一个汉字,
         */
        CharsetEncoder encoder = charset.newEncoder();
        // 解码, 把磁盘上文件数据解码成Buffer
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer charBuffer = decoder.decode(inputData);
        System.out.println(charBuffer.get(10));
//        ByteBuffer outputData = encoder.encode(charBuffer); // 上面是iso-8859-1, 这里用这个正常输出
        ByteBuffer outputData = Charset.forName("utf-8").encode(charBuffer); // 上面iso-8859-1, 下面用这个就出现错了

        outputFileChannel.write(outputData);

        inputRandomFile.close();
        outputRandomFile.close();


    }
}
