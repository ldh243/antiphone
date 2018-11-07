package com.example.jun.antiphone.cart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.login_logout.LoginActivity;
import com.example.jun.antiphone.singleton.RestfulAPIManager;
import com.example.jun.antiphone.singleton.VolleyCallback;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.firebase.auth.FirebaseAuth;

import util.Constants;

public class ConfirmGetVoucherDialogFragment extends DialogFragment {

    private String title;
    private String uid;
    private int postId;
    private TextView tvTotalPointsRemainPostDetail;

    public static ConfirmGetVoucherDialogFragment create(String title, String uid, int postId, TextView tvTotalPointsRemainPostDetail) {
        ConfirmGetVoucherDialogFragment saveSuccessDialogFragment = new ConfirmGetVoucherDialogFragment();
        saveSuccessDialogFragment.setTitle(title);
        saveSuccessDialogFragment.setUid(uid);
        saveSuccessDialogFragment.setPostId(postId);
        saveSuccessDialogFragment.setTvTotalPointsRemainPostDetail(tvTotalPointsRemainPostDetail);
        return saveSuccessDialogFragment;
    }

    public TextView getTvTotalPointsRemainPostDetail() {
        return tvTotalPointsRemainPostDetail;
    }

    public void setTvTotalPointsRemainPostDetail(TextView tvTotalPointsRemainPostDetail) {
        this.tvTotalPointsRemainPostDetail = tvTotalPointsRemainPostDetail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = null;
        builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                RestfulAPIManager.getInstancẹ̣̣̣().getVoucherByPostIdUsername(getContext(), uid, postId, new VolleyCallback() {
                                    @Override
                                    public void onSuccess(Object response) {
                                        JsonNode data = (JsonNode) response;
                                        String pointRemain = data.get("left").asText();
                                        tvTotalPointsRemainPostDetail.setText(pointRemain);
                                    }

                                    @Override
                                    public void onError(Object ex) {

                                    }
                                });
                                dialog.dismiss();

                            }
                        }
                ).setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_dialog_confirm_getvoucher, null, false);
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
