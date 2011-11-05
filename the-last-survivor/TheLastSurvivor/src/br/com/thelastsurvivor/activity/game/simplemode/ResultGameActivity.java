package br.com.thelastsurvivor.activity.game.simplemode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.activity.twitter.LoginTwitterActivity;
import br.com.thelastsurvivor.activity.twitter.TwitterActivity;
import br.com.thelastsurvivor.util.FT2FontTextView;
import br.com.thelastsurvivor.util.MyAudioPlayer;

public class ResultGameActivity extends Activity {
	
	private MyAudioPlayer audioBackgraund;
	private Vibrator vibrator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		audioBackgraund = new MyAudioPlayer(ResultGameActivity.this, R.raw.sol);
		audioBackgraund.start();

		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
		
		Bundle s = this.getIntent().getExtras().getBundle("gameBundle");
		
		String name = s.getString("name_player");
		Integer score = s.getInt("points");	
		Long time = s.getLong("time");	

		setContentView(R.layout.result_game_view);
		
		final FT2FontTextView scoreGame = (FT2FontTextView)findViewById(R.id.result_score);
		scoreGame.setText(score+"");

	    String textTwitte = name+" "+this.getString(R.string.twitter_survived)+" "; 
		
		if((time/60000) == 0){
			Integer timeGame = (int) (time/1000);
			textTwitte += timeGame+" "+this.getString(R.string.twitter_seconds)+" ";

		}else{
			textTwitte += this.getString(R.string.twitter_more)+" "+
					Integer.parseInt((time/60000)+"")+" "+this.getString(R.string.twitter_minutes)+" ";
		}
		
		
		textTwitte += this.getString(R.string.twitter_score)+" "+score+" "+this.getString(R.string.twitter_points)
				+ " #theLastSurvivorUpTo4Players"; 
		
		
		final FT2FontTextView textTwitteView = (FT2FontTextView)findViewById(R.id.text_twitte);
		textTwitteView.setText(textTwitte);
		
	    final String tweet = textTwitte;
		
		Button twitte = (Button)findViewById(R.id.button_twitte);
		twitte.setOnClickListener(new OnClickListener() {    
			  public void onClick(View v) {  
				  
				  vibrator.vibrate(80);
				  
				  Intent i = new Intent(ResultGameActivity.this, LoginTwitterActivity.class);
				  
				  startActivityForResult(i, 0);
				  
				  Intent i2 = new Intent(ResultGameActivity.this, TwitterActivity.class);
				  Bundle s = new Bundle();
				  s.putString("tweet", tweet);
				    
				  i2.putExtra("tweetBundle",s);
				  
				  startActivity(i2);
				  
				  ResultGameActivity.this.finish();
			  }  
		});  
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	  
	    	  Intent i = new Intent(ResultGameActivity.this, MainMenuActivity.class);
			  startActivity(i);
			  
			  ResultGameActivity.this.finish();
	    	
	    	
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
