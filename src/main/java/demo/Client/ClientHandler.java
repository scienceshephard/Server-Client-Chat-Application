package demo.Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    private ArrayList<ClientHandler> clientHandlers= new ArrayList<>();
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String UserName;
    private String msg;
    private Socket socket;
    Scanner scn= new Scanner(System.in);

    public ClientHandler( Socket socket){
        this.socket= socket;
    }

    @Override
    public void run() {

        displayMessages(UserName+":cllc "+ msg);
    }

    private void displayMessages(String msg) {

        String consoleReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while((consoleReader=bufferedReader.readLine()) != null) {
                System.out.println(UserName+":Server" + consoleReader);
                msg= scn.nextLine();
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeConnection();
        }
    }
    public void closeConnection(){
        if( socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
