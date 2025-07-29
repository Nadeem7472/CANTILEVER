package Cantilever.Task2;

import java.io.*;
import java.net.*;
import java.util.*;

// clint will send the request to the server and server will responds to it 

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 1234);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);

        Thread t = new Thread(() -> {
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg == null) break;
                    System.out.println(msg);
                }
            } catch (Exception e) {
            }
        });
        t.start();

        while (true) {
            String msg = sc.nextLine();
            pw.println(msg);
        }
    }
}
