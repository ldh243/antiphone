package com.example.jun.antiphone.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.singleton.RestfulAPIManager;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import entity.UserDTO;

public class FragmentEditProfile extends Fragment implements View.OnClickListener {

    private final String TAG = "PERSONNALLOG";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText txtName;
    private EditText txtPhone;
    View view;
    NiceSpinner spinnerGender;
    NiceSpinner spinnerLocation;
    List<String> listGender;
    List<String> listLocation;
    UserDTO userInfo;
    Button btnSave;
    Button btnChangeDob;
    public FragmentEditProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        userInfo = new UserDTO();
        return view;
    }

    public void getCurrentUserInformation() {
        userInfo = RestfulAPIManager.getInstancẹ̣̣̣().getUserInformation(getContext(), "");
    }

    @Override
    public void onStart() {
        getCurrentUserInformation();
        setChangeDateListener();

        txtName = getActivity().findViewById(R.id.txtNameEdit);
        txtPhone = getActivity().findViewById(R.id.txtPhoneEdit);

        spinnerGender = getActivity().findViewById(R.id.spinnerGenderEdit);
        listGender = new ArrayList<>();
        listGender.add("Male");
        listGender.add("Female");
        listGender.add("Other");
        spinnerGender.attachDataSource(listGender);


        spinnerLocation= getActivity().findViewById(R.id.spinnerLocationEdit);
        listLocation = new ArrayList<>();
        listLocation.add("Vietnam");
        listLocation.add("Uganda");
        listLocation.add("Wakanda");
        listLocation.add("United Kingdom");
        listLocation.add("United State");
        listLocation.add("Uruguay");
        listLocation.add("Zimbabwe");
        listLocation.add("Russia");
        listLocation.add("Portugal");
        listLocation.add("Paraguay");
        listLocation.add("Panama");
        listLocation.add("Pakistan");
        listLocation.add("Mexico");
        listLocation.add("Monaco");
        listLocation.add("Singapore");
        listLocation.add("Laos");
        listLocation.add("India");
        listLocation.add("China");
        listLocation.add("Korea");
        listLocation.add("Japan");
        listLocation.add("Egypt");
        listLocation.add("Cuba");
        listLocation.add("Costa Rica");
        listLocation.add("Colombia");
        listLocation.add("Canada");
        listLocation.add("Cambodia");
        listLocation.add("Bahamas");
        listLocation.add("Australia");
        listLocation.add("Argentina");
        listLocation.add("Afghanistan");
        Collections.sort(listLocation);
        spinnerLocation.attachDataSource(listLocation);
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userInfo.setLocation(String.valueOf(parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userInfo.setGender(String.valueOf(parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i < listLocation.size(); i++) {
            if (listLocation.get(i).equals(userInfo.getLocation())) {
                spinnerLocation.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < listGender.size(); i++) {
            if (listGender.get(i).equals(userInfo.getGender())) {
                spinnerGender.setSelectedIndex(i);
                break;
            }
        }

        String phone = userInfo.getPhone().toString();
        txtPhone.setText("+84 " + phone);
        txtName.setText(userInfo.getFirstName());

        btnSave = getActivity().findViewById(R.id.btnSaveEdit);
        btnSave.setOnClickListener(this);

        btnChangeDob = getActivity().findViewById(R.id.txtDobEdit);
        btnChangeDob.setOnClickListener(this);

        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveEdit:
                saveEditProfile();
                break;
            case  R.id.txtDobEdit:
                changeDate();
                break;
        }
    }

    private void saveEditProfile() {
    }

    public void changeDate() {
        Log.d("PERSONALLOG", "changeDate: ");
        String date = userInfo.getDob();
        int currentYear = Integer.parseInt(date.split("-")[0]);
        int currentMonth = Integer.parseInt(date.split("-")[1]);
        int currentDay = Integer.parseInt(date.split("-")[2]);
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                mDateSetListener,
                currentYear, (currentMonth - 1), currentDay);
        dialog.show();
    }

    public void setChangeDateListener() {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                Log.d(TAG, "onDateSet: " + date);
                userInfo.setDob(date);
                btnChangeDob.setText(day + "/" + month + "/" + year);
            }
        };
    }
}
