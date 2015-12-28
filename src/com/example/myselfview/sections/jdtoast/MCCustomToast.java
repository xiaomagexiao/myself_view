package com.example.myselfview.sections.jdtoast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myself_view.R;


public final class MCCustomToast extends Toast {
	public TextView tipInfo;
	public ImageView tipImage;

	public MCCustomToast(Context context) {
		super(context);

		View view = LayoutInflater.from(context).inflate(R.layout.toast_style_center, null);
		this.setView(view);
		this.tipInfo = (TextView) view.findViewById(R.id.jd_toast_txt);
		this.tipImage = (ImageView) view.findViewById(R.id.jd_toast_image);
		this.setGravity(17, 0, 0);
	}

	public final void setType(int type) {
		if (this.tipImage != null) {
			switch (type) {
			case 1: {
				this.tipImage.setBackgroundResource(R.drawable.toast_exclamation);
				return;
			}
			case 2: {
				this.tipImage.setBackgroundResource(R.drawable.toast_tick);
				return;
			}
			}
		}
	}

	public final void setText(CharSequence text) {
		if (this.tipInfo != null) {
			this.tipInfo.setText(text);
		}
	}
}
