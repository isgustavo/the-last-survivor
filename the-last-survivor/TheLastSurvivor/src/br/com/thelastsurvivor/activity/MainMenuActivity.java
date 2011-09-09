package br.com.thelastsurvivor.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.MultiGameActivity;
import br.com.thelastsurvivor.activity.game.SimpleGameActivity;
import br.com.thelastsurvivor.activity.player.AlterationPlayerActivity;
import br.com.thelastsurvivor.activity.rank.RankActivity;
import br.com.thelastsurvivor.activity.trophies.TrophiesActivity;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.util.FT2FontTextView;
import br.com.thelastsurvivor.view.BackgroundView;

public class MainMenuActivity extends Activity{
		/** Called when the activity is first created. */
	
	private Player player;
	private BackgroundView view;
	
	private FT2FontTextView nickname;
	private FT2FontTextView lgTwitter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		
		setContentView(R.layout.main_menu_view);
		
		init();
		
		//this.view = (BackgroundView)findViewById(R.id.surfaceView);
		
	
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
	
	public void init(){
		
		loadPlayer();
		
		this.nickname = (FT2FontTextView)findViewById(R.id.user_name);
		this.nickname.setText(this.player.getIdentifier());
		
		this.lgTwitter = (FT2FontTextView)findViewById(R.id.lg_twitter);
		this.lgTwitter.setText(this.player.getLgTwitter());
		
	}
	
	public void loadPlayer() {

		Cursor c = getContentResolver().query(PlayerProvider.CONTENT_URI, 
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
	
	private OnClickListener buttonSimpleGameListener = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	Intent i = new Intent(MainMenuActivity.this, SimpleGameActivity.class);
        //	Intent i = new Intent(MainMenuActivity.this, Principal.class);
        	
			startActivity(i);
     
        }  
	};  
   
	private OnClickListener buttonMultiModeListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			Intent i = new Intent(MainMenuActivity.this, MultiGameActivity.class);
			startActivity(i);
		}  
	};  
  
	private OnClickListener buttonTrophiesListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			Intent i = new Intent(MainMenuActivity.this, TrophiesActivity.class);
			
			startActivity(i);
		}  
	};  
 
	private OnClickListener rankListener = new OnClickListener() {  
		public void onClick(View v) {  
			Intent i = new Intent(MainMenuActivity.this, RankActivity.class);
			
			startActivity(i);
		}  
	};  

	private OnClickListener buttonOptionListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			Intent i = new Intent(MainMenuActivity.this, AlterationPlayerActivity.class);
			
			startActivity(i);
		}  
	};  
	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		
		//this.view.getViewThread().start();
	}
	
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//this.view.getViewThread().destroy();
	}
	
	
	
	
	@Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	 	
    }
}
