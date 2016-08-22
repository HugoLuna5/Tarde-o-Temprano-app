package es.esy.lunainc.www.cbtis71;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LogIn extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_log_in);




        if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null){



            // App code
            Intent intent = new Intent(LogIn.this,MainActivity.class);

            startActivity(intent);
            finish();
        }




        callbackManager = CallbackManager.Factory.create();
            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {



                    Intent intent = new Intent(LogIn.this, MainActivity.class);


                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(LogIn.this, "Se ha cancelado el Log In", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(LogIn.this, "Opss Ocurrio un Error...", Toast.LENGTH_SHORT).show();
                }
            });




    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


public void no_sesion (View view){
    // App code
    Intent intent = new Intent(LogIn.this,MainActivity.class);

    startActivity(intent);
    finish();
}

}

