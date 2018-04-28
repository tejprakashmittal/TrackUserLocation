package com.example.tez.trackuserlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tez.trackuserlocation.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    private TextView name,username;
    private Button logout,btncontinue;
    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE=9001;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        name=findViewById(R.id.txtName);
        username=findViewById(R.id.txtUsername);
        logout=findViewById(R.id.btnlogout);
        btncontinue=findViewById(R.id.btnlocation);
        signInButton=findViewById(R.id.btnSignIn);

        signInButton.setOnClickListener(this);
        logout.setOnClickListener(this);

        btncontinue.setVisibility(View.INVISIBLE);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),MapsActivity.class));
            }
        });

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();

    }

    @Override
    public void onClick(View v) {
       switch (v.getId())
       {
           case R.id.btnSignIn:
               signin();
               break;
           case R.id.btnlogout:
               signout();
               break;
       }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void signin()
    {
        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    public void signout()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                btncontinue.setVisibility(View.INVISIBLE);
                logout.setVisibility(View.INVISIBLE);
                name.setVisibility(View.INVISIBLE);
                username.setVisibility(View.INVISIBLE);
                signInButton.setVisibility(View.VISIBLE);
            }
        });
    }

    public void handleResult(GoogleSignInResult googleSignInResult)
    {
        if(googleSignInResult.isSuccess()){
            GoogleSignInAccount acount=googleSignInResult.getSignInAccount();
            String strname=acount.getDisplayName();
            String strusername=acount.getEmail();
            name.setText(strname);
            username.setText(strusername);
            btncontinue.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
