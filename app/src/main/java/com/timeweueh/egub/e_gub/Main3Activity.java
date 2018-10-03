package com.timeweueh.egub.e_gub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;

import android.view.View;

import android.view.WindowManager;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import static android.accounts.AccountManager.KEY_PASSWORD;

public class Main3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //WebView myWebView = (WebView) findViewById(R.id.webviewmain3);
        //myWebView.loadUrl("http://ggshort.esy.es/sistem-poll-php-sql/index.php");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        WebView web = (WebView) findViewById(R.id.webview);
        //web.loadUrl("http://ggshort.esy.es/sistem-poll-php-sql/hasil.php?IDpoll=1");
        if (checkInternet()) {
            //  WebSettings webSettings =web.getSettings();
            //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            web.loadUrl("http://ggshort.esy.es/sistem-poll-php-sql/index.php");
            web.setWebViewClient(new Main3Activity.load());

        } else {
            //WebSettings webSettings = web.getSettings();
            //webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
            // web.loadUrl("https://goo.gl/TW54iM");
            web.destroy();
        }

    }

    private class load extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    //    private class loadurlnya extends WebChromeClient{
    //    @Override
    //    public void  onProgressChanged(WebView web,int newProggress){
    //      Main2Activity.this.ser
    //  super.onProgressChanged(web,newProggress);
    ///}
    ///  }
    public boolean checkInternet() {
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            connectStatus = true;
        } else {
            connectStatus = false;
        }
        return connectStatus;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            finish();
        }

    }

    private void clearcookie(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }
    private void userLogin() {
        User userlogin = SharedPrefManager.getInstance(this).getUser();
        //first getting the values
        final String username = userlogin.getEmail();
        final String password = userlogin.getPassword();


        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

     }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                params.put("status", "off");
                params.put("level", "umum");

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();}


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home =new Intent(Main3Activity.this, Umah.class);
            startActivity(home);
            // Handle the camera action
        }else if (id == R.id.nav_polling){
          Intent vote = new Intent(Main3Activity.this,Main3Activity.class);
          startActivity(vote);
        } else if (id == R.id.nav_Logout) {
userLogin();
           // SharedPrefManager.getInstance(this).logout();
            new Main3Activity().clearcookie(getApplicationContext());
            Intent login = new Intent(Main3Activity.this, MainActivity.class);
            startActivity(login);
        } else if (id == R.id.nav_game) {
            Intent game = new Intent(Main3Activity.this, Polling.class);
            startActivity(game);
        } else if (id == R.id.masalah) {
            Intent masalah = new Intent(Main3Activity.this, Pengaduan.class);
            startActivity(masalah);
        } else if (id == R.id.nav_about) {
            Intent about = new Intent(Main3Activity.this, Abou.class);
            startActivity(about);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
