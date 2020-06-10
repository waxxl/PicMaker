package com.eptonic.photocollage.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eptonic.photocollage.presenter.listener.OnItemClickListener;
import com.eptonic.photocollage.R;

public class FreeTabEditAdapter extends RecyclerView.Adapter<FreeTabEditAdapter.ViewHolder> {
    private OnItemClickListener mItemListener;
    private final int[] data;

    public FreeTabEditAdapter(int[] data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.sticker_custom_recycler_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(data == null) {
            return;
        }
        holder.imageView.setImageResource(data[position]);
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
        if(data == null) {
            return 0;
        }
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
        }
    }
}
