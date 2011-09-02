package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.view.BackgroundView;


public class CadastrePlayerActivity extends Activity {

	private BackgroundView view;
	
	private EditText nickname;
	private EditText lgTwitter;

	private Intent i;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.i = new Intent(this, MainMenuActivity.class);
		
		setContentView(R.layout.cadastre_player_view);
		
		this.view = (BackgroundView)findViewById(R.id.surfaceView);

		this.setNickname((EditText) findViewById(R.id.editNickName));
		this.setLgTwitter((EditText) findViewById(R.id.editLGTwitter));

		final Button button = (Button) findViewById(R.id.buttonOk);
		button.setOnClickListener(buttonListener);
				

	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
	}
	

	@Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	 	
    }
	
	private OnClickListener buttonListener = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	if (getNickname().getText().equals("")) {
				AlertDialog.Builder alert = new AlertDialog.Builder(CadastrePlayerActivity.this);
				alert.setIcon(null);
				alert.setTitle("Nickname is necessary");
				alert.setNeutralButton("OK", null);
				alert.show();
			}else{
				if(insertPlayer(new Player(getNickname().getText().toString(), getLgTwitter().getText().toString()))){
				   startActivity(i);

				   CadastrePlayerActivity.this.finish();
	    		}
			}
     
        }  
	};
	
	
	public boolean insertPlayer(Player player) {
		ContentValues values = new ContentValues();

		values.put(PlayerProvider.IDENTIFIER_PLAYER, player.getIdentifier());
		values.put(PlayerProvider.LGTWITTER, player.getLgTwitter());

		getContentResolver().insert(PlayerProvider.CONTENT_URI, values);
		
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
