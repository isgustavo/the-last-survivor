package br.com.thelastsurvivor.activity.game.simplemode;

import android.app.Activity;
import android.os.Bundle;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.util.FT2FontTextView;

public class ResultGameActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.result_game_view);
		
		final FT2FontTextView scoreGame = (FT2FontTextView)findViewById(R.id.points_game);
		scoreGame.setText(" 00 pt");
	
		final FT2FontTextView timeGame = (FT2FontTextView)findViewById(R.id.time_game);
		timeGame.setText(" 00 min");
		
		
	}
	
	
}
