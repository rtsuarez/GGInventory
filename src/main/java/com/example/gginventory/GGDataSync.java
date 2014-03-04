package com.example.gginventory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.AsyncTask;

public class GGDataSync extends AsyncTask<Context, Integer, Boolean> {
    private final static String serverIp = "192.168.0.4";
    private final static int port = 2046;
    private final static String dbName = "/databases/gginventory.db";

    public void sync(Context context) {
        try {
            String dbPath = context.getFilesDir().getParentFile().getPath() + dbName;
            System.out.println("DBPATH: " + dbPath);
            InetAddress serverAddr = InetAddress.getByName(serverIp);
            System.out.println("Trying to create socket for host address " + serverIp + " on port " + port);
            Socket socket = new Socket(serverAddr, port);
            System.out.println("Success!");

            File db = new File(dbPath);

            byte[] mybytearray = new byte[(int)db.length()];

            FileInputStream fileIn = new FileInputStream(db);
            BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
            bufferedIn.read(mybytearray, 0, mybytearray.length);
            OutputStream output = socket.getOutputStream();

            output.write(mybytearray, 0, mybytearray.length);
            output.flush();
            bufferedIn.close();
            socket.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
        }
    }

    protected void OnPostExecute() {
        System.out.println("Done in OnPostExecute");
    }

    protected Boolean doInBackground(Context... c) {
        sync(c[0]);
        return true;
    }
}
