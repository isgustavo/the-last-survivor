package br.com.thelastsurvivor.engine.multiplayergame;

import java.io.IOException;

import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class MultiplayerClient implements Runnable{

	private MultiGameActivity activity;
	BluetoothDevice device;
	BluetoothSocket serverSocket;
	
	public MultiplayerClient(BluetoothDevice device, MultiGameActivity activity) {
		this.device = device;
		this.activity = activity;
	}
	
	
	@Override
	public void run() {
		try {
			serverSocket = device.createRfcommSocketToServiceRecord(MultiGameActivity._UUID);
			serverSocket.connect();
			activity.addSocket(serverSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void stopClient(){
		 try {
			 serverSocket.close();
			       } catch (Exception e) {
			         e.printStackTrace();
		       }
	}
	
	
	
}
