package com.example.jun.antiphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "Taggg";
    private EditText edtUsername;
    private EditText edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        edtUsername = findViewById(R.id.txtUsername);
//        edtPassword = findViewById(R.id.txtPassword);
//        edtUsername.clearFocus();
//        edtUsername.setOnKeyListener(new View.OnKeyListener() {
//            @Override 
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.d(TAG, "onKey: " + keyCode);
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {
////                    edtPassword.requestFocus();
//                    Log.d(TAG, "onKey: press enter");;
//                    return true;
//                }
//                return false;
//            }
//        });
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void gotoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
