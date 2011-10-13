package br.com.thelastsurvivor.engine.multiplayergame.server;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;

public class ThreadServer extends Thread {
    
	private MultiGameActivity activity;
	private BluetoothAdapter adapter;
	
	private BluetoothServerSocket serverSocket;
    private BluetoothSocket clientSocket;
              
    public void run() {
    	try {
         
			 for (UUID uuid : MultiGameActivity._UUID) {
				 
				 serverSocket = adapter.listenUsingRfcommWithServiceRecord(MultiGameActivity.SERVICE, uuid);
	
	  		   	 clientSocket = serverSocket.accept();
	  		   	 this.activity.setSocketServer(clientSocket);
			 
			 }
       
       } catch (IOException e) {
      	 /*handler.obtainMessage(MSG_DISCONNECTED, 
                e.getMessage()+"[1]").sendToTarget();
         e.printStackTrace();*/
       }
   }
              
   public void init(BluetoothAdapter adapter, MultiGameActivity activity){
	   this.adapter = adapter;
	   this.activity = activity;
	   
	   start();
   }
              
   public void stopServer(){
	   try {
		   serverSocket.close();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }
}