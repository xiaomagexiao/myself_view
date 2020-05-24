package com.example.myselfview.sections.wave;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myself_view.R;

public class WaveActivity extends Activity {

	private Button button;
	private RippleLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wave_activity);

		this.layout = (RippleLayout) findViewById(R.id.ripple_layout);
		this.button = (Button) findViewById(R.id.centerButton);
		this.button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (layout.isRippleAnimationRunning()) {
					layout.stopRippleAnimation();
				} else {
					layout.startRippleAnimation();
				}
			}
		});
	}

}
