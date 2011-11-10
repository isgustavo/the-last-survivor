package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.game.AsteroidProvider;
import br.com.thelastsurvivor.provider.game.GameProvider;
import br.com.thelastsurvivor.provider.game.ShootProvider;
import br.com.thelastsurvivor.provider.game.SpacecraftProvider;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.provider.rank.RankProvider;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;

public class AlterationPlayerActivity extends Activity{

	private Vibrator vibrator;
	
	private Player player;
	private EditText nickname;
	
	private Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alteration_player_view);
		
		init();

	}
	
	public void init(){
		
		this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				
		loadPlayer();
		
		this.nickname = (EditText)findViewById(R.id.editNickName);
		this.nickname.setText(this.player.getNickname());
	
		final Button button = (Button) findViewById(R.id.buttonOk);
		button.setOnClickListener(buttonOkListener);
		
		final Button buttonRemove = (Button) findViewById(R.id.buttonRemove);
		buttonRemove.setOnClickListener(buttonRemoveListener);
		
	}
	
	
	public void loadPlayer() {

		Cursor c = this.getContentResolver().query(PlayerProvider.CONTENT_URI, 
				null, null , null, null);
	
			while(c.moveToNext()){
				this.player = new Player(c.getInt(0),c.getString(1));
			}
		
	}
	
	private OnClickListener buttonOkListener = new OnClickListener() {  
        public void onClick(View v) {  
        	vibrator.vibrate(80);
        	if (getNickname().getText().toString().equals("")) {
				
        		Toast.makeText(AlterationPlayerActivity.this,R.string.erro_profile , 
                        Toast.LENGTH_SHORT).show();
        		
			}else{
				if(updatePlayer(new Player(player.getId(), getNickname().getText().toString()))){
				   
					Intent i = new Intent(AlterationPlayerActivity.this, MainMenuActivity.class);
					
					startActivity(i);
					
				    AlterationPlayerActivity.this.finish();
				   
				   
	    		}
			}
     
        }  
	};	
	
	
	private OnClickListener buttonRemoveListener = new OnClickListener() {  
		
        public void onClick(View v) {  
        	vibrator.vibrate(80);
        	dialog = new Dialog(AlterationPlayerActivity.this, R.style.PauseGameDialogTheme){
        		public boolean onKeyDown(int keyCode, KeyEvent event){
        				return false;
        		}

        	};
        	
    		dialog.setContentView(R.layout.remove_player_dialog);
    		
    		dialog.show();
    		
    		Button back = (Button)dialog.findViewById(R.id.button_back_game);
    		back.setOnClickListener(new OnClickListener() {  
    			public void onClick(View v) {  
    				vibrator.vibrate(80);
    				dialog.cancel();
    				dialog.dismiss();
    				
    			}
    		});  
    		
    		Button openGame = (Button)dialog.findViewById(R.id.button_remove);
    		openGame.setOnClickListener(new OnClickListener() {  
    			public void onClick(View v2) {  
    				vibrator.vibrate(80);
    			    deleteAll();
    			    
    			    AlterationPlayerActivity.this.finish();
    			    
    			}
    		}); 
        }
	};
	
	public boolean updatePlayer(Player player) {
		
		ContentValues values = new ContentValues();
		values.put(PlayerProvider.IDENTIFIER_PLAYER, player.getNickname());

		getContentResolver().update(PlayerProvider.CONTENT_URI, values, PlayerProvider.ID +" = "+ player.getId(), null);
		
		return true;
	}
	
	public void deleteAll(){
		
		dialog = new Dialog(AlterationPlayerActivity.this, R.style.PauseGameDialogTheme){
    		public boolean onKeyDown(int keyCode, KeyEvent event){
    				return false;
    		}

    	};
    	dialog.setContentView(R.layout.wait_delete_all);
    	dialog.show();
    	
    	getContentResolver().delete(PlayerProvider.CONTENT_URI, null, null);
    	
    	getContentResolver().delete(GameProvider.CONTENT_URI, null, null);
		
		getContentResolver().delete(SpacecraftProvider.CONTENT_URI, null, null);
		
		getContentResolver().delete(ShootProvider.CONTENT_URI, null, null);
		
		getContentResolver().delete(AsteroidProvider.CONTENT_URI, null, null);
		
		getContentResolver().delete(RankProvider.CONTENT_URI, null, null);
		
		
		ContentValues values = new ContentValues();
		
		String value = null;
		
		values.put(TrophiesProvider.DATE_ACHIEVED, value);
		getContentResolver().update(TrophiesProvider.CONTENT_URI, values , null, null);
		
    	dialog.cancel();

	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		 if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	  
			 Intent i = new Intent(AlterationPlayerActivity.this, MainMenuActivity.class);
				
				startActivity(i);
				
			    AlterationPlayerActivity.this.finish();
	    	
	    	
	    }
		return true;
	}

	public Player getPlayer(){
		return player;
	}

	public EditText getNickname() {
		return nickname;
	}
	
}
