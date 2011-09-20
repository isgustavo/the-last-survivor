package br.com.thelastsurvivor.activity.trophies;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.model.trophies.Trophies;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;
import br.com.thelastsurvivor.util.DateTimeUtil;
import br.com.thelastsurvivor.util.MyAudioPlayer;

public class TrophiesActivity extends Activity{
	
	private List<Trophies> trophies; 
	private Boolean flag;
	
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
		
		flag = new Boolean(true);
		
		this.trophies = loadTrophies();
		
		for (Trophies trophie : trophies) {
			switch (trophie.getId()) {
			case 1:
				Drawable image = getResources().getDrawable(R.drawable.trophies_01);
				
				if(trophie.getDateAchieved() == null){
					image.setAlpha(70);
				}
				
				Button trophie1 = (Button)findViewById(R.id.trophies_1);
				trophie1.setBackgroundDrawable(image);
				trophie1.setOnClickListener(buttonListener1);
			break;
			case 2:
				Drawable image2 = getResources().getDrawable(R.drawable.trophies_02);
				
				if(trophie.getDateAchieved() == null){
					image2.setAlpha(70);
				}
				
				Button trophie2 = (Button)findViewById(R.id.trophies_2);
				trophie2.setBackgroundDrawable(image2);
				trophie2.setOnClickListener(buttonListener2);
			break;
			case 3:
				Drawable image3 = getResources().getDrawable(R.drawable.trophies_03);
				
				if(trophie.getDateAchieved() == null){
					image3.setAlpha(70);
				}
				
				Button trophie3 = (Button)findViewById(R.id.trophies_3);
				trophie3.setBackgroundDrawable(image3);
				trophie3.setOnClickListener(buttonListener3);
			break;
			case 4:
				Drawable image4 = getResources().getDrawable(R.drawable.trophies_04);
				
				if(trophie.getDateAchieved() == null){
					image4.setAlpha(70);
				}
				
				Button trophie4 = (Button)findViewById(R.id.trophies_4);
				trophie4.setBackgroundDrawable(image4);
				trophie4.setOnClickListener(buttonListener4);
			break;
			case 5:
				Drawable image5 = getResources().getDrawable(R.drawable.trophies_05);
				
				if(trophie.getDateAchieved() == null){
					image5.setAlpha(70);
				}
				
				Button trophie5 = (Button)findViewById(R.id.trophies_5);
				trophie5.setBackgroundDrawable(image5);
				trophie5.setOnClickListener(buttonListener5);
			break;
			case 6:
				Drawable image6 = getResources().getDrawable(R.drawable.trophies_06);
				
				if(trophie.getDateAchieved() == null){
					image6.setAlpha(70);
				}
				
				Button trophie6 = (Button)findViewById(R.id.trophies_6);
				trophie6.setBackgroundDrawable(image6);
				trophie6.setOnClickListener(buttonListener6);
			break;
			case 7:
				Drawable image7 = getResources().getDrawable(R.drawable.trophies_07);
				
				if(trophie.getDateAchieved() == null){
					image7.setAlpha(70);
				}
				
				Button trophie7 = (Button)findViewById(R.id.trophies_7);
				trophie7.setBackgroundDrawable(image7);
				trophie7.setOnClickListener(buttonListener7);
			break;
			case 8:
				Drawable image8 = getResources().getDrawable(R.drawable.trophies_08);
				
				if(trophie.getDateAchieved() == null){
					image8.setAlpha(70);
				}
				
				Button trophie8 = (Button)findViewById(R.id.trophies_8);
				trophie8.setBackgroundDrawable(image8);
				trophie8.setOnClickListener(buttonListener8);
			break;
			case 9:
				Drawable image9 = getResources().getDrawable(R.drawable.trophies_09);
				
				if(trophie.getDateAchieved() == null){
					image9.setAlpha(70);
				}
				
				Button trophie9 = (Button)findViewById(R.id.trophies_9);
				trophie9.setBackgroundDrawable(image9);
				trophie9.setOnClickListener(buttonListener9);
			break;
			case 10:
				Drawable image10 = getResources().getDrawable(R.drawable.trophies_10);
				
				if(trophie.getDateAchieved() == null){
					image10.setAlpha(70);
				}
				
				Button trophie10 = (Button)findViewById(R.id.trophies_10);
				trophie10.setBackgroundDrawable(image10);
				trophie10.setOnClickListener(buttonListener10);
			break;
			case 11:
				Drawable image11 = getResources().getDrawable(R.drawable.trophies_11);
				
				if(trophie.getDateAchieved() == null){
					image11.setAlpha(70);
				}
				
				Button trophie11 = (Button)findViewById(R.id.trophies_11);
				trophie11.setBackgroundDrawable(image11);
				trophie11.setOnClickListener(buttonListener11);
			break;
			case 12:
				Drawable image12 = getResources().getDrawable(R.drawable.trophies_12);
				
				if(trophie.getDateAchieved() == null){
					image12.setAlpha(70);
				}
				
				Button trophie12 = (Button)findViewById(R.id.trophies_12);
				trophie12.setBackgroundDrawable(image12);
				trophie12.setOnClickListener(buttonListener12);
			break;
			case 13:
				Drawable image13 = getResources().getDrawable(R.drawable.trophies_13);
				
				if(trophie.getDateAchieved() == null){
					image13.setAlpha(70);
				}
				
				Button trophie13 = (Button)findViewById(R.id.trophies_13);
				trophie13.setBackgroundDrawable(image13);
				trophie13.setOnClickListener(buttonListener13);
			break;
			case 14:
				Drawable image14 = getResources().getDrawable(R.drawable.trophies_14);
				
				if(trophie.getDateAchieved() == null){
					image14.setAlpha(70);
				}
				
				Button trophie14 = (Button)findViewById(R.id.trophies_14);
				trophie14.setBackgroundDrawable(image14);
				trophie14.setOnClickListener(buttonListener14);
			break;

			default:
				break;
			}
		}
		
