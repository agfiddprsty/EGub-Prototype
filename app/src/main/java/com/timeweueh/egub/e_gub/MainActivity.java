package com.timeweueh.egub.e_gub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);  WebView webView=(WebView)findViewById(R.id.webview);
        if(checkInternet()) {

            webView.loadUrl("http://fauchet-ganargatul.hol.es/artikel/artikel.php");
            webView.setWebViewClient(new MainActivity.load());
            webView.setWebViewClient(new MyWebViewClient());

        }else{
            //WebSettings webSettings = webView.getSettings();
            //webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
          //  webView.loadUrl("http://fauchet-ganargatul.hol.es/artikel/artikel.php");
            webView.destroy();
        }
    }
    public class MyWebViewClient extends WebViewClient {

        boolean timeout = true;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Runnable run = new Runnable() {
                public void run() {
                    if(timeout) {
                        WebView webView= (WebView)findViewById(R.id.webview);

                        webView.destroy();
                        // do what you want
                     //   showAlert("Connection Timed out", "Whoops! Something went wrong. Please try again later.");
                    }
                }
            };
            Handler myHandler = new Handler(Looper.myLooper());
            myHandler.postDelayed(run, 5000);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            timeout = false;
        }
    }
    private  class load extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return  super.shouldOverrideUrlLoading(view,url);
        }
    }
    //    private class loadurlnya extends WebChromeClient{
    //    @Override
    //    public void  onProgressChanged(WebView web,int newProggress){
    //      Main2Activity.this.ser
    //  super.onProgressChanged(web,newProggress);
    ///}
    ///  }
    public boolean checkInternet(){
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            connectStatus = true;
        }
        else {
            connectStatus = false;
        }
        return connectStatus;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
           // new MainActivity().clearcookie(getApplicationContext());
        }
        else {
            finish();
        }
    }

    private void clearcookie(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
            CookieSyncManager cookieSyncMngr= CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager= CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home =new Intent(MainActivity.this, MainActivity.class);
            startActivity(home);
            // Handle the camera action
        } else if (id == R.id.nav_login) {
Intent login = new Intent(MainActivity.this, Login.class);
startActivity(login);
        } else if (id == R.id.nav_game) {
Intent game = new Intent(MainActivity.this, Main2Activity.class);
startActivity(game);
        } else if (id == R.id.masalah) {
            Intent masalah = new Intent(MainActivity.this, Lapor.class);
            startActivity(masalah);
        } else if (id == R.id.nav_about) {
            Intent about = new Intent(MainActivity.this, About.class);
            startActivity(about);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
