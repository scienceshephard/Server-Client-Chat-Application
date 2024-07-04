package demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Clientdemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Clientdemo();
	}
	BufferedWriter skwrite= null;
	BufferedReader skread, consolereader= null;
	Socket socket= null;
	
	Clientdemo(){
		try {
			socket= new Socket("localhost", 1999);
			skwrite= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			skread= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			consolereader= new BufferedReader(new InputStreamReader(System.in));
			String mssg= null;
			String word="Type: (EXIT) to quit";
			System.out.println(word);
			while((mssg=consolereader.readLine())!=null) {
				if(mssg.equalsIgnoreCase("exit")) {
					break;
				}
				skwrite.write(mssg);
				skwrite.write("\n");
				skwrite.flush();
				String sev= skread.readLine();
				System.out.println("Server: "+sev);
				System.out.println();
				System.out.println(word);	
			}
		}catch(IOException io) {
			io.getMessage();
		}finally {
			if(socket!=null) {
				try{
					socket.close();
				}catch(IOException io) {
					io.getMessage();
				}
			}
		}
	}
}
