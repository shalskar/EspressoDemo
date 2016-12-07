package com.example.vtetau.espressodemo.component;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.vtetau.espressodemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class FieldViewHolder extends RecyclerView.ViewHolder {

    public static final int NO_RESOURCE = -1;

    @Bind(R.id.icon_imageview)
    ImageView iconImageView;

    public FieldViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setIconImageResource(@DrawableRes int resourceId) {
        this.iconImageView.setImageResource(resourceId);
    }

    public void setIconVisibility(boolean visible) {
        this.iconImageView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public abstract void setError(boolean visible, @Nullable String errorMessage);

    public abstract boolean isShowingError();
}
