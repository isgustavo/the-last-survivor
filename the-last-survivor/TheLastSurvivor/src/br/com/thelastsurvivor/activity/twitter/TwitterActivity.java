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
import android.widget.EditText;
import android.widget.Toast;

public class TwitterActivity extends Activity{

	private final String consumerKey = "lYzErfjAnwcmCGOkcVJ5g";  
	private final String consumerSecret = "en5DkY1oApA5vh7YInqZj4lcOfDHT8yXjKWkluecQ";  
	
	private final String CALLBACKURL = "callback://tweeter";  
	
	private Twitter twitter; 
	private static String textTwitter;
	@Override  
	  public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	    //setContentView(R.layout.main);  
	      
	    twitter = new TwitterFactory().getInstance();  
	    twitter.setOAuthConsumer(consumerKey, consumerSecret);  
	    
	    AccessToken accessToken = loadAccessToken(); 
	    twitter.setOAuthAccessToken(accessToken);  
	    
	    
	    Bundle s = this.getIntent().getExtras().getBundle("tweetBundle");
   	    textTwitter = s.getString("tweet");   
   	 
	  //  Button login = (Button)findViewById(R.id.button1);
	   // login.setOnClickListener(new OnClickListener() {  
	  //  	 public void onClick(View v) {  
	    		//  try {  
	    		//    AccessToken accessToken = loadAccessToken();  
	    		//    if (accessToken == null) {  
	    		//      twitter = new TwitterFactory().getInstance();  
	    		//      twitter.setOAuthConsumer(  
	    		//        consumerKey, consumerSecret);  
	    		//      
	    		//      RequestToken requestToken =   
	    		//        twitter.getOAuthRequestToken(CALLBACKURL);  
	    		//   
	    		//      String url = requestToken.getAuthenticationURL();  
	    		//      Intent it = new Intent(  
	    		//        Intent.ACTION_VIEW, Uri.parse(url));  
	    		//      it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);  
	    		//      startActivity(it);  
	    		      
	    		//      saveRequestData(requestToken.getToken(),   
	    		//        requestToken.getTokenSecret());  
	    		     
	    		//    } else {  
	    		//      twitter.setOAuthAccessToken(accessToken);  
	    		//    }  
	    		//  } catch (Exception e) {  
	    		//    e.printStackTrace();  
	    		//    showToast(e.getMessage());  
	    		//  }  
	    	//  }//}//);
	    
	    //Button tweet = (Button)findViewById(R.id.button3);
	   // tweet.setOnClickListener(new OnClickListener() {  
	    	 //public void onClick(View v) {  
	   		  try {  
	   		    if (loadAccessToken() != null){  
	   		      //EditText edt =   
	   		       // (EditText) findViewById(R.id.editText1);  
	   		     // String tweet = edt.getText().toString();  
	   		  
	   		      twitter.updateStatus(textTwitter);  
	   		      showToast("Status atualizado com sucesso!");  
	   		  
	   		    } else {  
	   		      showToast("Faça o login antes de Twittar");  
	   		    }  
	   		  } catch (TwitterException e) {  
	   		      e.printStackTrace();  
	   		      showToast(e.getMessage());  
	   		  }  
	   		//}  
	    	//});
	    
	    
	    finish();
	    //Button off = (Button)findViewById(R.id.button2);
	   // off.setOnClickListener(new OnClickListener() {  
	    //	 public void onClick(View v) {  
		//		  saveAccessToken(null, null);  
	   // 	 }});  
	    }
	  
	  
	 
		  
		  @Override  
		  protected void onResume() {  
		    super.onResume();  
		      
		    Uri uri = getIntent().getData();  
		    if (uri != null) {  
		      String oauthVerifier =   
		        uri.getQueryParameter("oauth_verifier");  
		     
		      try {  
		        RequestToken requestToken = loadRequestToken();  
		        AccessToken at = twitter.getOAuthAccessToken(  
		          requestToken, oauthVerifier);  
		    
		        saveAccessToken(  
		          at.getToken(), at.getTokenSecret());  
		    
		      } catch (TwitterException e) {  
		        e.printStackTrace();  
		        showToast(e.getMessage());  
		      }  
		    }  
		  }  
		  
		 
		  
		  private void showToast(String s){  
			  Toast.makeText(this, s, Toast.LENGTH_LONG).show();  
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
/*	@Override  
	  public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	   
	    if(textTwitter == null){
	    	 Bundle s = this.getIntent().getExtras().getBundle("tweetBundle");
	    	 textTwitter = s.getString("tweet");   
	    }
	   
	    
	   //Log.d("TWITTERACTIVITY","..");
		//Log.d("..",".."+textTwitter);
	    
	  
	    twitter = new TwitterFactory().getInstance();  
	    twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);  
	    
	    AccessToken accessToken = loadAccessToken();  
	    
	    twitter.setOAuthAccessToken(accessToken);  
	//    if(loadAccessToken() == null){
	    /*try {  
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
		    e.printStackTrace();  
		    showToast(e.getMessage());  
		  }  
	  		    
		tweet(textTwitter);
		
		finish();
		//finishActivity();
	
	}
	
	 private void showToast(String s){  
		  Toast.makeText(this, s, Toast.LENGTH_LONG).show();  
		}  
	
	public void tweet(String tweet){
		
		try {  
   		    if (loadAccessToken() != null){  
   		      twitter.updateStatus(tweet);  
   		      showToast("Status atualizado com sucesso!");  
   		  
   		    } else {  
   		      showToast("Faça o login antes de Twittar");  
   		    }  
   		  } catch (TwitterException e) {  
   		      e.printStackTrace();  
   		      showToast(e.getMessage());  
   		  }  
   		}  
    	
    	

	 @Override  
	  protected void onResume() {  
	    super.onResume();  
	      
	    Uri uri = getIntent().getData();  
	    if (uri != null) {  
	      String oauthVerifier =   
	        uri.getQueryParameter("oauth_verifier");  
	     
	      try {  
	        RequestToken requestToken = loadRequestToken();  
	        AccessToken at = twitter.getOAuthAccessToken(  
	          requestToken, oauthVerifier);  
	    
	        saveAccessToken(  
	          at.getToken(), at.getTokenSecret());  
	    
	      } catch (TwitterException e) {  
	        e.printStackTrace();  
	        showToast(e.getMessage());  
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
*/
}

