package br.com.thelastsurvivor.engine.multiplayergame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class MultiplayerServer extends Thread {

	private BluetoothAdapter adaptader;
	
	private BluetoothServerSocket serverSocket;
	private List<MultiplayerCommunication> clientsConnected;
	
	private List<BluetoothSocket> clientsSocket;
	

	public MultiplayerServer(BluetoothAdapter adaptader){
		this.adaptader = adaptader;
		
		this.clientsSocket = new ArrayList<BluetoothSocket>();
	}
	

	@Override
	public void run() {
		
		try{
			//serverSocket = adaptader.listenUsingRfcommWithServiceRecord(MultiGameActivity.SERVICE, MultiGameActivity._UUID);
			
			while(true){
				BluetoothSocket clientSocket = serverSocket.accept();
	           
				this.clientsSocket.add(clientSocket);
			}
		}catch(IOException e){
			
		}
	}
	
	public void startServer(){
		this.clientsSocket = new ArrayList<BluetoothSocket>();
		this.clientsConnected = new ArrayList<MultiplayerCommunication>();
		
		start();
	}
	
	public void stopServer(){
		try {
			serverSocket.close();
			
			for (MultiplayerCommunication client : this.clientsConnected) {
				client.stopCommunication();
			}
			
			this.clientsConnected.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	
	
	public boolean startCommunication(MultiplayerCommunicationServer communication){
		
		if(clientsSocket.size() == 0){
			return false;
		}else{
			communication.setClients(clientsSocket);
			Thread communicationThreat = new Thread(communication);
			communicationThreat.start();
			return true;
		}
		
	}
	
	public List<BluetoothSocket> getClientsSocket() {
		return clientsSocket;
	}

	public BluetoothServerSocket getServerSocket() {
		return serverSocket;
	}


	public List<MultiplayerCommunication> getClientsConnected() {
		return clientsConnected;
	}
	
	

	
}
