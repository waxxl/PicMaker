package com.yd.picmaker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.yd.picmaker.Listener.AddTextListener;
import com.yd.picmaker.R;

public class AddTextDialog extends Dialog {

    public AddTextDialog(Context context) {
        super(context);
    }

    public AddTextDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static class Builder implements View.OnClickListener {
        private final Context context;
        private View contentView;
        private EditText editText;
        private AddTextListener listener;
        public Builder(Context context) {
            this.context = context;
        }

        public void setListener(AddTextListener listener) {
            this.listener = listener;
        }

        public AddTextDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final AddTextDialog dialog = new AddTextDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_add_text, null);
            dialog.addContentView(layout, new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
            if (contentView != null) {
                ((ConstraintLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((ConstraintLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
            }
            editText = layout.findViewById(R.id.write_text);
            ImageView buttonOk = layout.findViewById(R.id.button_ok);
            ImageView buttonCancel = layout.findViewById(R.id.close);
            buttonOk.setOnClickListener(this);
            buttonCancel.setOnClickListener(this);
            dialog.setContentView(layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return dialog;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_ok:
                    if(listener != null)
                        listener.onOkClick(editText.getText().toString());
                    break;
                case R.id.close:
                    if(listener != null)
                        listener.onCancelClick();
                    break;
                default:
                    break;
            }
        }
    }
}