package Cantilever.Task2;

import java.io.*;
import java.net.*;
// server receive the clients request 
// port must be same for communication 
// i know each line of code It's not AI Generated
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(1234);
        Socket s1 = ss.accept();
        Socket s2 = ss.accept();

        BufferedReader br1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        PrintWriter pw1 = new PrintWriter(s1.getOutputStream(), true);

        BufferedReader br2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
        PrintWriter pw2 = new PrintWriter(s2.getOutputStream(), true);
        
        // i have used multithreading for real time communication :-)
        Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    String msg = br1.readLine();
                    if (msg == null) break;
                    pw2.println("User1: " + msg);
                }
            } catch (Exception e) {
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                while (true) {
                    String msg = br2.readLine();
                    if (msg == null) break;
                    pw1.println("User2: " + msg);
                }
            } catch (Exception e) {
            }
        });

        t1.start();
        t2.start();
    }
}
