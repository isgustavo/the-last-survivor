package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

public class AlterationPlayerActivity extends Activity{

	private Player player;
	private AlterationPlayerActivity activity;
	
	private EditText nickname;
	
	private Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alteration_player_view);
		
		init();

	}
	
	public void init(){
		
		loadPlayer();
		this.activity = this;
		
		this.nickname = (EditText)findViewById(R.id.editNickName);
		this.nickname.setText(this.player.getNickname());
	
		final Button button = (Button) findViewById(R.id.buttonOk);
		button.setOnClickListener(buttonListener);
		
		final Button buttonRemove = (Button) findViewById(R.id.buttonRemove);
		buttonRemove.setOnClickListener(buttonRemoveListener);
		
		this.i = new Intent(this, MainMenuActivity.class);
		
	}
	
	
	public void loadPlayer() {

		Cursor c = this.getContentResolver().query(PlayerProvider.CONTENT_URI, 
				null, null , null, null);
	
			while(c.moveToNext()){
				this.player = new Player(c.getInt(0),c.getString(1));
			}
		
	}
	
	private OnClickListener buttonListener = new OnClickListener() {  
        public void onClick(View v) {  

        	if (getNickname().getText().toString().equals("")) {
				
        		Toast.makeText(activity,R.string.erro_profile , 
                        Toast.LENGTH_SHORT).show();
        		
			}else{
				if(updatePlayer(new Player(getNickname().getText().toString()))){
				   startActivity(i);

				   AlterationPlayerActivity.this.finish();
	    		}
			}
     
        }  
	};	
	
	private android.content.DialogInterface.OnClickListener removeProfile = new android.content.DialogInterface.OnClickListener(){
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			removePlayer();
			
			
			
		}
	};
	
	private OnClickListener buttonRemoveListener = new OnClickListener() {  
		
        public void onClick(View v) {  
			AlertDialog.Builder alert = new AlertDialog.Builder(AlterationPlayerActivity.this);
			alert.setIcon(null);
			alert.setTitle(R.string.removeProfile);
			alert.setPositiveButton(R.string.remove_confirm, removeProfile);
			alert.setNeutralButton(R.string.remove_back, null);
			alert.show();
        }
	};
	
	
	public boolean updatePlayer(Player player) {
		ContentValues values = new ContentValues();

		values.put(PlayerProvider.ID, player.getId());

		getContentResolver().update(PlayerProvider.CONTENT_URI, values, PlayerProvider.ID +" = "+ player.getId(), null);
		
		return true;
	}
	
	public void removePlayer(){
		
		getContentResolver().delete(PlayerProvider.CONTENT_URI, PlayerProvider.ID +" = "+ player.getId(), null);
	}
	

	public Player getPlayer(){
		return player;
	}

	public EditText getNickname() {
		return nickname;
	}
	
}
