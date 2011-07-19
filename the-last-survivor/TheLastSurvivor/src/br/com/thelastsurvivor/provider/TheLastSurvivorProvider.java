package br.com.thelastsurvivor.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class TheLastSurvivorProvider extends ContentProvider  {
	
	public static final Uri CONTENT_URI = Uri.parse("br.com.thelastsurvivor.provider.thelastsurvivorprovider");
	
	private static final String DATABASE_NAME = "lastSurvivorGame.db"; 
	private static final int  DATABASE_VERSION = 1; 
	
	private DBHelper helper;
	private static final UriMatcher matcher;
	
	
	static {  
        matcher = new UriMatcher(UriMatcher.NO_MATCH);  
        matcher.addURI(CONTENT_URI.toString(), PlayerProvider.NAME_TABLE, PlayerProvider.NOTES);  
        
    }  
	
	
	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
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
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onCreate() {
		 helper = new DBHelper(getContext(),DATABASE_NAME,DATABASE_VERSION);;  
		return true;
	}

	public DBHelper getHelper() {
		return helper;
	}

	public void setHelper(DBHelper helper) {
		this.helper = helper;
	}


	
}
