package br.com.thelastsurvivor.activity.game.multiplayermode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
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
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayer.EngineGame;
import br.com.thelastsurvivor.engine.multiplayergame.client.EngineGameClient;
import br.com.thelastsurvivor.engine.multiplayergame.server.EngineGameServer;
import br.com.thelastsurvivor.engine.util.IClient;
import br.com.thelastsurvivor.engine.util.IServer;
import br.com.thelastsurvivor.engine.view.EngineMultiGameView;
import br.com.thelastsurvivor.util.MyAudioPlayer;

public class MultiGameActivity extends Activity implements SensorEventListener, 
		OnGestureListener, View.OnClickListener, DialogInterface.OnClickListener, 
		DialogInterface.OnCancelListener{

	public static final String SERVICE = "LASTSURVIVOR";
	public static final UUID _UUID =  UUID.fromString("db12d1e9-caba-84ef-398b-18211984abcd");
	public static final UUID _UUID2 =  UUID.fromString("db12d1e9-caba-84ef-398b-18221984abcd");
	public static final UUID _UUID3 =  UUID.fromString("db12d1e9-caba-84ef-398b-18231984abcd");
	public static final UUID _UUID4 =  UUID.fromString("db12d1e9-caba-84ef-398b-18241984abcd");

	
	private static final int BT_TIME = 30;
	
		private static final int FEATURE = 3;
		private static final int FEATURE_CLIENT = 4;

		private static final String RED = "1";
		private static final String BLUE = "2";
		private static final String YELLOW = "3";
		private static final String GREEN = "4";
		
	   private static final int BT_ATIVAR = 0;

	   private static final int BT_VISIBLE = 1;


	   private static final int MSG_TEXTO = 0;

	   private static final int MSG_DESCONECTOU = 2;

	   private static final int INICIAR_SERVIDOR = 1;

	   private static final int INICIAR_CLIENTE = 2;


	   private ThreadServidor threadServidor;

	   private ThreadCliente threadCliente;

	   private ThreadComunicacao threadComunicacao;
	   private List<ThreadComunicacao> threadsComunicacao;

	   private BluetoothAdapter adaptador;
	   private List<BluetoothDevice> dispositivosRemotos;
	   private EventosBluetoothReceiver meuReceiver;

	  

	   private ArrayAdapter<String> historico;
	   private TelaHandler telaHandler;
	   private ProgressDialog waitDialog;
	   
	   
	   private String namePlayer;
	   private SensorManager manager;
	    private Sensor accelerometer;
	    private GestureDetector gestureScanner;
	    private Vibrator vibrator;
	    private MyAudioPlayer audioPlayer;
	    private Display display;
	    
	    private EngineMultiGameView view;
	    private br.com.thelastsurvivor.engine.multiplayergame.client.EngineMultiGameView viewClient;
	    private EngineGame engine;
	    
	    private IServer engineGame;
	    private IClient engineGameClient;
	    //private EngineGameClient engineClient;
	    
	    private WakeLock wakeLock;
	    private Long beforeTime;
	    Context context;
	   
	    public MultiGameActivity activity;
	    
	   public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     
	     Bundle s = this.getIntent().getExtras().getBundle("playerBundle");
	     this.namePlayer = s.getString("id_player");
	     
	     setContentView(R.layout.multigame_mode_view);

	     telaHandler = new TelaHandler();
	    
	     activity = this;

	     meuReceiver = new EventosBluetoothReceiver();
	     dispositivosRemotos = new ArrayList<BluetoothDevice>();
	     adaptador = BluetoothAdapter.getDefaultAdapter();

	     if (adaptador != null) {
	       if (!adaptador.isEnabled()) {
	         Intent enableBtIntent = new Intent(
	           BluetoothAdapter.ACTION_REQUEST_ENABLE);
	         startActivityForResult(enableBtIntent, BT_ATIVAR);
	       }

	     } else {
	       Toast.makeText(this,"Aparelho não suporta Bluetooth", 
	      Toast.LENGTH_LONG).show();
	       finish();
	     }

	     IntentFilter filter1 = new IntentFilter(
	    BluetoothDevice.ACTION_FOUND);
	     IntentFilter filter2 = new IntentFilter(
	    BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

	     registerReceiver(meuReceiver, filter1);
	     registerReceiver(meuReceiver, filter2);
	     
	     context = this.getApplicationContext();
			
		   //this.audioPlayer = new MyAudioPlayer(this, R.raw.singleplayer_soundtrack);
		   //this.audioPlayer.start();
	     threadsComunicacao = new ArrayList<ThreadComunicacao>();
		   this.manager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
			
		   this.accelerometer = this.manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		   this.manager.registerListener (this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		      
		   this.gestureScanner = new GestureDetector(this);
		    	    
		   final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	       
		   this.wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "");
	       this.wakeLock.acquire();      
	       
	       this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	       
	       this.display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
	       
	       this.beforeTime = 0L;

	     final Button buttonServer = (Button) findViewById(R.id.server_image);
	       buttonServer.setOnClickListener(buttonListenerServer);
	      
	       final Button buttonClient = (Button) findViewById(R.id.client_image);
	       buttonClient.setOnClickListener(buttonListenerClient);
	   }

	   protected void onDestroy() {
	     super.onDestroy();
	     unregisterReceiver(meuReceiver);

	     paraTudo();
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
	        	
	        	 dispositivosRemotos.clear();
	        	 adaptador.startDiscovery();
	        	 
	        	 Log.d("LOG", ".CLIENT");
	        	 
	        	 dialog = new Dialog(activity, R.style.PauseGameDialogTheme);
	  		     dialog.setContentView(R.layout.features_wait_player_view);
	  		   
	  		     dialog.show();
	        	
	        	      
	     
	        }  
		};
	  
		@Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	     if (requestCode == BT_ATIVAR) {
	       if (RESULT_OK != resultCode) {
	         Toast.makeText(this, 
	        "Você deve ativar o Bluetooth pra continuar",
	        Toast.LENGTH_SHORT).show();
	         finish();
	       }

	     } else if (requestCode ==  BT_VISIBLE){
	       if (resultCode == BT_TIME) {
	         iniciaThreadServidor();
	       } else {
	         Toast.makeText(this, 
	       "Para iniciar o servidor, seu aparelho"+
	       "deve estar visível.", Toast.LENGTH_SHORT).show();
	       }
	     }
	       
	   
	   }

	   public void showWaitingCompleteFeatureGame(){
		   Log.d("CLIENT", "SHOW");
		   
		   dialog.cancel();
		   dialog.dismiss();
		   
		   dialog = new Dialog(this, R.style.PauseGameDialogTheme);
		   dialog.setContentView(R.layout.game_wait_view);
		   
		   dialog.show();
	   }
	   
	  
	   private void exibirDispositivosEncontrados() {

	    String[] aparelhos = 
	    new String[dispositivosRemotos.size()];
	     for (int i = 0; i < dispositivosRemotos.size(); i++) {
	    	 //iniciaThreadCliente(i);
	    	 
	    	 Log.d("CLIENT", "LISTA");
	       aparelhos[i] = dispositivosRemotos.get(i).getName();
	     }

	     AlertDialog dialog = new AlertDialog.Builder(this)
	    .setTitle("Aparelhos encontrados")
	    .setSingleChoiceItems(aparelhos, -1, this)
	    .create();
	     dialog.show();
	   }

	   public void onClick(DialogInterface dialog, int which) {
	     iniciaThreadCliente(which);
	     dialog.dismiss();
	   }

	   public void onCancel(DialogInterface dialog) {
	     adaptador.cancelDiscovery();
	     paraTudo();
	   }

	   public void onClick(View v) {}
	   
	   Dialog dialog;
	   private void exibirProgressDialog(String mensagem, long tempo){
		   dialog = new Dialog(this, R.style.PauseGameDialogTheme);
		   dialog.setContentView(R.layout.features_wait_player_view);
		   
		   dialog.show();
		   if (tempo > 0){
		       telaHandler.postDelayed(new Runnable() {
		         public void run() {
		           if (threadComunicacao == null)
		        	   dialog.cancel();
		           	   getFeaturesGame();
		         }
		       }, tempo * 1000);
		     }
		   
	   
	   }
	   
	   
	   
	   private void gameSystemPrepares(long tempo, final String[] values){
		    
		       telaHandler.postDelayed(new Runnable() {
		         public void run() {
		           startGameClient(values);
		         }
		       }, tempo * 1000);
		     
		   }

	   private void paraTudo(){
	   
	   if (threadServidor != null){
	       threadServidor.parar();
	       threadServidor = null;
	     }
	     if (threadCliente != null){
	      threadCliente.parar();
	       threadCliente = null;
	     }	
	   }

	   private void iniciaThreadServidor() {
	     exibirProgressDialog("Aguardando por conexões...",BT_TIME);
	     paraTudo();

	     threadServidor = new ThreadServidor();
	     threadServidor.iniciar();
	   }

	   private void iniciaThreadCliente(final int which) {
	     paraTudo();
	     threadCliente = new ThreadCliente();
	     threadCliente.iniciar(dispositivosRemotos.get(which));
	   }
	   
	   List<BluetoothSocket> clientsSocket;
	   //List<ThreadComunicacao> communicationSockets;
	   
	   private void trataSocketServer(final BluetoothSocket socket) {
		     //waitDialog.dismiss();

		     ThreadComunicacao threadComunicacao = new ThreadComunicacao();
		     threadComunicacao.iniciar(socket);
		     threadsComunicacao.add(threadComunicacao);
		     
	   }
	   
	   private Map<String, Integer> colorClients = new Hashtable<String, Integer>();
	   private Integer colorServer = 1;
	   
	   
	   private void getFeaturesGame(){
		   
		   dialog.dismiss();
		   int numberClient = 0;
		   
		   
		   if(threadsComunicacao.size() == 0){
			   Toast.makeText(this,  "Nenhum sobrevivente encontrado", Toast.LENGTH_SHORT).show();
			   paraTudo();
			   finish();
		   }
		   
		   try {
		   for (ThreadComunicacao communication : threadsComunicacao) {
			   		communication.os.writeUTF("featuresGame/");
				
		   }
		   } catch (IOException e) {
				e.printStackTrace();
		   }

		   
		   //startActivityForResult(new Intent(activity, FeaturesGameActivity.class), FEATURE);
		   showFeaturesGame();
		   
		  
	   }

	   
	   public void showFeaturesGame(){
		   dialog = new Dialog(this, R.style.PauseGameDialogTheme);
		   dialog.setContentView(R.layout.feature_view);
		   
		   Button buttonRed = (Button)dialog.findViewById(R.id.buttonRed);  
			buttonRed.setOnClickListener(buttonListenerRed);  
			
			Button buttonBlue = (Button)dialog.findViewById(R.id.buttonBlue);  
			buttonBlue.setOnClickListener(buttonListenerBlue); 
			
			Button buttonYellow = (Button)dialog.findViewById(R.id.buttonYellow);  
			buttonYellow.setOnClickListener(buttonListenerYellow); 
			
			Button buttonGreen = (Button)dialog.findViewById(R.id.buttonGreen);  
			buttonGreen.setOnClickListener(buttonListenerGreen); 
		   
		   
		   
		   dialog.show();
	   }
	   
	   private OnClickListener buttonListenerRed = new OnClickListener() {  
	        public void onClick(View v) {  
	        	
	        	if(threadComunicacao == null){
	        		nameAndColor.add(0, namePlayer);
	        		nameAndColor.add(1, RED);
	        		nameAndColor.add(2, namePlayer);
	        		
	        		if(!checksStartGame()){
	        			//Log.d("COLOR", "SEVER"+resultCode);
				    	showWaitingCompleteFeatureGame();
	        		}
			    	
			    	
	        	}else{
	        		Log.d("CLIENT", "RED");
	        		showWaitingCompleteFeatureGame();
					try {
						
						Log.d("CLIENT", "featureClient/"+namePlayer+"/"+RED+adaptador.getName());
						threadComunicacao.os.writeUTF("featureClient/"+namePlayer+"/"+RED+"/"+adaptador.getName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	     
	        }  
		};  
		
		private OnClickListener buttonListenerBlue = new OnClickListener() {  
	        public void onClick(View v) {  
	        	if(threadComunicacao == null){
	        		nameAndColor.add(0, namePlayer);
	        		nameAndColor.add(1, BLUE);
	        		nameAndColor.add(2, namePlayer);
	        		
	        		if(!checksStartGame()){
	        			checksStartGame();
	        		}
			    	//Log.d("COLOR", "SEVER"+resultCode);
			    	showWaitingCompleteFeatureGame();
	        	}else{
	        		showWaitingCompleteFeatureGame();
	        		try {
						threadComunicacao.os.writeUTF("featureClient/"+namePlayer+"/"+BLUE+"/"+threadCliente.socket.getRemoteDevice().getName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	     
	        }  
		};  
		
		private OnClickListener buttonListenerYellow = new OnClickListener() {  
	        public void onClick(View v) {  
	        	if(threadComunicacao == null){
	        		nameAndColor.add(0, namePlayer);
	        		nameAndColor.add(1, YELLOW);
	        		nameAndColor.add(2, namePlayer);
	        		
	        		if(!checksStartGame()){
	        			checksStartGame();
	        		}
			    	//Log.d("COLOR", "SEVER"+resultCode);
			    	showWaitingCompleteFeatureGame();
	        	}else{
	        		showWaitingCompleteFeatureGame();
	        		try {
						threadComunicacao.os.writeUTF("featureClient/"+namePlayer+"/"+YELLOW+"/"+threadCliente.socket.getRemoteDevice().getName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        
	     
	        }  
		};  
		
		private OnClickListener buttonListenerGreen = new OnClickListener() {  
	        public void onClick(View v) {  
	        	if(threadComunicacao == null){
	        		nameAndColor.add(0, namePlayer);
	        		nameAndColor.add(1, GREEN);
	        		nameAndColor.add(2, namePlayer);
	        		
	        		if(!checksStartGame()){
	        			checksStartGame();
	        		}
			    	//Log.d("COLOR", "SEVER"+resultCode);
			    	showWaitingCompleteFeatureGame();
	        	}else{
	        		showWaitingCompleteFeatureGame();
	        		try {                              
						threadComunicacao.os.writeUTF("featureClient/"+namePlayer+"/"+GREEN+"/"+threadCliente.socket.getRemoteDevice().getName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        
	        }  
		};  
	   
	   public void featuresClient(){
		   dialog.dismiss();
		   telaHandler.postDelayed(new Runnable() {
		         public void run() {
		        	 showFeaturesGame();
		         }
		       }, 0 * 1000);
		 
	   }
	   
	   public boolean checksStartGame(){

		   Log.d("nameANdColor"+nameAndColor.size()/3, ".CONNECTION"+threadsComunicacao.size()+1);
		   if((nameAndColor.size()/3) == threadsComunicacao.size()+1){
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
		  
		   for (String valor : nameAndColor) {
				Log.d("valor", ""+valor);
			   }
		   try {
		   Log.d("MULTIGAMEACTIVITY", ".FOR");
		   for (ThreadComunicacao communication : threadsComunicacao) {
			   //spacecraftsClient.add(new Spacecraft(this, communication.nome,
				//	   numberClient, colorClients.get(communication.nome)));
			   
			   
			   for (String valor : nameAndColor) {
				Log.d("valor", ""+valor);
			   }
			   
			   Log.d("...", "nome"+communication.nome);
			   
			   if(nameAndColor.get(2).equalsIgnoreCase(communication.nome)){
				   spacecraftsClient.add(new Spacecraft(this, nameAndColor.get(0),
						   numberClient, Integer.parseInt(nameAndColor.get(1))));
			   }else if(nameAndColor.size() > 3 && nameAndColor.get(5).equalsIgnoreCase(communication.nome)){
				   spacecraftsClient.add(new Spacecraft(this, nameAndColor.get(3),
						   numberClient, Integer.parseInt(nameAndColor.get(4))));
			   }else if(nameAndColor.size() > 9 && nameAndColor.get(8).equalsIgnoreCase(communication.nome)){
				   spacecraftsClient.add(new Spacecraft(this, nameAndColor.get(6),
						   numberClient, Integer.parseInt(nameAndColor.get(7))));
			   }else if(nameAndColor.size() == 12 && nameAndColor.get(11).equalsIgnoreCase(communication.nome)){
				   spacecraftsClient.add(new Spacecraft(this, nameAndColor.get(9),
						   numberClient, Integer.parseInt(nameAndColor.get(10))));
			   }
			   
			   numberClient += 1;
			  
			   communication.os.writeUTF("startGameClient/");
				
			   startGameServer(spacecraftsClient);
		   }
		   } catch (IOException e) {
				e.printStackTrace();
		   }
	   }
	   
	   public void startGameServer(final List<Spacecraft> spacecraftsClient){
		   
		   telaHandler.postDelayed(new Runnable() {
		         public void run() {
		           engineGame = new EngineGameServer(activity.context, MultiGameActivity.this, vibrator, display, Integer.parseInt(nameAndColor.get(1)), spacecraftsClient);
		  		   view = new EngineMultiGameView(activity.context,engineGame);
		  		   setContentView(view);
		         }
		       }, 0 * 1000);

	   }
		  
	   private void startGameClient(String[] values){
		   //dialog.cancel();
		   dialog.dismiss();
		   
		   Log.d("START", "GAME CLIENT");
		   this.engineGameClient = new EngineGameClient(context, MultiGameActivity.this, vibrator, display, namePlayer);
		   this.viewClient = new br.com.thelastsurvivor.engine.multiplayergame.client.EngineMultiGameView(this,this.engineGameClient);
		  
		   this.setContentView(viewClient);
		   
	   }
	   
	   public void sendToClientStatusGame(String values){
		   for (ThreadComunicacao communication : threadsComunicacao) {
				try {
					communication.os.writeUTF("serverToClientDrawGameClient/"+values);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	   }

	   public void sendsToServerClientSpacecraft(String message){
		   try {
			threadComunicacao.os.writeUTF("clientToServerSpacecraft/"+message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	   }
	   
	   public void sendsClientSpacecrafts(String message){
		   try {
			   for (ThreadComunicacao communication : threadsComunicacao) {
				   communication.os.writeUTF("spacecrafts/"+message);
			   }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
	   private void trataSocket(final BluetoothSocket socket) {
		     //waitDialog.dismiss();

		     threadComunicacao = new ThreadComunicacao();
		     threadComunicacao.iniciar(socket);
		     
		    
		   }
	   
	   
	   
	   private class ThreadServidor extends Thread {
		      BluetoothServerSocket serverSocket;
		      BluetoothSocket clientSocket;
		                
		       public void run() {
		          try {
		           serverSocket = adaptador.listenUsingRfcommWithServiceRecord(SERVICE, _UUID);
		           //clientSocket.add(serverSocket.accept());
		           clientSocket = serverSocket.accept();
		           trataSocketServer(clientSocket);
		                              
		         } catch (IOException e) {
		            telaHandler.obtainMessage(MSG_DESCONECTOU, 
		                  e.getMessage()+"[1]").sendToTarget();
		           e.printStackTrace();
		        }
		       }
		                
		     public void iniciar(){
		    	//clientsSocket = new ArrayList<BluetoothSocket>();
		        start();
		       }
		                
		      public void parar(){
		          try {
		            serverSocket.close();
		       } catch (IOException e) {
		         e.printStackTrace();
		        }
		       }
		     }
	   
	   private class ThreadCliente extends Thread {
		      BluetoothDevice device;
		      BluetoothSocket socket;

		     public void run() {	
		       try {
		        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(_UUID);
		          socket.connect();
		          trataSocket(socket);

		        } catch (IOException e) {
		            e.printStackTrace();
		            telaHandler.obtainMessage(MSG_DESCONECTOU, 
		                  e.getMessage()+"[2]").sendToTarget();
		         }
		        }

		       public void iniciar(BluetoothDevice device){
		         this.device = device;
		          start();
		       }

		      public void parar(){
		         try {
		           socket.close();
		         } catch (Exception e) {
		           e.printStackTrace();
		         }
		       }
		    }
	   
	   static Integer names = 3;
	   List<String> nameAndColor = new ArrayList<String>();
	   private class ThreadComunicacao extends Thread {
		   
		   DataInputStream is;
		   DataOutputStream os;
		   
		        String nome;
		        BluetoothSocket socket;
		    
		       public void run() {
		         try {
		          nome = socket.getRemoteDevice().getName();
		          is = new DataInputStream(socket.getInputStream());
		          os  = new DataOutputStream(socket.getOutputStream());
		          String string;
		          while (true) {
		             string = is.readUTF();
		           
		             
		            String[] values = string.split("/");
		             
		           
		            
		            
		             
		            //client send spacecraft to server  
		            if(values[0].equals("clientToServerSpacecraft")){
		            	
		            	if(engineGame != null){
		            		Log.d("SERVER", "values"+values[1]);
		            		engineGame.setSpacecraftClientToUpdate(values);
		            	}
		            	
		            }
		            
		            
		            //
		            if(values[0].equals("serverToClientDrawGameClient")){
		            	 Log.d("DRAW", "GAME CLIENT");
		            	if(engineGameClient != null){
		            		engineGameClient.drawGame(values);
		            	}
		            	 
		            }
		            
		           
		            if(values[0].equals("featureClient")){
		            	
		            	//int length = nameAndColor.length;
		            	nameAndColor.add(values[1]); 
		            	nameAndColor.add(values[2]); 
		            	nameAndColor.add(values[3]); 
		            	
		            	checksStartGame();
		            	
		            }
		            
		            if(values[0].equals("startGameClient")){
		            	
		            	gameSystemPrepares(0,values);
		            }
		            
		            if(values[0].equals("featuresGame")){
		            	
		            	featuresClient();
		            }
		             
		          }
		          
		          
		         } catch (IOException e) {
		            e.printStackTrace();
		            telaHandler.obtainMessage(MSG_DESCONECTOU, 
		                  e.getMessage()+"[3]").sendToTarget();
		        }/* catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
		        }
		                
		      public void iniciar(BluetoothSocket socket){
		          this.socket = socket;
		          start();
		      }
		                
		        public void parar(){
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
		     }
	   
	   private class EventosBluetoothReceiver  extends BroadcastReceiver {

	     public void onReceive(Context context, Intent intent) {
	       if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
	         
	    	 BluetoothDevice device = intent.getParcelableExtra(
	         BluetoothDevice.EXTRA_DEVICE);
	    	 
	    	 Log.d("CLIENT", "ENCONTROU");
	         dispositivosRemotos.add(device);

	       }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
	    	   
	    	 Log.d("CLIENT", "EXIBIR");
	         exibirDispositivosEncontrados();
	       }
	     }
	   }
	   
	   private class TelaHandler extends Handler {

		        public void handleMessage(Message msg) {
		          super.handleMessage(msg);

		         if (msg.what == MSG_TEXTO){
		            historico.add(msg.obj.toString());
		            historico.notifyDataSetChanged();

		         } else if (msg.what == MSG_DESCONECTOU){
		           Toast.makeText(MultiGameActivity.this, 
		                  "Desconectou. "+ msg.obj, Toast.LENGTH_SHORT)
		                  .show();
		        }
		       }
		   }

	
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
		
			//this.engine.getSpacecraft().newShoot();
				
				
			
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
	        	Log.d("MOVIMENTO", "SERVER");
	        	this.engineGame.getSpacecraft().updateOrientation((event.values[1]),(event.values[0]));  
	        	
	        }else if(this.engineGameClient != null ){
	        	Log.d("MOVIMENTO", "CLIENT");
	        	this.engineGameClient.getSpacecraft().updateOrientation((event.values[1]),(event.values[0])); 
	        }
			 
		}

		public List<ThreadComunicacao> getThreadsComunicacao() {
			return threadsComunicacao;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*	
	private static final int BT_TIME = 30;
	private static final int BT_ACTIVATE = 0;
	private static final int BT_VISIBLE = 1;
	
	private MultiplayerCommunicationServer communication;
	private MultiplayerServer server;	
	private MultiplayerClient client;
	private List<MultiplayerClient> clients;
	    
    private BluetoothAdapter adaptader;
    private List<BluetoothDevice> remoteDevices; 
    private BluetoothEvent event;
    
    ProgressDialog waitDialog;
	ScreenHandler screenHandler = new ScreenHandler();
    
	
	
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
        	showProgressClient(0);     
        	      
     
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
	    		 this.startServer();
	    	 }
	     } 
	 }

	@Override
	public void onCancel(DialogInterface arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void startServer(){
		showProgressServer(BT_TIME);
		//stopThreads();
		
		
		Thread threadserver = new Thread(server);
		threadserver.start();
		
		
	}
	
	public void startClient(Integer which){
		this.stopThreads();
		
		client = new MultiplayerClient();
		//client.startClient(this.remoteDevices.get(which), MultiplayerGameActivity.this);
		
		
	}
	
	public void showProgressClient(long time){
		
		waitDialog = ProgressDialog.show(this, "Aguarde", "", true, true, this);
	    waitDialog.show();
	    
	    if (time > 0){
	    	screenHandler.postDelayed(new Runnable() {
	            public void run() {
	              if(communication == null)
	                waitDialog.cancel();
	            }
	            
	          }, time * 1000);
	        }
	    }  
	
	
	public void showProgressServer(long time){
		
		waitDialog = ProgressDialog.show(this, "Aguarde", "", true, true, this);
	    waitDialog.show();
	    if (time > 0){
	    	screenHandler.postDelayed(new Runnable() {
	            public void run() {
	            	
	            	  	waitDialog.cancel(); 
	            	  	startCommunicationServer();
	            	  	
	            }
	          }, time * 1000);
	        }
	    }  
	    
	
	private void  startCommunicationServer(){
		
		if(this.server == null){
			server = new MultiplayerServer(this.adaptader);
		}
		if(!this.server.startCommunication(this.communication)){
			stopThreads();
			Toast.makeText(this, R.string.bluetooth_confirm,Toast.LENGTH_SHORT).show();
	  	}else{
	  		Toast.makeText(this, "conectado com cliente",Toast.LENGTH_SHORT).show();
	  	}
	}
	private void stopThreads(){
	     
		if (this.communication != null){
			this.communication.stopCommunication();
			this.communication = null;
		 }
     
	     if (this.server != null){
	    	 this.server.stopServer();
	    	 this.server = null;
	     }
		 
	     if (this.clients != null){
	    	 for (MultiplayerClient client : this.clients) {
	    		 client.stopClient();
			}
	    	this.clients = new ArrayList<MultiplayerClient>();
	    }	
	}
	
	
	private class BluetoothEvent  extends BroadcastReceiver {

		public void onReceive(Context context, Intent intent) {
			if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
        
				BluetoothDevice device = intent.getParcelableExtra(
				BluetoothDevice.EXTRA_DEVICE);
         
				remoteDevices.add(device);

			}else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
				
				showRemoteDevices();
			}
		}
    }
	   
	/*private void connectToServer(){
		waitDialog.dismiss();
		
		for (BluetoothDevice remote : this.remoteDevices) {
			Thread clientThread = new Thread(new MultiplayerClient(remote, MultiGameActivity.this));
			clientThread.start();
		}
		
		
		
		
	}
	
	private void showRemoteDevices() {
	    waitDialog.dismiss();

	    String[] aparelhos = 
	    new String[remoteDevices.size()];
	     for (int i = 0; i < remoteDevices.size(); i++) {
	       aparelhos[i] = remoteDevices.get(i).getName();
	     }

	     AlertDialog dialog = new AlertDialog.Builder(this)
	     .setTitle("Aparelhos encontrados")
	     .setSingleChoiceItems(aparelhos, -1, this)
	     .create();
	      dialog.show();
	}
	
	private class ScreenHandler extends Handler {

        public void handleMessage(Message msg) {
          super.handleMessage(msg);

       }
   }

	@Override
	public void onClick(DialogInterface dialog, int which) {
		this.startClient(which);
		dialog.dismiss();
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	
/*	public void startServer(){
		
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
		 
		Log.d("CLIENT","."+this.clients.size());
	}
	
	public void addSocket(final BluetoothSocket client){
		
		
		this.communication = new MultiplayerCommunication();
		this.communication.startCommunication(client);
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
	
	
	

		
	
	
	
	//MultiplayerCommunication communication;
	
	
	
	

	
	private class TelaHandler extends Handler {

		     public void handleMessage(Message msg) {
		      super.handleMessage(msg);

		     
		     }		   }*/

}
