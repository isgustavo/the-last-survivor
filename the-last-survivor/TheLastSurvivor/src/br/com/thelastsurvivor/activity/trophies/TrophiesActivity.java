package br.com.thelastsurvivor.activity.trophies;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.simplemode.SimpleGameActivity;
import br.com.thelastsurvivor.model.trophies.Trophies;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;
import br.com.thelastsurvivor.util.DateTimeUtil;
import br.com.thelastsurvivor.util.FT2FontTextView;
import br.com.thelastsurvivor.util.MyAudioPlayer;

public class TrophiesActivity extends Activity{
	
	private Vibrator vibrator;
	
	private List<Trophies> trophies; 
	private Dialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.trophies_player_view);
		
		init();
	}

	private void init() {
		
		this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		this.trophies = loadTrophies();
		
		for (Trophies trophie : trophies) {
			switch (trophie.getId()) {
			case 1:
				final Trophies trophieSelect = trophie;
				Drawable image = getResources().getDrawable(R.drawable.trophies_01_v3);
				
				if(trophie.getDateAchieved() == null){
					image.setAlpha(70);
				}
				
				Button trophie1 = (Button)findViewById(R.id.trophies_1);
				trophie1.setBackgroundDrawable(image);
				trophie1.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophieSelect.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_01_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t01));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective01));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophieSelect.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophieSelect.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});
				
			break;
			case 2:
				final Trophies trophie2Select = trophie;
				
				Drawable image2 = getResources().getDrawable(R.drawable.trophies_02_v3);
				
				if(trophie.getDateAchieved() == null){
					image2.setAlpha(70);
				}
				
				Button trophie2 = (Button)findViewById(R.id.trophies_2);
				trophie2.setBackgroundDrawable(image2);
				trophie2.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie2Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_02_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t02));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective02));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie2Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie2Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});
			break;
			case 3:
				final Trophies trophie3Select = trophie;
				Drawable image3 = getResources().getDrawable(R.drawable.trophies_03_v3);
				
				if(trophie.getDateAchieved() == null){
					image3.setAlpha(70);
				}
				
				Button trophie3 = (Button)findViewById(R.id.trophies_3);
				trophie3.setBackgroundDrawable(image3);
				trophie3.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie3Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_03_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t03));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective03));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie3Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie3Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});

			break;
			case 4:
				final Trophies trophie4Select = trophie;
				Drawable image4 = getResources().getDrawable(R.drawable.trophies_04_v3);
				
				if(trophie.getDateAchieved() == null){
					image4.setAlpha(70);
				}
				
				Button trophie4 = (Button)findViewById(R.id.trophies_4);
				trophie4.setBackgroundDrawable(image4);
				trophie4.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie4Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_04_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t04));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective04));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie4Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie4Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});

			break;
			case 5:
				final Trophies trophie5Select = trophie;
				Drawable image5 = getResources().getDrawable(R.drawable.trophies_05_v3);
				
				if(trophie.getDateAchieved() == null){
					image5.setAlpha(70);
				}
				
				Button trophie5 = (Button)findViewById(R.id.trophies_5);
				trophie5.setBackgroundDrawable(image5);
				trophie5.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie5Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_05_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t05));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective05));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie5Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie5Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});

			break;
			case 6:
				final Trophies trophie6Select = trophie;
				Drawable image6 = getResources().getDrawable(R.drawable.trophies_06_v3);
				
				if(trophie.getDateAchieved() == null){
					image6.setAlpha(70);
				}
				
				Button trophie6 = (Button)findViewById(R.id.trophies_6);
				trophie6.setBackgroundDrawable(image6);
				trophie6.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie6Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_06_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t06));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective06));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie6Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie6Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});

			break;
			case 7:
				final Trophies trophie7Select = trophie;
				Drawable image7 = getResources().getDrawable(R.drawable.trophies_07_v3);
				
				if(trophie.getDateAchieved() == null){
					image7.setAlpha(70);
				}
				
				Button trophie7 = (Button)findViewById(R.id.trophies_7);
				trophie7.setBackgroundDrawable(image7);
				trophie7.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie7Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_07_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t07));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective07));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie7Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie7Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});

			break;
			case 8:
				final Trophies trophie8Select = trophie;
				Drawable image8 = getResources().getDrawable(R.drawable.trophies_08_v3);
				
				if(trophie.getDateAchieved() == null){
					image8.setAlpha(70);
				}
				
				Button trophie8 = (Button)findViewById(R.id.trophies_8);
				trophie8.setBackgroundDrawable(image8);
				trophie8.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie8Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_08_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t08));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective08));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie8Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie8Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});

			break;
			case 9:
				final Trophies trophie9Select = trophie;
				Drawable image9 = getResources().getDrawable(R.drawable.trophies_09_v3);
				
				if(trophie.getDateAchieved() == null){
					image9.setAlpha(70);
				}
				
				Button trophie9 = (Button)findViewById(R.id.trophies_9);
				trophie9.setBackgroundDrawable(image9);
				trophie9.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie9Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_09_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t09));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective09));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie9Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie9Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});

			break;
			case 10:
				final Trophies trophie10Select = trophie;
				Drawable image10 = getResources().getDrawable(R.drawable.trophies_10_v3);
				
				if(trophie.getDateAchieved() == null){
					image10.setAlpha(70);
				}
				
				Button trophie10 = (Button)findViewById(R.id.trophies_10);
				trophie10.setBackgroundDrawable(image10);
				trophie10.setOnClickListener(new OnClickListener() {  
			        public void onClick(View v) {  
			        	vibrator.vibrate(80);
			        	dialog = new Dialog(TrophiesActivity.this, R.style.PauseGameDialogTheme){
				    		
				    		public boolean onKeyDown(int keyCode, KeyEvent event){
				    			ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
				    			
				    			if(trophie10Select.getDateAchieved() == null){
				    				image.setAlpha(70);
				    			}
				    			dialog.cancel();
				    			return true;
				    		}

				    	};
				    	
						dialog.setContentView(R.layout.trophies_details_view);
						   
						ImageView image = (ImageView)dialog.findViewById(R.id.image_trophy);
						image.setImageDrawable(getResources().getDrawable(R.drawable.trophies_10_v3));
						
						FT2FontTextView name = (FT2FontTextView)dialog.findViewById(R.id.trophy_name);
						name.setText(getResources().getText(R.string.t10));
						
						FT2FontTextView details = (FT2FontTextView)dialog.findViewById(R.id.trophy_detail);
						details.setText(getResources().getText(R.string.tObjective10));
						
						FT2FontTextView achieved = (FT2FontTextView)dialog.findViewById(R.id.trophy_date);
						
						if(trophie10Select.getDateAchieved() == null){
							achieved.setText("-/-/-");
						}else{
							achieved.setText(DateTimeUtil.DateToString(trophie10Select.getDateAchieved()));
						}
						
						
						dialog.show();
						
					
			        }  
				});

			break;

			default:
				break;
			}
		}
		
		

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
		
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//audioPlayer.fechar();
	}
}
