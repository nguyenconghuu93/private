package com.vicloud.vn.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vicloud.vn.R;
import com.vicloud.vn.models.SheetItem;

import java.util.List;

/**
 * Created by huunc on 7/23/16.
 */
public class SheetAdapter extends RecyclerView.Adapter<SheetAdapter.ViewHolder>{
    private List<SheetItem> mItems;
    public SheetAdapter(List<SheetItem> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sheet_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;
        public SheetItem sheetItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.icon_action);
            textView = (TextView) itemView.findViewById(R.id.text_action);
        }

        public void setData(SheetItem sheetItem) {
            this.sheetItem = sheetItem;
            imageView.setImageResource(sheetItem.getDrawableResource());
            textView.setText(sheetItem.getTitle());
        }
    }
}
