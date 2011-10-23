package br.com.thelastsurvivor.activity.game.simplemode;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.model.game.Asteroid;
import br.com.thelastsurvivor.model.game.Game;
import br.com.thelastsurvivor.model.game.PowerUp;
import br.com.thelastsurvivor.model.game.Shoot;
import br.com.thelastsurvivor.model.game.Spacecraft;
import br.com.thelastsurvivor.provider.game.AsteroidProvider;
import br.com.thelastsurvivor.provider.game.GameProvider;
import br.com.thelastsurvivor.provider.game.PowerUpProvider;
import br.com.thelastsurvivor.provider.game.ShootProvider;
import br.com.thelastsurvivor.provider.game.SpacecraftProvider;
import br.com.thelastsurvivor.util.DateTimeUtil;
import br.com.thelastsurvivor.util.Vector2D;

public class SavedGameActivity extends ListActivity {
	
	//public static final int EXIT_GAME = 0;
	
	private ListAdapter adapter;
	
	private Integer idPlayer;
	private Game game;
	private List<Game> games;
	ListView listView;  

	private Context context;
	private Dialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle s = this.getIntent().getExtras().getBundle("playerBundle");
		this.idPlayer = s.getInt("id_player");
		
		context = SavedGameActivity.this;
		
		games = new ArrayList<Game>();
		
		if (isThereGame()) {
			
			setContentView(R.layout.saved_game_view);  
			
			
			setListAdapter(new ListGameAdapter(games, this));
			
			Button newGame = (Button)findViewById(R.id.button_new_game);
			newGame.setOnClickListener(new OnClickListener() {  
				public void onClick(View v2) {  
					
					Intent i = new Intent(SavedGameActivity.this, SimpleGameActivity.class);
					
					Bundle b = new Bundle();
					b.putInt("id_player", SavedGameActivity.this.idPlayer);
				   
					i.putExtra("startGame",b);
				    
					startActivity(i);
					
					SavedGameActivity.this.finish();
					
				}
			});    
	
		} else{
			
			Intent i = new Intent(SavedGameActivity.this, SimpleGameActivity.class);
			
			Bundle b = new Bundle();
			b.putInt("id_player", this.idPlayer);
		    
			i.putExtra("startGame",b);
		    
			startActivity(i);
			
			SavedGameActivity.this.finish();
		}
		
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		
		optionSavedGame(v);
		
		//Log.d("ID",".."+id);
		//Log.d("ID",".."+position);
		
		//Log.d("VIEWID",".."+((TextView)v.findViewById(R.id.game_saved_id)).getText());
		
		
		
			//Intent i = new Intent(SavedGameActivity.this, MainMenuActivity.class);
			
			
			//startActivity(i);
			
