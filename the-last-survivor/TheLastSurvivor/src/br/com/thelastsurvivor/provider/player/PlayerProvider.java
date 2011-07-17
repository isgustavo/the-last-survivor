package br.com.thelastsurvivor.provider.player;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;

public class PlayerProvider extends TheLastSurvivorProvider implements BaseColumns{

	
	private static final Uri CONTENT_URI = Uri.parse("content://"
			+ TheLastSurvivorProvider.CONTENT_URI+ "/player");
	
	private static final String CONTENT_TYPE = "vnd.android.cursor.dir/"
			+TheLastSurvivorProvider.CONTENT_URI;
	
	private static final String NAME_TABLE ="player";
	private static final String ID = "id";
	private static final String IDENTIFIER_PLAYER = "identifier";
	private static final String LGTWITTER = "lg_twitter"; 
	
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

	
	public static Uri getContentUri() {
		return CONTENT_URI;
	}


	public static String getContentType() {
		return CONTENT_TYPE;
	}


	public static String getNameTable(){
		return NAME_TABLE;
	}
	
	public static String getId() {
		return ID;
	}

	public static String getIdentifierPlayer(){
		return IDENTIFIER_PLAYER;
	}

	public static String getLgtwitter() {
		return LGTWITTER;
	}
	
	

}
