package com.yd.picmaker.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.Listener.OnItemClickListener;
import com.yd.picmaker.R;
import com.yd.picmaker.model.StickerSaveData;
import com.yd.picmaker.util.PhotoUtils;

import java.util.List;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.ViewHolder> {
    private OnItemClickListener mItemListener;
    private List<StickerSaveData> data;

    public SaveAdapter(List<StickerSaveData> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.save_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(data == null) {
            return;
        }
        PhotoUtils.loadImageWithGlide(holder.imageView.getContext(), holder.imageView, data.get(position).getFilename());
    }

    @Override
    public int getItemCount() {
        if(data == null) {
            return 0;
        }
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
        }
    }
}
