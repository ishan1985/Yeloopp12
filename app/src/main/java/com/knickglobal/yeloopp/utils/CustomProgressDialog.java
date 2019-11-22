package com.knickglobal.yeloopp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import com.knickglobal.yeloopp.R;

import java.util.Objects;

public class CustomProgressDialog extends Dialog {

    private Activity activity;

    private ProgressBar pBar;

    public CustomProgressDialog(Activity activity) {
        super(activity);
        this.activity = activity;

    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_progress);
        pBar =  findViewById(R.id.custom_progress);
        setCancelable(false);
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

}