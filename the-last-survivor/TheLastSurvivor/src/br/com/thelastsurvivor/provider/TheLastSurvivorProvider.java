package br.com.thelastsurvivor.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import br.com.thelastsurvivor.provider.game.AsteroidProvider;
import br.com.thelastsurvivor.provider.game.GameProvider;
import br.com.thelastsurvivor.provider.game.PowerUpProvider;
import br.com.thelastsurvivor.provider.game.ShootProvider;
import br.com.thelastsurvivor.provider.game.SpacecraftProvider;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.provider.rank.RankProvider;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;
import br.com.thelastsurvivor.provider.util.Constant;

public class TheLastSurvivorProvider extends ContentProvider {

	//sqlite3 /data/data/br.com.thelastsurvivor/databases/LastSurvivorDatabase.db
	public static final String AUTHORITY = "br.com.thelastsurvivor.provider.thelastsurvivorprovider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	
	 public static final String CONTENT_TYPE =  
             "vnd.android.cursor.dir/" + AUTHORITY; 

	private static final String DATABASE_NAME = "LastSurvivorDatabase.db";
	private static final int DATABASE_VERSION = 1;

	private SQLiteHelper helper;


	private static final UriMatcher matcher;

	
	
	static {
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		
	}

	public static UriMatcher getMatcher() {
		return matcher;
	}

	@Override
	public boolean onCreate() {
		
		helper = new SQLiteHelper(getContext(), DATABASE_NAME, DATABASE_VERSION);

		return true;
	}

	@Override  
    public Cursor query(Uri uri, String[] projection2, String selection,  
            String[] selectionArgs, String sortOrder) {  
            
            SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();  
            SQLiteDatabase database = helper.getReadableDatabase();  
            Cursor cursor;  
            switch (matcher.match(uri)) {  
                
        	case Constant.IS_PLAYER:  
                builder.setTables(PlayerProvider.NAME_TABLE);  
                
            break;  
                
            case Constant.IS_TROPHIES:  
                builder.setTables(TrophiesProvider.NAME_TABLE);  
               
            break;
            
            case Constant.IS_GAME:
            	builder.setTables(GameProvider.NAME_TABLE);
  
            break;
            
            case Constant.IS_SPACECRAFT:
            	builder.setTables(SpacecraftProvider.NAME_TABLE);
  
            break;
            
            case Constant.IS_SHOOT:
            	builder.setTables(ShootProvider.NAME_TABLE);
  
            break;
            
            case Constant.IS_ASTEROID:
            	builder.setTables(AsteroidProvider.NAME_TABLE);
  
            break;
            
            case Constant.IS_POWER_UP:
            	builder.setTables(PowerUpProvider.NAME_TABLE);
  
            break;
            
            case Constant.IS_RANK :
            	builder.setTables(RankProvider.NAME_TABLE);
  
            break;
            
            default:  
                throw new IllegalArgumentException(  
                      "URI desconhecida " + uri);  
            }  
  
            cursor = builder.query(database, projection2, selection,  
            selectionArgs, null, null, sortOrder);  
  
            cursor.setNotificationUri(getContext().getContentResolver(), uri);  
            return cursor;  
    }  

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId = 0;
		SQLiteDatabase db;
		
