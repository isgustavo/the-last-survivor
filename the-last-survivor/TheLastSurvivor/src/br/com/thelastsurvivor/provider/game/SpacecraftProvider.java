package br.com.thelastsurvivor.provider.game;

import android.net.Uri;
import android.provider.BaseColumns;
import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;
import br.com.thelastsurvivor.provider.util.Constant;
import br.com.thelastsurvivor.util.Vector2D;

public class SpacecraftProvider extends TheLastSurvivorProvider implements BaseColumns{

	
	public static final Uri CONTENT_URI = Uri.parse("content://" +TheLastSurvivorProvider.AUTHORITY+ "/spacecraft");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"+ TheLastSurvivorProvider.AUTHORITY;

	public static final String NAME_TABLE = "spacecraft";
	//public static final int IS_PLAYERS = 1;

	public static final String ID = "id";
	public static final String ID_GAME = "game";
	public static final String POS_X = "pos_x";
	public static final String POS_Y = "pos_y";
	public static final String ANGLE = "angle";
	public static final String LIFE = "life";
	public static final String POINTS = "points";
	

	static{
		getMatcher().addURI(TheLastSurvivorProvider.AUTHORITY, SpacecraftProvider.NAME_TABLE, Constant.IS_SPACECRAFT);

	}
	
}
