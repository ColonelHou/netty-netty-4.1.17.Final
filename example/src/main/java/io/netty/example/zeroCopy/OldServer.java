package io.netty.example.zeroCopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OldServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            try {
                byte[] byteArray = new byte[4096];

                // 不断的获取数据
                while (true) {
                    int readCount = in.read(byteArray, 0, byteArray.length);
                    if (-1 == readCount) {
                        break;
                    }
                }

            } catch (Exception e) {

            }
        }
    }
}
