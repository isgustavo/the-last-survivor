package br.com.thelastsurvivor.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public abstract class TheLastSurvivorProvider extends ContentProvider {
	
	private  static final Uri AUTHORITY = Uri  
			.parse("content://br.com.thelastsurvivor.provider.TheLastSurvivorProvider"); 
	
	private static final String DATABASE_NAME = "lastSurvivor.db";
	private static final Integer DATABASE_VERSION = 1;
	//private static final UriMatcher MATCHER;
	
	private DBHelper dbHelper;
	 
	
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
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	public DBHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public static Uri getAuthority() {
		return AUTHORITY;
	}

	public static String getDatabaseName() {
		return DATABASE_NAME;
	}

	public static Integer getDatabaseVersion() {
		return DATABASE_VERSION;
	}

	
	
	
}
