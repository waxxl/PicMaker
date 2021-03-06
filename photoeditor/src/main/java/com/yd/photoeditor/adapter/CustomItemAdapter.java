package com.yd.photoeditor.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yd.photoeditor.R;
import com.yd.photoeditor.listener.OnBottomMenuItemClickListener;
import com.yd.photoeditor.model.XXXXXXXXXXXXXX;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomItemAdapter extends RecyclerView.Adapter<CustomItemAdapter.RViewHolder> {
    private OnBottomMenuItemClickListener mListener;
    private ImageProcessingActivity activity;
    ArrayList<XXXXXXXXXXXXXX> datas = new ArrayList<>();
    public CustomItemAdapter(ImageProcessingActivity activity) {
        this.activity = activity;
    }

    public CustomItemAdapter(ImageProcessingActivity mActivity, List<XXXXXXXXXXXXXX> mMenuItems, boolean b) {

    }

    public void setData(ArrayList<XXXXXXXXXXXXXX> dataT) {
        this.datas.clear();
        datas.addAll(dataT);
        notifyDataSetChanged();
    }

    public boolean isShaking() {
        return false;
    }

    public void setShaking(boolean shaking) {

    }

    public void setListener(OnBottomMenuItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.process_item, null);
        return new RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        //PhotoUtils.loadImageWithGlide(activity, holder.imageView, datas.get(position).getThumbnail());
        holder.imageView.setImageResource(Integer.parseInt(datas.get(position).getThumbnail().substring(11)));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mListener) {
                    mListener.onEditorItemClick(position, datas.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(datas == null) {
            return 0;
        }
        return datas.size();
    }

    public class RViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
        }
    }
}
