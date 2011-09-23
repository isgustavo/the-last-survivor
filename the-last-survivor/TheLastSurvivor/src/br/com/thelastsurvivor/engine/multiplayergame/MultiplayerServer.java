package br.com.thelastsurvivor.engine.multiplayergame;

import java.io.IOException;

import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class MultiplayerServer implements Runnable{

	private MultiGameActivity activity;
	private BluetoothAdapter adaptader;
	private BluetoothServerSocket serverSocket;
	private BluetoothSocket clientSocket;
	

	public MultiplayerServer(BluetoothAdapter adaptader, MultiGameActivity activity){
		this.adaptader = adaptader;
		this.activity = activity;
	}
	

	@Override
	public void run() {
		
		try{
			serverSocket = adaptader.listenUsingRfcommWithServiceRecord(MultiGameActivity.SERVICE, MultiGameActivity. _UUID);
			
			clientSocket = serverSocket.accept();
			activity.addSocket(clientSocket);
			
		}catch(IOException e){
			
		}
	}
	
	

	public void stopServer(){
		      try {
		         serverSocket.close();
		       } catch (IOException e) {
		         e.printStackTrace();
		       }
	    }
}
