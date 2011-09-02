package br.com.thelastsurvivor.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FT2FontTextView extends TextView {

	protected Context context;
	protected String ttfName;

	public FT2FontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		for (int i = 0; i < attrs.getAttributeCount(); i++) {
			this.ttfName = attrs
					.getAttributeValue(
							"http://schemas.android.com/apk/res/br.com.thelastsurvivor",
							"ttf_name");
			init();
		}
	}

	private void init() {
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/"
				+ ttfName);
		setTypeface(font);
	}

	@Override
	public void setTypeface(Typeface tf) {
		super.setTypeface(tf);
	}

}
