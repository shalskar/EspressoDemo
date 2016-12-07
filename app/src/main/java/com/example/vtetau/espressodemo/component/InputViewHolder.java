package com.example.vtetau.espressodemo.component;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;

import com.example.vtetau.espressodemo.R;
import com.example.vtetau.espressodemo.util.StringUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.annotation.Retention;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.OnFocusChange;

import static com.example.vtetau.espressodemo.component.InputViewHolder.InputType.DECIMAL;
import static com.example.vtetau.espressodemo.component.InputViewHolder.InputType.INTEGER;
import static com.example.vtetau.espressodemo.component.InputViewHolder.InputType.STRING;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class InputViewHolder extends FieldViewHolder {

    @Retention(SOURCE)
    @IntDef({INTEGER, DECIMAL, STRING})
    public @interface InputType {
        int INTEGER = android.text.InputType.TYPE_CLASS_NUMBER;
        int DECIMAL = android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;
        int STRING = android.text.InputType.TYPE_CLASS_TEXT;
    }

    public static final int DEFAULT_INPUT_LENGTH = 64;

    @Bind(R.id.input_edittext)
    MaterialEditText inputEditText;

    @BindString(R.string.required_suffix)
    String requiredText;

    @BindColor(R.color.error)
    int errorColour;

    @BindColor(R.color.colorAccent)
    int accentColour;

    @BindColor(R.color.text_secondary_dark)
    int secondaryTextColour;

    @NonNull
    final FieldViewHolderListener listener;

    private int maxInputLength;

    private boolean required;

    @Nullable
    private String hint;

    public InputViewHolder(@NonNull View itemView, @NonNull FieldViewHolderListener listener) {
        super(itemView);

        this.listener = listener;
        this.inputEditText.addTextChangedListener(this.textWatcher);
    }

    @Override
    public void setError(boolean visible, @Nullable String errorMessage) {
        this.inputEditText.setHintTextColor(visible ? this.errorColour : this.secondaryTextColour);
        this.inputEditText.setPrimaryColor(visible ? this.errorColour : this.accentColour);
        this.inputEditText.setError(visible ? errorMessage : null);
    }

    @Override
    public boolean isShowingError() {
        return !StringUtil.emptyString(this.inputEditText.getError());
    }

    @OnFocusChange(R.id.input_edittext)
    void onInputFocusChange(boolean focused) {
        this.inputEditText.removeTextChangedListener(this.textWatcher);
        updateHint();
        if (focused) {
            setInputFilters(this.maxInputLength);

            this.inputEditText.setFloatingLabelAlwaysShown(true);
            this.inputEditText.setHint(null);
        } else {
            this.inputEditText.setFilters(new InputFilter[]{});

            this.inputEditText.setFloatingLabelAlwaysShown(false);
        }
        this.listener.onViewFocusChanged(this, focused);
        this.inputEditText.addTextChangedListener(this.textWatcher);
    }


    @NonNull
    public String getText() {
        return this.inputEditText.getText().toString();
    }

    public void setText(@Nullable String text) {
        this.inputEditText.removeTextChangedListener(this.textWatcher);
        this.inputEditText.setText(text);
        if (!StringUtil.emptyString(text)) {

        }
        this.inputEditText.addTextChangedListener(this.textWatcher);
    }

    public void setHint(@Nullable String hintText) {
        this.hint = hintText;
        updateHint();
    }

    public void setHint(@StringRes int hintId) {
        this.hint = this.itemView.getContext().getString(hintId);
        updateHint();
    }

    @Nullable
    public String getHint() {
        return this.hint;
    }

    private void updateHint() {
        String textToShow = shouldShowRequiredText() ? String.format("%s %s", this.hint, this.requiredText) : this.hint;
        this.inputEditText.setHint(textToShow);
        this.inputEditText.setFloatingLabelText(textToShow);
    }

    private boolean shouldShowRequiredText() {
        return this.required && !this.inputEditText.isFocused() && this.inputEditText.getText().length() == 0;
    }

    @NonNull
    public String getInputText() {
        return this.inputEditText.getText().toString();
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setInputType(@InputType int inputType) {
        this.inputEditText.setInputType(inputType);
    }

    public void setMaxInputLength(int maxInputLength) {
        this.maxInputLength = maxInputLength;
    }

    private void setInputFilters(int maxInputLength) {
        this.inputEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxInputLength)});
    }

    @NonNull
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(@NonNull CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(@NonNull CharSequence charSequence, int start, int before, int count) {
            String output = charSequence.toString();

            listener.onValueChanged(InputViewHolder.this, output);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

}
