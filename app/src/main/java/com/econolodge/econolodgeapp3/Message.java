package com.econolodge.econolodgeapp3;

import android.graphics.Picture;

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
    public static final String SERVERIP = "10.0.0.15";
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
    }

    public static class PictureMessage implements Serializable {
        static final long serialVersionUID = -52888313;
        private byte[] picture;
        private String id;

        PictureMessage(byte[] picture, String id) {
            this.picture = picture;
            this.id = id;
        }

        public byte[] getPicture() {
            return picture;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setPicture(byte[] picture) {
            this.picture = picture;
        }
    }

    public static class AndroidToServer implements Serializable {
        static final long serialVersionUID = -52888313;
        private String message;

        public AndroidToServer(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ServerToAndroid implements Serializable {
        static final long serialVersionUID = -52888313;
        private String message;

        public ServerToAndroid(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
