package com.yd.photoeditor.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.yd.photoeditor.R;
import com.yd.photoeditor.model.EditedImageItem;
import com.yd.photoeditor.view.PreviewDrawingView;

public class DialogUtils {

    public interface ConfirmDialogOnClickListener {
        void onCancelButtonOnClick();

        void onOKButtonOnClick();
    }

    public interface DialogOnClickListener {
        void onOKButtonOnClick();
    }

    public interface InputDialogOnClickListener {
        void onCancelButtonOnClick();

        void onOKButtonOnClick(String str);
    }

    public interface OnAddImageButtonClickListener {
        void onCameraButtonClick();

        void onGalleryButtonClick();
    }

    public interface OnSelectDrawEffectListener {
        void onSelectEffect(int i);
    }

    public interface OnSelectPaintSizeListener {
        void onSelectPaintSize(float f);
    }

    public static abstract class EditedImageLongClickListener {
        private EditedImageItem mImageItem;

        public abstract void onDeleteButtonClick();

        public abstract void onEditButtonClick();

        public abstract void onShareButtonClick();

        public void setImageItem(EditedImageItem editedImageItem) {
            this.mImageItem = editedImageItem;
        }

        public EditedImageItem getImageItem() {
            return this.mImageItem;
        }
    }

    public static Dialog showCoolConfirmDialog(Context context, int i, int i2, ConfirmDialogOnClickListener confirmDialogOnClickListener) {
        String string = context.getResources().getString(i);
        String string2 = context.getResources().getString(i2);
        if (Build.VERSION.SDK_INT > 20) {
            return showConfirmDialog(context, string, string2, confirmDialogOnClickListener);
        }


        return new Dialog(context);
    }





    public static Dialog createCustomInputDialog(Activity activity, String str, String str2, String str3, String str4, final InputDialogOnClickListener inputDialogOnClickListener) {
        final Dialog dialog = new Dialog(activity);
        return dialog;
    }

    public static Dialog createCustomOkDialog(Activity activity, String str, String str2) {
        return createCustomOkDialog(activity, str, str2, new DialogOnClickListener() {
            public void onOKButtonOnClick() {
            }
        });
    }

    public static Dialog createCustomOkDialog(Activity activity, String str, String str2, final DialogOnClickListener dialogOnClickListener) {
        final Dialog dialog = new Dialog(activity);
        return dialog;
    }

    public static Dialog createCustomConfirmDialog(Context context, String str, String str2, String str3, String str4, final ConfirmDialogOnClickListener confirmDialogOnClickListener) {
        final Dialog dialog = new Dialog(context);
        return dialog;
    }

    public static Dialog createEditImageDialog(Context context, final EditedImageLongClickListener editedImageLongClickListener, boolean z) {

        final Dialog dialog = new Dialog(context);
        return dialog;
    }

    public static Dialog createAddImageDialog(Context context, final OnAddImageButtonClickListener onAddImageButtonClickListener, boolean z) {
        final Dialog dialog = new Dialog(context);
        return dialog;
    }

    public static Dialog createDrawEffectDialog(Context context, final OnSelectDrawEffectListener onSelectDrawEffectListener, boolean z) {
        final Dialog dialog = new Dialog(context);
        return dialog;
    }

    public static Dialog createPreviewDrawingDialog(Context context, final OnSelectPaintSizeListener onSelectPaintSizeListener, boolean z) {
        final Dialog dialog = new Dialog(context);
        return dialog;
    }

    public static Dialog showDialog(Context context, int i, int i2) {
        return showDialog(context, i, i2, null);
    }

    public static Dialog showDialog(Context context, int i, int i2, DialogOnClickListener dialogOnClickListener) {
        return showDialog(context, context.getResources().getString(i), context.getResources().getString(i2), dialogOnClickListener);
    }

    public static Dialog showDialog(Context context, String str, String str2, final DialogOnClickListener dialogOnClickListener) {
        if (context == null || ((Activity) context).isFinishing()) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(str).setMessage(str2).setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog create = builder.create();
        create.show();
        return create;
    }

    public static Dialog showDialogOkClick(Context context, int i, int i2, int i3, DialogInterface.OnClickListener onClickListener) {
        if (context == null || ((Activity) context).isFinishing()) {
            return null;
        }
        String string = context.getResources().getString(i);
        String string2 = context.getResources().getString(i2);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(string).setMessage(string2).setCancelable(false).setPositiveButton(i3, onClickListener);
        AlertDialog create = builder.create();
        create.show();
        return create;
    }

    public static Dialog showConfirmDialog(Context context, int i, int i2, ConfirmDialogOnClickListener confirmDialogOnClickListener) {
        return showConfirmDialog(context, context.getResources().getString(i), context.getResources().getString(i2), confirmDialogOnClickListener);
    }

    public static Dialog showConfirmDialog(Context context, int i, int i2, int i3, int i4, final ConfirmDialogOnClickListener confirmDialogOnClickListener) {
        if (context == null || ((Activity) context).isFinishing()) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(i).setMessage(i2).setCancelable(false).setPositiveButton(i3, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton(i4, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog create = builder.create();
        create.show();
        return create;
    }

    public static Dialog showConfirmDialog(Context context, String str, String str2, final ConfirmDialogOnClickListener confirmDialogOnClickListener) {
        if (context == null || ((Activity) context).isFinishing()) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(str).setMessage(str2).setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog create = builder.create();
        create.show();
        return create;
    }
}
