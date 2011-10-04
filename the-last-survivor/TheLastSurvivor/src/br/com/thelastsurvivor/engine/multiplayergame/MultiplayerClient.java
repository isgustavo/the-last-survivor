package br.com.thelastsurvivor.engine.multiplayergame;

import java.io.IOException;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;

public class MultiplayerClient extends Thread{

	private MultiGameActivity activity;
	BluetoothDevice device;
	
	MultiplayerCommunication serverConnected;
	BluetoothSocket serverSocket;
	
	
	
	@Override
	public void run() {
		try {
			serverSocket = device.createRfcommSocketToServiceRecord(MultiGameActivity._UUID);
			serverSocket.connect();
			
			
			//activity.setSocketServer(serverSocket);
		} catch (IOException e) {
			
		}
		
		
	}
	
	public void startClient(BluetoothDevice device, MultiGameActivity activity){
		this.device = device;
		this.activity = activity;
		this.serverConnected = new MultiplayerCommunication();
		
		start();
	}

	public void stopClient(){
		try {
			 serverSocket.close();
			 
			 serverConnected = null;
			 
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}

	public MultiplayerCommunication getServerConnected() {
		return serverConnected;
	}
	
	
	
}
