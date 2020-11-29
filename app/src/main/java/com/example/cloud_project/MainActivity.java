package com.example.cloud_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView register = (TextView)findViewById(R.id.lnkRegister);
        TextView Login = (TextView)findViewById(R.id.btnLogin);

        register.setMovementMethod(LinkMovementMethod.getInstance());
        //on click of register adi redirecting to registration activty java class akkadnundi it gets redirected to xml.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        Login.setMovementMethod(LinkMovementMethod.getInstance());
        //on click of register adi redirecting to registration activty java class akkadnundi it gets redirected to xml.
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, upload.class);
                startActivity(intent1);
            }
        });



    }
}