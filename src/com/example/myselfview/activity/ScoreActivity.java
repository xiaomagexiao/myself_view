package com.example.myselfview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.myself_view.R;
import com.example.myselfview.view.ScoreWithPicView;

public class ScoreActivity extends Activity implements OnClickListener {

	private EditText score_text;
	private Button score_button;
	private Button increase_button;
	private ScoreWithPicView score_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.score_activity_layout);
		score_text = (EditText) this.findViewById(R.id.score_text);
		score_button = (Button) this.findViewById(R.id.score_button);
		increase_button = (Button) this.findViewById(R.id.increase_button);
		score_content = (ScoreWithPicView) this
				.findViewById(R.id.score_content);
		score_content.setScore(65, ScoreWithPicView.COMMON);

		this.score_button.setOnClickListener(this);
		this.increase_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int score = 0;
		switch (v.getId()) {
		case R.id.score_button:
			try {
				score = Integer.valueOf(this.score_text.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
				score = 0;
			}
			score_content.setScore(score, ScoreWithPicView.COMMON);
			break;
		case R.id.increase_button:
			score_content.setDelayTime(10);
			try {
				score = Integer.valueOf(this.score_text.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
				score = 0;
			}
			score_content.setScore(score, ScoreWithPicView.INCREMENT);
			break;

		default:
			break;
		}
	}

}
