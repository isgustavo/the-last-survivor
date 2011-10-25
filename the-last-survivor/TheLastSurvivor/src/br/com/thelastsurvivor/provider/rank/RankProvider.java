package br.com.thelastsurvivor.provider.rank;

import android.net.Uri;
import android.provider.BaseColumns;
import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;
import br.com.thelastsurvivor.provider.util.Constant;

public class RankProvider extends TheLastSurvivorProvider implements BaseColumns {
	
	
	public static final Uri CONTENT_URI = Uri.parse("content://" +TheLastSurvivorProvider.AUTHORITY+ "/rank");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"+ TheLastSurvivorProvider.AUTHORITY;

	public static final String NAME_TABLE = "rank";
	//public static final int IS_PLAYERS = 1;
	

	public static final String ID = "id";
	public static final String IDENTIFIER_PLAYER = "id_player";
	public static final String POINTS = "points";
	public static final String DATE = "date";
	public static final String TYPE = "type";
	
	static{
		getMatcher().addURI(TheLastSurvivorProvider.AUTHORITY, RankProvider.NAME_TABLE, Constant.IS_RANK);
		
		//getProjection().put(PlayerProvider.ID, PlayerProvider.ID);
		//getProjection().put(PlayerProvider.IDENTIFIER_PLAYER, PlayerProvider.IDENTIFIER_PLAYER);
		//getProjection().put(PlayerProvider.LGTWITTER, PlayerProvider.LGTWITTER);
	}
	
	
}
