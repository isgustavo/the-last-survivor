package br.com.thelastsurvivor.activity.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;

public class TwitterActivity extends Activity{

	private final String CONSUMER_KEY = "lYzErfjAnwcmCGOkcVJ5g";  
	private final String CONSUMER_SECRET = "en5DkY1oApA5vh7YInqZj4lcOfDHT8yXjKWkluecQ";  
	
	private final String CALLBACK = "callback://tweeter";  
	
	private Twitter twitter;  

	@Override  
	  public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	      
	    Bundle s = this.getIntent().getExtras().getBundle("tweetBundle");
	    String textTwitter = s.getString("tweet");
	    
	    twitter = new TwitterFactory().getInstance();  
	    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);  
	    
		try {  
		   AccessToken accessToken = loadAccessToken();  
		   if (accessToken == null) {  
			   twitter = new TwitterFactory().getInstance();  
			   twitter.setOAuthConsumer(  
	    		  CONSUMER_KEY, CONSUMER_SECRET);  
	      
			   RequestToken requestToken =   
					   twitter.getOAuthRequestToken(CALLBACK);  
	   
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
			
			finishActivity();
		}  
	    	 
	    
	   
		tweet(textTwitter);
		finishActivity();
	
	}
	
	public void tweet(String tweet){
		try {  
   		    if (loadAccessToken() != null){  
   		      twitter.updateStatus(tweet); 
   		      Toast.makeText(this, this.getString(R.string.twitter_posted), Toast.LENGTH_LONG).show();  
   		    } 
   		 } catch (TwitterException e) {  
   			finishActivity();
   		 }  
	}
	
	private void saveRequestData(  
		String requestToken, String requestTokenSecret){  
	  
		SharedPreferences prefs = PreferenceManager.  
				getDefaultSharedPreferences(this);  
		SharedPreferences.Editor editor = prefs.edit();  
	  
		editor.putString("request_token", requestToken);  
		editor.putString("request_tokensecret", requestTokenSecret);  
	  
		editor.commit();    
	}  


	@Override  
	protected void onResume() {  
		super.onResume();  
	    
		Uri uri = getIntent().getData();  
		if (uri != null) {  
			String oauthVerifier = uri.getQueryParameter("oauth_verifier");  
	   
			try {  
				RequestToken requestToken = loadRequestToken();  
				AccessToken at = twitter.getOAuthAccessToken(  
						requestToken, oauthVerifier);  
	  
				saveAccessToken(at.getToken(), at.getTokenSecret());  
	  
			} catch (TwitterException e) {  
				e.printStackTrace();  
				//showToast(e.getMessage());  
			}  
		}  
	}  
	
	private RequestToken loadRequestToken(){  
		SharedPreferences prefs = PreferenceManager.  
		    getDefaultSharedPreferences(this);  
		String reqToken =  prefs.getString("request_token", null);  
		String reqTokenSecret = prefs.getString("request_tokensecret", null);  
		    
		return new RequestToken(reqToken, reqTokenSecret);  
	}  
	
	private AccessToken loadAccessToken() {  
		SharedPreferences prefs = PreferenceManager.  
		    getDefaultSharedPreferences(this);  
		String acToken = prefs.getString("access_token", null);  
		String acTokenSecret = prefs.getString("access_tokensecret", null);  
		  
		if (acToken != null || acTokenSecret != null){  
		    return new AccessToken(acToken, acTokenSecret);  
		}  
		
		return null;  
	}  
	
	private void saveAccessToken(  
		String accessToken, String accessTokenSecret) {  
	  
		SharedPreferences prefs =  
				PreferenceManager.getDefaultSharedPreferences(this);  
	  
		SharedPreferences.Editor editor = prefs.edit();  
	  
		editor.putString("access_token", accessToken);  
		editor.putString("access_tokensecret", accessTokenSecret);  
	  
		editor.commit();  
	}
	
	private void finishActivity(){
		
		Intent i = new Intent(TwitterActivity.this, MainMenuActivity.class);
		
		startActivity(i);
		
		TwitterActivity.this.finish();
	}
}

