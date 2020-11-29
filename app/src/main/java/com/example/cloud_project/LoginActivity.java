package com.example.cloud_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView login = (TextView) findViewById(R.id.lnkRegister);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Login();
    }
        private void Login(){
            Log.i(TAG,"login function");

            final EditText emails = findViewById(R.id.txtEmail);
            final EditText Passwords = findViewById(R.id.txtPwd);

            final AuthenticationHandler handler = new AuthenticationHandler() {

                @Override
                public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                    Log.i(TAG,"++++++++++++++++++++++Successfully log in ");

                }

                //for details we put
                @Override
                public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                    Log.i(TAG,"in getAuthenticationDetail()");

                    //username and password.
                    AuthenticationDetails Details = new AuthenticationDetails(userId,String.valueOf(Passwords.getText().toString()),null);

                    //sign in part
                    authenticationContinuation.setAuthenticationDetails(Details);

                    //it allows sign in
                    authenticationContinuation.continueTask();
                }

                @Override
                public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

                }

                @Override
                public void authenticationChallenge(ChallengeContinuation continuation) {

                }

                @Override
                public void onFailure(Exception exception) {
                    Log.i(TAG,"Login failed"+ exception.getLocalizedMessage());
                }
            };
            Button button = findViewById(R.id.btnLogin);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cognito_settings cognitoSettings = new cognito_settings(LoginActivity.this);
                    CognitoUser user = cognitoSettings.getUserpool().getUser(String.valueOf(emails));

                    Log.i(TAG, "button clicked");
                    setContentView(R.layout.activity_upload);
                    user.getSessionInBackground(handler);
                }
            });
        }
    }