		switch (matcher.match(uri)) {
		
		case Constant.IS_PLAYER: 
		
			db = helper.getWritableDatabase();
			rowId = db.insert(PlayerProvider.NAME_TABLE, null, values);
		
			if (rowId > 0) {
				Uri uriId = ContentUris.withAppendedId(uri, rowId);		
				getContext().getContentResolver().notifyChange(uriId, null);			
				return uriId;
			}
			
			
		break;

		case Constant.IS_GAME:
			db = helper.getWritableDatabase();
			rowId = db.insert(GameProvider.NAME_TABLE, null, values);
		
			if (rowId > 0) {
				Uri uriId = ContentUris.withAppendedId(uri, rowId);		
				getContext().getContentResolver().notifyChange(uriId, null);			
				return uriId;
			}
			
		break;
		
		case Constant.IS_SPACECRAFT:
			db = helper.getWritableDatabase();
			rowId = db.insert(SpacecraftProvider.NAME_TABLE, null, values);
		
			if (rowId > 0) {
				Uri uriId = ContentUris.withAppendedId(uri, rowId);		
				getContext().getContentResolver().notifyChange(uriId, null);			
				return uriId;
			}
		
		break;
		
		case Constant.IS_SHOOT:
			db = helper.getWritableDatabase();
			rowId = db.insert(ShootProvider.NAME_TABLE, null, values);
		
			if (rowId > 0) {
				Uri uriId = ContentUris.withAppendedId(uri, rowId);		
				getContext().getContentResolver().notifyChange(uriId, null);			
				return uriId;
			}
		
		break;
		
		case Constant.IS_ASTEROID:
			db = helper.getWritableDatabase();
			rowId = db.insert(AsteroidProvider.NAME_TABLE, null, values);
		
			if (rowId > 0) {
				Uri uriId = ContentUris.withAppendedId(uri, rowId);		
				getContext().getContentResolver().notifyChange(uriId, null);			
				return uriId;
			}
		
		break;
		
		case Constant.IS_POWER_UP:
			db = helper.getWritableDatabase();
			rowId = db.insert(PowerUpProvider.NAME_TABLE, null, values);
		
			if (rowId > 0) {
				Uri uriId = ContentUris.withAppendedId(uri, rowId);		
				getContext().getContentResolver().notifyChange(uriId, null);			
				return uriId;
			}
		
		break;
		
		case Constant.IS_RANK:
			db = helper.getWritableDatabase();
			rowId = db.insert(RankProvider.NAME_TABLE, null, values);
		
			if (rowId > 0) {
				Uri uriId = ContentUris.withAppendedId(uri, rowId);		
				getContext().getContentResolver().notifyChange(uriId, null);			
				return uriId;
			}
		
		break;

		default:
			
			throw new IllegalArgumentException("URI desconhecida " + uri);
		}
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] arg3) {
		SQLiteDatabase db;
		int romId;
		switch (matcher.match(uri)) {
		
		case Constant.IS_PLAYER: 
			 db = helper.getWritableDatabase();
			romId = db.update(PlayerProvider.NAME_TABLE,values, selection, null);
		
			if (romId > 0) {
				Uri playerUri = ContentUris.withAppendedId(uri, romId);		
				getContext().getContentResolver().notifyChange(playerUri, null);			
				return romId;
			}
			
		break;
		
		case Constant.IS_TROPHIES: 
			db = helper.getWritableDatabase();
			romId = db.update(TrophiesProvider.NAME_TABLE,values, selection, null);
		
			if (romId > 0) {
				Uri playerUri = ContentUris.withAppendedId(uri, romId);		
				getContext().getContentResolver().notifyChange(playerUri, null);			
				return romId;
			}
			
		break;
		
		default:
			
			throw new IllegalArgumentException("URI desconhecida " + uri);
		}
		
		return 0;
	}

	@Override
	public int delete(Uri uri, String where, String[] arg2) {
		SQLiteDatabase db;
		 
		switch (matcher.match(uri)) {
		  case Constant.IS_PLAYER: 
			  db = helper.getWritableDatabase();
			  db.delete(PlayerProvider.NAME_TABLE, where, null);
		  break;
		  
		  case Constant.IS_GAME: 
			  db = helper.getWritableDatabase();
			  db.delete(GameProvider.NAME_TABLE, where, null);
		  break;
		  
		  case Constant.IS_SPACECRAFT: 
			  db = helper.getWritableDatabase();
			  db.delete(SpacecraftProvider.NAME_TABLE, where, null);
		  break;
		  
		  case Constant.IS_SHOOT: 
			  db = helper.getWritableDatabase();
			  db.delete(ShootProvider.NAME_TABLE, where, null);
		  break;
		  
		  case Constant.IS_ASTEROID: 
			  db = helper.getWritableDatabase();
			  db.delete(AsteroidProvider.NAME_TABLE, where, null);
		  break;
		
		  
		  case Constant.IS_POWER_UP: 
			  db = helper.getWritableDatabase();
			  db.delete(PowerUpProvider.NAME_TABLE, where, null);
		  break;
		  
		  default:
				
				throw new IllegalArgumentException("URI desconhecida " + uri);
		}

		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {  
        case Constant.IS_PLAYER: 
            return CONTENT_TYPE;  
        default:  
            throw new IllegalArgumentException(  
                "URI desconhecida " + uri);  
    } 
	}

	public SQLiteHelper getHelper() {
		return helper;
	}

	public void setHelper(SQLiteHelper helper) {
		this.helper = helper;
	}

	
}
