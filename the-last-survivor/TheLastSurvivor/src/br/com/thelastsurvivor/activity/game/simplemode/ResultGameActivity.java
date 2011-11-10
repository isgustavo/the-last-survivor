package br.com.thelastsurvivor.activity.game.simplemode;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
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
	
	private final String consumerKey = "lYzErfjAnwcmCGOkcVJ5g";  
	private final String consumerSecret = "en5DkY1oApA5vh7YInqZj4lcOfDHT8yXjKWkluecQ";  
	
	private final String CALLBACKURL = "callback://tweeter";  
	
	private Twitter twitter; 
	private static String textTwitter;
	
	
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
				+ " #theLastSurvivorUpTo4PlayersBeta"; 
		
		
		final FT2FontTextView textTwitteView = (FT2FontTextView)findViewById(R.id.text_twitte);
		textTwitteView.setText(textTwitte);
		
	    final String tweet = textTwitte;
		
		Button twitte = (Button)findViewById(R.id.button_twitte);
		twitte.setOnClickListener(new OnClickListener() {    
			  public void onClick(View v) {  
				  
				  vibrator.vibrate(80);
				  
				  twitter = new TwitterFactory().getInstance();  
				  twitter.setOAuthConsumer(consumerKey, consumerSecret);  
				    
				  try {  
		    		    AccessToken accessToken = loadAccessToken();  
		    		    if (accessToken == null) {  
		    		      twitter = new TwitterFactory().getInstance();  
		    		      twitter.setOAuthConsumer(  
		    		        consumerKey, consumerSecret);  
		    		      
		    		      RequestToken requestToken =   
		    		        twitter.getOAuthRequestToken(CALLBACKURL);  
		    		   
		    		      String url = requestToken.getAuthenticationURL();  
		    		      Intent it = new Intent(  
		    		        Intent.ACTION_VIEW, Uri.parse(url));  
		    		      it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);  
		    		      startActivity(it);  
		    		      
		    		      saveRequestData(requestToken.getToken(),   
		    		        requestToken.getTokenSecret());  
		    		     
		    		    } else {  
		    		      twitter.setOAuthAccessToken(accessToken);  
		    		    }  
		    		  } catch (Exception e) {  
		    		    e.printStackTrace();  
		    		   
		    		  }  
				  
				 // Intent i = new Intent(ResultGameActivity.this, LoginTwitterActivity.class);
				  
				  //startActivityForResult(i, 0);
				  
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
	
	private AccessToken loadAccessToken() {  
		  SharedPreferences prefs = PreferenceManager.  
		    getDefaultSharedPreferences(this);  
		  String acToken =   
		    prefs.getString("access_token", null);  
		  String acTokenSecret =   
		    prefs.getString("access_tokensecret", null);  
		  
		  if (acToken != null || acTokenSecret != null){  
		    return new AccessToken(acToken, acTokenSecret);  
		  }  
		  return null;  
		}  
	
	private void saveRequestData(  
			  String requestToken, String requestTokenSecret){  
			  
			  SharedPreferences prefs = PreferenceManager.  
			    getDefaultSharedPreferences(this);  
			  SharedPreferences.Editor editor = prefs.edit();  
			  
			  editor.putString(  
			    "request_token", requestToken);  
			  editor.putString(  
			    "request_tokensecret", requestTokenSecret);  
			  
			  editor.commit();    
			}  
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		this.audioBackgraund.fechar();
	}
	
}
