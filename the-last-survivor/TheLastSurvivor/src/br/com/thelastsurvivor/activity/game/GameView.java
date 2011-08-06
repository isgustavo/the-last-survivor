package br.com.thelastsurvivor.activity.game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
 
public class GameView extends View implements Runnable {
 
    private static final String TAG = "GAMEVIEW";
 
    private static final int INTERVAL = 10;
    private Paint cPaint;
 
    private float x = 50;
 
    private float y = 50;
 
    private boolean running = true;
 
    FPSCounter fps;
 
    public GameView(Context context) {
        super(context);
        cPaint = new Paint();
        setFocusable(true);
        setClickable(true);
        setLongClickable(true);
 
        fps = new FPSCounter();
 
        // Set the background
        this.setBackgroundColor(Color.WHITE);
        Log.i(TAG, "game view created");
 
        Thread monitorThread = new Thread(this);
        monitorThread.setPriority(Thread.MIN_PRIORITY);
        monitorThread.start();
    }
 
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }
 
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled = false;
        Log.i(TAG, "key down");
 
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            y = y - 5;
            handled = true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            y = y + 5;
            handled = true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            x = x - 5;
            handled = true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            x = x + 5;
            handled = true;
        }
        return handled;
    }
 
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getRawX();
        y = event.getRawY();
        Log.i(TAG, "on touch");
 
        return super.onTouchEvent(event);
    }
 
    public void draw(Canvas canvas) {
        super.draw(canvas);
 
        cPaint.setColor(Color.BLUE);
        canvas.drawCircle(x, y, 15, cPaint);
 
        cPaint.setColor(Color.BLACK);
        canvas.drawText(fps.getFPS(), 20, 20, cPaint);
 
    }
 
    public void run() {
        while (running) {
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                Log.e(TAG, "main loop finished");
            }
            update();
            postInvalidate();
        }
    }
 
    private void update() {
        fps.update();
 
    }
 
    public void pause() {
        running = false;
    }
 
}