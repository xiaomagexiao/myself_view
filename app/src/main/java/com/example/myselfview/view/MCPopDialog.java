package com.example.myselfview.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myself_view.R;


public class MCPopDialog extends Dialog {

	public static final int MB_OK = 0;//只有确定类型的对话框
	public static final int MB_CANCELOK = 1;//确定取消都有的对话框
	
	public static final int NO = 0;//取消，设置监听事件的时候用
	public static final int YES = 1;//确认，设置监听事件的时候用
	
	private View.OnClickListener commitListener;
	private View.OnClickListener cancelListener;
	private TextView bt_cancel;
	private TextView bt_commit;
	private TextView dialog_message;

	public MCPopDialog(Context context) {
		super(context);
		initView(context, 1);
	}

	
	public MCPopDialog(Context context, int type) {
		super(context);
		initView(context, type);
	}
	

	/**
	 * 按钮类型+信息
	 * @param context
	 * @param type
	 * @param message
	 */
	public MCPopDialog(Context context, int type, String message) {
		super(context);
		initView(context, type);
		setMessage(message);
	}
	
	private void initView(Context context, int type) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = View.inflate(context, R.layout.dialog_self_layout, null);
		setContentView(view);
		dialog_message = (TextView) view.findViewById(R.id.dialog_message);
		bt_cancel = (TextView) view.findViewById(R.id.bt_cancel);
		bt_commit = (TextView) view.findViewById(R.id.bt_commit);
		bt_commit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(commitListener!=null){
					commitListener.onClick(v);
				}
				MCPopDialog.this.dismiss();
			}
		});
		bt_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cancelListener!=null){
					cancelListener.onClick(v);
				}
				MCPopDialog.this.dismiss();
			}
		});

		if(type == MB_OK){
			bt_cancel.setVisibility(View.GONE);
			view.findViewById(R.id.divid_line).setVisibility(View.GONE);
		}
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes(); 
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		lp.width = (int) (dm.widthPixels * 0.9); // 设置宽度
		lp.alpha=0.9f;//设置透明度
		dialogWindow.setAttributes(lp);
		
		//点击边缘是否关闭对话框
		setCanceledOnTouchOutside(false);
	}

	/**
	 * 设置监听事件，自带销毁，只要点击了按钮就会销毁了。
	 * @param listener
	 * @param type
	 */
	public MCPopDialog setOnClickListener(android.view.View.OnClickListener listener, int type){
		if(type == 1){
			this.commitListener = listener;
		}else if(type == 0){
			this.cancelListener = listener;
		}
		return this;
	}
	
	/**
	 * 设置显示消息的内容
	 * @param message
	 * @return
	 */
	public MCPopDialog setMessage(String message) {
		if (dialog_message != null) {
			dialog_message.setText(message);
		}
		return this;
	}

}
