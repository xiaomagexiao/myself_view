package com.example.myselfview.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myself_view.R;
import com.example.myselfview.view.ChoicePicFromLocalView;

public class PicFullScreenActivity extends Activity {

	private ChoicePicFromLocalView choic_carme_pic;
	private Button showpath_button;
	private Button fullscreen_button;
	private TextView path_text;
	private LinearLayout pic_content_img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//或者清单文件中配置  android:windowSoftInputMode="adjustPan|stateHidden"
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		this.setContentView(R.layout.fullscreen_pic_activity_layout);
		this.choic_carme_pic = (ChoicePicFromLocalView) findViewById(R.id.choic_carme_pic);
		this.showpath_button = (Button) findViewById(R.id.showpath_button);
		this.fullscreen_button = (Button) findViewById(R.id.fullscreen_button);
		this.path_text = (TextView) findViewById(R.id.path_text);
		this.pic_content_img = (LinearLayout) findViewById(R.id.pic_content_img);
//int i=1/0;
		//如果显示的位置有特殊要求可以在这里传入要显示的布局
		//this.choic_carme_pic.setContentWrap(this.pic_content_img);
		this.showpath_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String paths = "所有路径如下：\n";
				for (String str : choic_carme_pic.getAllFilePaths()) {
					paths += str + "\n";
				}
				path_text.setText(paths);
			}
		});
		
		this.fullscreen_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PicFullScreenActivity.this, PicFullScreenShowActivity.class);
				List<String> imgPath = new ArrayList<String>();//choic_carme_pic.getAllFilePaths();
				Bundle bundle = new Bundle();
				
				imgPath.add("http://www.rs.net.cn/UploadFiles/FCKFile/UIImage%BA%CDUIImageView.png");
				imgPath.add("http://pic.zdface.com/upload/2010102516658279.jpg");
				imgPath.add("http://ent.cqwb.com.cn/pic/Upload/Image/2015/03/16/2015031608310240697ecfddfc-da47-4edb-976d-b11fbde89079_size69_w416_h625.jpg");
				
				bundle.putSerializable("imgPath", (Serializable) imgPath);
				bundle.putInt("startIndex", 0);
				intent.putExtras(bundle);
				PicFullScreenActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		choic_carme_pic.onActivityResult(requestCode, resultCode, data);
	}

}
