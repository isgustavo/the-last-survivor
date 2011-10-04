package br.com.thelastsurvivor.activity.game.multiplayermode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.thelastsurvivor.R;

public class FeaturesGameActivity extends Activity{

	private static final int RED = 1;
	private static final int BLUE = 2;
	private static final int YELLOW = 3;
	private static final int GREEN = 4;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.feature_view);
		
		init();

	}
	
	
	public void init(){
		                                            
		Button buttonRed = (Button)findViewById(R.id.buttonRed);  
		buttonRed.setOnClickListener(buttonListenerRed);  
		
		Button buttonBlue = (Button)findViewById(R.id.buttonBlue);  
		buttonBlue.setOnClickListener(buttonListenerBlue); 
		
		Button buttonYellow = (Button)findViewById(R.id.buttonYellow);  
		buttonYellow.setOnClickListener(buttonListenerYellow); 
		
		Button buttonGreen = (Button)findViewById(R.id.buttonGreen);  
		buttonGreen.setOnClickListener(buttonListenerGreen); 
		
	
	}
	
	private OnClickListener buttonListenerRed = new OnClickListener() {  
        public void onClick(View v) {  
        	
        Intent i = new Intent();
        i.putExtra("color", ""+RED);
        setResult(RESULT_OK, i);
        finish();
     
        }  
	};  
	
	private OnClickListener buttonListenerBlue = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	Intent i = new Intent();
            i.putExtra("color", ""+BLUE);
            setResult(RESULT_OK, i);
            finish();
     
        }  
	};  
	
	private OnClickListener buttonListenerYellow = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	Intent i = new Intent();
            i.putExtra("color", ""+YELLOW);
            setResult(RESULT_OK, i);
            finish();
     
        }  
	};  
	
	private OnClickListener buttonListenerGreen = new OnClickListener() {  
        public void onClick(View v) {  
        	
        	Intent i = new Intent();
            i.putExtra("color", ""+GREEN);
            setResult(RESULT_OK, i);
            finish();
     
        }  
	};  
	
}
