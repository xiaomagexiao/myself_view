package com.example.myselfview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.myself_view.R;

public class CircleImgShowActivity extends Activity implements OnClickListener {

	private Button local_pic_button;
	private TextView net_pic_url;
	private Button net_pic_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.circle_img_activity);
		
		this.local_pic_button = (Button) this.findViewById(R.id.local_pic_button);
		this.net_pic_button = (Button) this.findViewById(R.id.net_pic_button);
		this.net_pic_url = (TextView) this.findViewById(R.id.net_pic_url);
		
		this.local_pic_button.setOnClickListener(this);
		this.net_pic_button.setOnClickListener(this);
		this.net_pic_url.setText("http://img.sc115.com/uploads/sc/130311/2013031119370038.jpg");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.local_pic_button:
			this.startActivity(new Intent(this, CircleImgLocalActivity.class));
			break;
		case R.id.net_pic_button:
			Intent intent = new Intent(this, CircleImgNetActivity.class);
			intent.putExtra("url", this.net_pic_url.getText());
			
			this.startActivity(intent);
			break;

		default:
			break;
		}
	}
}
