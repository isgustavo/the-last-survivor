package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class AlterationPlayerActivity extends Activity{

	//private BackgroundView view;
	private Player player;
	
	private EditText nickname;
	private EditText lgTwitter;
	
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
		
		this.nickname = (EditText)findViewById(R.id.editNickName);
		this.nickname.setText(this.player.getIdentifier());
		this.lgTwitter = (EditText) findViewById(R.id.editLGTwitter);
		this.lgTwitter.setText(this.player.getLgTwitter());
		
		final Button button = (Button) findViewById(R.id.buttonOk);
		button.setOnClickListener(buttonListener);
		
		this.i = new Intent(this, MainMenuActivity.class);
		
	}
	
	
	public void loadPlayer() {

		Cursor c = this.getContentResolver().query(PlayerProvider.CONTENT_URI, 
				null, null , null, null);
		try{
			while(c.moveToNext()){
				Log.d("COUNT", ""+c.getColumnCount());
				player = new Player(c.getInt(0),c.getString(1), c.getString(2));
			}
			
		}catch(IllegalStateException e){
			player = new Player(c.getInt(0),c.getString(1), "");
		}
	
		
	}
	
	private OnClickListener buttonListener = new OnClickListener() {  
		
        public void onClick(View v) {  

        	if (getNickname().getText().toString().equals("")) {
        		Log.d("BUTTON",""+getNickname().getText());
				AlertDialog.Builder alert = new AlertDialog.Builder(AlterationPlayerActivity.this);
				alert.setIcon(null);
				alert.setTitle("Nickname is necessary");
				alert.setNeutralButton("OK", null);
				alert.show();
			}else{
				if(updatePlayer(new Player(getPlayer().getId(), getNickname().getText().toString(), getLgTwitter().getText().toString()))){
				   startActivity(i);

				   AlterationPlayerActivity.this.finish();
	    		}
			}
     
        }  
	};
	
	public boolean updatePlayer(Player player) {
		ContentValues values = new ContentValues();

		values.put(PlayerProvider.IDENTIFIER_PLAYER, player.getIdentifier());
		values.put(PlayerProvider.LGTWITTER, player.getLgTwitter());

		getContentResolver().update(PlayerProvider.CONTENT_URI, values, PlayerProvider.ID +" = "+ player.getId(), null);
		
		return true;
	}

	public Player getPlayer(){
		return player;
	}

	public EditText getNickname() {
		return nickname;
	}

	public EditText getLgTwitter() {
		return lgTwitter;
	}
	
	
	
	
}
