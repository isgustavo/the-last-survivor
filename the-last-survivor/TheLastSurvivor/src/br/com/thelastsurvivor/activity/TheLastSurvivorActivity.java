package br.com.thelastsurvivor.activity;

import br.com.thelastsurvivor.R;
import android.app.Activity;
import android.os.Bundle;

public class TheLastSurvivorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}