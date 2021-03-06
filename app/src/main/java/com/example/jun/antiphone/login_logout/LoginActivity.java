package com.example.jun.antiphone.login_logout;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jun.antiphone.MainActivity;
import com.example.jun.antiphone.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.Arrays;

import entity.UserDTO;
import util.Constants;
import util.MapUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private int TEST_MODE = 0;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnLoginFacebook;
    private Button btnLoginGoogle;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private TextView createAccount;
    private String TAG = "PERSONALLOG";

    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;

//    public void callApi() {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        String URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=10.7994789,106.6114475" +
//                "&destinations=10.8538493,106.6261721&key=AIzaSyBsY-26loYcr2kpIARp5wTmbExsf-BWC7M";
//
//
//        JsonObjectRequest objectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                URL,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String json = response.toString();
//                            ObjectMapper om = new ObjectMapper();
//                            JsonNode jsonNode = om.readTree(json);
//                            Log.d(TAG, "onResponse: " + jsonNode.get("rows").get(0).get("elements").get(0).get("distance").get("text"));
////                            String distance = jsonNode.get("rows").get("elements").get("distance").get("text").asText();
////                            Log.d(TAG, "onResponseeeeeeeeeeeeeeeee: " + distance);
//
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                            Log.d(TAG, "onResponse: " + ex.getMessage());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "onErrorResponse: " + error.toString());
//                    }
//                }
//        );
//        requestQueue.add(objectRequest);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        txtUsername = findViewById(R.id.txtEmailSignin);
        txtPassword = findViewById(R.id.txtPasswordSignin);
        txtUsername.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    txtPassword.requestFocus();
                    return true;
                }
                return false;
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        firebaseAuth = FirebaseAuth.getInstance();
//        checkRemember();
        firebaseAuth.signOut();
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnLoginFacebook = findViewById(R.id.btnLoginFacebook);
        btnLoginFacebook.setOnClickListener(this);

        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
        btnLoginGoogle.setOnClickListener(this);

        createAccount = findViewById(R.id.tvDontHaveAccount);
        createAccount.setOnClickListener(this);

        configLoginGoogle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkRemember() {
        if (firebaseAuth.getCurrentUser() != null) {
            updateUI();
        }
    }

    public void gotoHome(View view) {
        updateUI();
    }

    public void checkLogin() {
        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            updateUI();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().toString().split(":")[1].trim(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void configLoginGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "You got an Error", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            checkLogin();
        }

        if (view == btnLoginFacebook) {
            if (TEST_MODE == 1) {
                updateUI();
            }
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    Log.d(TAG, "facebook:onSuccess:" + loginResult);
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    // App code
                    Log.d(TAG, "facebook:onCancel");

                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Log.d(TAG, "facebook:onError", exception);
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (view == createAccount) {
            startActivity(new Intent(this, SignupActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        if (view == btnLoginGoogle) {
            signIn();
        }

    }

    private void signIn() {
        Log.d(TAG, "signIn: Sign in with google");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            apiRegisterWithoutPassword(firebaseAuth.getUid()
                                    , acct.getDisplayName(), acct.getEmail());
                            updateUI();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, task.getException().toString().split(": ")[1],
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            apiRegisterWithoutPassword(user.getUid(), user.getDisplayName(), user.getEmail());
                            Log.d(TAG, "onComplete: " + user.getEmail());
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "-----" + task.getException().toString().split(": ")[1],
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: FAIL O DAY NE");
            }
        });
    }

    private void updateUI() {
        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void apiRegisterWithoutPassword(String uid, String firstName, String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = Constants.API_PATH + "/api/users/register-without-password";

        Log.d(TAG, "apiRegisterWithoutPassword: " + uid);
        Log.d(TAG, "apiRegisterWithoutPassword: " + email);
        Log.d(TAG, "apiRegisterWithoutPassword: " + firstName);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", uid);
            jsonObject.put("firstName", firstName);
            jsonObject.put("email", email);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String json = response.toString();
                            Log.d(TAG, "onResponse: " + json);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.d(TAG, "onResponse: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
    }
}
