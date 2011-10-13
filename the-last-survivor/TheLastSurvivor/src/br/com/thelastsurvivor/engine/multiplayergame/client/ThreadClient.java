package br.com.thelastsurvivor.engine.multiplayergame.client;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;

public class ThreadClient extends Thread {
    
	private MultiGameActivity activity;
	private BluetoothAdapter adapter;
	
	BluetoothDevice device;
    BluetoothSocket socket;

    public void run() {	
    
  	   for (UUID uuid : MultiGameActivity._UUID) {
  		   
  		   try {
  			   socket = device.createRfcommSocketToServiceRecord(uuid);
  		   
  			   //Log.d("client", ".for");
  		   
  			   if(socket != null){
  				   //Log.d("client", ".if");
  				   socket.connect();
	    	       activity.setSocket(socket);
	    	       break;
  			   }
  		   
  		   } catch (IOException e) {
		            e.printStackTrace();
		           /* handler.obtainMessage(MSG_DISCONNECTED, 
		                  e.getMessage()+"[2]").sendToTarget();*/
		   }
  	   	}
    }

    public void init(BluetoothDevice device, MultiGameActivity activity){
    	this.activity = activity;
    	this.device = device;
        
    	start();
     }

    public void stopClient(){
    	try {
    		socket.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
     }
}