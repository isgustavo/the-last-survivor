package br.com.thelastsurvivor.provider.game;

import android.net.Uri;
import android.provider.BaseColumns;
import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;
import br.com.thelastsurvivor.provider.util.Constant;
import br.com.thelastsurvivor.util.Vector2D;

public class EffectProvider extends TheLastSurvivorProvider implements BaseColumns{
	
	public static final Uri CONTENT_URI = Uri.parse("content://" +TheLastSurvivorProvider.AUTHORITY+ "/effect");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"+ TheLastSurvivorProvider.AUTHORITY;

	public static final String NAME_TABLE = "effect";
	

	public static final String ID = "id";
	public static final String ID_GAME = "id_game";
	public static final String POS_X = "pos_x";
	public static final String POS_Y = "pos_y";
	public static final String TIME = "time";
	
	
	static{
		getMatcher().addURI(TheLastSurvivorProvider.AUTHORITY, EffectProvider.NAME_TABLE, Constant.IS_EFFECT);
		
		
	}
}
