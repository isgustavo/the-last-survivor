package br.com.thelastsurvivor.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class SQLiteHelper extends SQLiteOpenHelper {

	public SQLiteHelper(Context context, final String DATABASE_NAME,
			final Integer DATABASE_VERSION) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		Log.d("SQLITE HELPER", "01");
		db.execSQL("CREATE TABLE " + PlayerProvider.NAME_TABLE + " ( "
				+ PlayerProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ PlayerProvider.IDENTIFIER_PLAYER + " VARCHAR, "
				+ PlayerProvider.LGTWITTER + " VARCHAR );");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub


	}

}
