package demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Serverdemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Serverdemo();
	}
	ServerSocket ss=null;
	Socket socket=null;
	Serverdemo() throws UnknownHostException, IOException{
		ss= new ServerSocket(1999, 100, InetAddress.getByName("localhost"));
		System.out.println("Server start at socket: "+ ss);
		while(true) {
			System.out.println("Waiting for Connection...");
			socket= ss.accept();
			Runnable run= () -> {
				request(socket);
			};
			new Thread(run).start();
		}
	}
	static void request(Socket socket){

		try{
			BufferedReader skread= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter skwrite= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader consoleread= new BufferedReader(new InputStreamReader(System.in));
			String mssg=null;
			while((mssg=skread.readLine())!=null) {
				System.out.println("Client: "+ mssg);
				String  word=consoleread.readLine();
				skwrite.write(word);
				skwrite.write("\n");
				skwrite.flush();
			}
		}catch(IOException io) {
			io.getMessage();
		}finally {
			try {
				socket.close();
			}catch(IOException io){ io.getMessage();}
		}
		
	}
}
