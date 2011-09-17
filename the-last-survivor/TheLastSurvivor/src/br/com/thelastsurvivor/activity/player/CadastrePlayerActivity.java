package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.player.PlayerProvider;



public class CadastrePlayerActivity extends Activity {

	private Intent i;
	private CadastrePlayerActivity activity;
	
	private EditText nickname;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cadastre_player_view);

		init();
	}
	
	public void init(){
		
		this.i = new Intent(this, MainMenuActivity.class);
		this.activity = this;
		
		this.setNickname((EditText) findViewById(R.id.editNickName));

		final Button button = (Button) findViewById(R.id.buttonOk);
		button.setOnClickListener(buttonListener);
	}
	
	private OnClickListener buttonListener = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	if (getNickname().getText().toString().equals("")) {
				
        		Toast.makeText(activity,R.string.erro_profile , 
                        Toast.LENGTH_SHORT).show();
        		
			}else{
				if(insertPlayer(new Player(getNickname().getText().toString()))){
				   startActivity(i);

				   CadastrePlayerActivity.this.finish();
	    		}
			}
     
        }  
	};
	
	
	public boolean insertPlayer(Player player) {
		ContentValues values = new ContentValues();

		values.put(PlayerProvider.IDENTIFIER_PLAYER, player.getNickname());
		getContentResolver().insert(PlayerProvider.CONTENT_URI, values);
		
		return true;
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
	}
	

	@Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	 	
    }
	
	public EditText getNickname() {
		return nickname;
	}

	public void setNickname(EditText nickname) {
		this.nickname = nickname;
	}

}
