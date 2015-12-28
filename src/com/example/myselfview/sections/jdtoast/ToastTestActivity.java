package com.example.myselfview.sections.jdtoast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.myself_view.R;

public class ToastTestActivity extends Activity implements OnClickListener {
	private Button success_toast;
	private Button fail_toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toast_activity);

		this.success_toast = (Button) findViewById(R.id.success_toast);
		this.fail_toast = (Button) findViewById(R.id.fail_toast);
		
		this.success_toast.setOnClickListener(this);
		this.fail_toast.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.success_toast:
			ToastUtils.showToastInCenter(this, 2, "恭喜您，商品已添加至购物车！", Toast.LENGTH_SHORT);

			break;
		case R.id.fail_toast:
			ToastUtils.showToastInCenter(this, 1, "操作失败！", Toast.LENGTH_SHORT);
			break;
		}
	}

}
