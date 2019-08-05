package ServerCodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

//负责处理每个线程通信的线程类
public class ServerThread implements Runnable {
    // 定义当前线程所处理的Socket
    Socket s = null;
    // 该线程所处理的Socket所对应的输入流
    BufferedReader br = null;

    public ServerThread(Socket s)
            throws IOException {
        this.s = s;
        // 初始化该Socket对应的输入流
        br = new BufferedReader(new InputStreamReader(
                s.getInputStream(), StandardCharsets.UTF_8));
    }

    public void run() {
        try {
            String content = null;
            // 采用循环不断从Socket中读取客户端发送过来的数据
            while ((content = readFromClient()) != null) {
                System.out.println("---" + Arrays.toString(content.getBytes(StandardCharsets.UTF_8)));
                System.out.println("---" + content);
                // 遍历socketList中的每个Socket，
                // 将读到的内容向每个Socket发送一次
                for (Iterator<Socket> it = MyServer.socketList.iterator(); it.hasNext(); ) {
                    Socket s = it.next();
                    try {

                        OutputStream os = s.getOutputStream();
                        os.write((content + "\n").getBytes(StandardCharsets.UTF_8));
                    } catch (SocketException e) {
                        e.printStackTrace();
                        // 删除该Socket。
                        it.remove();
                        System.out.println(MyServer.socketList);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 定义读取客户端数据的方法
    private String readFromClient() {
        try {
            return br.readLine();
        }
        // 如果捕捉到异常，表明该Socket对应的客户端已经关闭
        catch (IOException e) {
            e.printStackTrace();
            // 删除该Socket。
            MyServer.socketList.remove(s);
        }
        return null;
    }
}
