package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
	private static final int REMOVE_DIALOG = 0;
	
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
		
		final Button buttonRemove = (Button) findViewById(R.id.buttonRemove);
		buttonRemove.setOnClickListener(buttonRemoveListener);
		
		this.i = new Intent(this, MainMenuActivity.class);
		
	}
	
	
	public void loadPlayer() {

		Cursor c = this.getContentResolver().query(PlayerProvider.CONTENT_URI, 
				null, null , null, null);
		try{
			while(c.moveToNext()){
				//Log.d("COUNT", ""+c.getColumnCount());
				this.player = new Player(c.getInt(0),c.getString(1), c.getString(2));
			}
			
		}catch(IllegalStateException e){
			this.player = new Player(c.getInt(0),c.getString(1), "");
		}
	
		
	}
	
	private OnClickListener buttonListener = new OnClickListener() {  
		
        public void onClick(View v) {  

        	if (getNickname().getText().toString().equals("")) {
        	
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
	
	private OnClickListener buttonRemoveListener = new OnClickListener() {  
		
        public void onClick(View v) {  
        	showDialog(0);
			//AlertDialog.Builder alert = new AlertDialog.Builder(AlterationPlayerActivity.this);
			//alert.setIcon(null);
			//alert.setTitle(R.string.removeProfile);
			//alert.setNeutralButton("OK", null);
			//alert.show();
			
			
			
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
	Button buttonOk;
	Button buttonRemove;
	 
	@Override
	 protected Dialog onCreateDialog(int id) {
	  // TODO Auto-generated method stub
	  Dialog dialog = null;;
	     switch(id) {
	     case REMOVE_DIALOG:
		      dialog = new Dialog(this); 
		      dialog.setContentView(R.layout.remove_player_dialog);
		      dialog.setTitle(R.string.removeProfile);
		      dialog.setTitle(R.string.removeProfile);
		
		      buttonOk = (Button)dialog.findViewById(R.id.buttonOk);
		      buttonRemove = (Button)dialog.findViewById(R.id.buttonRemove);
	      
	     break;
	     }
	     return dialog;
	 }  
	
	
}
