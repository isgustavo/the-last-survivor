package br.com.thelastsurvivor.activity.game.multiplayermode;

import br.com.thelastsurvivor.R;
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
       
       setContentView(R.layout.multigame_mode_view);

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
	 }
}
