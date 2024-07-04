package demo.Server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    Socket socket;
    private String msg;

    Server(ServerSocket serverSocket){
        this.serverSocket= serverSocket;
    }

    private void BroadcastMsg(){
        try{
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            msg=bufferedReader.readLine();
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(bufferedWriter, bufferedReader,  serverSocket);
        }
    }

    public void startServer() {
        while (!serverSocket.isClosed()){
            try {
                socket= serverSocket.accept();
                System.out.println("A new device has connected");
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BroadcastMsg();
                    }
                });
                thread.start();
            } catch (IOException e) {
                closeEverything(bufferedWriter, bufferedReader, serverSocket);
            }
        }
    }

    private void closeEverything(BufferedWriter bufferedWriter, BufferedReader bufferedReader, ServerSocket serverSocket) {
        try{
            if (bufferedReader==null) bufferedReader.close();
            if (bufferedWriter == null) bufferedWriter.close();
            if (serverSocket == null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket= new ServerSocket(999);
        Server server=new Server(serverSocket);
        server.startServer();
    }
}
