package com.yd.picmaker.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.yd.picmaker.Listener.AddTextListener;
import com.yd.picmaker.R;

public class ExitDialog extends Dialog {

    private ExitDialog(Context context) {
        super(context);
    }

    private ExitDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static class Builder implements View.OnClickListener {
        private Activity activity;
        private View contentView;
        private AddTextListener listener;
        private ExitDialog dialog;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public ExitDialog create() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialog = new ExitDialog(activity, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_exit, null);
            dialog.addContentView(layout, new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
            if (contentView != null) {
                ((ConstraintLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((ConstraintLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
            }
            TextView cancel = layout.findViewById(R.id.cancel);
            TextView exit = layout.findViewById(R.id.exit);
            cancel.setOnClickListener(this);
            exit.setOnClickListener(this);
            dialog.setContentView(layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return dialog;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel:
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    break;
                case R.id.exit:
                    if(dialog != null) {
                        dialog.dismiss();
                    }
                    if(activity != null) {
                        activity.finish();
                    }
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}