package br.com.thelastsurvivor.activity.player;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class AlterationPlayerActivity extends Activity{

	//private BackgroundView view;
	
	private EditText nickname;
	private EditText lgTwitter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		
		
	
		setContentView(R.layout.alteration_player_view);
		
		init();
	
		loadPlayer();
	
	}
	
	public void init(){
		
		this.nickname = (EditText)findViewById(R.id.editNickName);
		//this.nickname.setText("GUSTAVO");
	}
	
	
	public void loadPlayer() {

		Cursor c = this.getContentResolver().query(PlayerProvider.CONTENT_URI, 
				null, null , null, null);
		
		
		while(c.moveToNext()){
		
			// name = cur.getString(nameColumn);
	         //   phoneNumber = cur.getString(phoneColumn);
			this.nickname.setText(c.getString(2));
			
		}
	
		
	}
	
	
}
