package br.com.thelastsurvivor.engine.multiplayergame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;

public class MultiplayerCommunication extends Thread{

	private MultiGameActivity activity;
	
	//private DataInputStream is;
	private ObjectInputStream obis;
	//private DataOutputStream os;
	private ObjectOutputStream obos;
	
	private BluetoothSocket socket;
	
	@Override
	public void run() {
		
			try {
				//Log.d("RUN", "RUN");
				//is = new DataInputStream(socket.getInputStream());
				obis = new ObjectInputStream(socket.getInputStream());
				obos = new ObjectOutputStream(socket.getOutputStream());
				//os = new DataOutputStream(socket.getOutputStream());
				
				//Log.d("RUN", "RUN");
				
				//Log.d("LOG............................", "RUN"+socket.getRemoteDevice().getName());
				
				while (true) {
					//Log.d("LOG", "WHILE");
		            //String string = is.readUTF();
		            
		          /* Log.d("LOG", "UFT");
		            if(string.equalsIgnoreCase("1")){
		            	Log.d("LOG", "IF");
		            	activity.getEngine().s// aqui utiliza a autlaização direta na lista de coisas desenlhavei 
		            }*/
		            
		            
		            try {
						if(obis.readObject() instanceof Spacecraft){
							 Spacecraft spacecraft = (Spacecraft) obis.readObject(); 
							// activity.startGameClient(spacecraft);
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		           
		        }
			} catch (IOException e) {
				Log.d("LOG", "EXEC");
				e.printStackTrace();
			}
		 
	}
	
	
	public void startCommunication(final BluetoothSocket socket, MultiGameActivity activity){
		//Log.d("startCommunication", "startCommunication");
		this.activity = activity;
		this.socket = socket;
		//Log.d("start", "start");
		start();
	}
	
	
	public void stopCommunication(){
		
		//Log.d("LOG", "STOP");
		try {
	       //is.close();
	       this.obis.close();
	    } catch (IOException e) {
	       e.printStackTrace();
	    }
	    try {
	      //os.close();
	    	this.obos.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}


/*	public DataInputStream getIs() {
		return is;
	}


	public void setIs(DataInputStream is) {
		this.is = is;
	}


	public DataOutputStream getOs() {
		return os;
	}


	public void setOs(DataOutputStream os) {
		this.os = os;
	}
*/

	public ObjectInputStream getObis() {
		return obis;
	}


	public void setObis(ObjectInputStream obis) {
		this.obis = obis;
	}


	public ObjectOutputStream getObos() {
		return obos;
	}


	public void setObos(ObjectOutputStream obos) {
		this.obos = obos;
	}
	
	
	
	
}
