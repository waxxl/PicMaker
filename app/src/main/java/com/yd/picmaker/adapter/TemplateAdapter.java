package com.yd.picmaker.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.R;
import com.yd.picmaker.model.TemplateItem;

import java.util.ArrayList;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateViewHolder> {
    private static final int LINEAR = 0;
    public static final int VIEW_TYPE_CONTENT = 0;
    public static final int VIEW_TYPE_HEADER = 1;
    private final Context mContext;
    private int mHeaderDisplay;
    private LayoutInflater mInflater;
    private final ArrayList<TemplateItem> mItems = new ArrayList<>();
    private boolean mMarginsFixed;
    private TemplateViewHolder.OnTemplateItemClickListener mOnTemplateItemClickListener;

    public TemplateAdapter(Context context, int i, ArrayList<TemplateItem> arrayList, TemplateViewHolder.OnTemplateItemClickListener onTemplateItemClickListener) {
        this.mContext = context;
        this.mHeaderDisplay = i;
        this.mOnTemplateItemClickListener = onTemplateItemClickListener;
        this.mInflater = LayoutInflater.from(context);
        String str = "";
        int i2 = 0;
        int i3 = -1;
        int i4 = 0;
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            TemplateItem templateItem = arrayList.get(i5);
            String str2 = "" + templateItem.getPhotoItemList().size();
            if (!TextUtils.equals(str, str2)) {
                TemplateItem templateItem2 = new TemplateItem();
                int i6 = i5 + i2;
                i2++;
                templateItem2.setIsHeader(true);
                templateItem2.setHeader(str2);
                templateItem2.setSectionManager(1);
                templateItem2.setSectionFirstPosition(i6);
                this.mItems.add(templateItem2);
                i4 = i6;
                str = str2;
                i3 = 1;
            }
            templateItem.setSectionFirstPosition(i4);
            templateItem.setSectionManager(i3);
            templateItem.setIsHeader(false);
            this.mItems.add(templateItem);
        }
    }

    public void setData(ArrayList<TemplateItem> arrayList) {
        this.mItems.clear();
        String str = "";
        int i = 0;
        int i2 = -1;
        int i3 = 0;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            TemplateItem templateItem = arrayList.get(i4);
            String str2 = "" + templateItem.getPhotoItemList().size();
            if (!TextUtils.equals(str, str2)) {
                TemplateItem templateItem2 = new TemplateItem();
                int i5 = i4 + i;
                i++;
                templateItem2.setIsHeader(true);
                templateItem2.setHeader(str2);
                templateItem2.setSectionManager(1);
                templateItem2.setSectionFirstPosition(i5);
                this.mItems.add(templateItem2);
                i3 = i5;
                str = str2;
                i2 = 1;
            }
            templateItem.setSectionFirstPosition(i3);
            templateItem.setSectionManager(i2);
            templateItem.setIsHeader(false);
            this.mItems.add(templateItem);
        }
        notifyDataSetChanged();
    }

    public TemplateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        view = this.mInflater.inflate(R.layout.item_template, viewGroup, false);
        return new TemplateViewHolder(view, i);
    }

    public void onBindViewHolder(TemplateViewHolder templateViewHolder, int i) {
        TemplateItem templateItem = this.mItems.get(i);
        View view = templateViewHolder.itemView;
        templateViewHolder.bindItem(templateItem, this.mOnTemplateItemClickListener);
    }

    public int getItemViewType(int i) {
        return this.mItems.get(i).isHeader() ? 1 : 0;
    }

    public int getItemCount() {
        return this.mItems.size();
    }

    public void setHeaderDisplay(int i) {
        this.mHeaderDisplay = i;
        notifyHeaderChanges();
    }

    public void setMarginsFixed(boolean z) {
        this.mMarginsFixed = z;
        notifyHeaderChanges();
    }

    private void notifyHeaderChanges() {
        for (int i = 0; i < this.mItems.size(); i++) {
            if (this.mItems.get(i).isHeader()) {
                notifyItemChanged(i);
            }
        }
    }
}