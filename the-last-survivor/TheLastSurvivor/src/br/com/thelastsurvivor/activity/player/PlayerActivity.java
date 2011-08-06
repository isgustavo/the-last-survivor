package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class PlayerActivity extends Activity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Log.d(" PlayerActivity", "01");
		
		//Log.d(" PlayerActivity", "02");
		if (isTherePlayer()) {
		//	Log.d(" PlayerActivity", "03");
			//Intent i = new Intent(this, SavedGamesActivity.class);
			//startActivity(i);
			Intent i = new Intent(this, MainMenuActivity.class);
			startActivity(i);
		} else {
		//	Log.d(" PlayerActivity", "04");
			Intent i = new Intent(this, CadastrePlayerActivity.class);
			startActivity(i);
		}

		// setContentView(R.layout.main);
	}
	
	
	
	private boolean isTherePlayer(){
		
		Cursor c = this.getContentResolver().  
                query(PlayerProvider.CONTENT_URI, null, null, null, null);  

		if (c == null) {
			return false;
		}
		return true;

	}

}
