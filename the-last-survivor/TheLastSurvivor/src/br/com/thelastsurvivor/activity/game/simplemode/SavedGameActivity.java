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
import br.com.thelastsurvivor.model.game.Game;
import br.com.thelastsurvivor.model.game.Spacecraft;
import br.com.thelastsurvivor.provider.game.GameProvider;
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
		
	
		
		if (isThereGame()) {
			setListAdapter(new ListGameAdapter(games, this));
			
	
		} else{
			
			Intent i = new Intent(SavedGameActivity.this, SimpleGameActivity.class);
			
			Bundle b = new Bundle();
			b.putInt("id_player", this.idPlayer);
		    
			i.putExtra("idPlayerGame",b);
		    
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
		backList.setOnClickListener(buttonBackListener);  
		
		Button openGame = (Button)dialog.findViewById(R.id.button_open_game);
		openGame.setOnClickListener(new OnClickListener() {  
			public void onClick(View v2) {  
			    game = openGame(Integer.parseInt(((TextView)v.findViewById(R.id.game_saved_id)).getText().toString()));
				
			}
		}); 
		
		Button removeGame = (Button)dialog.findViewById(R.id.button_remove);
		removeGame.setOnClickListener(buttonExitListener);  
		
		dialog.show();
    	
	}
	
	private OnClickListener buttonBackListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			dialog.cancel();
			dialog.dismiss();
			
		}
	};  
	
	
	private OnClickListener buttonExitListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			
		}
	};  
	
	public boolean isThereGame(){
		
		Cursor c = this.getContentResolver().  
        query(GameProvider.CONTENT_URI, null, null, null, null);  

		if (c.getCount() == 0) {
			return false;
		}else{
			this.games = new ArrayList<Game>();
			while(c.moveToNext()){
				this.games.add(new Game(c.getInt(0),c.getInt(1), DateTimeUtil.stringToDate(c.getString(2)),
						c.getLong(3), loadSpacecraft(c.getInt(0))));
			}
		}
		return true;
	}

	public Spacecraft loadSpacecraft(Integer id){
		
		Cursor c = this.getContentResolver().  
		query(SpacecraftProvider.CONTENT_URI, null, SpacecraftProvider.ID_GAME+ " = "+ id , null, null); 
		
		while(c.moveToNext()){
			return new Spacecraft(new Vector2D(c.getInt(2),c.getInt(3)), 
					c.getDouble(4), c.getInt(5), c.getInt(6));
		}
		
		return null;
		
	}

	private Game openGame(Integer id){
		
		
		
		
		//return new Game();
		
		
		
		
		
		return null;
		
	}
	
	public List<Game> getGames() {
		return games;
	}


	public void setGames(List<Game> games) {
		this.games = games;
	}
	
/*	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);

	     if (requestCode == EXIT_GAME) {
	    	
	    	 if (RESULT_OK != resultCode) {
	    		 finish();
	    	 }
	     }
	 }*/
	
}
