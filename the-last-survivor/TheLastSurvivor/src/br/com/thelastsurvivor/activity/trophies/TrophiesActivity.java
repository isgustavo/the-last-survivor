package br.com.thelastsurvivor.activity.trophies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.model.Trophies;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class TrophiesActivity extends ListActivity{
	
	private List<Trophies> trophies; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trophies_list);
		init();
		getTrophies();
	}

	private void init() {
		this.trophies = new ArrayList<Trophies>();
	}

	private void getTrophies() {
	
	  
	        Map projection = new HashMap<String,String>();  
	        projection.put(TrophiesProvider.ID, TrophiesProvider.ID);  
	        projection.put(TrophiesProvider.NAME, TrophiesProvider.NAME); 
	        projection.put(TrophiesProvider.OBJECTIVE, TrophiesProvider.OBJECTIVE); 
	        projection.put(TrophiesProvider.IS_ACHIEVED, TrophiesProvider.IS_ACHIEVED); 
	        projection.put(TrophiesProvider.STATUS, TrophiesProvider.STATUS); 
		
		
		Cursor c = this.getContentResolver().  
                query(TrophiesProvider.CONTENT_URI, null, null, null, null);
		
		startManagingCursor(c);
		
		String[] from = new String[] {TrophiesProvider.NAME, TrophiesProvider.OBJECTIVE};
		
		int[] to = new int[] {R.id.trophie_name, R.id.trophie_objective};

		setListAdapter(new SimpleCursorAdapter(this,
		        R.layout.trophies_row,
		        c,
		        from,
		        to));
	}
}
