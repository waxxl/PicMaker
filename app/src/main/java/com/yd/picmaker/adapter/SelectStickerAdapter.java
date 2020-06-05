package com.yd.picmaker.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.Listener.OnItemClickListener;
import com.yd.picmaker.R;

import static com.yd.picmaker.util.Constants.STICKERS;

public class SelectStickerAdapter extends RecyclerView.Adapter<SelectStickerAdapter.ViewHolder> {
    private OnItemClickListener mItemListener;
    private final int mType = 0;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.sticker_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(STICKERS[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mItemListener) {
                    mItemListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return STICKERS.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
        }
    }
}
