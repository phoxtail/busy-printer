package com.example.demo_ad_sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class StatusBox {
    private View dialogView;
    private PopupWindow popupWindow;
    private View BoxParent;
    private Timer timer;
    private TimerTask timerTask;
    private Handler LooperHandler;
    private Handler TimerHandler;

    public StatusBox(Context context, View parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView = inflater.inflate(R.layout.statusbox, null, false);
        dialogView.setBackgroundResource(R.layout.statusbox_shape);
        popupWindow = new PopupWindow(dialogView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);//创建PopupWindow实例
        BoxParent = parent;

        timer = new Timer();

        TimerHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        timerTask.cancel();
                        Message m = LooperHandler.obtainMessage();
                        LooperHandler.sendMessage(m);
                        break;
                }
                super.handleMessage(msg);
            }
        };
        LooperHandler = new Handler() {
            @Override
            public void handleMessage(Message mesg) {
                throw new RuntimeException();
            }
        };
    }

    public void Show(String Msg) {
        TextView TvErrorInfo = dialogView.findViewById(R.id.textViewInfo);
        TvErrorInfo.setText(Msg);
        popupWindow.showAtLocation(BoxParent, Gravity.CENTER, 0, 0);

        timerTask = new TimerTask() {
            public void run() {
                Message message = new Message();
                message.what = 1;
                TimerHandler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 10);

        try {
            Looper.getMainLooper();
            Looper.loop();
        } catch (RuntimeException ignored) {
        }
    }

    public void Close() {
        popupWindow.dismiss();
    }
}
