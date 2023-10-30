/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.serverchess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cuong
 */


public class ServerChess {
    Socket soc;
    List<ChessProcessing> clients = new ArrayList<ChessProcessing>();
    List<BoardModel> listBoardModel = new ArrayList<BoardModel>();
    //

    public static void main(String[] args) {
        //open server
        try {
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Server is running...");
//            Thread thread = new Thread(() -> {
//                try {
//                    ServerSocket server1 = new ServerSocket(4567);
//                    System.out.println("Server is running 4567...");
//                    ServerChess serverChess = new ServerChess(server1);
//                }catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            });
//            thread.start();
            ServerChess serverChess = new ServerChess(server);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally{
            System.out.println("Server terminated!");
        }
        
    }
    
    public ServerChess(ServerSocket server){
        //accept client
        //start that client thread
        while(true){
            try{
                soc = server.accept();
                ChessProcessing client = new ChessProcessing(this, soc);
                clients.add(client);
                client.start();
            }catch(Exception ex){
//                System.out.println(ex.getMessage());
            }
//            break;
        }

    }
    
}

//each client communicate with the server through a thread
//we are in server side of the thread (a thread has two sides)
class ChessProcessing extends Thread{
    //always listen to client side
    Socket soc;
    ServerChess server;
    public ChessProcessing(ServerChess server, Socket soc){
        this.soc = soc;
        this.server = server;
    }
    
    public void run(){
        try{
            //ToDo: if type of mess 'create' -> send existed Board list
            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
//            dos.writeUTF("Chao client!");
            for(int i = 0; i < server.listBoardModel.size(); i++){
                dos.writeUTF(server.listBoardModel.get(i).getHostIP());
                dos.writeUTF(server.listBoardModel.get(i).getHostPlayer());
            }
            
            //ToDo: esle if type of mess 'request' -> receive ip(or id) then send 'request' to the host ip
            
            //ToDo: esle if type of mess 'view' just connect to host ip and view
            
            //always listen to that client side
            //ToDo: receive a board then verify move
            //if valid move then send to propreate clients (list clients)
            while(true){
                //receive a board
                BoardModel board =  receiveBoardData();
                
                //then send that board to all clients
                sendBoardData(board);
            }
        }catch(Exception ex){
//            System.out.println(ex.getMessage());    //null error
        }
    }
    
    public BoardModel receiveBoardData(){
        BoardModel board = new BoardModel();
        try{
            DataInputStream dis = new DataInputStream(soc.getInputStream());
            board.setHostIP(dis.readUTF());
            board.setHostPlayer(dis.readUTF());
            System.out.println("client say: " + board.getHostIP() + " " + board.getHostPlayer());
            server.listBoardModel.add(board);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return board;       
    }
    
    public void sendBoardData(BoardModel board){
        try{
            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
            sendAMessToAll(board.getHostIP());
            sendAMessToAll(board.getHostPlayer());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    void sendAMessToAll(String mess) {
        for(ChessProcessing client:server.clients) {
            try {
		DataOutputStream dosc = new DataOutputStream(client.soc.getOutputStream());
		dosc.writeUTF(mess);
            }catch(Exception e) {
		System.out.println(e.getMessage());
            }
	}
    }
}