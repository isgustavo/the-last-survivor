package br.com.thelastsurvivor.provider.trophies;

import android.net.Uri;
import android.provider.BaseColumns;
import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.provider.util.Constant;

public class TrophiesProvider extends TheLastSurvivorProvider implements BaseColumns{
	
	public static final Uri CONTENT_URI = Uri.parse("content://" +TheLastSurvivorProvider.AUTHORITY+ "/trophies");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"+ TheLastSurvivorProvider.AUTHORITY;
	
	public static final String NAME_TABLE = "trophies";
	
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String OBJECTIVE = "objective";
	public static final String IS_ACHIEVED = "is_achieved";
	public static final String STATUS = "status";
	
	static{
		getMatcher().addURI(TheLastSurvivorProvider.AUTHORITY, TrophiesProvider.NAME_TABLE, Constant.IS_TROPHIES);
		
		getProjection().put(TrophiesProvider.ID, TrophiesProvider.ID);
		getProjection().put(TrophiesProvider.NAME, TrophiesProvider.NAME);
		getProjection().put(TrophiesProvider.OBJECTIVE, TrophiesProvider.OBJECTIVE);
		getProjection().put(TrophiesProvider.IS_ACHIEVED, TrophiesProvider.IS_ACHIEVED);
		getProjection().put(TrophiesProvider.STATUS, TrophiesProvider.STATUS);
	}
	
	

}
