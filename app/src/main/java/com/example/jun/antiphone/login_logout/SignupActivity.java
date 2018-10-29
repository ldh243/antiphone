package com.example.jun.antiphone.login_logout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.jun.antiphone.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        btnLogin = findViewById(R.id.btnLoginSignup);
        btnSignup = findViewById(R.id.btnSignupSignup);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        if (v == btnSignup) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}
