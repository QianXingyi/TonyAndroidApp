package ServerCodes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer {
    // 定义保存所有Socket的ArrayList
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();

    public static void main(String[] args)
            throws IOException {
        ServerSocket ss = new ServerSocket(3001);
        while (true) {
            // 此行代码会阻塞，将一直等待别人的连接

            Socket s = ss.accept();
            System.out.println("0");
            socketList.add(s);
            // 每当客户端连接后启动一条ServerThread线程为该客户端服务
            System.out.println("1");
            new Thread(new ServerThread(s)).start();
            System.out.println("2");
        }
    }
}
