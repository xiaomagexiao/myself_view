package com.example.myselfview.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.myself_view.R;
import com.example.myselfview.view.CircleImageView;

public class CircleImgLocalActivity extends Activity{

	private CircleImageView top_image;
	private CircleImageView bottom_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.circle_img_show_activity);
		
		this.top_image = (CircleImageView) this.findViewById(R.id.top_image);
		this.bottom_image = (CircleImageView) this.findViewById(R.id.bottom_image);
		
		this.top_image.setImageResource(R.drawable.hugh);
		this.bottom_image.setImageResource(R.drawable.hugh);
		
	}
}
