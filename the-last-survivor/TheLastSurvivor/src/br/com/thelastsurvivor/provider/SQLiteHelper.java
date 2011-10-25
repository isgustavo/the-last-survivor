package br.com.thelastsurvivor.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.thelastsurvivor.provider.game.AsteroidProvider;
import br.com.thelastsurvivor.provider.game.GameProvider;
import br.com.thelastsurvivor.provider.game.PowerUpProvider;
import br.com.thelastsurvivor.provider.game.ShootProvider;
import br.com.thelastsurvivor.provider.game.SpacecraftProvider;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.provider.rank.RankProvider;
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
		
		db.execSQL(" CREATE TABLE "+ GameProvider.NAME_TABLE + "( "
				+ GameProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ GameProvider.ID_PLAYER + " INTEGER, "	
				+ GameProvider.DATE_PAUSE +" VARCHAR, " 
				+ GameProvider.TIME_PAUSE +" INTEGER, " 
				+ GameProvider.POWER_UP +" INTEGER ); "); 
		
		db.execSQL(" CREATE TABLE "+ SpacecraftProvider.NAME_TABLE + "( "
				+ SpacecraftProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ SpacecraftProvider.ID_GAME + " INTEGER, "	
				+ SpacecraftProvider.POS_X +" INTEGER, " 
				+ SpacecraftProvider.POS_Y +" INTEGER, " 
				+ SpacecraftProvider.ANGLE +" REAL," 
				+ SpacecraftProvider.LIFE +" INTEGER, "
				+ SpacecraftProvider.POINTS +" INTEGER ); "); 
		
		db.execSQL(" CREATE TABLE "+ ShootProvider.NAME_TABLE + "( "
				+ ShootProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ ShootProvider.ID_SPACECRAFT + " INTEGER, "	
				+ ShootProvider.POS_X +" INTEGER, " 
				+ ShootProvider.POS_Y +" INTEGER, " 
				+ ShootProvider.ANGLE +" REAL ); ");
		
		db.execSQL(" CREATE TABLE "+ AsteroidProvider.NAME_TABLE + "( "
				+ AsteroidProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ AsteroidProvider.ID_GAME + " INTEGER, "	
				+ AsteroidProvider.POS_X +" INTEGER, " 
				+ AsteroidProvider.POS_Y +" INTEGER, "
				+ AsteroidProvider.ANGLE +" REAL, "
				+ AsteroidProvider.LIFE +" INTEGER, "
				+ AsteroidProvider.ROUTE +" INTEGER, "
				+ AsteroidProvider.TYPE +" INTEGER ); ");
		
		db.execSQL(" CREATE TABLE "+ RankProvider.NAME_TABLE + "( "
				+ RankProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ RankProvider.IDENTIFIER_PLAYER + " VARCHAR, "	
				+ RankProvider.POINTS +" INTEGER, " 
				+ RankProvider.DATE +" VARCHAR, " 
				+ RankProvider.TYPE + " INTEGER ); ");
		
		
		db.execSQL(" CREATE TABLE "+ PowerUpProvider.NAME_TABLE + "( "
				+ PowerUpProvider.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ PowerUpProvider.ID_GAME + " INTEGER, "	
				+ PowerUpProvider.POS_X +" INTEGER, " 
				+ PowerUpProvider.POS_Y +" INTEGER, " 
				+ PowerUpProvider.ROUTE +" INTEGER ); ");
		
		
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
		
				

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
		
		//db.execSQL("Drop table");

		

	}

}
