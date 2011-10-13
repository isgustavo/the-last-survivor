package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.activity.TheLastSurvivorActivity;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class PlayerActivity extends Activity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (isTherePlayer()) {
	
			Intent i = new Intent(this, MainMenuActivity.class);
			startActivity(i);
		} else {
	
			Intent i = new Intent(this, CadastrePlayerActivity.class);
			startActivity(i);
		}
		
		PlayerActivity.this.finish();
	}
	
	
	
	private boolean isTherePlayer(){

		Cursor c = this.getContentResolver().  
                query(PlayerProvider.CONTENT_URI, null, null, null, null);  

		if (c.getCount() == 0) {
			return false;
		}
		return true;
		 
	}

}
