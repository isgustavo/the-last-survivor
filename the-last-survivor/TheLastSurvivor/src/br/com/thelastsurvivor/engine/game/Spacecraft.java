package br.com.thelastsurvivor.engine.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import br.com.thelastsurvivor.engine.IDrawControllable;

public class Spacecraft implements IDrawControllable {
	
	
	private String identifier;
	private Bitmap image;
	
	private Float orientationX;
	private Float orientationY;
	
	private Paint cPaint;
	
	private Integer posX;
	private Integer posY;
	
	
	public Spacecraft(){
		posX = 50;
		posY = 50;
		orientationX = 0f;
		orientationY = 0f;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void update() {
		getNewPosition();

	}
	
	@Override
	public void updateOrientation(float orientationX, float orientationY){
		this.orientationX = orientationX;
		this.orientationY = orientationY;
	}

	@Override
	public void draw(Canvas c) {
		Log.d("Spacecraft", "01");
		cPaint = new Paint();
		cPaint.setColor(Color.BLUE);
	    c.drawCircle(posX, posY, 15, cPaint);
		
	}
	
	
	private void getNewPosition(){
		
		/*if((orientationX*10) > 10 && (orientationX*10) < 20 ){
			this.posX += 4;
		}*/if((this.orientationX*10) > 0 && (this.orientationX*10) < 20){
			this.posX += 2;
		}else if((this.orientationX*10) > 20){
			this.posX += 5;
		}else /*if((event.values[1]*10) > -10 && (event.values[1]*10) < -20 ){
			this.engine.getSpacecraft().setPosX(this.engine.getSpacecraft().getPosX()-4);
		}else if((this.orientationX*10) > -20 && (this.orientationX*10) < -30){
			this.posX -= 2;
		}else */if((this.orientationX*10) < 0 && (this.orientationX*10) > -20){
			this.posX -= 2;
		}else if((this.orientationX*10) < -20){
			this.posX -= 5;
		}
		
		
	/*	if((this.orientationY*10) > 20 && (this.orientationY*10) < 30){
			this.posY += 10;
		}else if((this.orientationY*10) > 30){
			this.posY += 10;	
		}else if((this.orientationY*10) < 0 && (this.orientationY*10) > -30){
			this.posY -= 2;*/
		if((this.orientationY*10) < -5){
			this.posY -= 5;
		}else if((this.orientationY*10) > 20){
			this.posY += 5;
		}
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Paint getcPaint() {
		return cPaint;
	}

	public void setcPaint(Paint cPaint) {
		this.cPaint = cPaint;
	}


	
	
	
	

	

}
