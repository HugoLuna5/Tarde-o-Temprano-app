package es.esy.lunainc.www.cbtis71;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

public class Perfil extends AppCompatActivity {

    CallbackManager callbackManager;

    ProfilePictureView profilePictureView;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_perfil);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);




        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                Toast.makeText(Perfil.this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(Perfil.this, "Se ha cancelado el Inicio de Sesión", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Perfil.this, "Opss Ocurrio un Error...", Toast.LENGTH_SHORT).show();
            }
        });

        TextView user_name=(TextView)findViewById(R.id.perfil_bien);

        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        Profile perfil = com.facebook.Profile.getCurrentProfile();

        String nombre=perfil.getName();
        String juju=perfil.getId();


        user_name.setText(nombre);
        profilePictureView.setProfileId(juju);



    }
    //Acción de la flecha de retorno
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);

        finish();
        return true;

    }
    @Override
    public void onBackPressed() {

        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);

        finish();

    }

public void cerrarsesion(View view ){
    new AlertDialog.Builder(Perfil.this)
            .setTitle("Cerrar Sesión")
            .setMessage("Estás apunto de cerrar sesión")
            .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){


                @Override
                public void onClick(DialogInterface dialog, int which) {


                    startActivity(intent = new Intent(Perfil.this,LogIn.class));
                    finish();
                }
            })
            .setNegativeButton(android.R.string.no, null).create().show();
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
