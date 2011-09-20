package br.com.thelastsurvivor.engine.multiplayergame;

import java.io.IOException;

import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class MultiplayerServer extends MultiPlayerMode implements Runnable{

	private BluetoothAdapter adaptader;
	private BluetoothServerSocket serverSocket;
	private BluetoothSocket clientSocket;
	

	public MultiplayerServer(BluetoothAdapter adaptader){
		this.adaptader = adaptader;
	}
	

	@Override
	public void run() {
		
		try{
			serverSocket = adaptader.listenUsingRfcommWithServiceRecord(SERVICE, MEU_UUID);
			clientSocket = serverSocket.accept();
			
			addSocket(clientSocket);
			
		}catch(IOException e){
			
		}
	}
	
	private void addSocket(final BluetoothSocket client){
		
		
		this.communication = new MultiplayerCommunication();
		this.communication.start(client);
	}

}
