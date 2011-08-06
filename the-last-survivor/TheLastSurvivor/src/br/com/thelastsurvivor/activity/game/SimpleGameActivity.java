package br.com.thelastsurvivor.activity.game;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.simpleplayergame.SimplePlayerMode;
import br.com.thelastsurvivor.engine.view.EngineGameView;

public class SimpleGameActivity extends Activity implements SensorEventListener{

	TextView txtAcc;
	TextView txtX;
	TextView txtY;
	TextView txtZ;
	
	private SensorManager manager;
    private Sensor accelerometer;
    
    private EngineGameView view;
    private SimplePlayerMode engine;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	    manager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		//boolean accelerometerAvailable = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0;
		
	    accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		//if(!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME ) )
		   //accelerometerAvailable = false;
		
		//txtAcc = (TextView) this.findViewById(R.id.txtAccuracy);
		//txtX   = (TextView) this.findViewById(R.id.txtX);
		//txtY   = (TextView) this.findViewById(R.id.txtY);
		//txtZ   = (TextView) this.findViewById(R.id.txtZ);    
	    Log.d("SimpleGameActivity", "01");
		 init();
		 Log.d("SimpleGameActivity", "02");
	     setContentView(view);
	     Log.d("SimpleGameActivity", "03");
		
		//setContentView(R.layout.simple_game);
		
	/*	sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);              
		 accelerometer  = 
			        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); 
		 
		
		
		
		*/
	}
	
	public void init(){
		engine = new SimplePlayerMode();
    	view = new EngineGameView(this,engine);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		 // Mudou a acurácia do sensor
        //txtAcc.setText("= " + arg1);
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		this.engine.getSpacecraft().updateOrientation(event.values[1],event.values[0]);
		
/*		if((event.values[1]*10) > 10 && (event.values[1]*10) < 20 ){
			this.engine.getSpacecraft().setPosX(this.engine.getSpacecraft().getPosX()+4);
		}else if((event.values[1]*10) > 20 && (event.values[1]*10) < 30){
			this.engine.getSpacecraft().setPosX(this.engine.getSpacecraft().getPosX()+10);
		}else if((event.values[1]*10) > 30){
			this.engine.getSpacecraft().setPosX(this.engine.getSpacecraft().getPosX()+15);
		}else if((event.values[1]*10) > -10 && (event.values[1]*10) < -20 ){
			this.engine.getSpacecraft().setPosX(this.engine.getSpacecraft().getPosX()-4);
		}else if((event.values[1]*10) > -20 && (event.values[1]*10) < -30){
			this.engine.getSpacecraft().setPosX(this.engine.getSpacecraft().getPosX()-10);
		}else if((event.values[1]*10) > -30){
			this.engine.getSpacecraft().setPosX(this.engine.getSpacecraft().getPosX()-15);
		}
		if((event.values[0]*10) > 10 && (event.values[0]*10) < 20 ){
			this.engine.getSpacecraft().setPosY(this.engine.getSpacecraft().getPosY()+4);
		}else if((event.values[0]*10) > 20 && (event.values[0]*10) < 30){
			this.engine.getSpacecraft().setPosY(this.engine.getSpacecraft().getPosY()+10);
		}else if((event.values[0]*10) > 30){
			this.engine.getSpacecraft().setPosY(this.engine.getSpacecraft().getPosY()+15);	
		}else if((event.values[0]*10) < -10){
			this.engine.getSpacecraft().setPosY(this.engine.getSpacecraft().getPosY()-4);
		}else if((event.values[0]*10) < -20){
			this.engine.getSpacecraft().setPosY(this.engine.getSpacecraft().getPosY()-10);
		}else if((event.values[0]*10) < -30){
			this.engine.getSpacecraft().setPosY(this.engine.getSpacecraft().getPosY()-15);
		}
		*/
		
		 // Houve alguma aceleração no sensor
        //txtX.setText("= " + event.values[0]);
        //txtY.setText("= " + event.values[1]);
        //txtZ.setText("= " + event.values[2]);
		
	}
	
	 @Override
	 public void onPause() {
	        super.onPause();
	        manager.unregisterListener(this); 
	    }

	    @Override
	    public void onResume() {
	        super.onResume();
	        manager.registerListener(this, 
	            accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	    }

}
