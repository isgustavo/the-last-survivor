package br.com.thelastsurvivor.engine.multiplayergame;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothSocket;

public class MultiplayerCommunication implements Runnable{

	
	List<BluetoothSocket> clients;
	
	public MultiplayerCommunication(){
		this.clients = new ArrayList<BluetoothSocket>();
	}
	
	public void start(final BluetoothSocket client){
		this.clients.add(client);
		
		this.run();
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
