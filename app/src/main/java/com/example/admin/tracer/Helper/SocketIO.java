package com.example.admin.tracer.Helper;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by admin on 2016-07-13.
 */
public class SocketIO {
    private volatile static Socket socket = null;
    private static final String SERVER_ADDRESS = "http://52.78.18.19";

    private SocketIO(){

    }

    public static Socket getSocket(){
        if(socket==null){
            synchronized (SocketIO.class){
                if(socket==null){
                    try {
                        socket = IO.socket(SERVER_ADDRESS);
                        socket.connect();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return socket;
    }
}
