package br.com.thelastsurvivor.activity.game;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MultiGameActivity extends Activity{

	private BluetoothAdapter adaptader;
	
	private static final int BT_TEMPO_DESCOBERTA = 30;
	private static final int BT_ACTIVATE = 0;
	private static final int BT_VISIBLE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

       
       init();
       
       if(this.adaptader == null){
    	   Toast.makeText(this,"Aparelho não suporta Bluetooth", 
	                Toast.LENGTH_LONG).show();
	       finish();
       }else if(!this.adaptader.isEnabled()){
    	   Intent enableBtIntent = 
    		   new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    	   startActivityForResult(enableBtIntent, BT_ACTIVATE);
       }
       
      
	  
	}
	
	
	public void init(){
		
		this.adaptader = BluetoothAdapter.getDefaultAdapter();
	}
	
	protected void onActivityResult(
	         int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);

	     if (requestCode == BT_ACTIVATE) {
	      if (RESULT_OK != resultCode) {
	         Toast.makeText(this, 
	               "Você deve ativar o Bluetooth pra continuar",
	               Toast.LENGTH_SHORT).show();
	         finish();
	       }

     } else if (requestCode == BT_VISIBLE){
	       if (resultCode == BT_TEMPO_DESCOBERTA) {
	         //iniciaThreadServidor();
	      } else {
	        Toast.makeText(this, 
	              "Para iniciar o servidor, seu aparelho"+
	              "deve estar visível.", Toast.LENGTH_SHORT).show();
       }
     }
	   }
}
