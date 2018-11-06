package com.example.jun.antiphone.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jun.antiphone.R;

public class SaveSuccessDialogFragment extends DialogFragment {

    private String title;

    public static SaveSuccessDialogFragment create(String title) {
        SaveSuccessDialogFragment saveSuccessDialogFragment = new SaveSuccessDialogFragment();
        saveSuccessDialogFragment.setTitle(title);
        return saveSuccessDialogFragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(700, 400);
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setNegativeButton("OK!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_dialog_save_profile_success, null, false);
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        builder.setView(v);
        return builder.create();

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
