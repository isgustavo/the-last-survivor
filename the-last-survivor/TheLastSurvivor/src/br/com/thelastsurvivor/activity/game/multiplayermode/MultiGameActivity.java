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

public class MultiGameActivity extends Activity  implements View.OnClickListener, DialogInterface.OnClickListener, DialogInterface.OnCancelListener{

	public static final String SERVICE = "LASTSURVIVOR";
	public static final UUID _UUID =  UUID.fromString("db12d1e9-caba-84ef-398b-18211984abcd");
	
	private static final int BT_TEMPO_DESCOBERTA = 30;
	private static final int BT_ACTIVATE = 0;
	private static final int BT_VISIBLE = 1;
	
	private MultiplayerCommunication communication;
	private MultiplayerServer server;	
	private MultiplayerClient client;
	    
    private BluetoothAdapter adaptader;
    private List<BluetoothDevice> remoteDevices; 
    private BluetoothEvent event;
    
    ProgressDialog waitDialog;
	TelaHandler telaHandler = new TelaHandler();
    
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.multigame_mode_view);
       init();

	}
	
	
	public void init(){
		
		this.adaptader = BluetoothAdapter.getDefaultAdapter();
		
		this.remoteDevices = new ArrayList<BluetoothDevice>();
		this.event = new BluetoothEvent();
		
		
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
        	
        	discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, BT_TEMPO_DESCOBERTA);
        	
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
		    	 //verifica se bluetooth foi ativado
		    	 if (RESULT_OK != resultCode) {
		    		 Toast.makeText(this, R.string.bluetooth_confirm,Toast.LENGTH_SHORT).show();
		    		 finish();
		    	 }
		     }
		     
		     if(requestCode == BT_VISIBLE){
		    	 if (resultCode != BT_TEMPO_DESCOBERTA) {
		    		 Toast.makeText(this, R.string.server_confirm,Toast.LENGTH_SHORT).show();
		    		 
		    	 }else{
		    		 //Toast.makeText(this, "servidor",Toast.LENGTH_SHORT).show();
		    		 startServer();
		    	 }
		     }
		     
		     
		 }
	
	
	
	//MultiplayerCommunication communication;
	
	public void showProgress(long tempo){
		
		waitDialog = ProgressDialog.show(
	           this, "Aguarde", "", true, true, this);
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
	
	public void startServer(){
		showProgress(BT_TEMPO_DESCOBERTA);
		paraTudo();

		this.server = new MultiplayerServer(this.adaptader,this);
		Thread threadserver = new Thread(server);
		threadserver.start();
	}

	
	private class TelaHandler extends Handler {

		     public void handleMessage(Message msg) {
		      super.handleMessage(msg);

		     
		     }		   }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	private void paraTudo(){
	     if (communication != null){
	    	 communication.stopCommunication();
	    	 communication = null;
		     }
		     if (server != null){
		    	 server.stopServer();
		    	 server = null;
		     }
		  //  if (threadCliente != null){
		  //     threadCliente.parar();
		  //     threadCliente = null;
	    //}	
	}
	
	public void addSocket(final BluetoothSocket client){
		
		
		this.communication = new MultiplayerCommunication();
		this.communication.startCommunication(client);
	}

	@Override
	public void onCancel(DialogInterface arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private class BluetoothEvent extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
				
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				remoteDevices.add(device);
	       }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
	            exibirDispositivosEncontrados();
	       }
	    }
		
	}
	
	private void exibirDispositivosEncontrados() {
		     

		for (BluetoothDevice remote : this.remoteDevices) {
			startClient(remote);
		}
	}
	
	public void startClient(BluetoothDevice remote){
		
		MultiplayerClient client = new MultiplayerClient(remote,this);
		
		Thread threadClient = new Thread(client);
		threadClient.start();
		
	}
	
	 	
	
}
