package com.example.myselfview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.myself_view.R;
import com.example.myselfview.activity.CircleImgShowActivity;
import com.example.myselfview.activity.DialogActivity;
import com.example.myselfview.activity.PicActivity;
import com.example.myselfview.activity.PicFullScreenActivity;
import com.example.myselfview.activity.ScoreActivity;
import com.example.myselfview.activity.ScrollListPagerActivity;
import com.example.myselfview.sections.bind.BindServiceActivity;
import com.example.myselfview.sections.camer.CamerServiceActivity;
import com.example.myselfview.sections.cookie.HttpCookieTestActivity;
import com.example.myselfview.sections.gridview.GridviewDemoActivity;
import com.example.myselfview.sections.jdtoast.ToastTestActivity;
import com.example.myselfview.sections.wave.WaveActivity;

public class MainActivity extends Activity implements OnClickListener {

	private Button dialog_button;
	private Button score_button;
	private Button pic_button;
	private Button relative_button;
	private Button full_pic_show;
	private Button circle_pic_show;
	private Button svl_view;
	private Button jd_toast;
	private Button wave_effect;
	private Button gridview_header;
	private Button http_cookie;
	private Button bt_service;
	private Button bt_camer_service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		this.dialog_button = (Button) findViewById(R.id.dialog_button);
		this.score_button = (Button) findViewById(R.id.score_button);
		this.pic_button = (Button) findViewById(R.id.pic_button);
		this.relative_button = (Button) findViewById(R.id.relative_button);
		this.full_pic_show = (Button) findViewById(R.id.full_pic_show);
		this.circle_pic_show = (Button) findViewById(R.id.circle_pic_show);
		this.svl_view = (Button) findViewById(R.id.svl_view);
		this.jd_toast = (Button) findViewById(R.id.jd_toast);
		this.wave_effect = (Button) findViewById(R.id.wave_effect);
		this.gridview_header = (Button) findViewById(R.id.gridview_header);
		this.http_cookie = (Button) findViewById(R.id.http_cookie);
		this.bt_service = (Button) findViewById(R.id.bt_service);
		this.bt_camer_service = (Button) findViewById(R.id.bt_camer_service);

		this.dialog_button.setOnClickListener(this);
		this.score_button.setOnClickListener(this);
		this.pic_button.setOnClickListener(this);
		this.relative_button.setOnClickListener(this);
		this.full_pic_show.setOnClickListener(this);
		this.circle_pic_show.setOnClickListener(this);
		this.svl_view.setOnClickListener(this);
		this.jd_toast.setOnClickListener(this);
		this.wave_effect.setOnClickListener(this);
		this.gridview_header.setOnClickListener(this);
		this.http_cookie.setOnClickListener(this);
		this.bt_service.setOnClickListener(this);
		this.bt_camer_service.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_button:
			this.startActivity(new Intent(this, DialogActivity.class));
			break;
		case R.id.score_button:
			this.startActivity(new Intent(this, ScoreActivity.class));
			break;
		case R.id.pic_button:
			this.startActivity(new Intent(this, PicActivity.class));
			break;
		case R.id.full_pic_show:
			this.startActivity(new Intent(this, PicFullScreenActivity.class));
			break;
		case R.id.circle_pic_show:
			this.startActivity(new Intent(this, CircleImgShowActivity.class));
			break;
		case R.id.svl_view:
			this.startActivity(new Intent(this, ScrollListPagerActivity.class));
			break;
		case R.id.jd_toast:
			this.startActivity(new Intent(this, ToastTestActivity.class));
			break;
		case R.id.wave_effect:
			this.startActivity(new Intent(this, WaveActivity.class));
			break;
		case R.id.gridview_header:
			this.startActivity(new Intent(this, GridviewDemoActivity.class));
			break;
		case R.id.http_cookie:
			this.startActivity(new Intent(this, HttpCookieTestActivity.class));
			break;
		case R.id.bt_service:
			this.startActivity(new Intent(this, BindServiceActivity.class));
			break;
		case R.id.bt_camer_service:
			this.startActivity(new Intent(this, CamerServiceActivity.class));
			break;
		default:
			break;
		}
	}

}
