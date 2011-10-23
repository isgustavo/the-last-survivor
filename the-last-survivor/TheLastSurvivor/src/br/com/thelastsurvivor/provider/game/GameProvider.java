package br.com.thelastsurvivor.provider.game;

import android.net.Uri;
import android.provider.BaseColumns;
import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;
import br.com.thelastsurvivor.provider.util.Constant;

public class GameProvider extends TheLastSurvivorProvider implements BaseColumns{
	
	public static final Uri CONTENT_URI = Uri.parse("content://" +TheLastSurvivorProvider.AUTHORITY+ "/game");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"+ TheLastSurvivorProvider.AUTHORITY;

	public static final String NAME_TABLE = "game";
	

	public static final String ID = "id";
	public static final String ID_PLAYER = "player";
	public static final String DATE_PAUSE = "date_pause";
	public static final String TIME_PAUSE = "time_pause";
	public static final String POWER_UP = "power_up";
	
	
	static{
		getMatcher().addURI(TheLastSurvivorProvider.AUTHORITY, GameProvider.NAME_TABLE, Constant.IS_GAME);
		
		
	}
}
