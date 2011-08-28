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

public class TheLastSurvivorProvider extends ContentProvider {

	public static final String AUTHORITY = "br.com.thelastsurvivor.provider.thelastsurvivorprovider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	
	 public static final String CONTENT_TYPE =  
             "vnd.android.cursor.dir/" + AUTHORITY; 

	private static final String DATABASE_NAME = "theLastSurvivorGame.db";
	private static final int DATABASE_VERSION = 1;

	private SQLiteHelper helper;

	// private static final String TAG = "TheLastSurvivorProvider";

	private static final UriMatcher matcher;
	private static HashMap<String, String> projection;
	
	/*public static final String NAME_TABLE = "player";
	public static final int IS_PLAYERS = 1;

	public static final String ID = "id";
	public static final String IDENTIFIER_PLAYER = "identifier";
	public static final String LGTWITTER = "lg_twitter";
*/
	
	static {
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		
		projection = new HashMap<String, String>();
		
	}

	public static HashMap<String, String> projection() {
		return projection;
	}

	
	
	public static HashMap<String, String> getProjection() {
		return projection;
	}



	public static void setProjection(HashMap<String, String> projection) {
		TheLastSurvivorProvider.projection = projection;
	}

	public static UriMatcher getMatcher() {
		return matcher;
	}

	@Override
	public boolean onCreate() {
		Log.d("TheLastSurvivorProvider", "01");
		helper = new SQLiteHelper(getContext(), DATABASE_NAME, DATABASE_VERSION);

		// Log.d("TheLastSurvivorProvider", "01");
		// helper = new
		// SQLiteHelper(getContext(),DATABASE_NAME,DATABASE_VERSION);
		// Log.d("TheLastSurvivorProvider", "02");
		// persistence = helper.getReadableDatabase();
		// Log.d("TheLastSurvivorProvider", "03");
		return true;
	}

	@Override  
    public Cursor query(Uri uri, String[] projection2, String selection,  
            String[] selectionArgs, String sortOrder) {  
            // Aqui usaremos o SQLiteQueryBuilder para construir  
            // a query que será feito ao DB, retornando um cursor  
            // que enviaremos à aplicação.  
            SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();  
            SQLiteDatabase database = helper.getReadableDatabase();  
            Cursor cursor;  
            switch (matcher.match(uri)) {  
                case PlayerProvider.IS_PLAYERS:  
                    // O Builer receberá dois parametros: a tabela  
                    // onde será feita a busca, e uma projection -  
                    // que nada mais é que uma HashMap com os campos  
                    // que queremos recuperar do banco de dados.  
                    builder.setTables(PlayerProvider.NAME_TABLE);  
                    builder.setProjectionMap(projection);  
                    break;  
                    
                case br.com.thelastsurvivor.provider.util.Constant.IS_TROPHIES:  
                    // O Builer receberá dois parametros: a tabela  
                    // onde será feita a busca, e uma projection -  
                    // que nada mais é que uma HashMap com os campos  
                    // que queremos recuperar do banco de dados.  
                    builder.setTables(TrophiesProvider.NAME_TABLE);  
                    builder.setProjectionMap(projection);  
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
		Log.d("PlayerProvider", "03");
		switch (matcher.match(uri)) {
		case PlayerProvider.IS_PLAYERS:
			Log.d("PlayerProvider", "04");
			SQLiteDatabase db = helper.getWritableDatabase();
			Log.d("PlayerProvider", "05");
			long rowId = db.insert(PlayerProvider.NAME_TABLE, null, values);
			Log.d("PlayerProvider", "06");
			if (rowId > 0) {
				Log.d("PlayerProvider", "07");
				Uri playerUri = ContentUris.withAppendedId(uri, rowId);
				Log.d("PlayerProvider", "08");
				getContext().getContentResolver().notifyChange(playerUri, null);
				Log.d("PlayerProvider", "09");
				return playerUri;
			}
		default:
			Log.d("PlayerProvider", "10");
			throw new IllegalArgumentException("URI desconhecida " + uri);
		}
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
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
        case PlayerProvider.IS_PLAYERS:  
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
