package br.com.thelastsurvivor.activity.game.multiplayermode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.multiplayergame.MultiPlayerMode;
import br.com.thelastsurvivor.engine.multiplayergame.MultiplayerClient;
import br.com.thelastsurvivor.engine.multiplayergame.MultiplayerCommunication;
import br.com.thelastsurvivor.engine.multiplayergame.MultiplayerServer;

public class MultiGameActivity extends Activity  implements DialogInterface.OnCancelListener{

	public static final String SERVICE = "LASTSURVIVOR";
	public static final UUID _UUID =  UUID.fromString("db12d1e9-caba-84ef-398b-18211984abcd");
	
	private static final int BT_TIME = 30;
	private static final int BT_ACTIVATE = 0;
	private static final int BT_VISIBLE = 1;
	
	private MultiplayerCommunication communication;
	private MultiplayerServer server;	
	private List<MultiplayerClient> clients;
	    
    private BluetoothAdapter adaptader;
    private List<BluetoothDevice> remoteDevices; 
    private BluetoothEvent event;
    
    ProgressDialog waitDialog;
	TelaHandler telaHandler = new TelaHandler();
    
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	   setContentView(R.layout.multigame_mode_view);
	   
	   this.adaptader = BluetoothAdapter.getDefaultAdapter();
	   this.remoteDevices = new ArrayList<BluetoothDevice>();
	   this.event = new BluetoothEvent();
		
	   this.clients = new ArrayList<MultiplayerClient>();
		
	   if(this.adaptader == null){

		   Toast.makeText(this,R.string.not_bluetooth, Toast.LENGTH_LONG).show();
		   finish();
	   }else if(!this.adaptader.isEnabled()){
	    	   
		   Intent enableBtIntent =  new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		   startActivityForResult(enableBtIntent, BT_ACTIVATE);
	   }
		
	   IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	   IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

	   registerReceiver(event, filter1);
	   registerReceiver(event, filter2);

       final Button buttonServer = (Button) findViewById(R.id.server_image);
       buttonServer.setOnClickListener(buttonListenerServer);
      
       final Button buttonClient = (Button) findViewById(R.id.client_image);
       buttonClient.setOnClickListener(buttonListenerClient);

	}
		
	private OnClickListener buttonListenerServer = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        	
        	discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, BT_TIME);
        	
        	startActivityForResult(discoverableIntent, BT_VISIBLE);
     
        }  
	};
	
	private OnClickListener buttonListenerClient = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	remoteDevices.clear();
        	adaptader.startDiscovery();
        	showProgress(0);     
        	      
     
        }  
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);

	     if (requestCode == BT_ACTIVATE) {
	    	 if (RESULT_OK != resultCode) {
	    		
	    		 Toast.makeText(this, R.string.bluetooth_confirm,Toast.LENGTH_SHORT).show();
	    		 finish();
	    	 }
	     }else if(requestCode == BT_VISIBLE){
	    	 if (resultCode != BT_TIME) {
	    		 
	    		 Toast.makeText(this, R.string.server_confirm,Toast.LENGTH_SHORT).show();
	    	 }else{
	    		 startServer();
	    	 }
	     } 
	 }
	
	public void startServer(){
		showProgress(BT_TIME);
		this.stop();

		this.server = new MultiplayerServer(this.adaptader,this);
		Thread threadserver = new Thread(server);
		threadserver.start();
	}
	
	private void connectClients(){
		 waitDialog.dismiss();
		 
		 this.stop();
		 
		 for (BluetoothDevice client : this.remoteDevices) {
			this.clients.add(new MultiplayerClient(client,this));
		 }
		 
		 for (MultiplayerClient client : this.clients) {
			Thread clientThread = new Thread(client);
			clientThread.start();
		}
	}
	
	public void addSocket(final BluetoothSocket client){
		
		
		this.communication = new MultiplayerCommunication();
		this.communication.startCommunication(client);
	}

	private void stop(){
	     
		if (this.communication != null){
			this.communication.stopCommunication();
			this.communication = null;
		 }
     
	     if (this.server != null){
	    	 this.server.stopServer();
	    	 this.server = null;
	     }
		 
	     if (clients != null){
	    	 for (MultiplayerClient client : this.clients) {
	    		 client.stopClient();
			}
	    	this.clients = new ArrayList<MultiplayerClient>();
	    }	
	}
	
	@Override
	public void onCancel(DialogInterface arg0) {
		 this.adaptader.cancelDiscovery();
		 this.stop();
		
	}
	
	@Override
	protected void onDestroy() {
	     super.onDestroy();
	     unregisterReceiver(event);

	     stop();
	}
	
	private class BluetoothEvent extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
				
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				remoteDevices.add(device);
	       }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
	    	   	connectClients();
	       }
	    }
		
	}
	
	public void showProgress(long tempo){
			
		waitDialog = ProgressDialog.show(this, "Aguarde", "", true, true, this);
	    waitDialog.show();
	    if (tempo > 0){
	       telaHandler.postDelayed(new Runnable() {
	       public void run() {
	           if (communication == null)
	             waitDialog.cancel();
	         }
	       }, tempo * 1000);
	     }
	 }
	
	

		
	
	
	
	//MultiplayerCommunication communication;
	
	
	
	

	
	private class TelaHandler extends Handler {

		     public void handleMessage(Message msg) {
		      super.handleMessage(msg);

		     
		     }		   }

}
