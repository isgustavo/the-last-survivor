package br.com.thelastsurvivor.activity;

import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.player.CadastrePlayerActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class MainMenuActivity extends Activity{
		/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_menu);
		
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
        	AlertDialog.Builder alerta = new AlertDialog.Builder(
					MainMenuActivity.this);
			alerta.setIcon(null);
			alerta.setTitle("SIMPLE GAME");
			alerta.setNeutralButton("OK", null);
			alerta.show();
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
			AlertDialog.Builder alerta = new AlertDialog.Builder(
					MainMenuActivity.this);
			alerta.setIcon(null);
			alerta.setTitle("YOU TROPHIES GAME");
			alerta.setNeutralButton("OK", null);
			alerta.show();
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
			AlertDialog.Builder alerta = new AlertDialog.Builder(
				MainMenuActivity.this);
			alerta.setIcon(null);
			alerta.setTitle("OPTION GAME");
			alerta.setNeutralButton("OK", null);
			alerta.show();
		}  
	};  
	
}
