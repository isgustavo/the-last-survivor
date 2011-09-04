package br.com.thelastsurvivor.activity.trophies;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.model.trophies.Trophies;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;

public class TrophiesActivity extends ListActivity{
	
	private List<Trophies> trophies; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//init();
		
		setContentView(R.layout.trophies_list);
		
		getTrophies();
		
		
	}

	private void init() {
		this.trophies = new ArrayList<Trophies>();
	}

	private void getTrophies() {
	
	     /* Map projection = new HashMap<String,String>();  
	        projection.put(TrophiesProvider.ID, TrophiesProvider.ID);   
	        projection.put(TrophiesProvider.DATE_ACHIEVED, TrophiesProvider.DATE_ACHIEVED); 
		
		
		Cursor c = this.getContentResolver().  
                query(TrophiesProvider.CONTENT_URI, null, null, null, null);
		
		startManagingCursor(c);*/
		
		
		//String[] from = new String[] {TrophiesProvider.ID, TrophiesProvider.DATE_ACHIEVED};
		
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		
		HashMap<String, String> item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t01));
		item.put("objective", getString(R.string.tObjective01));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t02));
		item.put("objective", getString(R.string.tObjective02));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t03));
		item.put("objective", getString(R.string.tObjective03));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t04));
		item.put("objective", getString(R.string.tObjective04));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t05));
		item.put("objective", getString(R.string.tObjective05));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t06));
		item.put("objective", getString(R.string.tObjective06));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t07));
		item.put("objective", getString(R.string.tObjective07));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t08));
		item.put("objective", getString(R.string.tObjective08));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t09));
		item.put("objective", getString(R.string.tObjective09));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t10));
		item.put("objective", getString(R.string.tObjective10));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t11));
		item.put("objective", getString(R.string.tObjective11));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t12));
		item.put("objective", getString(R.string.tObjective12));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t13));
		item.put("objective", getString(R.string.tObjective13));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t14));
		item.put("objective", getString(R.string.tObjective14));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("nome", getString(R.string.t15));
		item.put("objective", getString(R.string.tObjective15));
		list.add(item);
		
		/*Class c;
		try {
			c = Class.forName("android.R.string");
		Field f = c.getField("t0" + 1);
		Log.d("AHH", (String)f.toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("AHH", e.getMessage());
		}*/
//				
		String[] from = new String[] {"nome", "objective"};
		
		int[] to = new int[] {R.id.trophie_name, R.id.trophie_objective};

		setListAdapter(new SimpleAdapter(this,list, R.layout.trophies_row,from, to));

		
//		setListAdapter(new ArrayAdapter(this,R.id.trophie_name,R.layout.trophies_row, from));
	}
}
