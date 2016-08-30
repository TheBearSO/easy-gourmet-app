package com.easygourmet.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.easygourmet.main.R;

public class Splash extends Activity {

	/** Duration of wait **/
	private final int SPLASH_DISPLAY_LENGTH = 750;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splash);

		// Setea la letra customizada del titulo de la app
		TextView textView = (TextView) findViewById(R.id.splash_title);
		Typeface typeFace = Typeface.createFromAsset(Splash.this.getAssets(), "fonts/Pacifico.ttf");
		textView.setTypeface(typeFace);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent mainIntent = new Intent(Splash.this, MenuPrincipal.class);
				Splash.this.startActivity(mainIntent);
				Splash.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}

}
