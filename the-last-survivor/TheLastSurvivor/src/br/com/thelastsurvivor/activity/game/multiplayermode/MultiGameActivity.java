package br.com.thelastsurvivor.activity.game.multiplayermode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayergame.client.EngineGameClient;
import br.com.thelastsurvivor.engine.multiplayergame.client.ThreadClient;
import br.com.thelastsurvivor.engine.multiplayergame.communication.ThreadCommunication;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.engine.multiplayergame.server.EngineGameServer;
import br.com.thelastsurvivor.engine.multiplayergame.server.ThreadServer;
import br.com.thelastsurvivor.engine.util.IClient;
import br.com.thelastsurvivor.engine.util.IServer;
import br.com.thelastsurvivor.engine.view.EngineMultiGameView;
import br.com.thelastsurvivor.util.MyAudioPlayer;

public class MultiGameActivity extends Activity implements SensorEventListener, 
		OnGestureListener, View.OnClickListener, DialogInterface.OnClickListener, 
		DialogInterface.OnCancelListener{

	public static final String SERVICE = "LASTSURVIVOR";
	public static List<UUID> _UUID;
	
	private static final int BT_TIME = 30;
	private static final int BT_START = 0;
	private static final int BT_VISIBLE = 1;
	
	private static final int MSG_DISCONNECTED = 2;
	
	private static final String RED = "1";
	private static final String BLUE = "2";
	private static final String YELLOW = "3";
	private static final String GREEN = "4";
	
	private Context context;
	private SensorManager manager;
	private Sensor accelerometer;
	private GestureDetector gestureScanner;
	private Vibrator vibrator;
	private MyAudioPlayer audioPlayer;
	private Display display;
	private WakeLock wakeLock;
	
	public MultiGameActivity activity;

	private String namePlayer;

	private ThreadServer threadServer;
	private EngineMultiGameView viewServer;
	private IServer engineGame;
	
	private ThreadClient threadClient;
	private br.com.thelastsurvivor.engine.multiplayergame.client.EngineMultiGameView viewClient;
	private IClient engineGameClient;  
	
	private ThreadCommunication threadCommunication;
	private List<ThreadCommunication> threadsCommunication;
	
	List<String> nameAndColor = new ArrayList<String>();
		
	private BluetoothAdapter adapter;
	private List<BluetoothDevice> devicesRemote;
	private EventBluetoothReceiver receiver;	
	  
	private Dialog dialog;
	
	private ArrayAdapter<String> historico;
	private ScreenHandler handler;
	private ProgressDialog waitDialog;
	   
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = this.getApplicationContext();
		
		//showWaitVerificationBluetooth();
		
		Bundle s = this.getIntent().getExtras().getBundle("playerBundle");
		this.namePlayer = s.getString("id_player");
     
    	setContentView(R.layout.multigame_mode_view);

    	this.handler = new ScreenHandler();
    
    	this.activity = MultiGameActivity.this;

    	this.receiver = new EventBluetoothReceiver();
    	
    	IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    	IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    	
    	registerReceiver(this.receiver , filter1);
        registerReceiver(this.receiver , filter2);
        
        this.devicesRemote = new ArrayList<BluetoothDevice>();
        
        this.adapter = BluetoothAdapter.getDefaultAdapter();

        if (this.adapter != null) {
        	if (!adapter.isEnabled()) {
        		
        		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        		startActivityForResult(enableBtIntent, BT_START);
        		
        		
        	}
        } else {
        	Toast.makeText(this, this.context.getText(R.string.not_bluetooth), 
        			Toast.LENGTH_LONG).show();
        	finish();
        }

        this.manager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		
 	   	this.accelerometer = this.manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
 	   	this.manager.registerListener (this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
 	      
 	   	this.gestureScanner = new GestureDetector(this);
 	    	    
 	   	final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        
 	   	this.wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "");
        this.wakeLock.acquire();      
        
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        this.display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        _UUID = new ArrayList<UUID>();
        _UUID.add(UUID.fromString("db12d1e9-caba-84ef-398b-18211984abcd"));
        _UUID.add(UUID.fromString("db12d1e9-caba-84ef-398b-18221984abcd"));
        _UUID.add(UUID.fromString("db12d1e9-caba-84ef-398b-18231984abcd"));
        _UUID.add(UUID.fromString("db12d1e9-caba-84ef-398b-18241984abcd"));
 	
        this.threadsCommunication = new ArrayList<ThreadCommunication>();

        final Button buttonServer = (Button) findViewById(R.id.server_image);
        buttonServer.setOnClickListener(buttonListenerServer);
      
        final Button buttonClient = (Button) findViewById(R.id.client_image);
        buttonClient.setOnClickListener(buttonListenerClient);
	}
	
	/*public void showWaitVerificationBluetooth(){
		dialog = new Dialog(this, R.style.PauseGameDialogTheme);
		dialog.setContentView(R.layout.wait_game_view);
		   
		dialog.show();
	}*/
	
	private OnClickListener buttonListenerServer = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        	
        	discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, BT_TIME);
        	
        	startActivityForResult(discoverableIntent, BT_VISIBLE);
     
        }  
	};

	private OnClickListener buttonListenerClient = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	devicesRemote.clear();
        	adapter.startDiscovery();

        	dialog = new Dialog(activity, R.style.PauseGameDialogTheme);
  		    dialog.setContentView(R.layout.wait_multigame_view);
  		    
  		    TextView text = (TextView)dialog.findViewById(R.id.wait);
  		    text.setText(MultiGameActivity.this.getString(R.string.wait_client));
  		   
  		    dialog.show();

        }  
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == BT_START) {
			if (RESULT_OK != resultCode) {
				Toast.makeText(this, this.context.getText(R.string.bluetooth_confirm),
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}else if (requestCode ==  BT_VISIBLE){
			if (resultCode == BT_TIME) {
				startThreadServer();
			} else {
				Toast.makeText(this, this.context.getText(R.string.bluetooth_server), 
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	private void startThreadServer() {
		showProgressDialogWaitClient();
		stopThreads();

		threadServer = new ThreadServer();
		threadServer.init(adapter, MultiGameActivity.this);
	}
	
	private void startThreadClient(final int which) {
		
		stopThreads();
	    
		threadClient = new ThreadClient();
	    threadClient.init(devicesRemote.get(which), MultiGameActivity.this);
	}

	public void setSocketServer(final BluetoothSocket socket) {
	     
		 ThreadCommunication threadCommunication = new ThreadCommunication();
		 
		 threadCommunication.init(socket, MultiGameActivity.this);
		 
		 this.threadsCommunication.add(threadCommunication);
	}
	
	public void setSocket(final BluetoothSocket socket) {

	   threadCommunication = new ThreadCommunication();
	   threadCommunication.init(socket, MultiGameActivity.this);

	}
	
	private void getFeaturesGame(){
		
		dialog.dismiss();
		
	   if(threadsCommunication.size() == 0){
		   Toast.makeText(this, this.context.getText(R.string.no_client), Toast.LENGTH_SHORT).show();
		   stopThreads();
		   finish();
	   }
		   
	   try {
		   for (ThreadCommunication communication : threadsCommunication) {
		   		communication.os.writeUTF("featuresGame/");
		   }
		   
	   } catch (IOException e) {
			e.printStackTrace();
	   }

	   showProgressDialogWaitFeaturesGame();

	}
	
	public void featuresClient(){
	   
		dialog.dismiss();
		handler.postDelayed(new Runnable() {
	         public void run() {
	        	 showProgressDialogWaitFeaturesGame();
	         }
	    },0);
	}
	
	
	public boolean checksStartGame(){
	   if((nameAndColor.size()/3) == threadsCommunication.size()+1){
		   dialog.cancel();
		   
		   startGame();
		   return true;
	   }
	   
	   return false;
	}
	
	 private void startGame(){
		 dialog.dismiss();
		   
		 int numberClient = 0;
		 List<Spacecraft> spacecraftsClient = new ArrayList<Spacecraft>();
	  
		 try {
			 
			 for (ThreadCommunication communication : threadsCommunication) {
		 
				 if(nameAndColor.get(2).equalsIgnoreCase(communication.getPointName())){
					 
					 spacecraftsClient.add(new Spacecraft(this, nameAndColor.get(0),
					   numberClient, Integer.parseInt(nameAndColor.get(1))));
					 
				 }else if(nameAndColor.size() >= 3 && nameAndColor.get(5).equalsIgnoreCase(communication.getPointName())){
					
					 spacecraftsClient.add(new Spacecraft(this, nameAndColor.get(3),
					   numberClient, Integer.parseInt(nameAndColor.get(4))));
					 
				 }else if(nameAndColor.size() >= 9 && nameAndColor.get(8).equalsIgnoreCase(communication.getName())){
					
					 spacecraftsClient.add(new Spacecraft(this, nameAndColor.get(6),
					   numberClient, Integer.parseInt(nameAndColor.get(7))));
					 
				 }else if(nameAndColor.size() == 12 && nameAndColor.get(11).equalsIgnoreCase(communication.getName())){
					 spacecraftsClient.add(new Spacecraft(this, nameAndColor.get(9),
					   numberClient, Integer.parseInt(nameAndColor.get(10))));
				 }

				 numberClient += 1;
				 communication.os.writeUTF("startGameClient/");
			}
	   
			startGameServer(spacecraftsClient);
	   } catch (IOException e) {
			e.printStackTrace();
	   }
	}
	 
	 public void startGameServer(final List<Spacecraft> spacecraftsClient){
		   
			handler.postDelayed(new Runnable() {
				public void run() {
					engineGame = new EngineGameServer(activity.context, MultiGameActivity.this, vibrator, display, Integer.parseInt(nameAndColor.get(1)), spacecraftsClient);
					viewServer = new EngineMultiGameView(activity.context,engineGame);
					
					setContentView(viewServer);
				}
			}, 0);

	   }
		
	
  public void gameSystemPrepares(final String[] values){
		    
	   handler.postDelayed(new Runnable() {
		   public void run() {
			   startGameClient(values);
		   }
       	}, 0);
     
   	}
   
  private void startGameClient(String[] values){
	   dialog.dismiss();
	   
	   this.engineGameClient = new EngineGameClient(context, MultiGameActivity.this, vibrator, display, namePlayer);
	   this.viewClient = new br.com.thelastsurvivor.engine.multiplayergame.client.EngineMultiGameView(this,this.engineGameClient);
	  
	   this.setContentView(viewClient);
	   
  }
   
   public void sendToClientStatusGame(String values){
	   
	   for (ThreadCommunication communication : threadsCommunication) {
			try {
				communication.os.writeUTF("serverToClientDrawGameClient/"+values);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
   }

   public void sendsClientSpacecrafts(String message){
	   try {
		   for (ThreadCommunication communication : threadsCommunication) {
			   communication.os.writeUTF("spacecrafts/"+message);
		   }
		
	   } catch (IOException e) {
		e.printStackTrace();
	   }
   }
   
   public void sendsToServerClientSpacecraft(String message){
	   
	   try {
		   threadCommunication.os.writeUTF("clientToServerSpacecraft/"+message);
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }
   
   private void showProgressDialogWaitClient(){
		
		dialog = new Dialog(this, R.style.PauseGameDialogTheme){
    		
    		public boolean onKeyDown(int keyCode, KeyEvent event){
    				return false;
    		}

    	};
    	
		dialog.setContentView(R.layout.wait_multigame_view);
		    
		TextView text = (TextView)dialog.findViewById(R.id.wait);
		text.setText(MultiGameActivity.this.getString(R.string.wait_server));
		   
		dialog.show();
		if (BT_TIME > 0){
			handler.postDelayed(new Runnable() {
				public void run() {
					if (threadsCommunication == null)
					   dialog.cancel();
	           	   	getFeaturesGame();
			   	}
			}, BT_TIME * 1000);
		}
	} 
	
	private void showDevicesfound() {

	    String[] devices = new String[devicesRemote.size()];
	    
	    dialog.dismiss();
    	dialog.cancel();
    	
	    if(devices.length == 0){	
	    	Toast.makeText(this, this.context.getText(R.string.no_server),
					Toast.LENGTH_SHORT).show();
	    	
	    	return;
	    }
	    
	    for (int i = 0; i < devicesRemote.size(); i++) {
	    	devices[i] = devicesRemote.get(i).getName();
	    }

	    AlertDialog dialog = new AlertDialog.Builder(this).setTitle(MultiGameActivity.this.getString(R.string.devices))
	    	.setSingleChoiceItems(devices, -1, this)
	    	.create();
	    
	    dialog.show();
	}
	
	public void showProgressDialogWaitFeaturesGame(){
	   
		dialog = new Dialog(this, R.style.PauseGameDialogTheme);
		dialog.setContentView(R.layout.feature_view);
	   
		Button buttonRed = (Button)dialog.findViewById(R.id.buttonRed);  
		buttonRed.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	        	
	        	if(threadCommunication == null){
	        		//is server
	        		
	        		nameAndColor.add(0, namePlayer);
	        		nameAndColor.add(1, RED);
	        		nameAndColor.add(2, namePlayer);
	        		
	        		if(!checksStartGame()){
	        			showProgressDialogWaitCompleteFeatureGame();
	        		}
	        	}else{
	        		//is client
	        		showProgressDialogWaitCompleteFeatureGame();
					
	        		try {
						threadCommunication.os.writeUTF("featureClient/"+namePlayer+"/"+RED+"/"+adapter.getName());
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	        }  
		});   
		
		Button buttonBlue = (Button)dialog.findViewById(R.id.buttonBlue);  
		buttonBlue.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	        	
	        	if(threadCommunication == null){
	        		//is Server
	        		
	        		nameAndColor.add(0, namePlayer);
	        		nameAndColor.add(1, BLUE);
	        		nameAndColor.add(2, namePlayer);
	        		
	        		if(!checksStartGame()){
	        			checksStartGame();
	        		}
	        		showProgressDialogWaitCompleteFeatureGame();
	        	
	        	}else{
	        		//is Client
	        		showProgressDialogWaitCompleteFeatureGame();
	        		
	        		try {
	        			threadCommunication.os.writeUTF("featureClient/"+namePlayer+"/"+BLUE+"/"+adapter.getName());
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	     
	        }  
		});
		
		Button buttonYellow = (Button)dialog.findViewById(R.id.buttonYellow);  
		buttonYellow.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	        	
	        	if(threadCommunication == null){
	        		//is Server
	        		
	        		nameAndColor.add(0, namePlayer);
	        		nameAndColor.add(1, YELLOW);
	        		nameAndColor.add(2, namePlayer);
	        		
	        		if(!checksStartGame()){
	        			checksStartGame();
	        		}

	        		showProgressDialogWaitCompleteFeatureGame();
	        	}else{
	        		//is Client
	        		showProgressDialogWaitCompleteFeatureGame();
	        		
	        		try {
	        			threadCommunication.os.writeUTF("featureClient/"+namePlayer+"/"+YELLOW+"/"+adapter.getName());
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	        
	     
	        }  
		});
		
		Button buttonGreen = (Button)dialog.findViewById(R.id.buttonGreen);  
		buttonGreen.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	        	
	        	if(threadCommunication == null){
	        		//is Server
	        		
	        		nameAndColor.add(0, namePlayer);
	        		nameAndColor.add(1, GREEN);
	        		nameAndColor.add(2, namePlayer);
	        		
	        		if(!checksStartGame()){
	        			checksStartGame();
	        		}
			    	
	        		showProgressDialogWaitCompleteFeatureGame();
	        	}else{
	        		//is Client
	        		
	        		showProgressDialogWaitCompleteFeatureGame();
	        		try {                              
	        			threadCommunication.os.writeUTF("featureClient/"+namePlayer+"/"+GREEN+"/"+adapter.getName());
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	        
	        }  
		});

		dialog.show();
	}
	
	public void showProgressDialogWaitCompleteFeatureGame(){
	   dialog.cancel();
	   dialog.dismiss();
	   
	   dialog = new Dialog(this, R.style.PauseGameDialogTheme){
   		
   		public boolean onKeyDown(int keyCode, KeyEvent event){
   				return false;
   		}

   	};
	   dialog.setContentView(R.layout.wait_game_view);
	   
	   dialog.show();
	}
	   
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
	     
		dialog.dismiss();
		
		this.dialog = new Dialog(activity, R.style.PauseGameDialogTheme);
		this.dialog.setContentView(R.layout.wait_multigame_view);
		    
		TextView text = (TextView)this.dialog.findViewById(R.id.wait);
		text.setText(MultiGameActivity.this.getString(R.string.wait_init_server));
		   
		this.dialog.show();
		
		
		
		
		startThreadClient(which);
	    
	}
	
	
	 @Override
	 public boolean onTouchEvent(MotionEvent event){
	    	
		if(this.gestureScanner != null){
			 return this.gestureScanner.onTouchEvent(event);
		}
	   return false;
	 }
	 
	
	@Override
	public void onCancel(DialogInterface dialog) {
	   
		adapter.cancelDiscovery();
		stopThreads();
	}
	
	@Override
	public void onClick(View v) {}
	
	@Override
	public void onShowPress(MotionEvent arg0) {}
		    
	@Override
	public boolean onDown(MotionEvent arg0) {
		
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg3,
			float arg4) {
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		
		return false;
	}

	@Override 
	public boolean onSingleTapUp(MotionEvent event) {
		
		if(this.engineGame != null){
			
			this.engineGame.getSpacecraft().newShoot();
        	
        }else if(this.engineGameClient != null ){
        	
        	this.engineGameClient.getSpacecraft().newShootClient();
        }
		
		return true;
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		//this.engine.getSpacecraft().updateOrientation((event.values[1]),(event.values[0]));   
		
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;	
		
        if(this.engineGame != null){
        	this.engineGame.getSpacecraft().updateOrientation((event.values[1]),(event.values[0]));  
        	
        }else if(this.engineGameClient != null ){
        	this.engineGameClient.getSpacecraft().updateOrientation((event.values[1]),(event.values[0])); 
        }
		 
	}

	
	private void stopThreads(){
		   
		if (threadServer != null){
			threadServer.stopServer();
			threadServer = null;
		}
		
		if (threadClient != null){
			threadClient.stopClient();
			threadClient = null;
		}	
	}
	
	private class EventBluetoothReceiver  extends BroadcastReceiver {
		
		public void onReceive(Context context, Intent intent) {
	       
			if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
	         
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	    	 
				devicesRemote.add(device);

			}else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
	    	   
				showDevicesfound();
			}
		}
	}
	
	private class ScreenHandler extends Handler {

		public void handleMessage(Message msg) {
		  super.handleMessage(msg);
		
		  	if (msg.what == MSG_DISCONNECTED){
		  		Toast.makeText(MultiGameActivity.this, 
		          "Desconectou. "+ msg.obj, Toast.LENGTH_SHORT)
		              .show();
		    }
		}
	}
	 
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);

		stopThreads();
	}
   
	public void addNameAndColor(String[] values){
		this.nameAndColor.add(values[1]); 
		this.nameAndColor.add(values[2]); 
		this.nameAndColor.add(values[3]); 
		
		this.checksStartGame();
	}
	
	public List<ThreadCommunication> getThreadsCommunication() {
		return threadsCommunication;
	}


	public List<String> getNameAndColor() {
		return nameAndColor;
	}


	public IServer getEngineGame() {
		return engineGame;
	}


	public IClient getEngineGameClient() {
		return engineGameClient;
	}
	
	
	
	
	
	
	
	
	
}
