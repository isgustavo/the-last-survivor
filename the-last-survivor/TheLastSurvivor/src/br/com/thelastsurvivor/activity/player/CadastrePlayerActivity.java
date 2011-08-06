package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class CadastrePlayerActivity extends Activity {

	private EditText nickname;
	private EditText lgTwitter;

	// private PlayerControl playerControl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cadastre_player);

		this.setNickname((EditText) findViewById(R.id.editNickName));
		this.setLgTwitter((EditText) findViewById(R.id.editLGTwitter));

		final Button button = (Button) findViewById(R.id.buttonOk);

		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				//String nickname = getNickname().getText().toString();
				//String lgTwitter = getLgTwitter().getText().toString();

				if (getNickname().getText().equals("")) {
					AlertDialog.Builder alerta = new AlertDialog.Builder(
							CadastrePlayerActivity.this);
					alerta.setIcon(null);
					alerta.setTitle("Nickname is necessary");
					alerta.setNeutralButton("OK", null);
					alerta.show();
				} 
			}
		});
		
		if(insertPlayer(new Player(getNickname().getText().toString(), getLgTwitter().getText().toString()))){
			Intent i = new Intent(this, MainMenuActivity.class);
			startActivity(i);
		}
				
				

	}

	public boolean insertPlayer(Player player) {
		ContentValues values = new ContentValues();

		values.put(PlayerProvider.IDENTIFIER_PLAYER, player.getIdentifier());
		values.put(PlayerProvider.LGTWITTER, player.getLgTwitter());

		getContentResolver().insert(PlayerProvider.CONTENT_URI, values);
		Log.d("CadastrePlayerActivity", "02");
		return true;
	}

	
	

	public EditText getNickname() {
		return nickname;
	}

	public void setNickname(EditText nickname) {
		this.nickname = nickname;
	}

	public EditText getLgTwitter() {
		return lgTwitter;
	}

	public void setLgTwitter(EditText lgTwitter) {
		this.lgTwitter = lgTwitter;
	}

}
