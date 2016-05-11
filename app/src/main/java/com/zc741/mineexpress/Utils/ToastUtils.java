package com.zc741.mineexpress.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zc741.mineexpress.R;

/**
 * Created by jiae on 2016/5/11.
 */
public class ToastUtils extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ToastUtils(Context context) {
        super(context);
    }
    public static void toast(Context context, String msg, int duration) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View layout = layoutInflater.inflate(R.layout.toast_layout, null);

        TextView text = (TextView) layout.findViewById(R.id.toast_simple_msg);
        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(duration);
        text.setText(msg);
        toast.setView(layout);
        toast.show();
    }

    public static void toast(Context context, int resId, int duration) {
        String msg = context.getResources().getString(resId);
        toast(context, msg, duration);
    }
}