			//SavedGameActivity.this.finish();
		
		
	}

	
	public void optionSavedGame(final View v){
		
		
		
		dialog = new Dialog(this, R.style.PauseGameDialogTheme){
    		
    		public boolean onKeyDown(int keyCode, KeyEvent event){
    				return false;
    		}

    	};
    	
		dialog.setContentView(R.layout.select_game_view);
		
		Button backList = (Button)dialog.findViewById(R.id.button_back);
		backList.setOnClickListener( new OnClickListener() {  
			public void onClick(View v) {  
				
				dialog.cancel();
				dialog.dismiss();
				
			}
		});  
		
		Button openGame = (Button)dialog.findViewById(R.id.button_open_game);
		openGame.setOnClickListener(new OnClickListener() {  
			public void onClick(View v2) {  
			    game = openGame(Integer.parseInt(((TextView)v.findViewById(R.id.game_saved_id)).getText().toString()));
				
			    dialog.cancel();
				dialog.dismiss();
				
			    restartGame();
			    
			}
		}); 
		
		Button removeGame = (Button)dialog.findViewById(R.id.button_remove);
		removeGame.setOnClickListener(new OnClickListener() {  
			public void onClick(View v2) {  
				
				deleteGame(Integer.parseInt(((TextView)v.findViewById(R.id.game_saved_id)).getText().toString()));
				
				dialog.cancel();
				dialog.dismiss();
				
				removeGameList(Integer.parseInt(((TextView)v.findViewById(R.id.game_saved_id)).getText().toString()));
				
				setListAdapter(new ListGameAdapter(games, SavedGameActivity.this));
				
			}
		});    
		
		dialog.show();
    	
	}
	
	private void removeGameList(Integer id){
		
		List<Game> gameList = new ArrayList<Game>();
		
		for (Game game : this.games) {
			if(!game.getId().equals(id)){
				gameList.add(game);
			}
		}
		
		games.clear();
		games.addAll(gameList);
		
		
	}
		
	

	private Game openGame(Integer id){
		
		Game game = loadGame(id);
		Spacecraft spacecraft = loadSpacecraft(id);
		spacecraft.setShoots(loadShoots(spacecraft.getId()));
	
		return new Game(game.getId(), game.getDate(), game.getRunTime(), game.getPowerUp(),
				spacecraft, loadAsteroid(id), loadPowerUp(id));

	}
	
	private void restartGame(){
		Intent i = new Intent(SavedGameActivity.this, SimpleGameActivity.class);
		
		Bundle b = new Bundle();
		b.putInt("id_player", this.idPlayer);
		b.putSerializable("game", this.game);
		
		i.putExtra("startGame",b);
	    
		startActivity(i);
		
		SavedGameActivity.this.finish();
	}
	
	private boolean deleteGame(Integer id){
		
		getContentResolver().delete(GameProvider.CONTENT_URI, GameProvider.ID +" = "+ id, null);
		
		Spacecraft spacecraft = loadSpacecraft(id);
		
		getContentResolver().delete(SpacecraftProvider.CONTENT_URI, SpacecraftProvider.ID_GAME +" = "+ id, null);
		
		getContentResolver().delete(ShootProvider.CONTENT_URI, ShootProvider.ID_SPACECRAFT +" = "+ spacecraft.getId(), null);
		
		getContentResolver().delete(AsteroidProvider.CONTENT_URI, AsteroidProvider.ID_GAME +" = "+ id, null);
		
		getContentResolver().delete(PowerUpProvider.CONTENT_URI, PowerUpProvider.ID_GAME +" = "+ id, null);
		
		return true;
		
	}
	
	public boolean isThereGame(){
		
		
		this.games.addAll(loadGames());
		
		if(this.games.size() == 0){
			return false;
		}
		return true;
	}

	public List<Game> loadGames(){
		List<Game> games = new ArrayList<Game>();
		
		Cursor c = this.getContentResolver().  
		query(GameProvider.CONTENT_URI, null, null, null, null);  

		if (c.getCount() == 0) {
			return new ArrayList<Game>();
		}else{
			while(c.moveToNext()){
			games.add(new Game(c.getInt(0),c.getInt(1), DateTimeUtil.stringToDate(c.getString(2)),
					c.getLong(3), c.getInt(4), loadSpacecraft(c.getInt(0))));
			}
		}
		
		return games;
	}
	
	public Game loadGame(Integer id){
		
		for(Game game : this.games){
			if(game.getId().equals(id)){
				return game;
			}
		}
		
		return null;
	}
	
	public Spacecraft loadSpacecraft(Integer id){
		
		Cursor c = this.getContentResolver().  
		query(SpacecraftProvider.CONTENT_URI, null, SpacecraftProvider.ID_GAME+ " = "+ id , null, null); 
		
		while(c.moveToNext()){
			return new Spacecraft(c.getInt(1), new Vector2D(c.getInt(2),c.getInt(3)), 
					c.getDouble(4), c.getInt(5), c.getInt(6));
		}
		
		return null;
		
	}
	
	
	public List<Shoot> loadShoots(Integer id){
		
		List<Shoot> shoots = new ArrayList<Shoot>();
		
		Cursor c = this.getContentResolver().  
		query(ShootProvider.CONTENT_URI, null, ShootProvider.ID_SPACECRAFT+ " = "+ id , null, null); 
		
		while(c.moveToNext()){
			shoots.add(new Shoot(new Vector2D(c.getInt(2),c.getInt(3)),c.getDouble(4)));
		}
		
		return shoots;
		
	}
	


	public List<Asteroid> loadAsteroid(Integer id){
		
		List<Asteroid> asteroid = new ArrayList<Asteroid>();
		
		Cursor c = this.getContentResolver().  
		query(AsteroidProvider.CONTENT_URI, null, AsteroidProvider.ID_GAME+ " = "+ id , null, null); 
		
		while(c.moveToNext()){
			asteroid.add(new Asteroid(new Vector2D(c.getInt(2),c.getInt(3)),c.getDouble(4),
					c.getInt(5),c.getInt(6),c.getInt(7)));
		}
		
		return asteroid;
		
	}

	public List<PowerUp> loadPowerUp(Integer id){
		
		List<PowerUp> powerUp = new ArrayList<PowerUp>();
		
		Cursor c = this.getContentResolver().  
		query(PowerUpProvider.CONTENT_URI, null, PowerUpProvider.ID_GAME+ " = "+ id , null, null); 
		
		while(c.moveToNext()){
			powerUp.add(new PowerUp(new Vector2D(c.getInt(2),c.getInt(3)),
					c.getInt(4)));
		}
		
		return powerUp;
		
	}

	public List<Game> getGames() {
		return games;
	}


	public void setGames(List<Game> games) {
		this.games = games;
	}
	

}
