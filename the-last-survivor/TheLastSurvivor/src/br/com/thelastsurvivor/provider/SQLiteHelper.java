package br.com.thelastsurvivor.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class SQLiteHelper extends SQLiteOpenHelper {

	public SQLiteHelper(Context context, final String DATABASE_NAME,
			final Integer DATABASE_VERSION) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + PlayerProvider.NAME_TABLE + " ( "
				+ PlayerProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ PlayerProvider.IDENTIFIER_PLAYER + " VARCHAR, "
				+ PlayerProvider.LGTWITTER + " VARCHAR ); ");
		
/*		db.execSQL(" CREATE TABLE "+ TrophiesProvider.NAME_TABLE + "( "
				+ TrophiesProvider.ID + " INTEGER PRIMARY KEY, " 
				+ TrophiesProvider.IS_ACHIEVED +" INTEGER ); ");
		
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (0, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (1, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (2, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (3, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (4, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (5, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (6, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (7, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (8, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (9, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (10, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (11, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (12, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (13, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (14, 0 ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ "VALUES (15, 0 ); ");
				
		*/

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub


	}

}
