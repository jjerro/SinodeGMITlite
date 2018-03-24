package com.dalehi.sinodegmit_lite;

import android.annotation.SuppressLint;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressBar progressBar;
    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );


        DrawerLayout drawer = findViewById ( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener ( toggle );
        toggle.syncState ();

        NavigationView navigationView = findViewById ( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener ( this );

        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings= mWebView.getSettings();


        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setEnableSmoothTransition(true);

        mWebView.loadUrl("http://sinodegmit.or.id");


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(1);

        mWebView.setWebChromeClient(new WebChromeClient () {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().equals("sinodegmit.or.id")) {
                    return false;
                } else {
                    try{
                        Intent Intent = new Intent(getApplicationContext(), ExternalFragment.class);
                        String theUrl = String.valueOf ( Uri.parse(url) );

                        Intent.putExtra(ExternalFragment.EXTRA_URL, String.valueOf (theUrl));
                        startActivity(Intent);
                    } catch (Exception e){
                        e.printStackTrace ();
                    }

                    return true;
                }

            }
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById ( R.id.drawer_layout );
        if (drawer.isDrawerOpen ( GravityCompat.START )) {
            drawer.closeDrawer ( GravityCompat.START );
        } else {
            super.onBackPressed ();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();

        return id == R.id.action_settings || super.onOptionsItemSelected ( item );

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId ();

        if (id == R.id.nav_home) {
            mWebView.loadUrl("http://sinodegmit.or.id/");
        } else if (id == R.id.nav_news) {
            mWebView.loadUrl("http://sinodegmit.or.id/category/berita-hari-ini/");
        } else if (id == R.id.nav_article) {
            mWebView.loadUrl("http://sinodegmit.or.id/category/artikel/");
        } else if (id == R.id.nav_liturgy) {
            mWebView.loadUrl("http://sinodegmit.or.id/category/liturgi/");
        } else if (id == R.id.nav_shepherd) {
            mWebView.loadUrl("http://sinodegmit.or.id/category/suara-gembala/");
        } else if (id == R.id.nav_PA) {
            mWebView.loadUrl("http://sinodegmit.or.id/category/bahan-pa/");
        }else if (id == R.id.nav_download) {
            mWebView.loadUrl("http://sinodegmit.or.id/category/download/");
        }else if (id == R.id.nav_videos) {
            mWebView.loadUrl("http://sinodegmit.or.id/category/video/");
        }else if (id == R.id.nav_privacyURL) {
            mWebView.loadUrl("http://sinodegmit.or.id/kebijakan-privasi/");
        }else if (id == R.id.nav_close) {
            finish();
        } else if (id == R.id.action_settings){
            mWebView.loadUrl( "javascript:window.location.reload( true )" );
        }

        DrawerLayout drawer = findViewById ( R.id.drawer_layout );
        drawer.closeDrawer ( GravityCompat.START );
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()){
                        mWebView.goBack();
                    }
                    else {
                        finish();
                    }
                    return  true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
