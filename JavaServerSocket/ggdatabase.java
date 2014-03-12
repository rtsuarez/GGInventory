import java.io.*;
import java.net.*;

public class ggdatabase {
	public final static String dbPath = "C:\\HDD\\SQLite\\gginventory";
	public static int portNum = 2045;
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
			
			if(debug) System.out.println("Accepted connection: " + sock);
			BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            if(debug) System.out.println("Accepted connection: " + sock);

            //String serverResponse = input.readLine();
            if(debug) System.out.println("Accepted connection: " + sock);

            File db = new File(dbPath);
			byte[] mybytearray = new byte[(int)db.length()];
			FileInputStream fileIn = new FileInputStream(db);
			BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
			bufferedIn.read(mybytearray, 0, mybytearray.length);
			OutputStream output = sock.getOutputStream();
			
			if(debug) System.out.println("Sending...");
			output.write(mybytearray, 0, mybytearray.length);
			output.flush();
			bufferedIn.close();
			sock.close();
			server.close();
			
			if(debug) System.out.println("Done");
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		finally {
			
		}
	}
}
