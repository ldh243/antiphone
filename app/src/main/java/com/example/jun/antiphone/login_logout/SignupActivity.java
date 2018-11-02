package com.example.jun.antiphone.login_logout;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jun.antiphone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    Button btnSignup;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String TAG = "PERSONALLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnLoginSignup);
        btnSignup = findViewById(R.id.btnSignupSignup);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        edtEmail = findViewById(R.id.edtEmailSignup);
        edtPassword = findViewById(R.id.edtPasswordSignup);
        edtConfirmPassword = findViewById(R.id.edtPasswordConfirmSignup);
    }


    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        if (v == btnSignup) {
            registerUser();
        }
    }

    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();
        String passwordConfirm = edtConfirmPassword.getText().toString();
        Log.d(TAG, "registerUser: " + email);
        Log.d(TAG, "registerUser: " + password);
        Log.d(TAG, "registerUser: " + passwordConfirm);
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordConfirm)) {
            Toast.makeText(this, "Your confirm password must be the same", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "registerUser: " + firebaseAuth.getInstance());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registing user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        } else {
                            Log.d(TAG, "onComplete: " + task.getException());
                            Toast.makeText(SignupActivity.this, task.getException().toString().split(":")[1].trim(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}
