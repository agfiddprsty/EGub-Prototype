package com.timeweueh.egub.e_gub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Polling extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);
    //    WebView myWebView = (WebView) findViewById(R.id.webview);
     //   myWebView.loadUrl("http://ggshort.esy.es/sistem-poll-php-sql/hasil.php?IDpoll=1");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);WebView web = (WebView) findViewById(R.id.webview);
        //web.loadUrl("http://ggshort.esy.es/sistem-poll-php-sql/hasil.php?IDpoll=1");
        if(checkInternet()){
            //  WebSettings webSettings =web.getSettings();
            //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            web.loadUrl("http://ggshort.esy.es/sistem-poll-php-sql/hasil.php?IDpoll=1");
            web.setWebViewClient(new load());
            web.setWebViewClient(new Polling.MyWebViewClient());

        }else {
            //WebSettings webSettings = web.getSettings();
            //webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
            //web.loadUrl("http://ggshort.esy.es/sistem-poll-php-sql/hasil.php?IDpoll=1");
             web.destroy();
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
        }
        else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.polling, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home =new Intent(Polling.this, Umah.class);
            startActivity(home);
            // Handle the camera action
        } else if (id == R.id.nav_polling){
            Intent vote = new Intent(Polling.this,Main3Activity.class);
            startActivity(vote);
        }else if (id == R.id.nav_Logout) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234");

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //  Log.d(Tag,"User Account Deleted")
                                //Log.d("User account deleted.");
                            }
                        }
                    });

                }
            });
            FirebaseAuth.getInstance().signOut();
            Intent login = new Intent(Polling.this, MainActivity.class);
            startActivity(login);
        } else if (id == R.id.nav_game) {
            Intent game = new Intent(Polling.this, Polling.class);
            startActivity(game);
        } else if (id == R.id.masalah) {
            Intent masalah = new Intent(Polling.this, Pengaduan.class);
            startActivity(masalah);
        } else if (id == R.id.nav_about) {
            Intent about = new Intent(Polling.this, Abou.class);
            startActivity(about);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
