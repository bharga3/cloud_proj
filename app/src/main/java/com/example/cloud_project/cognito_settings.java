package com.example.cloud_project;
import android.content.Context;


import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class cognito_settings {

    private String userPoolid = "";
    private String clientId = "";
    private String clientSecret ="";
    private Regions cognitoRegion = Regions.US_EAST_1;

    private Context context;



    public cognito_settings(Context context){
        this.context = context;
    }

    public String getUserPoolid(){
        return  userPoolid;
    }

    public String getClientId(){
        return clientId;

    }

    public String getClientSecret(){
        return clientSecret;
    }

    public Regions getCognitoRegion() {
        return cognitoRegion;
    }

    public CognitoUserPool getUserpool(){
        return  new CognitoUserPool(context,userPoolid,clientId,clientSecret,cognitoRegion);
    }



}



