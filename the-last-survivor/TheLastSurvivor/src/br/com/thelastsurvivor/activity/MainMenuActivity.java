package br.com.thelastsurvivor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.activity.game.simplemode.SavedGameActivity;
import br.com.thelastsurvivor.activity.player.AlterationPlayerActivity;
import br.com.thelastsurvivor.activity.rank.RankActivity;
import br.com.thelastsurvivor.activity.trophies.TrophiesActivity;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class MainMenuActivity extends Activity{
	
	private Player player;
	
	private Vibrator vibrator;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_menu_view);
		
		init();

	}
	
	public void init(){
		
		this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		 
		loadPlayer();
		
		Log.d("MAINMENUACTIVITY","..");
		Log.d(".."+this.player.getId(),".."+this.player.getNickname());
		
		Button buttonSimpleGame = (Button)findViewById(R.id.buttonSimpleMode);  
		buttonSimpleGame.setOnClickListener(buttonSimpleGameListener);  
		
		Button buttonMultiMode = (Button)findViewById(R.id.buttonMultiMode);  
		buttonMultiMode.setOnClickListener(buttonMultiModeListener); 
		
		Button buttonTrophies = (Button)findViewById(R.id.buttonTrophies);  
		buttonTrophies.setOnClickListener(buttonTrophiesListener); 
		
		Button buttonRank = (Button)findViewById(R.id.buttonRank);  
		buttonRank.setOnClickListener(rankListener); 
		
		Button buttonOption = (Button)findViewById(R.id.buttonOption);  
		buttonOption.setOnClickListener(buttonOptionListener); 
		
	
	}
	
	public void loadPlayer() {

		Cursor c = getContentResolver().query(PlayerProvider.CONTENT_URI, 
				null, null , null, null);
		
		while(c.moveToNext()){
			this.player = new Player(c.getInt(0),c.getString(1));
		}
	}
	
	private OnClickListener buttonSimpleGameListener = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	vibrator.vibrate(80);
        	
        	Intent i = new Intent(MainMenuActivity.this, SavedGameActivity.class);
        	
        	Bundle s = new Bundle();
		    s.putInt("id_player",player.getId());
		    s.putString("name_player",player.getNickname());
		    
		    i.putExtra("playerBundle",s);
		    
			
			startActivity(i);
			
			
     
        }  
	};  
   
	private OnClickListener buttonMultiModeListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			vibrator.vibrate(50);
			Intent i = new Intent(MainMenuActivity.this, MultiGameActivity.class);
			
			Bundle s = new Bundle();
		    s.putString("id_player",player.getNickname());
		    
		    i.putExtra("playerBundle",s);

			startActivity(i);
		}  
	};  
  
	private OnClickListener buttonTrophiesListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			vibrator.vibrate(50);
			Intent i = new Intent(MainMenuActivity.this, TrophiesActivity.class);
			
			startActivity(i);
		}  
	};  
 
	private OnClickListener rankListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			vibrator.vibrate(50);
			Intent i = new Intent(MainMenuActivity.this, RankActivity.class);
			
			startActivity(i);
		}  
	};  

	private OnClickListener buttonOptionListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			vibrator.vibrate(50);
			Intent i = new Intent(MainMenuActivity.this, AlterationPlayerActivity.class);
			
			startActivity(i);
			
			MainMenuActivity.this.finish();
		
		}  
	};  
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	MainMenuActivity.this.finish();
	    	
	    }
		return false;
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
	
}
