package com.yd.picmaker.util;

public class DialogUtils {
//    public static final int CREATE_FRAME_DIALOG_TYPE = 3;
//    public static final int ITEM_DIALOG_TYPE = 0;
//    public static final int STICKER_DIALOG_TYPE = 2;
//
//    public interface ConfirmDialogOnClickListener {
//        void onCancelButtonOnClick();
//
//        void onOKButtonOnClick();
//    }
//
//    public interface DialogOnClickListener {
//        void onOKButtonOnClick();
//    }
//
//    public interface OnAddImageButtonClickListener {
//        void onBackgroundColorButtonClick();
//
//        void onBackgroundPhotoButtonClick();
//
//        void onCameraButtonClick();
//
//        void onGalleryButtonClick();
//
//        void onStickerButtonClick();
//
//        void onTextButtonClick();
//    }
//
//    public interface OnBorderShadowOptionListener {
//        void onBorderSizeChange(float f);
//
//        void onShadowSizeChange(float f);
//    }
//
//    public interface OnEditImageMenuClickListener {
//        void onAlterBackgroundButtonClick();
//
//        void onBorderAndShaderButtonClick();
//
//        void onCancelEdit();
//
//        void onColorBorderButtonClick();
//
//        void onEditButtonClick();
//
//        void onRemoveButtonClick();
//    }
//
//    public static Dialog createSelectPhotoDialog(Context context, OnEditImageMenuClickListener onEditImageMenuClickListener, OnAddImageButtonClickListener onAddImageButtonClickListener, boolean z) {
//        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_select_photo, (ViewGroup) null);
//        Dialog dialog = new Dialog(context, 16973840);
//        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
//        attributes.gravity = 17;
//        attributes.flags &= -3;
//        attributes.width = -1;
//        attributes.height = -1;
//        dialog.setContentView(inflate);
//        dialog.setOnCancelListener(new C25181(onEditImageMenuClickListener));
//        inflate.findViewById(R.id.libraryView).setOnClickListener(new C25292(dialog, onAddImageButtonClickListener));
//        inflate.findViewById(R.id.stickerView).setOnClickListener(new C25403(dialog, onAddImageButtonClickListener));
//        inflate.findViewById(R.id.alterBackgroundView).setOnClickListener(new C25444(dialog, onEditImageMenuClickListener));
//        inflate.findViewById(R.id.borderShadowView).setOnClickListener(new C25455(dialog, onEditImageMenuClickListener));
//        inflate.findViewById(R.id.borderColorView).setOnClickListener(new C25466(dialog, onEditImageMenuClickListener));
//        inflate.findViewById(R.id.cancelView).setOnClickListener(new C25477(dialog, onEditImageMenuClickListener));
//        if (z) {
//            try {
//                inflate.startAnimation(AnimationUtils.loadAnimation(context, C2414R.anim.slide_in_bottom));
//                dialog.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return dialog;
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$1 */
//    static class C25181 implements DialogInterface.OnCancelListener {
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C25181(OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onCancel(DialogInterface dialogInterface) {
//            dialogInterface.dismiss();
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onCancelEdit();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$2 */
//    static class C25292 implements View.OnClickListener {
//        final /* synthetic */ OnAddImageButtonClickListener val$addImageListener;
//        final /* synthetic */ Dialog val$dialog;
//
//        C25292(Dialog dialog, OnAddImageButtonClickListener onAddImageButtonClickListener) {
//            this.val$dialog = dialog;
//            this.val$addImageListener = onAddImageButtonClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnAddImageButtonClickListener onAddImageButtonClickListener = this.val$addImageListener;
//            if (onAddImageButtonClickListener != null) {
//                onAddImageButtonClickListener.onGalleryButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$3 */
//    static class C25403 implements View.OnClickListener {
//        final /* synthetic */ OnAddImageButtonClickListener val$addImageListener;
//        final /* synthetic */ Dialog val$dialog;
//
//        C25403(Dialog dialog, OnAddImageButtonClickListener onAddImageButtonClickListener) {
//            this.val$dialog = dialog;
//            this.val$addImageListener = onAddImageButtonClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnAddImageButtonClickListener onAddImageButtonClickListener = this.val$addImageListener;
//            if (onAddImageButtonClickListener != null) {
//                onAddImageButtonClickListener.onStickerButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$4 */
//    static class C25444 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C25444(Dialog dialog, OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$dialog = dialog;
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onAlterBackgroundButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$5 */
//    static class C25455 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C25455(Dialog dialog, OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$dialog = dialog;
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onBorderAndShaderButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$6 */
//    static class C25466 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C25466(Dialog dialog, OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$dialog = dialog;
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onColorBorderButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$7 */
//    static class C25477 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C25477(Dialog dialog, OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$dialog = dialog;
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onCancelEdit();
//            }
//        }
//    }
//
//    public static Dialog createGuideDialog(Context context, boolean z) {
//        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_gesture, (ViewGroup) null);
//        Dialog dialog = new Dialog(context, 16973840);
//        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
//        attributes.gravity = 17;
//        attributes.flags &= -3;
//        attributes.width = -1;
//        attributes.height = -1;
//        dialog.setContentView(inflate);
//        dialog.setCanceledOnTouchOutside(true);
//        if (z) {
//            inflate.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom));
//            dialog.show();
//        }
//        inflate.setOnClickListener(new C25488(dialog));
//        dialog.setCanceledOnTouchOutside(true);
//        return dialog;
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$8 */
//    static class C25488 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//
//        C25488(Dialog dialog) {
//            this.val$dialog = dialog;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//        }
//    }
//
//    public static Dialog createBorderAndShadowOptionDialog(Context context, OnBorderShadowOptionListener onBorderShadowOptionListener, boolean z) {
//        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_border_shadow, (ViewGroup) null);
//        Dialog dialog = new Dialog(context, 16973840);
//        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
//        attributes.gravity = 17;
//        attributes.flags &= -3;
//        attributes.width = -1;
//        attributes.height = -1;
//        dialog.setContentView(inflate);
//        dialog.setCanceledOnTouchOutside(true);
//        float dimension = context.getResources().getDimension(C2414R.dimen.max_border_size);
//        OptionBorderView optionBorderView = (OptionBorderView) inflate.findViewById(C2414R.C2417id.borderView);
//        SeekBar seekBar = (SeekBar) inflate.findViewById(C2414R.C2417id.borderSeekBar);
//        seekBar.setOnSeekBarChangeListener(new C25499(dimension, optionBorderView));
//        SeekBar seekBar2 = (SeekBar) inflate.findViewById(C2414R.C2417id.shadowSeekBar);
//        OptionShadowView optionShadowView = (OptionShadowView) inflate.findViewById(C2414R.C2417id.shadowView);
//        float dimension2 = context.getResources().getDimension(C2414R.dimen.max_shadow_size);
//        seekBar2.setOnSeekBarChangeListener(new C251910(dimension2, optionShadowView));
//        optionBorderView.getViewTreeObserver().addOnGlobalLayoutListener(new C252011(context, optionBorderView, optionShadowView));
//        inflate.findViewById(C2414R.C2417id.cancelView).setOnClickListener(new C252112(dialog));
//        inflate.findViewById(C2414R.C2417id.okView).setOnClickListener(new C252213(dimension, seekBar, dimension2, seekBar2, dialog, onBorderShadowOptionListener));
//        if (z) {
//            try {
//                dialog.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        inflate.setOnClickListener(new C252314(dialog));
//        return dialog;
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$9 */
//    static class C25499 implements SeekBar.OnSeekBarChangeListener {
//        final /* synthetic */ OptionBorderView val$borderView;
//        final /* synthetic */ float val$maxBorder;
//
//        public void onStartTrackingTouch(SeekBar seekBar) {
//        }
//
//        public void onStopTrackingTouch(SeekBar seekBar) {
//        }
//
//        C25499(float f, OptionBorderView optionBorderView) {
//            this.val$maxBorder = f;
//            this.val$borderView = optionBorderView;
//        }
//
//        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
//            this.val$borderView.setBorderSize(this.val$maxBorder * (((float) i) / 100.0f));
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$10 */
//    static class C251910 implements SeekBar.OnSeekBarChangeListener {
//        final /* synthetic */ float val$maxShadow;
//        final /* synthetic */ OptionShadowView val$shadowView;
//
//        public void onStartTrackingTouch(SeekBar seekBar) {
//        }
//
//        public void onStopTrackingTouch(SeekBar seekBar) {
//        }
//
//        C251910(float f, OptionShadowView optionShadowView) {
//            this.val$maxShadow = f;
//            this.val$shadowView = optionShadowView;
//        }
//
//        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
//            this.val$shadowView.setShadowSize((int) (this.val$maxShadow * (((float) i) / 100.0f)));
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$11 */
//    static class C252011 implements ViewTreeObserver.OnGlobalLayoutListener {
//        final /* synthetic */ OptionBorderView val$borderView;
//        final /* synthetic */ Context val$context;
//        final /* synthetic */ OptionShadowView val$shadowView;
//
//        C252011(Context context, OptionBorderView optionBorderView, OptionShadowView optionShadowView) {
//            this.val$context = context;
//            this.val$borderView = optionBorderView;
//            this.val$shadowView = optionShadowView;
//        }
//
//        public void onGlobalLayout() {
//            Rect rect = new Rect();
//            float dimension = this.val$context.getResources().getDimension(C2414R.dimen.shadow_view_size);
//            rect.top = (int) ((dimension - ((float) this.val$borderView.getHeight())) / 2.0f);
//            rect.left = (int) ((dimension - ((float) this.val$borderView.getWidth())) / 2.0f);
//            rect.right = rect.left + this.val$borderView.getWidth();
//            rect.bottom = rect.top + this.val$borderView.getHeight();
//            this.val$shadowView.setDrawableBounds(rect);
//            if (Build.VERSION.SDK_INT >= 16) {
//                this.val$borderView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            } else {
//                this.val$borderView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$12 */
//    static class C252112 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//
//        C252112(Dialog dialog) {
//            this.val$dialog = dialog;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$13 */
//    static class C252213 implements View.OnClickListener {
//        final /* synthetic */ SeekBar val$borderSeekBar;
//        final /* synthetic */ Dialog val$dialog;
//        final /* synthetic */ OnBorderShadowOptionListener val$listener;
//        final /* synthetic */ float val$maxBorder;
//        final /* synthetic */ float val$maxShadow;
//        final /* synthetic */ SeekBar val$shadowSeekBar;
//
//        C252213(float f, SeekBar seekBar, float f2, SeekBar seekBar2, Dialog dialog, OnBorderShadowOptionListener onBorderShadowOptionListener) {
//            this.val$maxBorder = f;
//            this.val$borderSeekBar = seekBar;
//            this.val$maxShadow = f2;
//            this.val$shadowSeekBar = seekBar2;
//            this.val$dialog = dialog;
//            this.val$listener = onBorderShadowOptionListener;
//        }
//
//        public void onClick(View view) {
//            float progress = this.val$maxBorder * (((float) this.val$borderSeekBar.getProgress()) / 100.0f);
//            int progress2 = (int) (this.val$maxShadow * (((float) this.val$shadowSeekBar.getProgress()) / 100.0f));
//            this.val$dialog.dismiss();
//            OnBorderShadowOptionListener onBorderShadowOptionListener = this.val$listener;
//            if (onBorderShadowOptionListener != null) {
//                onBorderShadowOptionListener.onBorderSizeChange(progress);
//                this.val$listener.onShadowSizeChange((float) progress2);
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$14 */
//    static class C252314 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//
//        C252314(Dialog dialog) {
//            this.val$dialog = dialog;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//        }
//    }
//
//    public static Dialog createAddImageDialog(Context context, OnAddImageButtonClickListener onAddImageButtonClickListener, boolean z) {
//        View inflate = LayoutInflater.from(context).inflate(C2414R.layout.dialog_add_image, (ViewGroup) null);
//        Dialog dialog = new Dialog(context, 16973840);
//        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
//        attributes.gravity = 80;
//        attributes.flags &= -3;
//        attributes.width = -1;
//        attributes.height = -1;
//        dialog.setContentView(inflate);
//        inflate.findViewById(C2414R.C2417id.cameraView);
//        inflate.findViewById(C2414R.C2417id.galleryView).setOnClickListener(new C252415(onAddImageButtonClickListener));
//        View findViewById = inflate.findViewById(C2414R.C2417id.stickerView);
//        findViewById.setOnClickListener(new C252516(onAddImageButtonClickListener));
//        inflate.findViewById(C2414R.C2417id.textView).setOnClickListener(new C252617(onAddImageButtonClickListener));
//        inflate.findViewById(C2414R.C2417id.alterBackgroundView).setOnClickListener(new C252718(dialog, onAddImageButtonClickListener));
//        inflate.findViewById(C2414R.C2417id.alterBackgroundColorView).setOnClickListener(new C252819(dialog, onAddImageButtonClickListener));
//        inflate.findViewById(C2414R.C2417id.cancelView).setOnClickListener(new C253020(dialog));
//        if (onAddImageButtonClickListener != null && (onAddImageButtonClickListener instanceof CreateFrameFragment)) {
//            findViewById.setVisibility(8);
//        }
//        if (z) {
//            try {
//                inflate.startAnimation(AnimationUtils.loadAnimation(context, C2414R.anim.slide_in_bottom));
//                dialog.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return dialog;
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$15 */
//    static class C252415 implements View.OnClickListener {
//        final /* synthetic */ OnAddImageButtonClickListener val$listener;
//
//        C252415(OnAddImageButtonClickListener onAddImageButtonClickListener) {
//            this.val$listener = onAddImageButtonClickListener;
//        }
//
//        public void onClick(View view) {
//            OnAddImageButtonClickListener onAddImageButtonClickListener = this.val$listener;
//            if (onAddImageButtonClickListener != null) {
//                onAddImageButtonClickListener.onGalleryButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$16 */
//    static class C252516 implements View.OnClickListener {
//        final /* synthetic */ OnAddImageButtonClickListener val$listener;
//
//        C252516(OnAddImageButtonClickListener onAddImageButtonClickListener) {
//            this.val$listener = onAddImageButtonClickListener;
//        }
//
//        public void onClick(View view) {
//            OnAddImageButtonClickListener onAddImageButtonClickListener = this.val$listener;
//            if (onAddImageButtonClickListener != null) {
//                onAddImageButtonClickListener.onStickerButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$17 */
//    static class C252617 implements View.OnClickListener {
//        final /* synthetic */ OnAddImageButtonClickListener val$listener;
//
//        C252617(OnAddImageButtonClickListener onAddImageButtonClickListener) {
//            this.val$listener = onAddImageButtonClickListener;
//        }
//
//        public void onClick(View view) {
//            OnAddImageButtonClickListener onAddImageButtonClickListener = this.val$listener;
//            if (onAddImageButtonClickListener != null) {
//                onAddImageButtonClickListener.onTextButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$18 */
//    static class C252718 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//        final /* synthetic */ OnAddImageButtonClickListener val$listener;
//
//        C252718(Dialog dialog, OnAddImageButtonClickListener onAddImageButtonClickListener) {
//            this.val$dialog = dialog;
//            this.val$listener = onAddImageButtonClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnAddImageButtonClickListener onAddImageButtonClickListener = this.val$listener;
//            if (onAddImageButtonClickListener != null) {
//                onAddImageButtonClickListener.onBackgroundPhotoButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$19 */
//    static class C252819 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//        final /* synthetic */ OnAddImageButtonClickListener val$listener;
//
//        C252819(Dialog dialog, OnAddImageButtonClickListener onAddImageButtonClickListener) {
//            this.val$dialog = dialog;
//            this.val$listener = onAddImageButtonClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnAddImageButtonClickListener onAddImageButtonClickListener = this.val$listener;
//            if (onAddImageButtonClickListener != null) {
//                onAddImageButtonClickListener.onBackgroundColorButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$20 */
//    static class C253020 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//
//        C253020(Dialog dialog) {
//            this.val$dialog = dialog;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//        }
//    }
//
//    public static Dialog createEditImageDialog(Context context, OnEditImageMenuClickListener onEditImageMenuClickListener, int i, boolean z) {
//        View inflate = LayoutInflater.from(context).inflate(C2414R.layout.dialog_edit_image, (ViewGroup) null);
//        Dialog dialog = new Dialog(context, 16973840);
//        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
//        attributes.gravity = 17;
//        attributes.flags &= -3;
//        attributes.width = -1;
//        attributes.height = -1;
//        dialog.setContentView(inflate);
//        dialog.setOnCancelListener(new C253121(onEditImageMenuClickListener));
//        inflate.findViewById(C2414R.C2417id.removeView).setOnClickListener(new C253222(onEditImageMenuClickListener));
//        View findViewById = inflate.findViewById(C2414R.C2417id.alterBackgroundView);
//        findViewById.setOnClickListener(new C253323(onEditImageMenuClickListener));
//        inflate.findViewById(C2414R.C2417id.borderShadowView).setOnClickListener(new C253424(onEditImageMenuClickListener));
//        inflate.findViewById(C2414R.C2417id.cancelView).setOnClickListener(new C253525(dialog, onEditImageMenuClickListener));
//        View findViewById2 = inflate.findViewById(C2414R.C2417id.editView);
//        findViewById2.setOnClickListener(new C253626(onEditImageMenuClickListener));
//        View findViewById3 = inflate.findViewById(C2414R.C2417id.borderColorView);
//        findViewById3.setOnClickListener(new C253727(onEditImageMenuClickListener));
//        if (i == 2) {
//            findViewById2.setVisibility(8);
//            findViewById3.setVisibility(8);
//        } else if (i == 3) {
//            findViewById.setVisibility(8);
//        }
//        if (z) {
//            try {
//                dialog.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return dialog;
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$21 */
//    static class C253121 implements DialogInterface.OnCancelListener {
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C253121(OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onCancel(DialogInterface dialogInterface) {
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onCancelEdit();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$22 */
//    static class C253222 implements View.OnClickListener {
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C253222(OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onRemoveButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$23 */
//    static class C253323 implements View.OnClickListener {
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C253323(OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onAlterBackgroundButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$24 */
//    static class C253424 implements View.OnClickListener {
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C253424(OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onBorderAndShaderButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$25 */
//    static class C253525 implements View.OnClickListener {
//        final /* synthetic */ Dialog val$dialog;
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C253525(Dialog dialog, OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$dialog = dialog;
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            this.val$dialog.dismiss();
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onCancelEdit();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$26 */
//    static class C253626 implements View.OnClickListener {
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C253626(OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onEditButtonClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$27 */
//    static class C253727 implements View.OnClickListener {
//        final /* synthetic */ OnEditImageMenuClickListener val$listener;
//
//        C253727(OnEditImageMenuClickListener onEditImageMenuClickListener) {
//            this.val$listener = onEditImageMenuClickListener;
//        }
//
//        public void onClick(View view) {
//            OnEditImageMenuClickListener onEditImageMenuClickListener = this.val$listener;
//            if (onEditImageMenuClickListener != null) {
//                onEditImageMenuClickListener.onColorBorderButtonClick();
//            }
//        }
//    }
//
//    public static Dialog showDialog(Context context, int i, int i2) {
//        return showDialog(context, i, i2, (DialogOnClickListener) null);
//    }
//
//    public static Dialog showDialog(Context context, int i, int i2, DialogOnClickListener dialogOnClickListener) {
//        return showDialog(context, context.getResources().getString(i), context.getResources().getString(i2), dialogOnClickListener);
//    }
//
//    public static Dialog showDialog(Context context, String str, String str2, DialogOnClickListener dialogOnClickListener) {
//        if (context == null || ((Activity) context).isFinishing()) {
//            return null;
//        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(str).setMessage(str2).setCancelable(false).setPositiveButton(17039370, new C253828(dialogOnClickListener));
//        AlertDialog create = builder.create();
//        create.show();
//        return create;
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$28 */
//    static class C253828 implements DialogInterface.OnClickListener {
//        final /* synthetic */ DialogOnClickListener val$listener;
//
//        C253828(DialogOnClickListener dialogOnClickListener) {
//            this.val$listener = dialogOnClickListener;
//        }
//
//        public void onClick(DialogInterface dialogInterface, int i) {
//            DialogOnClickListener dialogOnClickListener = this.val$listener;
//            if (dialogOnClickListener != null) {
//                dialogOnClickListener.onOKButtonOnClick();
//            }
//        }
//    }
//
//    public static Dialog showDialogOkClick(Context context, int i, int i2, int i3, DialogInterface.OnClickListener onClickListener) {
//        if (context == null || ((Activity) context).isFinishing()) {
//            return null;
//        }
//        String string = context.getResources().getString(i);
//        String string2 = context.getResources().getString(i2);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(string).setMessage(string2).setCancelable(false).setPositiveButton(i3, onClickListener);
//        AlertDialog create = builder.create();
//        create.show();
//        return create;
//    }
//
//    public static Dialog showConfirmDialog(Context context, int i, int i2, ConfirmDialogOnClickListener confirmDialogOnClickListener) {
//        return showConfirmDialog(context, context.getResources().getString(i), context.getResources().getString(i2), confirmDialogOnClickListener);
//    }
//
//    public static Dialog showConfirmDialog(Context context, int i, int i2, int i3, int i4, ConfirmDialogOnClickListener confirmDialogOnClickListener) {
//        if (context == null || ((Activity) context).isFinishing()) {
//            return null;
//        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(i).setMessage(i2).setCancelable(false).setPositiveButton(i3, new C254130(confirmDialogOnClickListener)).setNegativeButton(i4, new C253929(confirmDialogOnClickListener));
//        AlertDialog create = builder.create();
//        create.show();
//        return create;
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$30 */
//    static class C254130 implements DialogInterface.OnClickListener {
//        final /* synthetic */ ConfirmDialogOnClickListener val$listener;
//
//        C254130(ConfirmDialogOnClickListener confirmDialogOnClickListener) {
//            this.val$listener = confirmDialogOnClickListener;
//        }
//
//        public void onClick(DialogInterface dialogInterface, int i) {
//            ConfirmDialogOnClickListener confirmDialogOnClickListener = this.val$listener;
//            if (confirmDialogOnClickListener != null) {
//                confirmDialogOnClickListener.onOKButtonOnClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$29 */
//    static class C253929 implements DialogInterface.OnClickListener {
//        final /* synthetic */ ConfirmDialogOnClickListener val$listener;
//
//        C253929(ConfirmDialogOnClickListener confirmDialogOnClickListener) {
//            this.val$listener = confirmDialogOnClickListener;
//        }
//
//        public void onClick(DialogInterface dialogInterface, int i) {
//            ConfirmDialogOnClickListener confirmDialogOnClickListener = this.val$listener;
//            if (confirmDialogOnClickListener != null) {
//                confirmDialogOnClickListener.onCancelButtonOnClick();
//            }
//            dialogInterface.cancel();
//        }
//    }
//
//    public static Dialog showConfirmDialog(Context context, String str, String str2, ConfirmDialogOnClickListener confirmDialogOnClickListener) {
//        if (context == null || ((Activity) context).isFinishing()) {
//            return null;
//        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(str).setMessage(str2).setCancelable(false).setPositiveButton(17039370, new C254332(confirmDialogOnClickListener)).setNegativeButton(17039360, new C254231(confirmDialogOnClickListener));
//        AlertDialog create = builder.create();
//        create.show();
//        return create;
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$32 */
//    static class C254332 implements DialogInterface.OnClickListener {
//        final /* synthetic */ ConfirmDialogOnClickListener val$listener;
//
//        C254332(ConfirmDialogOnClickListener confirmDialogOnClickListener) {
//            this.val$listener = confirmDialogOnClickListener;
//        }
//
//        public void onClick(DialogInterface dialogInterface, int i) {
//            ConfirmDialogOnClickListener confirmDialogOnClickListener = this.val$listener;
//            if (confirmDialogOnClickListener != null) {
//                confirmDialogOnClickListener.onOKButtonOnClick();
//            }
//        }
//    }
//
//    /* renamed from: design.matrix.photocollage.utils.DialogUtils$31 */
//    static class C254231 implements DialogInterface.OnClickListener {
//        final /* synthetic */ ConfirmDialogOnClickListener val$listener;
//
//        C254231(ConfirmDialogOnClickListener confirmDialogOnClickListener) {
//            this.val$listener = confirmDialogOnClickListener;
//        }
//
//        public void onClick(DialogInterface dialogInterface, int i) {
//            ConfirmDialogOnClickListener confirmDialogOnClickListener = this.val$listener;
//            if (confirmDialogOnClickListener != null) {
//                confirmDialogOnClickListener.onCancelButtonOnClick();
//            }
//            dialogInterface.cancel();
//        }
//    }
}
