package com.example.vtetau.espressodemo.component;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.example.vtetau.espressodemo.R;

import butterknife.Bind;
import butterknife.BindColor;

public class DetailViewHolder extends FieldViewHolder {

    @Bind(R.id.primary_textview)
    TextView primaryTextView;

    @Bind(R.id.secondary_textview)
    TextView secondaryTextView;

    @BindColor(R.color.error)
    int errorColour;

    @BindColor(R.color.text_primary_dark)
    int primaryTextColour;

    @BindColor(R.color.text_secondary_dark)
    int secondaryTextColour;

    public DetailViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setError(boolean visible, @Nullable String errorMessage) {
        this.primaryTextView.setTextColor(visible ? this.errorColour : this.primaryTextColour);
        this.secondaryTextView.setTextColor(visible ? this.errorColour : this.secondaryTextColour);
    }

    @Override
    public boolean isShowingError() {
        return this.primaryTextView.getCurrentTextColor() == this.errorColour;
    }

    public void setPrimaryText(@Nullable String primaryText) {
        this.primaryTextView.setText(primaryText);
    }

    public void setPrimaryText(@StringRes int resourceId) {
        this.primaryTextView.setText(resourceId);
    }

    public void setSecondaryText(@Nullable String secondaryText) {
        this.secondaryTextView.setText(secondaryText);
    }

    public void setSecondaryText(@StringRes int resourceId) {
        this.secondaryTextView.setText(resourceId);
    }

    @Nullable
    public String getPrimaryText(){
        return this.primaryTextView.getText().toString();
    }
}
