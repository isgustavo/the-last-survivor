package br.com.thelastsurvivor.activity.game;

import java.text.DecimalFormat;

import android.os.SystemClock;
 
public class FPSCounter {
 
    public static final DecimalFormat format = new DecimalFormat("###0.00");
 
    private float frames;
    private String fps;
    private long start;
    private long current;
 
    public FPSCounter() {
        start = SystemClock.uptimeMillis();
        frames = 0;
        fps = "???";
    }
 
    public void update() {
        frames ++;
        current = SystemClock.uptimeMillis();
        if( current - start > 1000 ){
            fps = format.format(frames);
            frames = 0;
            start = current;
        }
    }
 
    public String getFPS() {
        return fps;
    }
}