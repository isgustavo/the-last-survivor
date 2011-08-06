package br.com.thelastsurvivor.activity.game;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/*
 * O programa está configurado para usar o dispositivo. Se quiser usar o SensorSimulator, 
 * comente as linhas marcadas como "Real device" e descomente as marcadas como "SensorSimulator"
 */

public class Principal extends Activity implements SensorEventListener {
        
        private SensorManager sensorManager; // real device
        //private SensorManagerSimulator sensorManager; // SensorSimulator
    private Sensor accelerometer;
    private TextView txtAcc;
    private TextView txtX;
    private TextView txtY;
    private TextView txtZ;
     
    
    private GameView view;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE); // Real device
        //sensorManager =  SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE); // SensorSimulator
        accelerometer  = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); 
        //sensorManager.connectSimulator(); // SensorSimulator
       // setContentView(R.layout.list);
       // txtAcc = (TextView) this.findViewById(R.id.txtAccuracy);
       // txtX   = (TextView) this.findViewById(R.id.txtX);
       // txtY   = (TextView) this.findViewById(R.id.txtY);
       // txtZ   = (TextView) this.findViewById(R.id.txtZ);   
        
        view = new GameView(this);
        setContentView(view);
    }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
                // Mudou a acurácia do sensor
                txtAcc.setText("= " + arg1);
        }

        public void onSensorChanged(SensorEvent event) {
                // Houve alguma aceleração no sensor
                // Normalmente, é preciso descontar a gravidade... Mas vamos deixar para depois
                txtX.setText("= " + event.values[0]);
                txtY.setText("= " + event.values[1]);
                txtZ.setText("= " + event.values[2]);
        }

        @Override
        protected void onPause() {
                // Vamos economizar baterias, pois a activity não está com o foco
                super.onPause();
                sensorManager.unregisterListener(this); // deixa de receber avisos
                view.pause();
        }
        
        @Override
        protected void onResume() {
                // Temos que reiniciar o sensor
                super.onResume();
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        
        
}
