package br.com.thelastsurvivor.engine.multiplayergame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothSocket;

public class MultiplayerCommunication implements Runnable{

	private DataInputStream is;
	private DataOutputStream os;
	
	String name;
	List<BluetoothSocket> clients;
	BluetoothSocket client;
	
	public MultiplayerCommunication(){
		this.clients = new ArrayList<BluetoothSocket>();
	}
	
	public void startCommunication(BluetoothSocket client){
		this.clients.add(client);
		
		this.run();
	}
	
	
	@Override
	public void run() {
		name = client.getRemoteDevice().getName();
		         try {
					is = new DataInputStream(client.getInputStream());
					os = new DataOutputStream(client.getOutputStream());
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

}
