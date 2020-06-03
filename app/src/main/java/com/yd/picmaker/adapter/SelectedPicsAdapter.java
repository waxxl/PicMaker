package com.yd.picmaker.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.R;
import com.yd.picmaker.util.PhotoUtils;

import java.util.ArrayList;

public class SelectedPicsAdapter extends RecyclerView.Adapter<SelectedPicsAdapter.ViewHolder> {

    private ArrayList<String> mSelectPics = new ArrayList<>();

    public void addPic(String s) {
        mSelectPics.add(s);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_selected_gallery_photo, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < mSelectPics.size())
            PhotoUtils.loadImageWithGlide(holder.mPic.getContext(), holder.mPic, mSelectPics.get(position));
    }

    @Override
    public int getItemCount() {
        return mSelectPics.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mClose;
        public ImageView mPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mClose = itemView.findViewById(R.id.delete);
            mPic = itemView.findViewById(R.id.photo_select);
        }
    }
}
