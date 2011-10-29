package br.com.thelastsurvivor.activity.rank;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.model.rank.Rank;
import br.com.thelastsurvivor.provider.rank.RankProvider;
import br.com.thelastsurvivor.util.DateTimeUtil;

public class RankActivity extends ListActivity {
	
	//public static final int EXIT_GAME = 0;
	
	private ListAdapter adapter;
	
	private Integer idPlayer;
	private List<Rank> rank;
	ListView listView;  

	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = RankActivity.this;
		
		rank = new ArrayList<Rank>();
		
		if (isThereRank()) {
			
			setContentView(R.layout.rank_player_view);  
			
			
			setListAdapter(new ListRankAdapter(rank, this));
		}
		
	}
	
	public boolean isThereRank(){
		
		
		this.rank.addAll(loadRank());
		
		if(this.rank.size() == 0){
			return false;
		}
		return true;
	}
	
	public List<Rank> loadRank(){
		List<Rank> rankList = new ArrayList<Rank>();
		
		Cursor c = this.getContentResolver().  
		query(RankProvider.CONTENT_URI, null, null, null, null);  

		if (c.getCount() == 0) {
			return new ArrayList<Rank>();
		}else{
			while(c.moveToNext()){
			rankList.add(new Rank(c.getString(1),
					c.getInt(2), DateTimeUtil.stringToDate(c.getString(3)),
					c.getInt(4)));
			}
		}
		
	
		Collections.sort(rankList, new Comparator<Rank>(){

			@Override
			public int compare(Rank rank, Rank rank2) {
				return rank.getPoint().compareTo(rank2.getPoint());
			}}); 
		
		return rankList;
	}
	
}
