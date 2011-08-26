package br.com.thelastsurvivor.engine.game;

import android.content.Context;
import android.graphics.drawable.Drawable;
import br.com.thelastsurvivor.R;

public final class SpacecraftImage {

	
	private static Drawable _up;
	public static final Integer UP = 1;
	
	private static Drawable _middle_right_up;
	public static final Integer MIDDLE_RIGHT_UP = 2;
	
	private static Drawable _right;
	public static final Integer RIGHT = 3;
	
	private static Drawable middle_right_down;
	private static Drawable down;
	private static Drawable middle_left_down;
	private static Drawable left;
	private static Drawable middle_left_up;
	
	
	public SpacecraftImage(Context context){
		this._up = context.getResources().getDrawable(R.drawable.spacecraft);
		this._middle_right_up = context.getResources().getDrawable(R.drawable.middle_right);
		this._right = context.getResources().getDrawable(R.drawable.right);
		this.middle_right_down = context.getResources().getDrawable(R.drawable.middle_right_down);
		this.down = context.getResources().getDrawable(R.drawable.down);
		this.middle_left_down = context.getResources().getDrawable(R.drawable.middle_left_down);
		this.left = context.getResources().getDrawable(R.drawable.left);
		this.middle_left_up = context.getResources().getDrawable(R.drawable.middle_left_up);
	}


	public static Drawable get_up() {
		return _up;
	}


	public static Drawable get_middle_right_up() {
		return _middle_right_up;
	}


	public static Drawable get_right() {
		return _right;
	}
	
	
	
	
	
}
