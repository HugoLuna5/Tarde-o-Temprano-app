package info.androidhive.listviewfeed;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    Dialog customDialog = null;
    public static final String CUSTOM_TAB_PACKAGE_NAME="com.android.chrome";
    static final String URL= "http://www.tarde-o-temprano-mx.esy.es/";
    static final String URL2= "https://www.instagram.com/tommy_torres/";
    CustomTabsClient mClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsSeviceConnection;
    CustomTabsIntent customTabsIntent;
    private int OL;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Ajustes Chrome bar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("Noticias");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

      BlankFragment fragment = new BlankFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
//Verificar inicio de sesion
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Navigation.this, Intro.class));
                    finish();
                }
            }
        };

        mCustomTabsSeviceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {

                //Pre-warning
                mClient = customTabsClient;
                mClient.warmup(OL);

                mCustomTabsSession = mClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mClient = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(Navigation.this,CUSTOM_TAB_PACKAGE_NAME,mCustomTabsSeviceConnection);


        customTabsIntent=new CustomTabsIntent.Builder(mCustomTabsSession).setToolbarColor(ContextCompat.getColor(Navigation.this, R.color.colorPrimary)).setShowTitle(true).build();

        //Fin del ajuste del¿ tab chrome


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(intent = new Intent(this,Ajustes.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            BlankFragment fragment = new BlankFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Noticias");


        }else if(id==R.id.nav_twitter) {
            Inicio fragment = new Inicio();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Twitter");
        }else if(id==R.id.nav_albums){

startActivity(intent = new Intent(Navigation.this,Albums.class));
            finish();
        }else if(id==R.id.nav_donar){
            AlertDialog.Builder alertDialogDonate = new AlertDialog.Builder(this);
            // Setting Dialog Message
            alertDialogDonate.setTitle("Donar");
            alertDialogDonate.setMessage("Tu donación ayuda a que esta aplicación mejore. \nDesde ya muchas gracias!");
            alertDialogDonate.setCancelable(true);
            alertDialogDonate.setPositiveButton("DONAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Uri.Builder uriBuilder = new Uri.Builder();
                    uriBuilder.scheme("https").authority("www.paypal.com").path("cgi-bin/webscr");
                    uriBuilder.appendQueryParameter("cmd", "_donations");

                    uriBuilder.appendQueryParameter("business", "hugo_1199@hotmail.com");
                    uriBuilder.appendQueryParameter("lc", "MXN");
                    uriBuilder.appendQueryParameter("item_name", "Proyecto App");
                    uriBuilder.appendQueryParameter("no_note", "1");
                    uriBuilder.appendQueryParameter("no_shipping", "1");
                    uriBuilder.appendQueryParameter("currency_code", "MXN");
                    Uri payPalUri = uriBuilder.build();

                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, payPalUri);
                    startActivity(viewIntent);
                }
            });

            alertDialogDonate.show();
            return true;


        }else if(id==R.id.nav_compartir){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Descarga el App oficial del club Tarde o Temprano México http://www.tarde-o-temprano-mx.esy.es/app");
            startActivity(Intent.createChooser(intent, "Compartir en"));
            getSupportActionBar().setTitle("Tarde o Temprano App");



        }else if(id==R.id.nav_unete){
            checarSDK();
        }else if(id==R.id.nav_instagram){
            checarSDK2();
        }else if (id==R.id.nav_contacto){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:tardeotempranomx@gmail.com");
            intent.setData(data);
            startActivity(intent);

        }else if(id==R.id.nav_ajustes){
            startActivity(intent = new Intent(this,Ajustes.class));
            finish();
        }else if(id==R.id.nav_sobre){

                // con este tema personalizado evitamos los bordes por defecto
                customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
                //deshabilitamos el título por defecto
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //obligamos al usuario a pulsar los botones para cerrarlo
                customDialog.setCancelable(false);
                //establecemos el contenido de nuestro dialog
                customDialog.setContentView(R.layout.about);



                ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        customDialog.dismiss();


                    }
                });



                customDialog.show();

        }else if (id==R.id.nav_des){
            // con este tema personalizado evitamos los bordes por defecto
            customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
            //deshabilitamos el título por defecto
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //obligamos al usuario a pulsar los botones para cerrarlo
            customDialog.setCancelable(false);
            //establecemos el contenido de nuestro dialog
            customDialog.setContentView(R.layout.desarrollador);



            ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    customDialog.dismiss();


                }
            });



            customDialog.show();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void contactardes(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:info@lunainc.esy.es");
        intent.setData(data);
        startActivity(intent);

    }



public void masapli(View view){
    Intent intent = new Intent(Intent.ACTION_VIEW);
    Uri data = Uri.parse("https://play.google.com/store/apps/dev?id=7892672339262016820");
    intent.setData(data);
    startActivity(intent);

}



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {


            //Cuadro de dialogo para alertar que se abandonara el app
            new AlertDialog.Builder(this)
                    .setTitle("Estás a punto de salir")
                    .setMessage("¿Seguro que quieres salir?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Navigation.super.onBackPressed();
                        }
                    }).create().show();

        }
    }
    private void checarSDK() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            customTabsIntent.launchUrl(Navigation.this,Uri.parse(URL));
        }else {
startActivity(intent =new Intent(this,Unete.class));
            finish();
        }
    }

    private void checarSDK2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            customTabsIntent.launchUrl(Navigation.this,Uri.parse(URL2));
        }else {
            startActivity(intent =new Intent(this,Instagram.class));
            finish();
        }
    }




    public static boolean compruebaConexion(Context context) {

        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }

}
