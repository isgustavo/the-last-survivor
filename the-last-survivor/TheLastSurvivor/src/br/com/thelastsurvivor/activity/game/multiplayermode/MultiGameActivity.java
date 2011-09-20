package br.com.thelastsurvivor.activity.game.multiplayermode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.multiplayergame.MultiplayerCommunication;
import br.com.thelastsurvivor.engine.multiplayergame.MultiplayerServer;

public class MultiGameActivity extends Activity  implements View.OnClickListener, DialogInterface.OnClickListener, DialogInterface.OnCancelListener{

	private BluetoothAdapter adaptader;
	
	private static final int BT_TEMPO_DESCOBERTA = 30;
	private static final int BT_ACTIVATE = 0;
	private static final int BT_VISIBLE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

       
       init();
       setContentView(R.layout.multigame_mode_view);
       
       final Button button = (Button) findViewById(R.id.server_image);
       button.setOnClickListener(buttonListener1);
	
      

	}
	
	
	public void init(){
		
		this.adaptader = BluetoothAdapter.getDefaultAdapter();
		
		//verifica se aparelho tem suporte ao bluetooth
		if(this.adaptader == null){
	    	   Toast.makeText(this,R.string.not_bluetooth, Toast.LENGTH_LONG).show();
		       finish();
		       
		//verifica se bluetooth esta ativo, se não estive solicita ativação       
	    }else if(!this.adaptader.isEnabled()){
	    	   Intent enableBtIntent =  new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    	   startActivityForResult(enableBtIntent, BT_ACTIVATE);
	    }
	}
	
	//metodo chamado para tratar o resultado do dialogo de ativação
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
	    		 Toast.makeText(this, "servidor",Toast.LENGTH_SHORT).show();
	    		 startServer();
	    	 }
	     }
	 }
	
	private OnClickListener buttonListener1 = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        	
        	discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, BT_TEMPO_DESCOBERTA);
        	
        	startActivityForResult(discoverableIntent, BT_VISIBLE);
     
        }  
	};
	
	
	ProgressDialog waitDialog;
	TelaHandler telaHandler = new TelaHandler();
	
	MultiplayerCommunication communication;
	
	public void showProgress(){
		
		waitDialog = ProgressDialog.show(
	           this, "Aguarde", "", true, true, this);
	     waitDialog.show();
	     if (BT_TEMPO_DESCOBERTA > 0){
	       telaHandler.postDelayed(new Runnable() {
	         public void run() {
	           if (communication == null)
	             waitDialog.cancel();
	         }
	       }, BT_TEMPO_DESCOBERTA * 1000);
	     }
	}
	
	public void startServer(){
		showProgress();
		//paraTudo();

		MultiplayerServer server = new MultiplayerServer(this.adaptader);
		server.run();
	}

	
	private class TelaHandler extends Handler {

		     public void handleMessage(Message msg) {
		      super.handleMessage(msg);

		     
		     }		   }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCancel(DialogInterface arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
}
