package br.com.thelastsurvivor.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;

public class SQLiteHelper extends SQLiteOpenHelper {

	public SQLiteHelper(Context context, final String DATABASE_NAME,
			final Integer DATABASE_VERSION) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + PlayerProvider.NAME_TABLE + " ( "
				+ PlayerProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ PlayerProvider.IDENTIFIER_PLAYER + " VARCHAR ); ");
		
		db.execSQL(" CREATE TABLE "+ TrophiesProvider.NAME_TABLE + "( "
				+ TrophiesProvider.ID + " INTEGER PRIMARY KEY, " 
				+ TrophiesProvider.DATE_ACHIEVED +" INTEGER ); ");
		
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (1, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (2, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (3, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (4, null); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (5, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (6, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (7, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (8, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (9, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (10, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (11, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (12, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (13, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (14, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (15, null ); ");
				

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
		
	/*	db.execSQL("Drop table");

		db.execSQL("CREATE TABLE " + PlayerProvider.NAME_TABLE + " ( "
				+ PlayerProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ PlayerProvider.IDENTIFIER_PLAYER + " VARCHAR ); ");
		
		db.execSQL(" CREATE TABLE "+ TrophiesProvider.NAME_TABLE + "( "
				+ TrophiesProvider.ID + " INTEGER PRIMARY KEY, " 
				+ TrophiesProvider.DATE_ACHIEVED +" INTEGER ); ");
		
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (1, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (2, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (3, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (4, null); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (5, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (6, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (7, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (8, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (9, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (10, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (11, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (12, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (13, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (14, null ); ");
		db.execSQL("INSERT INTO "+ TrophiesProvider.NAME_TABLE 
				+ " VALUES (15, null ); ");
*/
	}

}
