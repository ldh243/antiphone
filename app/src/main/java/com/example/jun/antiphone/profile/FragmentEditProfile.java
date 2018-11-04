package com.example.jun.antiphone.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.holding.HoldingPointDialogFragment;
import com.example.jun.antiphone.login_logout.LoginActivity;
import com.example.jun.antiphone.singleton.RestfulAPIManager;
import com.example.jun.antiphone.singleton.VolleyCallback;
import com.google.firebase.auth.FirebaseAuth;

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
    private View view;
    private Spinner spinnerGender;
    private Spinner spinnerLocation;
    private List<String> listGender;
    private List<String> listLocation;
    private UserDTO userInfo;
    private Button btnSave;
    private ProgressDialog myProgress;
    private TextView tvNameShowProfile;
    private Button btnLogout;

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
        myProgress = new ProgressDialog(getActivity());
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);
        myProgress.show();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        RestfulAPIManager.getInstancẹ̣̣̣().getUserInformation(getContext(), uid, new VolleyCallback() {
            @Override
            public void onSuccess(Object response) {
                userInfo = (UserDTO) response;
                initSpinner();
                myProgress.dismiss();
            }

            @Override
            public void onError(Object ex) {
                myProgress.dismiss();
            }
        });
    }

    private void initSpinner() {
        listGender = new ArrayList<>();
        listGender.add("Male");
        listGender.add("Female");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listGender);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(dataAdapter);

        listLocation = new ArrayList<>();
        listLocation.add("Vietnam"); listLocation.add("Uganda"); listLocation.add("Wakanda"); listLocation.add("United Kingdom");
        listLocation.add("United State"); listLocation.add("Uruguay"); listLocation.add("Zimbabwe"); listLocation.add("Russia");
        listLocation.add("Portugal"); listLocation.add("Paraguay"); listLocation.add("Panama"); listLocation.add("Pakistan");
        listLocation.add("Mexico"); listLocation.add("Monaco"); listLocation.add("Singapore"); listLocation.add("Laos");
        listLocation.add("India"); listLocation.add("China"); listLocation.add("Korea"); listLocation.add("Japan");
        listLocation.add("Egypt"); listLocation.add("Cuba"); listLocation.add("Costa Rica"); listLocation.add("Colombia");
        listLocation.add("Canada"); listLocation.add("Cambodia"); listLocation.add("Bahamas"); listLocation.add("Australia");
        listLocation.add("Argentina"); listLocation.add("Afghanistan");
        Collections.sort(listLocation);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listLocation);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(dataAdapter2);


        for (int i = 0; i < listLocation.size(); i++) {
            if (listLocation.get(i).equals(userInfo.getLocation())) {
                spinnerLocation.setSelection(i);
                break;
            }
        }

        String gender = userInfo.isGender() ? "Female" : "Male";

        for (int i = 0; i < listGender.size(); i++) {
            if (listGender.get(i).equals(gender)) {
                spinnerGender.setSelection(i);
                break;
            }
        }
        String phone = userInfo.getPhone().toString();
        txtPhone.setText("0" + phone);
        txtName.setText(userInfo.getFirstName());
        tvNameShowProfile.setText(userInfo.getFirstName());

        btnSave = getActivity().findViewById(R.id.btnSaveEdit);
        btnSave.setOnClickListener(this);

        String [] arr = userInfo.getDob().split("-");
        String dob = arr[2] + "/" + arr[1] + "/" + arr[0];
        btnChangeDob = getActivity().findViewById(R.id.txtDobEdit);
        btnChangeDob.setText(dob);
        btnChangeDob.setOnClickListener(this);

        btnLogout = getActivity().findViewById(R.id.btnLogoutEdit);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        setChangeDateListener();
        spinnerGender = getActivity().findViewById(R.id.spinnerGenderEdit);
        spinnerLocation= getActivity().findViewById(R.id.spinnerLocationEdit);
        getCurrentUserInformation();
        txtName = getActivity().findViewById(R.id.txtNameEdit);
        txtPhone = getActivity().findViewById(R.id.txtPhoneEdit);
        tvNameShowProfile = getActivity().findViewById(R.id.tvNameShowProfile);
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
            case R.id.btnLogoutEdit:
                confirmLogout();
                break;
        }
    }

    private void confirmLogout() {
        DialogFragment dialogFragment = ConfirmLogoutDialogFragment.create("Log out of this account?");
        dialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
    }

    private void saveEditProfile() {
        String name = txtName.getText().toString();
        String dob = btnChangeDob.getText().toString();
        String [] arr = dob.split("/");
        if (arr[0].length() == 1) {
            arr[0] = "0" + arr[0];
        }
        if (arr[1].length() == 1) {
            arr[1] = "0" + arr[1];
        }

        dob = arr[2] + "-" + arr[1] + "-" + arr[0];
        String phone = txtPhone.getText().toString();
        Long phoneLong = Long.parseLong(phone);
        String location = spinnerLocation.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserDTO dto = new UserDTO();
        dto.setUsername(uid);
        dto.setFirstName(name);
        dto.setDob(dob);
        dto.setPhone(phoneLong);
        dto.setLocation(location);
        boolean isGender = gender.equals("Female") ? true : false;
        dto.setGender(isGender);
        RestfulAPIManager.getInstancẹ̣̣̣().saveUserInformation(getActivity(), dto);
        DialogFragment dialogFragment = SaveSuccessDialogFragment.create("Your information has been updated");
        dialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
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
