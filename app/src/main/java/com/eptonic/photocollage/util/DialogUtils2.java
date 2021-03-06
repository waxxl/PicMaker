package com.eptonic.photocollage.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.eptonic.photocollage.R;

public class DialogUtils2 {

    public interface ConfirmDialogOnClickListener {
        void onCancelButtonOnClick();

        void onOKButtonOnClick();
    }

    public static Dialog showConfirmDialog(Context context, String str, String str2, DialogUtils2.ConfirmDialogOnClickListener confirmDialogOnClickListener) {
        if (context == null || ((Activity) context).isFinishing()) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(str)
                .setMessage(str2)
                .setCancelable(false)
                .setPositiveButton(R.string.button_ok, new onClickListener(confirmDialogOnClickListener))
                .setNegativeButton(R.string.button_cancel, new X5X6Lis(confirmDialogOnClickListener));
        AlertDialog create = builder.create();
        create.show();
        return create;
    }

    static class onClickListener implements DialogInterface.OnClickListener {
        final DialogUtils2.ConfirmDialogOnClickListener listener;

        onClickListener(DialogUtils2.ConfirmDialogOnClickListener confirmDialogOnClickListener) {
            listener = confirmDialogOnClickListener;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            DialogUtils2.ConfirmDialogOnClickListener confirmDialogOnClickListener = listener;
            if (confirmDialogOnClickListener != null) {
                confirmDialogOnClickListener.onOKButtonOnClick();
            }
        }
    }

    static class X5X6Lis implements DialogInterface.OnClickListener {
        final DialogUtils2.ConfirmDialogOnClickListener val$listener;

        X5X6Lis(DialogUtils2.ConfirmDialogOnClickListener confirmDialogOnClickListener) {
            this.val$listener = confirmDialogOnClickListener;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            DialogUtils2.ConfirmDialogOnClickListener confirmDialogOnClickListener = this.val$listener;
            if (confirmDialogOnClickListener != null) {
                confirmDialogOnClickListener.onCancelButtonOnClick();
            }
            dialogInterface.cancel();
        }
    }
}
