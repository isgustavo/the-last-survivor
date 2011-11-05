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

public class LoginTwitterActivity extends Activity {

	 private final String CONSUMER_KEY = "lYzErfjAnwcmCGOkcVJ5g";  
	 private final String CONSUMER_SECRET = "en5DkY1oApA5vh7YInqZj4lcOfDHT8yXjKWkluecQ";  
	 private final String CALLBACKURL = "callback://tweeter";  
	  
	  private Twitter twitter;  
	  
	  @Override  
	  public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	      
	    twitter = new TwitterFactory().getInstance();  
	    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);  
	    
	    AccessToken accessToken = loadAccessToken(); 
	    try {  
	    	if (accessToken == null) {  
	    		twitter = new TwitterFactory().getInstance();  
	    		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);  
	      
	    		RequestToken requestToken = twitter.getOAuthRequestToken(CALLBACKURL);  
	   
	    		String url = requestToken.getAuthenticationURL();  
	    		Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  
	    		it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);  
	    		startActivity(it);  
	      
	    		saveRequestData(requestToken.getToken(),requestToken.getTokenSecret());  
	     
	    	} 
	    } catch (Exception e) {  
	    	e.printStackTrace();  
	    }
	    
	    //Intent i = new Intent(LoginTwitterActivity.this, TwitterActivity.class);
	    
	    //startActivity(i);
	    
	    //finish();
	    
	    
	    
	  }
	  
	  
	  @Override  
	  protected void onResume() {  
	    super.onResume();  
	      
	    Uri uri = getIntent().getData();  
	    if (uri != null) {  
	      String oauthVerifier = uri.getQueryParameter("oauth_verifier");  
	     
	      try {  
	        RequestToken requestToken = loadRequestToken();  
	        AccessToken at = twitter.getOAuthAccessToken(requestToken, oauthVerifier);  
	    
	        saveAccessToken(at.getToken(), at.getTokenSecret());  
	    
	      } catch (TwitterException e) {  
	        LoginTwitterActivity.this.finish();
	      }  
	    }  
	  }  
	  
	  private RequestToken loadRequestToken(){  
		  SharedPreferences prefs = PreferenceManager.  
		    getDefaultSharedPreferences(this);  
		  String reqToken =   
		    prefs.getString("request_token", null);  
		  String reqTokenSecret =   
		    prefs.getString("request_tokensecret", null);  
		    
		  return new RequestToken(reqToken, reqTokenSecret);  
		}  
	  
	  
	  private AccessToken loadAccessToken() {  
		  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);  
		  String acToken = prefs.getString("access_token", null);  
		  String acTokenSecret = prefs.getString("access_tokensecret", null);  
		  
		  if (acToken != null || acTokenSecret != null){  
		    return new AccessToken(acToken, acTokenSecret);  
		  }  
		  return null;  
		} 
	  
	  private void saveRequestData(String requestToken, String requestTokenSecret){  
			  
		  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);  
		  SharedPreferences.Editor editor = prefs.edit();  
		  
		  editor.putString("request_token", requestToken);  
		  editor.putString("request_tokensecret", requestTokenSecret);  
		  
		  editor.commit();    
	  }
	  
	  private void saveAccessToken(  
			  String accessToken, String accessTokenSecret) {  
			  
			  SharedPreferences prefs =  
			    PreferenceManager.getDefaultSharedPreferences(this);  
			  
			  SharedPreferences.Editor editor = prefs.edit();  
			  
			  editor.putString(  
			    "access_token", accessToken);  
			  editor.putString(  
			    "access_tokensecret", accessTokenSecret);  
			  
			  editor.commit();  
			} 
			
}
