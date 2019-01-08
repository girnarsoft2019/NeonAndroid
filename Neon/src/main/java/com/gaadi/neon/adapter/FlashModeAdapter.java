package com.gaadi.neon.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import com.scanlibrary.R;

/**
 * @author shivani
 * @version 1.0
 * @since 28/2/17
 */

public class FlashModeAdapter extends RecyclerView.Adapter<FlashModeAdapter.ViewHolder> {

    private Context mContext;
    private List<String> flashList;
    private OnItemClickListener mItemClickListener;

    public FlashModeAdapter(Context context, List<String> imageList) {
        if (imageList == null) {
            flashList = new ArrayList<>();
        } else {
            this.flashList = imageList;
        }
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.flash_item_layout, null);
        return new ViewHolder(view);
    }


    public List<String> getItems() {
        return flashList;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String name = flashList.get(position);
        if ("off".equals(name)) {
            // holder.icon.setImageResource(R.drawable.ic_no_flash);
            holder.tvFlashtext.setText("Off");
        } else if ("on".equals(name)) {
            // holder.icon.setImageResource(R.drawable.ic_flash);
            holder.tvFlashtext.setText("On");
        } else if ("auto".equals(name)) {
            // holder.icon.setImageResource(R.drawable.ic_flash_auto);
            holder.tvFlashtext.setText("Auto");
        }
    }

    @Override
    public int getItemCount() {
        if (flashList != null)
            return flashList.size();
        return 0;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // ImageView icon;
        TextView tvFlashtext;

        public ViewHolder(View itemView) {
            super(itemView);
            //icon = (ImageView) itemView.findViewById(R.id.ivFlashItem);
            tvFlashtext = (TextView) itemView.findViewById(R.id.tvFlashtext);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }
}

