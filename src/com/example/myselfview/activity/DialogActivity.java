package com.example.myselfview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.myself_view.R;
import com.example.myselfview.view.MCPopDialog;

public class DialogActivity extends Activity implements OnClickListener {
	private Button dialog_cancel_button;
	private Button dialog_cancel_sure_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_activity_layout);
		initView();
	}

	private void initView() {
		this.dialog_cancel_button = (Button) findViewById(R.id.dialog_cancel_button);
		this.dialog_cancel_sure_button = (Button) findViewById(R.id.dialog_cancel_sure_button);

		this.dialog_cancel_button.setOnClickListener(this);
		this.dialog_cancel_sure_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_cancel_button:
			MCPopDialog dialog3 = new MCPopDialog(this, MCPopDialog.MB_OK, "只是让你确认而已！");
			dialog3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(DialogActivity.this, "点击了确认", Toast.LENGTH_SHORT).show();
				}
			}, MCPopDialog.YES);
			dialog3.show();
			break;
			
		case R.id.dialog_cancel_sure_button:
			
			MCPopDialog dialog = new MCPopDialog(this, MCPopDialog.MB_CANCELOK, "可以确认可以取消哦！");
			
			dialog.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(DialogActivity.this, "点击了确认", Toast.LENGTH_SHORT).show();
				}
			}, MCPopDialog.YES);
			
			dialog.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(DialogActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
				}
			}, MCPopDialog.NO);
			
			dialog.show();
			
		default:
			break;
		}
	}
}
