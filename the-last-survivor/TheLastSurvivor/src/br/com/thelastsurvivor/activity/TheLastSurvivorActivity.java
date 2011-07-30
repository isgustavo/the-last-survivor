package br.com.thelastsurvivor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.player.PlayerActivity;

public class TheLastSurvivorActivity extends Activity {

	private static final Integer SPLASH_DURATION = 2000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Log.d("TheLastSurvivorActivity", "01");

				Intent i = new Intent(TheLastSurvivorActivity.this,
						PlayerActivity.class);
				TheLastSurvivorActivity.this.startActivity(i);

				TheLastSurvivorActivity.this.finish();

				// startActivity(i);
				Log.d("TheLastSurvivorActivity", "02");

			}
		}, SPLASH_DURATION);

		// Log.d("TheLastSurvivorActivity", "01");
		// Intent i = new Intent(this, PlayerActivity.class);
		// startActivity(i);
		// Log.d("TheLastSurvivorActivity", "02");
	}
}