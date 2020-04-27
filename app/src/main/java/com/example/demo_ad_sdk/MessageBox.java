package com.example.demo_ad_sdk;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MessageBox extends Dialog {

    private int dialogResult;
    private Handler mHandler;

    public MessageBox(Activity context) {
        super(context);
        dialogResult = 0;
        setOwnerActivity(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        onCreate();
    }

    private void onCreate() {
        setContentView(R.layout.messagebox);
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                endDialog(0);
            }
        });
        findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                endDialog(1);
            }
        });
    }

    public int getDialogResult() {
        return dialogResult;
    }

    private void setDialogResult(int dialogResult) {
        this.dialogResult = dialogResult;
    }

    private void endDialog(int result) {
        dismiss();
        setDialogResult(result);
        Message m;
        m = mHandler.obtainMessage();
        mHandler.sendMessage(m);
    }

    public int showDialog(String Msg, String Title) {
        TextView TvErrorInfo = findViewById(R.id.textViewInfo);
        TvErrorInfo.setText(Msg);
        TextView TvTitle = findViewById(R.id.textViewTitle);
        TvTitle.setText(Title);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message mesg) {
                throw new RuntimeException();
            }
        };
        super.show();
        try {
            Looper.getMainLooper();
            Looper.loop();
        } catch (RuntimeException ignored) {
        }
        return dialogResult;
    }
}
