package br.com.thelastsurvivor.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.SimpleGameActivity;
import br.com.thelastsurvivor.activity.player.AlterationPlayerActivity;
import br.com.thelastsurvivor.activity.trophies.TrophiesActivity;
import br.com.thelastsurvivor.view.BackgroundView;

public class MainMenuActivity extends Activity{
		/** Called when the activity is first created. */
	
	private BackgroundView view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_menu_view);
		
		this.view = (BackgroundView)findViewById(R.id.surfaceView);
		
	
		Button buttonSimpleGame = (Button)findViewById(R.id.buttonSimpleMode);  
		buttonSimpleGame.setOnClickListener(buttonSimpleGameListener);  
		
		Button buttonMultiMode = (Button)findViewById(R.id.buttonMultiMode);  
		buttonMultiMode.setOnClickListener(buttonMultiModeListener); 
		
		Button buttonTrophies = (Button)findViewById(R.id.buttonTrophies);  
		buttonTrophies.setOnClickListener(buttonTrophiesListener); 
		
		Button rankTrophies = (Button)findViewById(R.id.rankTrophies);  
		rankTrophies.setOnClickListener(rankTrophiesListener); 
		
		Button buttonOption = (Button)findViewById(R.id.buttonOption);  
		buttonOption.setOnClickListener(buttonOptionListener); 
		
		
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
			AlertDialog.Builder alerta = new AlertDialog.Builder(
					MainMenuActivity.this);
			alerta.setIcon(null);
			alerta.setTitle("MULTIPLAYER GAME");
			alerta.setNeutralButton("OK", null);
			alerta.show();
		}  
	};  
  
	private OnClickListener buttonTrophiesListener = new OnClickListener() {  
		public void onClick(View v) {  
			/*AlertDialog.Builder alerta = new AlertDialog.Builder(
					MainMenuActivity.this);
			alerta.setIcon(null);
			alerta.setTitle("YOU TROPHIES GAME");
			alerta.setNeutralButton("OK", null);
			alerta.show();*/
			
			Intent i = new Intent(MainMenuActivity.this, TrophiesActivity.class);
	        //	Intent i = new Intent(MainMenuActivity.this, Principal.class);
	        	
				startActivity(i);
		}  
	};  
 
	private OnClickListener rankTrophiesListener = new OnClickListener() {  
		public void onClick(View v) {  
			AlertDialog.Builder alerta = new AlertDialog.Builder(
					MainMenuActivity.this);
			alerta.setIcon(null);
			alerta.setTitle("YOU RACK GAME");
			alerta.setNeutralButton("OK", null);
			alerta.show();
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
