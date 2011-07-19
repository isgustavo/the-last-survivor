package br.com.thelastsurvivor.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context, final String DATABASE_NAME, 
			final Integer DATABASE_VERSION ){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE" + PlayerProvider.NAME_TABLE +" ( " 
				+ PlayerProvider.ID +" NUMBER PRIMARY KEY AUTOINCREMENT, "
				+ PlayerProvider.IDENTIFIER_PLAYER +" LONGTEXT, "
				+ PlayerProvider.LGTWITTER + "LONGTEXT );");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
