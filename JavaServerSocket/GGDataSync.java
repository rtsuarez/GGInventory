import java.net.*;
import java.io.*;


public class GGDataSync {
	public final static String dbPath = "C:\\HDD\\SQLite\\gginventory1";
	public static int portNum = 2046;
	public static boolean debug = true;
	
	public static void main(String[] args) {
		if(args.length > 0) {
			portNum = Integer.parseInt(args[0]);
		}
		
		try {
			ServerSocket server = new ServerSocket(portNum);
			InetAddress addr = InetAddress.getLocalHost();
			
			if(debug) System.out.println("Waiting on port " + portNum + " from host " + addr.getHostAddress());
			Socket sock = server.accept();
			InputStream is = sock.getInputStream();
            if(debug) System.out.println("Accepted connection: " + sock);

            File db = new File(dbPath);
            
             
            int filesize = 6022386;
			int bytesRead;
			int current = 0;
			byte[] mybytearray = new byte[filesize];
            
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
			
			sock.close();
			
			if(debug) System.out.println("Done");
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		finally {
			
		}
	}
}
