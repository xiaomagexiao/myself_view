package com.example.myselfview.sections.jdtoast;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.myselfview.index.MoocApplication;

/**
 * toastSuccess/ toastFail可以在子线程中调用
 */
public class MCToast {
    private static Toast mToast;
    private static MCCustomToast centerToast;
    private static Handler mHandler;

    private static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }

        return mHandler;
    }


    /**
     * 系统自带的普通toast
     * @param msg
     */
    public static void show(String msg) {
        show(MoocApplication.getInstance(), msg);
    }

    public static void show(Context mContext, String msg) {
        show(mContext, msg, 0);
    }

    public static void show(Context mContext, String msg, int duration) {
        if (MCToast.mToast == null) {
            MCToast.mToast = new Toast(mContext);
            MCToast.mToast = Toast.makeText(mContext, ((CharSequence) msg), duration);
        }

        MCToast.mToast.setDuration(duration);
        MCToast.mToast.setText(((CharSequence) msg));
        MCToast.mToast.show();
    }

    /**
     * 成功的toast
     * @param msg
     */
    public static void toastSuccess(String msg){
        showToastInCenter(MoocApplication.getInstance(), 2, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 失败的toast
     * @param msg
     */
    public static void toastFail(String msg){
        showToastInCenter(MoocApplication.getInstance(), 1, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 在中部显示的自定义toast
     * @param context
     * @param info
     * @param druation
     */
    public static void showToastInCenter(final Context context, final int type, final String info, final int druation) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (centerToast == null) {
                    centerToast = new MCCustomToast(context);
                }
                centerToast.setType(type);
                centerToast.setText(info);
                centerToast.setDuration(druation);
                centerToast.show();
            }
        });
    }
}

