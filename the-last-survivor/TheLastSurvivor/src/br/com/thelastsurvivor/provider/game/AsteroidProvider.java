package br.com.thelastsurvivor.provider.game;

import android.net.Uri;
import android.provider.BaseColumns;
import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;
import br.com.thelastsurvivor.provider.util.Constant;

public class AsteroidProvider extends TheLastSurvivorProvider implements BaseColumns{

	public static final Uri CONTENT_URI = Uri.parse("content://" +TheLastSurvivorProvider.AUTHORITY+ "/asteroid");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"+ TheLastSurvivorProvider.AUTHORITY;

	public static final String NAME_TABLE = "asteroid";
	
	public static final String ID = "id";
	public static final String ID_GAME = "id_game";
	public static final String POS_X = "pos_x";
	public static final String POS_Y = "pos_y";
	public static final String ANGLE = "angle";
	public static final String LIFE = "life";
	public static final String ROUTE = "route";
	public static final String TYPE = "type";

	static{
		getMatcher().addURI(TheLastSurvivorProvider.AUTHORITY, AsteroidProvider.NAME_TABLE, Constant.IS_ASTEROID);

	}
}
