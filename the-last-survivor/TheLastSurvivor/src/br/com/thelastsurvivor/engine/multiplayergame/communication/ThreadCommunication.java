package br.com.thelastsurvivor.engine.multiplayergame.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;

public class ThreadCommunication extends Thread {
	
	private String pointName;
    
	private BluetoothSocket socket;
    private MultiGameActivity activity;
    
    public DataInputStream is;
    public DataOutputStream os;
	   
    
    public void run() {
    	try {
          
    	  pointName = socket.getRemoteDevice().getName();
          is = new DataInputStream(socket.getInputStream());
          os  = new DataOutputStream(socket.getOutputStream());
          
          String string;
	          
	          while (true) {
	             string = is.readUTF();
	           
	             
	            String[] values = string.split("/");
	             
	            
	            if(values[0].equals("serverToClientEndGame")){
	            	
	            	if(activity.getEngineGameClient() != null){
	            		//Log.d("IF","IF");
	            		activity.endGame(values);
	            	}
	            	
	            }
	                                 
	            if(values[0].equals("serverToClientDead")){
	            	//Log.d("RUN","RUN");
	            	if(activity.getEngineGameClient() != null){
	            		//Log.d("IF","IF");
	            		Log.d(activity.getEngineGameClient().getSpacecraft().getName(), values[1]);
	            		
	            		if(activity.getEngineGameClient().getSpacecraft().getName()
	            				.equalsIgnoreCase(values[1])){
	            			
	            			activity.getEngineGameClient().getSpacecraft().setIsDead(true);
	            		}
	            	}
	            }
	            
	             
	            //client send spacecraft to server  
	            if(values[0].equals("clientToServerSpacecraft")){
	            	
	            	if(activity.getEngineGame() != null){
	            		activity.getEngineGame().setSpacecraftClientToUpdate(values);
	            	}
	            	
	            }
	            
	            
	            //
	            if(values[0].equals("serverToClientDrawGameClient")){
	            	
	            	if(activity.getEngineGameClient() != null){
	            		activity.getEngineGameClient().drawGame(values);
	            	}
	            	 
	            }
	            
	           
	            if(values[0].equals("featureClient")){
	            	
	            	activity.addNameAndColor(values);
	            	
	            }
	            
	            if(values[0].equals("startGameClient")){
	            	
	            	activity.gameSystemPrepares(values);
	            }
	            
	            if(values[0].equals("featuresGame")){
	            	
	            	activity.featuresClient();
	            }
	             
	          }
	          
	          
	         } catch (IOException e) {
	            e.printStackTrace();
	            /*handler.obtainMessage(MSG_DISCONNECTED, 
	                  e.getMessage()+"[3]").sendToTarget();*/
	        }/* catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
	        }
    
    public void init(BluetoothSocket socket, MultiGameActivity activity){
        this.socket = socket;
        this.activity = activity;
        
        start();
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

	public String getPointName() {
		return pointName;
	}
    
}                      