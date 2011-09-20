package br.com.thelastsurvivor.activity.game.simplemode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.activity.player.CadastrePlayerActivity;
import br.com.thelastsurvivor.model.game.Asteroid;
import br.com.thelastsurvivor.model.game.Game;
import br.com.thelastsurvivor.model.game.Spacecraft;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.game.GameProvider;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class SavedGamesActivity extends Activity {

	private List<Game> games;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		if (isThereGame()) {
			
			Intent i = new Intent(this, MainMenuActivity.class);
			startActivity(i);
		} else {
	
			Intent i = new Intent(this, CadastrePlayerActivity.class);
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
			public Game(Integer id, Integer player, Date date, Integer runTime,
					Integer points, Spacecraft spacecraft, List<Asteroid> asteroids) {
				this.id = id;
				this.player = player;
				this.date = date;
				this.runTime = runTime;
				this.points = points;
				this.spacecraft = spacecraft;
				this.asteroids = asteroids;
			}
			this.games = new ArrayList<Game>();
			while(c.moveToNext()){
				this.games.add(c.getInt(0),c.getInt(1), c.getInt(2));
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
