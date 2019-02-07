package in.catking.gkapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * vvklr
 */

public class t_and_c extends AppCompatActivity {
    WebView webView;
    ProgressBar loader;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        url = "https://gkapp.in/privacy-policy/";
        loader = (ProgressBar) findViewById(R.id.loader);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
//       }
//        WebView myWebView = (WebView) findViewById(R.id.webView1);
//        myWebView.getSettings().setJavaScriptEnabled(true);
//        myWebView.loadUrl("http://www.google.com");
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        myWebView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    loader.setVisibility(View.GONE);
                } else {
                    loader.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
