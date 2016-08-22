package info.androidhive.listviewfeed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Albums extends AppCompatActivity {
    private ViewPager viewPager2;
    private MyViewPagerAdapter myViewPagerAdapter2;
    private LinearLayout dotsLayout2;
    private TextView[] dots2;
    private int[] layouts2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("https://open.spotify.com/artist/264nbMzGPSkDZqTY8nXwCG");
                intent.setData(data);
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager2 = (ViewPager) findViewById(R.id.view2);
        dotsLayout2 = (LinearLayout) findViewById(R.id.layoutDots2);

        layouts2 = new int[]{
                R.layout.album_slide1,
                R.layout.album_slide2,
                R.layout.album_slide3,
                R.layout.album_slide4};
        addBottomDots(0);
        myViewPagerAdapter2 = new MyViewPagerAdapter();
        viewPager2.setAdapter(myViewPagerAdapter2);
        viewPager2.addOnPageChangeListener(viewPagerPageChangeListener);




    }



    private void addBottomDots(int currentPage) {
        dots2 = new TextView[layouts2.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout2.removeAllViews();
        for (int i = 0; i < dots2.length; i++) {
            dots2[i] = new TextView(this);
            dots2[i].setText(Html.fromHtml("&#8226;"));
            dots2[i].setTextSize(35);
            dots2[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout2.addView(dots2[i]);
        }

        if (dots2.length > 0)
            dots2[currentPage].setTextColor(colorsActive[currentPage]);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts2.length - 1) {
                // last page. make button text to GOT IT
                ;
            } else {
                // still pages are left

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts2[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts2.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
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

    //Métodos onClick para albums

    public void Album1(View view){
        Toast.makeText(Albums.this, "No disponible en Spotify", Toast.LENGTH_SHORT).show();

    }

    public void Album2(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("https://open.spotify.com/album/3w3lml76b9PORrlDSZAHd7");
        intent.setData(data);
        startActivity(intent);

        Toast.makeText(Albums.this, "Estar de moda no está de moda", Toast.LENGTH_SHORT).show();

    }

    public void Album3(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("https://open.spotify.com/album/10puCHT7A7lCYydtRCnawp");
        intent.setData(data);
        startActivity(intent);

        Toast.makeText(Albums.this, "Tarde o Temprano", Toast.LENGTH_SHORT).show();

    }


    public void Album4(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("https://open.spotify.com/album/3EpLCnAGX2cSWEJ9WhL1G6");
        intent.setData(data);
        startActivity(intent);

        Toast.makeText(Albums.this, "12 Historias", Toast.LENGTH_SHORT).show();

    }

public void exito1(View view){
    Intent intent = new Intent(Intent.ACTION_VIEW);
    Uri data = Uri.parse("https://open.spotify.com/track/2JCXcyMXlVywgDJT86wWZW");
    intent.setData(data);
    startActivity(intent);
}
    public void exito2(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("https://open.spotify.com/track/5vF2AfqhFFVzxuVLy2VRis");
        intent.setData(data);
        startActivity(intent);
    }

        public void exito3(View view){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("https://open.spotify.com/track/3NkWUspEJzcMcUJKoX1KJa");
            intent.setData(data);
            startActivity(intent);
        }

           public void exito4(View view){
               Intent intent = new Intent(Intent.ACTION_VIEW);
               Uri data = Uri.parse("https://open.spotify.com/track/6mDQ2BuamnksLpl5JXl2FM");
               intent.setData(data);
               startActivity(intent);
           }

              public void exito5(View view){
                  Intent intent = new Intent(Intent.ACTION_VIEW);
                  Uri data = Uri.parse("https://open.spotify.com/track/4DiDfrTp1wpDfphcDaEfRO");
                  intent.setData(data);
                  startActivity(intent);
              }





}