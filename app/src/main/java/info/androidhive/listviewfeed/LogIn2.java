package info.androidhive.listviewfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class LogIn2 extends AppCompatActivity {
    private TwitterLoginButton loginButton;
    private static final String TWITTER_KEY = "3cqG0izj5oePdLn3WgWRoyfNk";
    private static final String TWITTER_SECRET = "4478MmsL1RtmTVek2N4khkduXtVLB6ZBOGpzV11kEGHIKlpc0I";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

if(Twitter.getInstance().core.getSessionManager().getActiveSession()!=null){

    startActivity(intent=new Intent(LogIn2.this,Navigation.class));
    finish();



}
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Twitter.getInstance().core.getSessionManager().getActiveSession();
                TwitterSession session = result.data;
                String msg = session.getUserName();

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                startActivity(intent=new Intent(LogIn2.this,Navigation.class));

                finish();



            }


            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(LogIn2.this, "Opps Ocurrio un error", Toast.LENGTH_SHORT).show();
                startActivity(intent=new Intent(LogIn2.this,Navigation.class));
                finish();
            }
        });

    }

    //Acción de la flecha de retorno
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Navigation.class);
        startActivityForResult(myIntent, 0);

        finish();
        return true;

    }

    @Override
    public void onBackPressed() {

        Intent myIntent = new Intent(getApplicationContext(), Navigation.class);
        startActivityForResult(myIntent, 0);

        finish();

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}

