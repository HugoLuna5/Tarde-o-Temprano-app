package info.androidhive.listviewfeed;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

public class Intro extends AppCompatActivity {
    VideoView videoView;
    Intent intent,intent2;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Intro.this, Navigation.class));
            finish();
        }



        setContentView(R.layout.activity_intro);
        videoView= (VideoView) findViewById(R.id.videoView);


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoView.start(); //need to make transition seamless.
            }
        });


        String uriPath = "android.resource://" + getPackageName()
                + "/" + R.raw.intro2;
        Uri path = Uri.parse(uriPath);


        videoView.requestFocus();
        videoView.setVideoURI(path);
        videoView.start();
        videoView.setMediaController(null);





    }


    public void iniciar(View view){
        startActivity(intent = new Intent (Intro.this,LoginActivity.class));
        finish();
    }

    public void registrarse(View view ){
        startActivity(intent2 = new Intent (Intro.this,SignupActivity.class));
        finish();
    }
}
