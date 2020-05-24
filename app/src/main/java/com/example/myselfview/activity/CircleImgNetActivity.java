package com.example.myselfview.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.myself_view.R;
import com.example.myselfview.view.CircleImageView;
import com.whaty.base.asyncimage.MCCacheManager;

public class CircleImgNetActivity extends Activity {

	private CircleImageView top_image;
	private CircleImageView bottom_image;

	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.circle_img_show_activity);

		this.top_image = (CircleImageView) this.findViewById(R.id.top_image);
		this.bottom_image = (CircleImageView) this
				.findViewById(R.id.bottom_image);

		this.url = getIntent().getStringExtra("url");

		//this.url = "/storage/emulated/0/whaty/smallPic/1428938373772.jpg";
	
		//this.top_image.setImageURI(Uri.parse(url));
		//this.bottom_image.setImageURI(Uri.parse(url));

		this.top_image.setImageUrl(this.url, MCCacheManager.getInstance().getImageLoader());
		this.bottom_image.setImageUrl(this.url, MCCacheManager.getInstance().getImageLoader());
	}
}
