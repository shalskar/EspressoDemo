package com.example.vtetau.espressodemo.component;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.annotation.StringRes;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.example.vtetau.espressodemo.R;

import java.lang.annotation.Retention;

import butterknife.Bind;
import butterknife.OnCheckedChanged;


import static com.example.vtetau.espressodemo.component.SwitchViewHolder.Boolean.FALSE;
import static com.example.vtetau.espressodemo.component.SwitchViewHolder.Boolean.TRUE;
import static java.lang.annotation.RetentionPolicy.CLASS;

public class SwitchViewHolder extends FieldViewHolder {

    @Retention(CLASS)
    @StringDef({TRUE, FALSE})
    public @interface Boolean {
        String TRUE = "true";
        String FALSE = "false";
    }

    @Bind(R.id.primary_textview)
    TextView primaryTextView;

    @Bind(R.id.secondary_textview)
    TextView secondaryTextView;

    @Bind(R.id.switchcompat)
    SwitchCompat switchCompat;

    @NonNull
    private final FieldViewHolderListener fieldViewHolderListener;

    public SwitchViewHolder(@NonNull View itemView, @NonNull FieldViewHolderListener fieldViewHolderListener) {
        super(itemView);

        this.fieldViewHolderListener = fieldViewHolderListener;
    }

    @Override
    public void setError(boolean visible, @Nullable String errorMessage) {
    }

    @Override
    public boolean isShowingError() {
        return false;
    }

    public void setPrimaryText(@Nullable String primaryText) {
        this.primaryTextView.setText(primaryText);
    }

    public void setPrimaryText(@StringRes int primaryTextResource) {
        this.primaryTextView.setText(primaryTextResource);
    }

    public void setSecondaryText(@Nullable String secondaryText) {
        this.secondaryTextView.setText(secondaryText);
    }

    public void setSecondaryText(@StringRes int secondaryTextResource) {
        this.secondaryTextView.setText(secondaryTextResource);
    }

    public void setSecondaryTextVisible(boolean visible) {
        this.secondaryTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Nullable
    public String getPrimaryText() {
        return this.primaryTextView.getText().toString();
    }

    public boolean isChecked() {
        return this.switchCompat.isChecked();
    }

    public void setChecked(boolean checked) {
        this.switchCompat.setChecked(checked);
    }

    public void setChecked(@NonNull String checked) {
        this.switchCompat.setChecked(checked.equals(TRUE));
    }

    @OnCheckedChanged(R.id.switchcompat)
    void onCheckedChanged() {
        String value = this.switchCompat.isChecked() ? TRUE : FALSE;
        this.fieldViewHolderListener.onValueChanged(this, value);
    }
}
