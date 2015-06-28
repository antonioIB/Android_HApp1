package com.econolodge.econolodgeapp3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Antonio on 6/27/2015.
 */
public class Message {
    private boolean lRun = false;
    private String serverMessage;
    public static final String SERVERIP = "10.0.0.8";
    public static final int SERVERPORT = 5551;

    PrintWriter out;
    BufferedReader in;

    public static class LogInMessage  implements Serializable  {
        private String username;
        private String password;

        LogInMessage(String u, String p) {
            username = u;
            password = p;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        /*
        public void sendData() {
            lRun = true;

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVERIP);
                Socket socket = new Socket(serverAddr, SERVERPORT);

                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream())), true);
                out.print()

            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}
