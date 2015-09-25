package com.erg.heatindex;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.KeyEvent;

/**
 * Created by SLuo on 8/27/2015.
 */
public class MyWebViewClient extends WebViewClient {
    @Override
    //show the web page in webview but not in web browser
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }


}

