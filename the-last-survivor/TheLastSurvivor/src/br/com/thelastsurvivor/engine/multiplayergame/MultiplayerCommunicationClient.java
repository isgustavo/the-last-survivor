package br.com.thelastsurvivor.engine.multiplayergame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.bluetooth.BluetoothSocket;

public class MultiplayerCommunicationClient implements Runnable{

	private DataInputStream is;
	private DataOutputStream os;
	
	String name;
	BluetoothSocket server;
	//BluetoothSocket client;
	
	public MultiplayerCommunicationClient(){
		//this.clients = new ArrayList<BluetoothSocket>();
	}
	
	public void startCommunication(BluetoothSocket server){
		this.server = server;
		
		this.run();
	}
	
	@Override
	public void run() {
		
			try {
				is = new DataInputStream(server.getInputStream());
				os = new DataOutputStream(server.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		         
		        
	        
	}
	
	 public void stopCommunication(){
		        try {
		          is.close();
		       } catch (IOException e) {
		          e.printStackTrace();
		       }
		       try {
		         os.close();
		      } catch (IOException e) {
		       e.printStackTrace();
		       }
	    }

	public BluetoothSocket getServer() {
		return server;
	}

	public void setServer(BluetoothSocket server) {
		this.server = server;
	}

	 
	 
}
