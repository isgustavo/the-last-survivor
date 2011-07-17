package br.com.thelastsurvivor.provider;

import br.com.thelastsurvivor.model.player.Player;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context, final String DATABASE_NAME, 
			final Integer DATABASE_VERSION ){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE" + Player.getNameTable() +" ( " 
				+ Player.getId() +" NUMBER PRIMARY KEY AUTOINCREMENT, "
				+ Player.getIdentifierPlayer() +" LONGTEXT, "
				+ Player.getLgtwitter() + "LONGTEXT );");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
