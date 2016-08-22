package info.androidhive.listviewfeed;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Unete extends AppCompatActivity {
    private WebView WebView;

    final String url = "http://www.lunainc.esy.es/tarde-o-temprano-mx/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unete);
        aviso2();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        WebView = (android.webkit.WebView) findViewById(R.id.web_unete);
        WebSettings webSettings = WebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebView.setWebViewClient(new WebViewClient());
        WebView.loadUrl(url);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public class forzar extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

    //Acción de la flecha de retorno
    public boolean onOptionsItemSelected(MenuItem item) {


        Intent myIntent = new Intent(getApplicationContext(), Navigation.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;

    }

    //Boton onBack
    @Override
    public void onBackPressed() {
        if (WebView.canGoBack()) {
            WebView.goBack();
        } else {

            Intent myIntent = new Intent(getApplicationContext(), Navigation.class);
            startActivityForResult(myIntent, 0);
            finish();
        }
    }

    public void aviso2() {
        Toast.makeText(Unete.this, "Espere un momento por favor...", Toast.LENGTH_LONG).show();

    }
}