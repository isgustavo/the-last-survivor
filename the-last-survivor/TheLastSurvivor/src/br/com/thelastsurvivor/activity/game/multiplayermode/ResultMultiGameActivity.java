package br.com.thelastsurvivor.activity.game.multiplayermode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.activity.twitter.TwitterActivity;
import br.com.thelastsurvivor.util.FT2FontTextView;
import br.com.thelastsurvivor.util.MyAudioPlayer;

public class ResultMultiGameActivity extends Activity {
	
	private MyAudioPlayer audioBackgraund;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		audioBackgraund = new MyAudioPlayer(ResultMultiGameActivity.this, R.raw.sol);
		audioBackgraund.start();

		Bundle s = this.getIntent().getExtras().getBundle("gameBundle");
		
		Integer teamRed = s.getInt("teamRed");
		Integer teamBlue = s.getInt("teamBlue");
		Integer teamYellow = s.getInt("teamYellow");
		Integer teamGreen = s.getInt("teamGreen");
		
		String id_player = s.getString("id_player");
		Integer id_color = s.getInt("color_player");
		
		String otherPlayer = "";
		Integer colorPlayer = 0; 
		
		String otherPlayer2 = "";
		Integer colorPlayer2 = 0; 
		
		String otherPlayer3 = "";
		Integer colorPlayer3 = 0; 
		
		if(s.getString("other_player_1") == ""){
			otherPlayer = s.getString("other_player_1");
			colorPlayer = s.getInt("other_player_1");
		}
		
		if(s.getString("other_player_2") == ""){
			otherPlayer2 = s.getString("other_player_2");
			colorPlayer2 = s.getInt("other_player_2");
		}
		
		if(s.getString("other_player_3") == ""){
			otherPlayer3 = s.getString("other_player_3");
			colorPlayer3 = s.getInt("other_player_3");
		}
		
		setContentView(R.layout.result_multi_game_view);
		
		final FT2FontTextView red = (FT2FontTextView)findViewById(R.id.red);
		red.setText(teamRed+"");
		final FT2FontTextView blue = (FT2FontTextView)findViewById(R.id.blue);
		blue.setText(teamBlue+"");
		final FT2FontTextView yellow = (FT2FontTextView)findViewById(R.id.yellow);
		yellow.setText(teamYellow+"");
		final FT2FontTextView green = (FT2FontTextView)findViewById(R.id.green);
		green.setText(teamGreen+"");
		
		String twitterMessage = ""+id_player+" ";
		String complementMessage = "";
		
		boolean coop = false;
		boolean versus = false;
		
		if(id_color == colorPlayer){
			twitterMessage += "played with "+otherPlayer;
			coop = true;
		}else{
			complementMessage += " "+otherPlayer;
			versus = true;
		}
		
		if(id_color == colorPlayer2){
			if(coop){
				twitterMessage += "and "+otherPlayer2;
			}
			twitterMessage += "played with "+otherPlayer2;
		}else if(versus){
			complementMessage += " ,"+otherPlayer2;
		}else{
			complementMessage += " "+otherPlayer2;
		}
		
		if(id_color == colorPlayer3){
			if(coop){
				twitterMessage += "and "+otherPlayer3;
			}
			twitterMessage += "played with "+otherPlayer3;
		}else if(versus){
			complementMessage += " and"+otherPlayer3;
		}else{
			complementMessage += " "+otherPlayer3;
		}
		
		twitterMessage += " vs "+complementMessage;
		
		twitterMessage += "played a multiplayer match and won. #TheLastSurivivorUpTo4Players";
		
		
		
		final FT2FontTextView textTwitteView = (FT2FontTextView)findViewById(R.id.text_twitte);
		textTwitteView.setText(twitterMessage);
		
	    final String tweet = twitterMessage;
		
		Button twitte = (Button)findViewById(R.id.button_twitte);
		twitte.setOnClickListener(new OnClickListener() {    
			  public void onClick(View v) {  
				  Intent i = new Intent(ResultMultiGameActivity.this, TwitterActivity.class);
				  
				  Bundle s = new Bundle();
				  s.putString("tweet", tweet);
				    
				  i.putExtra("tweetBundle",s);
				  
				  startActivity(i);
				  
				  ResultMultiGameActivity.this.finish();
			  }  
		});  
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	  
	    	  Intent i = new Intent(ResultMultiGameActivity.this, MainMenuActivity.class);
			  startActivity(i);
			  
			  ResultMultiGameActivity.this.finish();
	    	
	    	
	    }
		return true;
	}	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		this.audioBackgraund.fechar();
	}
	
}
