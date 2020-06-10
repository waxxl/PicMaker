//package com.yd.photocollage.frame;
//
//import android.graphics.RectF;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//
//import com.yd.photocollage.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FrameBuilder {
//    private List<View> mChildViews = new ArrayList();
//    private List<FrameImageView> mFrameImageViews = new ArrayList();
//    private List<RectF> mFramePositions = new ArrayList();
//    private int mFrameType = 0;
//    private View mRootView;
//
//    public FrameBuilder(View view, int i) {
//        this.mRootView = view;
//        this.mFrameType = i;
//        switch (i) {
//            case 0:
//                inflateFrame1();
//                break;
//            case 1:
//                inflateFrame2();
//                break;
//            case 2:
//                inflateFrame3();
//                break;
//            case 3:
//                inflateFrame4();
//                break;
//            case 4:
//                inflateFrame5();
//                break;
//            case 5:
//                inflateFrame6();
//                break;
//            case 6:
//                inflateFrame7();
//                break;
//            case 7:
//                inflateFrame8();
//                break;
//            case 8:
//                inflateFrame9();
//                break;
//        }
////        FrameImageView frameImageView = (FrameImageView) this.mRootView.findViewById(R.id.imageView1);
////        if (frameImageView != null) {
////            this.mFrameImageViews.add(frameImageView);
////        }
////        FrameImageView frameImageView2 = (FrameImageView) this.mRootView.findViewById(R.id.imageView2);
////        if (frameImageView2 != null) {
////            this.mFrameImageViews.add(frameImageView2);
////        }
////        FrameImageView frameImageView3 = (FrameImageView) this.mRootView.findViewById(R.id.imageView3);
////        if (frameImageView3 != null) {
////            this.mFrameImageViews.add(frameImageView3);
////        }
////        FrameImageView frameImageView4 = (FrameImageView) this.mRootView.findViewById(R.id.imageView4);
////        if (frameImageView4 != null) {
////            this.mFrameImageViews.add(frameImageView4);
////        }
//    }
//
//    public List<RectF> getFramePositions() {
//        return this.mFramePositions;
//    }
//
//    public void setBorderSize(int i) {
//        switch (this.mFrameType) {
//            case 0:
//                setBorderSizeForFrame1(i);
//                break;
//            case 1:
//                setBorderSizeForFrame2(i);
//                break;
//            case 2:
//                setBorderSizeForFrame3(i);
//                break;
//            case 3:
//                setBorderSizeForFrame4(i);
//                break;
//            case 4:
//                setBorderSizeForFrame5(i);
//                break;
//            case 5:
//                setBorderSizeForFrame6(i);
//                break;
//            case 6:
//                setBorderSizeForFrame7(i);
//                break;
//            case 7:
//                setBorderSizeForFrame8(i);
//                break;
//            case 8:
//                setBorderSizeForFrame9(i);
//                break;
//        }
//        findFramePositions(this.mRootView.getResources().getDimension(R.dimen.frame_container_margin), (float) i);
//    }
//
//    private void inflateFrame1() {
//        this.mChildViews.add((LinearLayout) this.mRootView.findViewById(R.id.linearLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout2));
//        this.mChildViews.add((LinearLayout) this.mRootView.findViewById(R.id.linearLayout2));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout3));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout4));
//    }
//
//    private void setBorderSizeForFrame1(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        layoutParams.bottomMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mChildViews.get(1).getLayoutParams();
//        layoutParams2.rightMargin = i;
//        this.mChildViews.get(1).setLayoutParams(layoutParams2);
//        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mChildViews.get(3).getLayoutParams();
//        layoutParams3.leftMargin = i;
//        layoutParams3.rightMargin = i;
//        layoutParams3.bottomMargin = i;
//        this.mChildViews.get(3).setLayoutParams(layoutParams3);
//        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mChildViews.get(4).getLayoutParams();
//        layoutParams4.rightMargin = i;
//        this.mChildViews.get(4).setLayoutParams(layoutParams4);
//    }
//
//    private void inflateFrame2() {
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout2));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout3));
//    }
//
//    private void setBorderSizeForFrame2(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        layoutParams.bottomMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mChildViews.get(1).getLayoutParams();
//        layoutParams2.rightMargin = i;
//        layoutParams2.leftMargin = i;
//        this.mChildViews.get(1).setLayoutParams(layoutParams2);
//        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mChildViews.get(2).getLayoutParams();
//        layoutParams3.leftMargin = i;
//        layoutParams3.topMargin = i;
//        layoutParams3.rightMargin = i;
//        layoutParams3.bottomMargin = i;
//        this.mChildViews.get(2).setLayoutParams(layoutParams3);
//    }
//
//    private void inflateFrame3() {
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//        this.mChildViews.add((LinearLayout) this.mRootView.findViewById(R.id.linearLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout2));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout3));
//    }
//
//    private void setBorderSizeForFrame3(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        layoutParams.bottomMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mChildViews.get(1).getLayoutParams();
//        layoutParams2.topMargin = i;
//        layoutParams2.rightMargin = i;
//        layoutParams2.bottomMargin = i;
//        this.mChildViews.get(1).setLayoutParams(layoutParams2);
//        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mChildViews.get(2).getLayoutParams();
//        layoutParams3.bottomMargin = i;
//        this.mChildViews.get(2).setLayoutParams(layoutParams3);
//    }
//
//    private void inflateFrame4() {
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout2));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout3));
//    }
//
//    private void setBorderSizeForFrame4(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        layoutParams.bottomMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mChildViews.get(1).getLayoutParams();
//        layoutParams2.topMargin = i;
//        layoutParams2.bottomMargin = i;
//        this.mChildViews.get(1).setLayoutParams(layoutParams2);
//        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mChildViews.get(2).getLayoutParams();
//        layoutParams3.leftMargin = i;
//        layoutParams3.topMargin = i;
//        layoutParams3.rightMargin = i;
//        layoutParams3.bottomMargin = i;
//        this.mChildViews.get(2).setLayoutParams(layoutParams3);
//    }
//
//    private void inflateFrame5() {
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//    }
//
//    private void setBorderSizeForFrame5(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        layoutParams.bottomMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//    }
//
//    private void inflateFrame6() {
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout2));
//    }
//
//    private void setBorderSizeForFrame6(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        layoutParams.bottomMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mChildViews.get(1).getLayoutParams();
//        layoutParams2.leftMargin = i;
//        layoutParams2.rightMargin = i;
//        layoutParams2.bottomMargin = i;
//        this.mChildViews.get(1).setLayoutParams(layoutParams2);
//    }
//
//    private void inflateFrame7() {
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout2));
//    }
//
//    private void setBorderSizeForFrame7(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        layoutParams.bottomMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mChildViews.get(1).getLayoutParams();
//        layoutParams2.topMargin = i;
//        layoutParams2.rightMargin = i;
//        layoutParams2.bottomMargin = i;
//        this.mChildViews.get(1).setLayoutParams(layoutParams2);
//    }
//
//    private void inflateFrame8() {
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//        this.mChildViews.add((LinearLayout) this.mRootView.findViewById(R.id.linearLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout2));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout3));
//    }
//
//    private void setBorderSizeForFrame8(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        layoutParams.bottomMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mChildViews.get(1).getLayoutParams();
//        layoutParams2.leftMargin = i;
//        layoutParams2.rightMargin = i;
//        layoutParams2.bottomMargin = i;
//        this.mChildViews.get(1).setLayoutParams(layoutParams2);
//        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mChildViews.get(2).getLayoutParams();
//        layoutParams3.rightMargin = i;
//        this.mChildViews.get(3).setLayoutParams(layoutParams3);
//    }
//
//    private void inflateFrame9() {
//        this.mChildViews.add((LinearLayout) this.mRootView.findViewById(R.id.linearLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout1));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout2));
//        this.mChildViews.add((FrameLayout) this.mRootView.findViewById(R.id.frameLayout3));
//    }
//
//    private void setBorderSizeForFrame9(int i) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mChildViews.get(0).getLayoutParams();
//        layoutParams.leftMargin = i;
//        layoutParams.topMargin = i;
//        layoutParams.rightMargin = i;
//        this.mChildViews.get(0).setLayoutParams(layoutParams);
//        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mChildViews.get(1).getLayoutParams();
//        layoutParams2.rightMargin = i;
//        this.mChildViews.get(1).setLayoutParams(layoutParams2);
//        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mChildViews.get(3).getLayoutParams();
//        layoutParams3.leftMargin = i;
//        layoutParams3.topMargin = i;
//        layoutParams3.rightMargin = i;
//        layoutParams3.bottomMargin = i;
//        this.mChildViews.get(3).setLayoutParams(layoutParams3);
//    }
//
//    public void findFramePositions(float f, float f2) {
//        RectF rectF;
//        switch (this.mFrameType) {
//            case 0:
//                findFramePositions1(f, f2);
//                break;
//            case 1:
//                findFramePositions2(f, f2);
//                break;
//            case 2:
//                findFramePositions3(f, f2);
//                break;
//            case 3:
//                findFramePositions4(f, f2);
//                break;
//            case 4:
//                findFramePositions5(f, f2);
//                break;
//            case 5:
//                findFramePositions6(f, f2);
//                break;
//            case 6:
//                findFramePositions7(f, f2);
//                break;
//            case 7:
//                findFramePositions8(f, f2);
//                break;
//            case 8:
//                findFramePositions9(f, f2);
//                break;
//        }
//        for (int i = 0; i < this.mFrameImageViews.size(); i++) {
//            if (i < this.mFramePositions.size() && (rectF = this.mFramePositions.get(i)) != null) {
//                this.mFrameImageViews.get(i).setFramePosition(rectF.left, rectF.top);
//            }
//        }
//    }
//
//    private void findFramePositions1(float f, float f2) {
//        this.mFramePositions.clear();
//        float f3 = 3.0f * f2;
//        float width = (((float) this.mRootView.getWidth()) - f3) / 2.0f;
//        float height = (((float) this.mRootView.getHeight()) - f3) / 2.0f;
//        RectF rectF = new RectF();
//        float f4 = f + f2;
//        rectF.left = f4;
//        rectF.top = f4;
//        rectF.right = rectF.left + width;
//        rectF.bottom = rectF.top + height;
//        this.mFramePositions.add(rectF);
//        RectF rectF2 = new RectF();
//        float f5 = f + (f2 * 2.0f);
//        float f6 = f5 + width;
//        rectF2.left = f6;
//        rectF2.top = f4;
//        rectF2.right = rectF2.left + width;
//        rectF2.bottom = rectF2.top + height;
//        this.mFramePositions.add(rectF2);
//        RectF rectF3 = new RectF();
//        rectF3.left = f4;
//        float f7 = f5 + height;
//        rectF3.top = f7;
//        rectF3.right = rectF3.left + width;
//        rectF3.bottom = rectF3.top + height;
//        this.mFramePositions.add(rectF3);
//        RectF rectF4 = new RectF();
//        rectF4.left = f6;
//        rectF4.top = f7;
//        rectF4.right = rectF4.left + width;
//        rectF4.bottom = rectF4.top + height;
//        this.mFramePositions.add(rectF4);
//    }
//
//    private void findFramePositions2(float f, float f2) {
//        this.mFramePositions.clear();
//        float f3 = f2 * 2.0f;
//        float width = ((float) this.mRootView.getWidth()) - f3;
//        float f4 = f2 * 3.0f;
//        float height = (((float) this.mRootView.getHeight()) - f4) / 3.0f;
//        RectF rectF = new RectF();
//        float f5 = f2 + f;
//        rectF.left = f5;
//        rectF.top = f5;
//        rectF.right = rectF.left + width;
//        rectF.bottom = rectF.top + height;
//        this.mFramePositions.add(rectF);
//        RectF rectF2 = new RectF();
//        rectF2.left = f5;
//        rectF2.top = f3 + f + height;
//        rectF2.right = rectF2.left + width;
//        rectF2.bottom = rectF2.top + height;
//        this.mFramePositions.add(rectF2);
//        RectF rectF3 = new RectF();
//        rectF3.left = f5;
//        rectF3.top = f + f4 + (2.0f * height);
//        rectF3.right = rectF3.left + width;
//        rectF3.bottom = rectF3.top + height;
//        this.mFramePositions.add(rectF3);
//    }
//
//    private void findFramePositions3(float f, float f2) {
//        this.mFramePositions.clear();
//        int width = this.mRootView.getWidth();
//        int height = this.mRootView.getHeight();
//        float f3 = 3.0f * f2;
//        float f4 = (((float) width) - f3) / 2.0f;
//        RectF rectF = new RectF();
//        float f5 = f + f2;
//        rectF.left = f5;
//        rectF.top = f5;
//        rectF.right = rectF.left + f4;
//        float f6 = (float) height;
//        float f7 = f2 * 2.0f;
//        rectF.bottom = rectF.top + (f6 - f7);
//        this.mFramePositions.add(rectF);
//        RectF rectF2 = new RectF();
//        float f8 = f + f7;
//        float f9 = f8 + f4;
//        rectF2.left = f9;
//        rectF2.top = f5;
//        rectF2.right = rectF2.left + f4;
//        float f10 = (f6 - f3) / 2.0f;
//        rectF2.bottom = rectF2.top + f10;
//        this.mFramePositions.add(rectF2);
//        RectF rectF3 = new RectF();
//        rectF3.left = f9;
//        rectF3.top = f8 + f10;
//        rectF3.right = rectF3.left + f4;
//        rectF3.bottom = rectF3.top + f10;
//        this.mFramePositions.add(rectF3);
//        this.mFramePositions.add(rectF3);
//    }
//
//    private void findFramePositions4(float f, float f2) {
//        this.mFramePositions.clear();
//        float width = (((float) this.mRootView.getWidth()) - (4.0f * f2)) / 3.0f;
//        float f3 = f2 * 2.0f;
//        float height = ((float) this.mRootView.getHeight()) - f3;
//        RectF rectF = new RectF();
//        float f4 = f + f2;
//        rectF.left = f4;
//        rectF.top = f4;
//        rectF.right = rectF.left + width;
//        rectF.bottom = rectF.top + height;
//        this.mFramePositions.add(rectF);
//        RectF rectF2 = new RectF();
//        rectF2.left = f3 + f + width;
//        rectF2.top = f4;
//        rectF2.right = rectF2.left + width;
//        rectF2.bottom = rectF2.top + height;
//        this.mFramePositions.add(rectF2);
//        RectF rectF3 = new RectF();
//        rectF3.left = f + (f2 * 3.0f) + (2.0f * width);
//        rectF3.top = f4;
//        rectF3.right = rectF3.left + width;
//        rectF3.bottom = rectF3.top + height;
//        this.mFramePositions.add(rectF3);
//    }
//
//    private void findFramePositions5(float f, float f2) {
//        this.mFramePositions.clear();
//        float f3 = 2.0f * f2;
//        float width = ((float) this.mRootView.getWidth()) - f3;
//        float height = ((float) this.mRootView.getHeight()) - f3;
//        RectF rectF = new RectF();
//        float f4 = f + f2;
//        rectF.left = f4;
//        rectF.top = f4;
//        rectF.right = rectF.left + width;
//        rectF.bottom = rectF.top + height;
//        this.mFramePositions.add(rectF);
//    }
//
//    private void findFramePositions6(float f, float f2) {
//        this.mFramePositions.clear();
//        float f3 = f2 * 2.0f;
//        float width = ((float) this.mRootView.getWidth()) - f3;
//        float height = (((float) this.mRootView.getHeight()) - (3.0f * f2)) / 2.0f;
//        RectF rectF = new RectF();
//        float f4 = f2 + f;
//        rectF.left = f4;
//        rectF.top = f4;
//        rectF.right = rectF.left + width;
//        rectF.bottom = rectF.top + height;
//        this.mFramePositions.add(rectF);
//        RectF rectF2 = new RectF();
//        rectF2.left = f4;
//        rectF2.top = f + f3 + height;
//        rectF2.right = rectF2.left + width;
//        rectF2.bottom = rectF2.top + height;
//        this.mFramePositions.add(rectF2);
//    }
//
//    private void findFramePositions7(float f, float f2) {
//        this.mFramePositions.clear();
//        float width = (((float) this.mRootView.getWidth()) - (3.0f * f2)) / 2.0f;
//        float f3 = 2.0f * f2;
//        float height = ((float) this.mRootView.getHeight()) - f3;
//        RectF rectF = new RectF();
//        float f4 = f2 + f;
//        rectF.left = f4;
//        rectF.top = f4;
//        rectF.right = rectF.left + width;
//        rectF.bottom = rectF.top + height;
//        this.mFramePositions.add(rectF);
//        RectF rectF2 = new RectF();
//        rectF2.left = f + f3 + width;
//        rectF2.top = f4;
//        rectF2.right = rectF2.left + width;
//        rectF2.bottom = rectF2.top + height;
//        this.mFramePositions.add(rectF2);
//    }
//
//    private void findFramePositions8(float f, float f2) {
//        this.mFramePositions.clear();
//        int width = this.mRootView.getWidth();
//        float f3 = 3.0f * f2;
//        float height = (((float) this.mRootView.getHeight()) - f3) / 2.0f;
//        RectF rectF = new RectF();
//        float f4 = f + f2;
//        rectF.left = f4;
//        rectF.top = f4;
//        float f5 = (float) width;
//        float f6 = f2 * 2.0f;
//        rectF.right = rectF.left + (f5 - f6);
//        rectF.bottom = rectF.top + height;
//        this.mFramePositions.add(rectF);
//        RectF rectF2 = new RectF();
//        rectF2.left = f4;
//        float f7 = f + f6;
//        float f8 = f7 + height;
//        rectF2.top = f8;
//        float f9 = (f5 - f3) / 2.0f;
//        rectF2.right = rectF2.left + f9;
//        rectF2.bottom = rectF2.top + height;
//        this.mFramePositions.add(rectF2);
//        RectF rectF3 = new RectF();
//        rectF3.left = f7 + f9;
//        rectF3.top = f8;
//        rectF3.right = rectF3.left + f9;
//        rectF3.bottom = rectF3.top + height;
//        this.mFramePositions.add(rectF3);
//    }
//
//    private void findFramePositions9(float f, float f2) {
//        this.mFramePositions.clear();
//        int width = this.mRootView.getWidth();
//        float f3 = 3.0f * f2;
//        float height = (((float) this.mRootView.getHeight()) - f3) / 2.0f;
//        RectF rectF = new RectF();
//        float f4 = f + f2;
//        rectF.left = f4;
//        rectF.top = f4;
//        float f5 = (float) width;
//        float f6 = (f5 - f3) / 2.0f;
//        rectF.right = rectF.left + f6;
//        rectF.bottom = rectF.top + height;
//        this.mFramePositions.add(rectF);
//        RectF rectF2 = new RectF();
//        float f7 = f2 * 2.0f;
//        float f8 = f + f7;
//        rectF2.left = f8 + f6;
//        rectF2.top = f4;
//        rectF2.right = rectF2.left + f6;
//        rectF2.bottom = rectF2.top + height;
//        this.mFramePositions.add(rectF2);
//        RectF rectF3 = new RectF();
//        rectF3.left = f4;
//        rectF3.top = f8 + height;
//        rectF3.right = rectF3.left + (f5 - f7);
//        rectF3.bottom = rectF3.top + height;
//        this.mFramePositions.add(rectF3);
//    }
//}
