package com.example.cloud_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;



public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//registration layout page
        setContentView(R.layout.registration);
        TextView login = (TextView) findViewById(R.id.lnkLogin);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
        registerUser();
    }


    private void registerUser() {

        final EditText Name = findViewById(R.id.regtxtName);
        final EditText email = findViewById(R.id.regtxtEmail);
        final EditText password1=findViewById(R.id.password1);
        final EditText password2 =findViewById(R.id.password2);

        final SignUpHandler signup = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                //just sign up but not confirmed yet
                //sign
                if (signUpConfirmationState) {
                    Log.i(TAG, "signup success.. confirmed" + cognitoUserCodeDeliveryDetails.getDestination());

                }else {
                    Log.i(TAG, "signup success..not confirmed...verification code sent to:" + cognitoUserCodeDeliveryDetails.getDestination());
                }
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i(TAG, "signup failure" + exception.getLocalizedMessage());
            }

        };

        Button register= findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (password1.getText().toString().equals(password2.getText().toString())) {
                    final CognitoUserAttributes userAttributes = new CognitoUserAttributes();

                    String getemail = email.getText().toString();
                    String getpassword = password1.getText().toString();

                    userAttributes.addAttribute("name", Name.getText().toString());
                    userAttributes.addAttribute("email", email.getText().toString());
                    cognito_settings cognitoSettings = new cognito_settings(RegistrationActivity.this);
                    cognitoSettings.getUserpool().signUpInBackground(getemail, getpassword, userAttributes, null, signup);
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegistrationActivity.this);

                    alert.setMessage("wrong password");
                    alert.setPositiveButton("OK", null);
                    alert.setCancelable(true);
                    alert.create().show();
                }
            }


        });

    }
}