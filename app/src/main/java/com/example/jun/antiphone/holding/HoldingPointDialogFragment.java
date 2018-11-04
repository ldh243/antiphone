package com.example.jun.antiphone.holding;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jun.antiphone.R;

public class HoldingPointDialogFragment extends DialogFragment {

    private String title;
    private long point;
    private long minutes;

    public static HoldingPointDialogFragment create(String title, long seconds, long point) {
        HoldingPointDialogFragment holdingPointDialogFragment = new HoldingPointDialogFragment();
        holdingPointDialogFragment.setMinutes(seconds / 60);
        holdingPointDialogFragment.setTitle(title);
        holdingPointDialogFragment.setPoint(point);
        return holdingPointDialogFragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(700, 600);
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Okay!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do something...
                                dialog.dismiss();

                            }
                        }
                );


        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_dialog_holding_point_report, null, false);
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvPoint = v.findViewById(R.id.tvPoint);
        TextView tvMinutes = v.findViewById(R.id.tvMiniute);

        tvTitle.setText(title);
        tvPoint.setText(String.format("%d points", point));
        if (point == 0) {
            tvPoint.setTextColor(Color.parseColor("#ff4d4d"));
        }
        tvMinutes.setText(String.format("%d minutes", minutes));


        builder.setView(v);

        return builder.create();

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        this.getActivity().finish();

    }
}
