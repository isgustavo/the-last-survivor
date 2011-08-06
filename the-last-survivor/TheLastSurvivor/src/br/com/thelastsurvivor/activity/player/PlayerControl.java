package br.com.thelastsurvivor.activity.player;

import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;
import br.com.thelastsurvivor.model.player.Player;
import br.com.thelastsurvivor.provider.player.PlayerProvider;

public class PlayerControl {

	private ContentResolver cr;

	// public Activity context;

	public PlayerControl(ContentResolver app) {
		this.cr = app;
		// provider = new PlayerProvider();

	}

	public boolean insertPlayer(Player player) {
		/*
		 * ContentValues values = new ContentValues();
		 * 
		 * values.put(PlayerProvider.IDENTIFIER_PLAYER, player.getIdentifier());
		 * values.put(PlayerProvider.LGTWITTER, player.getLgTwitter());
		 * 
		 * cr. getContentResolver().insert(
		 * QuickNotesProvider.Notes.CONTENT_URI, values);
		 * 
		 * cr.insert(PlayerProvider.CONTENT_URI, null);
		 */
		return true;
	}

	public boolean isTherePlayer() {
		Log.d(" PlayerControl", "01");

		final String[] PROJECTION = new String[] { PlayerProvider.ID, };
		// ContentResolver cr = getContentResolver();

		Cursor cursor = cr.query(PlayerProvider.CONTENT_URI, PROJECTION, null,
				null, null);

		if (cursor == null) {
			return false;
		}
		return true;

		/*
		 * cursor.moveToFirst(); while(!cursor.isAfterLast()){ return true; }
		 * return false;
		 */
	}

	private Player fillPlayer(Cursor c) {

		return new Player(c.getString(1), c.getString(2));

	}

}
