package br.com.thelastsurvivor.provider.player;

import android.net.Uri;
import android.provider.BaseColumns;
import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;

public class PlayerProvider extends TheLastSurvivorProvider implements BaseColumns {
	
	
	public static final Uri CONTENT_URI = Uri.parse("content://" +TheLastSurvivorProvider.AUTHORITY+ "/player");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"+ TheLastSurvivorProvider.AUTHORITY;

	public static final String NAME_TABLE = "player";
	public static final int IS_PLAYERS = 1;
	

	public static final String ID = "id";
	public static final String IDENTIFIER_PLAYER = "identifier";
	public static final String LGTWITTER = "lg_twitter";
	
	static{
		getMatcher().addURI(TheLastSurvivorProvider.AUTHORITY, PlayerProvider.NAME_TABLE, PlayerProvider.IS_PLAYERS);
		
		getProjection().put(PlayerProvider.ID, PlayerProvider.ID);
		getProjection().put(PlayerProvider.IDENTIFIER_PLAYER, PlayerProvider.IDENTIFIER_PLAYER);
		getProjection().put(PlayerProvider.LGTWITTER, PlayerProvider.LGTWITTER);
	}
	
	
	
}