		Drawable image15 = getResources().getDrawable(R.drawable.untitled_image);
		Button trophie15 = (Button)findViewById(R.id.trophies_15);
		trophie15.setBackgroundDrawable(image15);
		

	}
	
	public List<Trophies> loadTrophies(){
		
		List<Trophies> trophies = new ArrayList<Trophies>();
		
		Cursor c = this.getContentResolver().query(TrophiesProvider.CONTENT_URI, 
				null, null , null, null);
	
		while(c.moveToNext()){
			try{
				trophies.add(new Trophies(c.getInt(0), DateTimeUtil.stringToDate(c.getString(1))));
			
			}catch(NullPointerException e){
				trophies.add(new Trophies(c.getInt(0)));
			}
		}

		return trophies;
	}
	
	private OnClickListener buttonListener1 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(1);
        }  
	};
	private OnClickListener buttonListener2 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(2);
        }  
	};
	private OnClickListener buttonListener3 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(3);
        }  
	};
	private OnClickListener buttonListener4 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(4);
        }  
	};
	private OnClickListener buttonListener5 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(5);
        }  
	};
	private OnClickListener buttonListener6 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(6);
        }  
	};
	private OnClickListener buttonListener7 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(7);
        }  
	};
	private OnClickListener buttonListener8 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(8);
        }  
	};
	private OnClickListener buttonListener9 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(9);
        }  
	};
	private OnClickListener buttonListener10 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(10);
        }  
	};
	private OnClickListener buttonListener11 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(11);
        }  
	};
	private OnClickListener buttonListener12 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(12);
        }  
	};
	private OnClickListener buttonListener13 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(13);
        }  
	};
	private OnClickListener buttonListener14 = new OnClickListener() {  
        public void onClick(View v) {  
        	renderedTrophies(14);
        }  
	};

	
	public void renderedTrophies(Integer trophie){
		
		if(!this.flag){
			return;
		}
		
		this.flag = false; 
		
		Drawable image15 = null;
		
		Button trophie15 = (Button)findViewById(R.id.trophies_15);
		
		switch (trophie) {
		case 1:
			image15 = getResources().getDrawable(R.drawable.trophies_01);
			image15.setAlpha(1000);
			
		break;
		case 2:
			image15 = getResources().getDrawable(R.drawable.trophies_02);
			image15.setAlpha(1000);
			
		break;
		case 3:
			image15 = getResources().getDrawable(R.drawable.trophies_03);
			image15.setAlpha(1000);
			
		break;
		case 4:
			image15 = getResources().getDrawable(R.drawable.trophies_04);
			image15.setAlpha(1000);
			
		break;
		case 5:
			image15 = getResources().getDrawable(R.drawable.trophies_05);
			image15.setAlpha(1000);
			
		break;
		case 6:
			image15 = getResources().getDrawable(R.drawable.trophies_06);
			image15.setAlpha(1000);
			
		break;
		case 7:
			image15 = getResources().getDrawable(R.drawable.trophies_07);
			image15.setAlpha(1000);
			
		break;
		case 8:
			image15 = getResources().getDrawable(R.drawable.trophies_08);
			image15.setAlpha(1000);
			
		break;
		case 9:
			image15 = getResources().getDrawable(R.drawable.trophies_09);
			image15.setAlpha(1000);
			
		break;
		case 10:
			image15 = getResources().getDrawable(R.drawable.trophies_10);
			image15.setAlpha(1000);
			
		break;
		case 11:
			image15 = getResources().getDrawable(R.drawable.trophies_11);
			image15.setAlpha(1000);
			
		break;
		case 12:
			image15 = getResources().getDrawable(R.drawable.trophies_12);
			image15.setAlpha(1000);
			
		break;
		case 13:
			image15 = getResources().getDrawable(R.drawable.trophies_13);
			image15.setAlpha(1000);
			
		break;
		case 14:
			image15 = getResources().getDrawable(R.drawable.trophies_14);
			image15.setAlpha(1000);
			
		break;

		default:
			break;
		}
		
		trophie15.setBackgroundDrawable(image15);
		trophie15.setOnClickListener(buttonListener15);
		
	}
	
	private OnClickListener buttonListener15 = new OnClickListener() {  
        public void onClick(View v) {  
        	Button trophie15 = (Button)findViewById(R.id.trophies_15);
        	Drawable image = trophie15.getBackground();
        	image.setAlpha(70);
        	trophie15.setBackgroundDrawable(null);
        	
        	flag = true;
        }  
	};
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//audioPlayer.fechar();
	}
}
