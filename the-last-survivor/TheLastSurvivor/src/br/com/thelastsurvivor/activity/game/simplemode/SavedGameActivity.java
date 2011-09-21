package br.com.thelastsurvivor.activity.game.simplemode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.activity.player.CadastrePlayerActivity;
import br.com.thelastsurvivor.model.game.Game;
import br.com.thelastsurvivor.provider.game.GameProvider;
import br.com.thelastsurvivor.util.DateTimeUtil;

public class SavedGameActivity extends Activity {

	private List<Game> games;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		if (isThereGame()) {
			
			//Intent i = new Intent(this, MainMenuActivity.class);
			//startActivity(i);
		} else {
	
			Intent i = new Intent(this, SimpleGameActivity.class);
			startActivity(i);
		}
		
		//setContentView(R.layout.list);

	}
	
	
	public boolean isThereGame(){
		
		Cursor c = this.getContentResolver().  
        query(GameProvider.CONTENT_URI, null, null, null, null);  

		if (c.getCount() == 0) {
			return false;
		}else{
			this.games = new ArrayList<Game>();
			while(c.moveToNext()){
				this.games.add(new Game(c.getInt(0),c.getInt(1), DateTimeUtil.stringToDate(c.getString(2)),
						c.getInt(3), c.getInt(4)));
			}
		}
		return true;
 
	}


	public List<Game> getGames() {
		return games;
	}


	public void setGames(List<Game> games) {
		this.games = games;
	}
	
	
}
