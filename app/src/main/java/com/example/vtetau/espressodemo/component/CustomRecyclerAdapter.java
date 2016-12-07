package com.example.vtetau.espressodemo.component;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class CustomRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private CustomRecyclerAdapter.OnItemClickListener onItemClickListener;
    private CustomRecyclerAdapter.OnItemLongClickListener onItemLongClickListener;

    public CustomRecyclerAdapter() {
    }

    public void onBindViewHolder(@NonNull final VH holder, int position) {
        if (null != this.onItemClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CustomRecyclerAdapter.this.onItemClickListener.onItemClick(holder, holder.getAdapterPosition());
                }
            });
        }

        if (null != this.onItemLongClickListener) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    return CustomRecyclerAdapter.this.onItemLongClickListener.onItemLongClick(holder, holder.getAdapterPosition());
                }
            });
        }

    }

    public void setOnItemClickListener(CustomRecyclerAdapter.OnItemClickListener<VH> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(CustomRecyclerAdapter.OnItemLongClickListener<VH> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public CustomRecyclerAdapter.OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public CustomRecyclerAdapter.OnItemLongClickListener getOnItemLongClickListener() {
        return this.onItemLongClickListener;
    }

    public interface OnItemLongClickListener<VH> {
        boolean onItemLongClick(VH var1, int var2);
    }

    public interface OnItemClickListener<VH> {
        void onItemClick(VH var1, int var2);
    }
}
