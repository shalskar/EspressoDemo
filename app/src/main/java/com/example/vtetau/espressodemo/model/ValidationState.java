package com.example.vtetau.espressodemo.model;

import android.support.annotation.Nullable;

public class ValidationState {

    private boolean valid;

    @Nullable
    private String errorMessage;

    public ValidationState(boolean valid, @Nullable String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(@Nullable String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}