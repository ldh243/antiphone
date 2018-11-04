package com.example.jun.antiphone.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.login_logout.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ConfirmLogoutDialogFragment extends DialogFragment {

    private String title;

    public static ConfirmLogoutDialogFragment create(String title) {
        ConfirmLogoutDialogFragment saveSuccessDialogFragment = new ConfirmLogoutDialogFragment();
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
        getDialog().getWindow().setLayout(800, 350);
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseAuth.getInstance().signOut();
                                getActivity().finish();
                                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        }
                ).setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_dialog_confirm_logout, null, false);
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
