package br.com.thelastsurvivor.activity.game.simplemode;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.model.game.Game;
import br.com.thelastsurvivor.util.FT2FontTextView;

public class ListGameAdapter extends BaseAdapter{

	private Context context;
	private SavedGameActivity actitivy;
	private List<Game> games;
	LayoutInflater inflater;
	
	public ListGameAdapter(Context context,LayoutInflater inflater, List<Game> games){
		this.context = context;
		this.games = games;
		this.inflater = inflater;
	}
	
	public ListGameAdapter(List<Game> games,Context activity){
		//this.context = context;
		this.games = games;
		this.context = activity;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Game game = games.get(position);
		 
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				
		View v = inflater.inflate(R.layout.game_list_view, null);
		
		TextView idGame = (TextView) v.findViewById(R.id.game_saved_id);
		idGame.setText(game.getId()+"");
		idGame.setWillNotDraw(true);
		
		FT2FontTextView dateGame = (FT2FontTextView) v.findViewById(R.id.date_game);
		dateGame.setText(game.getDate()+"");
		
		FT2FontTextView timeGame = (FT2FontTextView) v.findViewById(R.id.time_game);
		timeGame.setText(game.getRunTime()+"");
		
		FT2FontTextView lifeGame = (FT2FontTextView) v.findViewById(R.id.life_game);
		lifeGame.setText("life: " + game.getSpacecraft().getLife());
		
		FT2FontTextView pointsGame = (FT2FontTextView) v.findViewById(R.id.points_game);
		pointsGame.setText("Point :" + game.getSpacecraft().getPoints());
	


		
		//LayoutInflater inflater = LayoutInflater.from(context);
		//v = LinearLayout.inflate(context, R.layout.game_list_view, null); 
		
		//View v = new LinearLayout(context);//  inflate(context, R.layout.game_list_view, null); //inflater.inflate(R.layout.game_list_view, null);
		//v.setLayoutParams(R.layout.game_list_view);
		
		
		
	
		return v;
	}
	
	@Override
	public int getCount() {
		return games.size();
	}

	@Override
	public Object getItem(int position) {
		return games.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	
	
}
