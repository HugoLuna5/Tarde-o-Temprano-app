package es.esy.lunainc.www.cbtis71;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;


    private Intent intent;
    ViewPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    public String[] mCircuito;





    protected static Integer[] mImageIds = {
            R.drawable.circuitochico,
            R.drawable.circuitolunar,
            R.drawable.ruta_delvino,
            R.drawable.circuito_delsol,
            R.drawable.circuito_verde,
            R.drawable.circuito_delrio
    };



    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        Fragmentos fragment2 = new Fragmentos();
        android.support.v4.app.FragmentTransaction fragmentTransaction2=
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.fragment_container_slider,fragment2);
        fragmentTransaction2.commit();
        //VIEWPAGER
        mCircuito = getResources().getStringArray(R.array.circuito);

        mSectionsPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);

        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(0,mCircuito[0], mImageIds[0]));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(1,mCircuito[1], mImageIds[1]));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(2,mCircuito[2], mImageIds[2]));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(3,mCircuito[3], mImageIds[3]));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(4,mCircuito[4], mImageIds[4]));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(5,mCircuito[5], mImageIds[5]));

        mViewPager.setAdapter(mSectionsPagerAdapter);
        //FIN VIEWPAGER



        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.three_buttons_activity);

        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);

        bottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.recent_item:


                        startActivity(intent = new Intent(MainActivity.this,MainActivity.class));
                        finish();


                        break;
                    case R.id.favorite_item:
                        startActivity(intent = new Intent(MainActivity.this,Navigation.class));
finish();
                        break;
                    case R.id.location_item:

                        startActivity(intent = new Intent(MainActivity.this,Ubicacion.class));
                        finish();

                        break;

                    case R.id.perfil_item:
                        if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null) {
                            startActivity(intent = new Intent(MainActivity.this, Perfil.class));
                            finish();
                        }else{
//Cuadro de dialogo
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Iniciar Sesión")
                                    .setMessage("Debes iniciar sesión para entrar a la sección Perfil")
                                    .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){


                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            startActivity(intent = new Intent(MainActivity.this,LogIn.class));
                                            finish();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, null).create().show();

                        }
                        break;
                }
            }
        });


        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        bottomBar.setActiveTabColor("#C2185B");



        BlankFragment fragment = new BlankFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


        // Use the dark theme. Ignored on mobile when there are more than three tabs.
        //bottomBar.useDarkTheme(true);

        // Use custom text appearance in tab titles.
        //bottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
        //bottomBar.setTypeFace("MyFont.ttf");

        if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null) {
            Profile perfil = com.facebook.Profile.getCurrentProfile();
            String nombreBien = perfil.getName();

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("¡Hola " + nombreBien + "!")
                    .setMessage("Bienvenido a la aplicación del C.B.T.i.s No. 71")
                    .setPositiveButton(android.R.string.yes, null)
                    .create().show();
        }else{

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("¡Hola!")
                    .setMessage("Bienvenido a la aplicación del C.B.T.i.s No. 71")
                    .setPositiveButton(android.R.string.yes, null)
                    .create().show();

        }




    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */






    /****************** VIEWPAGER *********************/
    public class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments; //acá voy a guardar los fragments

        //constructor
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<Fragment>();
        }

        @Override
        public Fragment getItem(int position) {
            //return PlaceholderFragment.newInstance(position + 1);
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            //return 3;
            return this.fragments.size();
        }

        public void addFragment(Fragment xfragment){
            this.fragments.add(xfragment);
        }
    }
    /****************** FIN VIEWPAGER *********************/

    /****************** FRAGMENTOS *********************/
    public static class Fragmentos extends Fragment {
        /**
         Agregue "color"
         */
        private static final String CURRENT_VIEWVAPER ="currentviewpager";
        private static final String NOMBRE_CIRCUITO = "circuito";
        private static final String IMAGEVIEW = "image";

        private int currentViewPager;
        private String nombre_circuito;
        private int image;

        public static Fragmentos newInstance(int currentViewPager, String circuitoNombre, int image) {

            Fragmentos fragment = new Fragmentos();   //instanciamos un nuevo fragment

            Bundle args = new Bundle();                                 //guardamos los parametros
            args.putInt(CURRENT_VIEWVAPER, currentViewPager);
            args.putString(NOMBRE_CIRCUITO, circuitoNombre);
            args.putInt(IMAGEVIEW, image);
            fragment.setArguments(args);
            fragment.setRetainInstance(true);     //agrego para que no se pierda los valores de la instancia
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //cuando crea una instancia de tipo PlaceholderFragment
            //si lo enviamos parametros, guarda esos
            //si no le envio nada, toma el color gris y un número aleatroio
            if(getArguments() != null){
                this.currentViewPager = getArguments().getInt(CURRENT_VIEWVAPER);
                this.nombre_circuito = getArguments().getString(NOMBRE_CIRCUITO);
                this.image = getArguments().getInt(IMAGEVIEW);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_circuito, container, false);

            TextView tv_circuito = (TextView) rootView.findViewById(R.id.tv_circuito);
            tv_circuito.setText(nombre_circuito);

            ImageView frg_image = (ImageView) rootView.findViewById(R.id.frg_imageView);
            frg_image.setImageResource(image);
            frg_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
                    //overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }
            });
            return rootView;
        }
    }
    /****************** FIN FRAGMENTOS *********************/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }







}



