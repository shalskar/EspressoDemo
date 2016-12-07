package com.example.vtetau.espressodemo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.vtetau.espressodemo.R;

import icepick.Icepick;
import icepick.Icicle;

public class AttributeOptionDialogFragment extends DialogFragment {

    private static final String ARG_ATTRIBUTE_POSITION = "attribute_position";
    private static final String ARG_OPTIONS = "options";
    private static final String ARG_SELECTED_OPTION = "selected_option";
    private static final String ARG_TITLE = "title";

    private static final int NO_OPTION_SELECTED = -1;

    private int attributePosition;

    @Icicle
    int selectedOption = NO_OPTION_SELECTED;

    @NonNull
    private String title;

    @NonNull
    private CharSequence[] options;

    @NonNull
    private AttributeOptionDialogListener listener;

    @NonNull
    public static AttributeOptionDialogFragment newInstance(int attributePosition, @NonNull String title,
                                                            @NonNull CharSequence[] options, int selectedOption) {
        AttributeOptionDialogFragment fragment = new AttributeOptionDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_ATTRIBUTE_POSITION, attributePosition);
        bundle.putString(ARG_TITLE, title);
        bundle.putCharSequenceArray(ARG_OPTIONS, options);
        bundle.putInt(ARG_SELECTED_OPTION, selectedOption);
        fragment.setArguments(bundle);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Icepick.restoreInstanceState(this, savedInstanceState);
        initialiseFields();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setSingleChoiceItems(this.options, this.selectedOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialogInterface, int which) {
                selectedOption = which;
                getAlertDialog().getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
            }
        }).setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null);

        AlertDialog alertDialog = builder.create();
        initialiseCustomTitle(alertDialog);
        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        initialiseButtons();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof AttributeOptionDialogListener)) {
            throw new IllegalArgumentException(getParentFragment().toString() + " must implement AttributeOptionDialogListener.");
        }

        this.listener = (AttributeOptionDialogListener) getActivity();
    }

    private void initialiseFields() {
        Bundle arguments = getArguments();
        this.attributePosition = arguments.getInt(ARG_ATTRIBUTE_POSITION);
        this.title = arguments.getString(ARG_TITLE);
        this.options = arguments.getCharSequenceArray(ARG_OPTIONS);
        if (selectedOption == NO_OPTION_SELECTED) {
            this.selectedOption = arguments.getInt(ARG_SELECTED_OPTION);
        }
    }

    private void initialiseCustomTitle(@NonNull AlertDialog alertDialog) {
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.alert_dialog_textview, null);
        textView.setText(this.title);
        alertDialog.setCustomTitle(textView);
    }

    private void initialiseButtons() {
        // We set the onClickListener here so that we can manually dismiss the dialog
        getAlertDialog().getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (selectedOption != NO_OPTION_SELECTED) {
                    listener.onPositiveClicked(attributePosition, selectedOption);
                    dismiss();
                }
            }
        });

        if (this.selectedOption == NO_OPTION_SELECTED) {
            getAlertDialog().getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    @Nullable
    private AlertDialog getAlertDialog(){
        return (AlertDialog) getDialog();
    }

    public interface AttributeOptionDialogListener {
        void onPositiveClicked(int position, int selectedOption);
    }
}
