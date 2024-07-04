package demo.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    //I  can't kill myself Future me Build this Abeg 🙄
    private Socket socket;
    private String msg;
    private BufferedReader bufferedReader;
    private BufferedReader socketReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    Client(String userName, Socket socket){
        this.socket = socket;
        this.userName= userName;
    }

    public void sendMsg() {
        try{
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String consoleReader;
            while ((consoleReader= bufferedReader.readLine()) !=null){
                bufferedWriter.write(userName+": "+consoleReader);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                //Displays message from server
                msg=socketReader.readLine();
                System.out.println(msg);
            }
        } catch (IOException e) {
            closeEverything(socket, socketReader, bufferedReader, bufferedReader);
        }


    }

    private void closeEverything(Socket socket, BufferedReader socketReader, BufferedReader bufferedReader, BufferedReader bufferedReader1) {
        try{
            if (socket !=null) socket.close();
            if (bufferedReader !=null) bufferedReader.close();
            if(bufferedWriter !=null) bufferedWriter.close();
            if (socketReader !=null) socketReader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scn= new Scanner(System.in);
        System.out.println("Enter your name: ");
        String userName= scn.nextLine();
        Socket socket = new Socket("localhost",999);
        Client client = new Client( userName,socket);
        client.sendMsg();
    }
}
