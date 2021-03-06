package br.com.thelastsurvivor.activity.rank;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.simplemode.SavedGameActivity;
import br.com.thelastsurvivor.model.rank.Rank;
import br.com.thelastsurvivor.util.DateTimeUtil;
import br.com.thelastsurvivor.util.FT2FontTextView;

public class ListRankAdapter extends BaseAdapter{

	private Context context;
	private SavedGameActivity actitivy;
	private List<Rank> rankList;
	LayoutInflater inflater;
	
	public ListRankAdapter(List<Rank> rank, Context activity){
		//this.context = context;
		this.rankList = rank;
		this.context = activity;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Rank rank = rankList.get(position);
		 
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
				
		View v = inflater.inflate(R.layout.rank_list_view, null);
		
		ImageView image = (ImageView) v.findViewById(R.id.typeGame);
		if(rank.getType() == Rank.SIMPLE){
			image.setImageResource(R.drawable.spacecraft_image_list);
		}else{
			image.setImageResource(R.drawable.online_image);
		}
		
		
		FT2FontTextView rankId = (FT2FontTextView) v.findViewById(R.id.rank_name);
		rankId.setText(rank.getIdentifier());
		
		FT2FontTextView rankScore = (FT2FontTextView) v.findViewById(R.id.rank_score);
		rankScore.setText(rank.getPoint()+"");
		
		FT2FontTextView rankDate = (FT2FontTextView) v.findViewById(R.id.rank_date);
		rankDate.setText(DateTimeUtil.DateToString(rank.getDate())+"");
		
		return v;
	}
	
	@Override
	public int getCount() {
		return rankList.size();
	}

	@Override
	public Object getItem(int position) {
		return rankList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	
	
}
