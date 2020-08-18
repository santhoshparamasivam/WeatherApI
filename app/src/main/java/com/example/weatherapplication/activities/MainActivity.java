package com.example.weatherapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weatherapplication.R;
import com.example.weatherapplication.sessionmanager.SessionKeys;
import com.example.weatherapplication.sessionmanager.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener {
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager=new SessionManager(this);

        String FirstName=sessionManager.getStringKey(SessionKeys.FIRST_NAME);
        String Email=sessionManager.getStringKey(SessionKeys.EMAIL);
        if (Email!=null&& !(Email.isEmpty())){
            gotoNextScreen();
        }
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
//        googleApiClient=new GoogleApiClient.Builder(this)
//                .enableAutoManage(this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signInButton=(SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


}
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                if (completedTask.isSuccessful()){
                    String name=    account.getDisplayName();
                    String email=    account.getEmail();
                    Uri pic=    account.getPhotoUrl();

                    sessionManager.setSessionValue(SessionKeys.EMAIL,email);
                    sessionManager.setSessionValue(SessionKeys.FIRST_NAME,name);
                    if (pic != null) {
                        sessionManager.setSessionValue(SessionKeys.PHOTO,pic.toString());
                    }
                    gotoNextScreen();
                } else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }



//    private void handleSignInResult(GoogleSignInResult result){
//        if(result.isSuccess()){
//        String name=    result.getSignInAccount().getDisplayName();
//        String email=    result.getSignInAccount().getEmail();
//        Uri pic=    result.getSignInAccount().getPhotoUrl();
//            Log.e("name",name+"  ");
//            Log.e("email",email+"  ");
//            Log.e("pic",pic+"  ");
//            sessionManager.setSessionValue(SessionKeys.EMAIL,email);
//            sessionManager.setSessionValue(SessionKeys.FIRST_NAME,name);
//            if (pic != null) {
//                sessionManager.setSessionValue(SessionKeys.PHOTO,pic.toString());
//            }
//            gotoNextScreen();
//        }else{
//            Log.e("result",result.getStatus().getStatusCode()+"  ");
//            Log.e("result",result.getStatus().getStatusMessage()+"  ");
//            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
//        }
//    }
    private void gotoNextScreen(){
        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}