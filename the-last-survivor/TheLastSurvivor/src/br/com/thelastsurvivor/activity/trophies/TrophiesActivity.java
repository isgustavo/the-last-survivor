package br.com.thelastsurvivor.activity.trophies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.util.MyAudioPlayer;

public class TrophiesActivity extends Activity{
	
//	private List<Trophies> trophies; 
	private MyAudioPlayer audioPlayer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//audioPlayer = new MyAudioPlayer(this, R.raw.sol);
		//audioPlayer.start();
		
		setContentView(R.layout.trophies_player_view);
		
		init();
		
		
		//getTrophies();
	}

	private void init() {
		ImageView image1 = (ImageView)findViewById(R.id.trophies_1);
		image1.setAlpha(70);
		
		ImageView image2 = (ImageView)findViewById(R.id.trophies_2);
		image2.setAlpha(70);
		
		ImageView image3 = (ImageView)findViewById(R.id.trophies_3);
		image3.setAlpha(70);
		
		ImageView image4 = (ImageView)findViewById(R.id.trophies_4);
		image4.setAlpha(70);
		
		ImageView image5 = (ImageView)findViewById(R.id.trophies_5);
		image5.setAlpha(70);
		
		ImageView image6 = (ImageView)findViewById(R.id.trophies_6);
		image6.setAlpha(70);
		
		ImageView image7 = (ImageView)findViewById(R.id.trophies_7);
		image7.setAlpha(70);
		
		ImageView image8 = (ImageView)findViewById(R.id.trophies_8);
		image8.setAlpha(70);
		
		ImageView image9 = (ImageView)findViewById(R.id.trophies_9);
		image9.setAlpha(70);
		
		ImageView image10 = (ImageView)findViewById(R.id.trophies_10);
		image10.setAlpha(70);
		
		ImageView image11 = (ImageView)findViewById(R.id.trophies_11);
		image11.setAlpha(70);
		
		ImageView image12 = (ImageView)findViewById(R.id.trophies_12);
		image12.setAlpha(70);
		
		ImageView image13 = (ImageView)findViewById(R.id.trophies_13);
		image13.setAlpha(70);
		
		ImageView image14 = (ImageView)findViewById(R.id.trophies_14);
		image14.setAlpha(70);
		
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
		item.put("name", getString(R.string.t01));
		item.put("objective", getString(R.string.tObjective01));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t02));
		item.put("objective", getString(R.string.tObjective02));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t03));
		item.put("objective", getString(R.string.tObjective03));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t04));
		item.put("objective", getString(R.string.tObjective04));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t06));
		item.put("objective", getString(R.string.tObjective06));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t07));
		item.put("objective", getString(R.string.tObjective07));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t08));
		item.put("objective", getString(R.string.tObjective08));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t09));
		item.put("objective", getString(R.string.tObjective09));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t10));
		item.put("objective", getString(R.string.tObjective10));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t11));
		item.put("objective", getString(R.string.tObjective11));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t12));
		item.put("objective", getString(R.string.tObjective12));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t13));
		item.put("objective", getString(R.string.tObjective13));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t14));
		item.put("objective", getString(R.string.tObjective14));
		list.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", getString(R.string.t15));
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
		String[] from = new String[] {"name", "objective"};
		
		int[] to = new int[] {R.id.trophie_name, R.id.trophie_objective};

		//setListAdapter(new SimpleAdapter(this,list, R.layout.trophies_row, from, to));

		
//		setListAdapter(new ArrayAdapter(this,R.id.trophie_name,R.layout.trophies_row, from));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		audioPlayer.fechar();
	}
}
