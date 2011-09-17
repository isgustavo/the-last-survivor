package br.com.thelastsurvivor.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;
import br.com.thelastsurvivor.provider.util.Constant;

public class TheLastSurvivorProvider extends ContentProvider {

	//sqlite3 /data/data/br.com.thelastsurvivor/databases/LastSurvivorDatabase.db
	public static final String AUTHORITY = "br.com.thelastsurvivor.provider.thelastsurvivorprovider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	
	 public static final String CONTENT_TYPE =  
             "vnd.android.cursor.dir/" + AUTHORITY; 

	private static final String DATABASE_NAME = "LastSurvivorDatabase.db";
	private static final int DATABASE_VERSION = 2;

	private SQLiteHelper helper;


	private static final UriMatcher matcher;
	//private static HashMap<String, String> projection;
	
	
	static {
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		
		//projection = new HashMap<String, String>();
		
	}

/*	public static HashMap<String, String> projection() {
		return projection;
	}

	
	
	public static HashMap<String, String> getProjection() {
		return projection;
	}



	public static void setProjection(HashMap<String, String> projection) {
		TheLastSurvivorProvider.projection = projection;
	}*/

	public static UriMatcher getMatcher() {
		return matcher;
	}

	@Override
	public boolean onCreate() {
		Log.d("TheLastSurvivorProvider", "01");
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
                    // O Builer receberá dois parametros: a tabela  
                    // onde será feita a busca, e uma projection -  
                    // que nada mais é que uma HashMap com os campos  
                    // que queremos recuperar do banco de dados.  
                    builder.setTables(TrophiesProvider.NAME_TABLE);  
                    //builder.setProjectionMap(projection);  
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
		
		switch (matcher.match(uri)) {
		
		case Constant.IS_PLAYER: 
		
			SQLiteDatabase db = helper.getWritableDatabase();
			long rowId = db.insert(PlayerProvider.NAME_TABLE, null, values);
		
			if (rowId > 0) {
				Uri playerUri = ContentUris.withAppendedId(uri, rowId);		
				getContext().getContentResolver().notifyChange(playerUri, null);			
				return playerUri;
			}
			
			
		break;
		
		
		default:
			
			throw new IllegalArgumentException("URI desconhecida " + uri);
		}
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] arg3) {
		
		switch (matcher.match(uri)) {
		
		case Constant.IS_PLAYER: 
			SQLiteDatabase db = helper.getWritableDatabase();
			int romId = db.update(PlayerProvider.NAME_TABLE,values, selection, null);
		
			if (romId > 0) {
				Uri playerUri = ContentUris.withAppendedId(uri, romId);		
				getContext().getContentResolver().notifyChange(playerUri, null);			
				return romId;
			}
			
		break;
		}
		return 0;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
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
