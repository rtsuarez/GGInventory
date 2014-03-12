package com.example.gginventory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.AsyncTask;

public class DatabaseSync extends AsyncTask<Context, Integer, Boolean> {
	private final static String serverIp = "192.168.0.4";
	private final static int port = 2045;
	private final static String dbName = "/databases/gginventory.db";
	
	public void sync(Context context) {
		try {
			String dbPath = context.getFilesDir().getParentFile().getPath() + dbName;
			System.out.println("DBPATH: " + dbPath);
			InetAddress serverAddr = InetAddress.getByName(serverIp);
			System.out.println("Trying to create socket for host address " + serverIp + " on port " + port);
			Socket socket = new Socket(serverAddr, port);
			System.out.println("Success!");
			int filesize = 6022386;
			int bytesRead;
			int current = 0;
			byte[] mybytearray = new byte[filesize];

			InputStream is = socket.getInputStream();

			BufferedReader ir = new BufferedReader(new InputStreamReader(is));
			FileOutputStream fos = new FileOutputStream(dbPath);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;

			do {
				bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
				if(bytesRead >= 0) {
					current += bytesRead;
				}
			} while (bytesRead > -1);

			bos.write(mybytearray, 0, current);
			bos.flush();
			bos.close();
			ir.close();

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
