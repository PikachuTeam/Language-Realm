package com.tatteam.languagerealm.ui.module;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseFragment;


/**
 * Created by hoaba on 9/14/2015.
 */
public abstract class GoogleSearchPhraseFragment extends BaseFragment {
    private String phrase;
    private WebView wvSearch;
    private Toolbar toolbar;
    private MyWebViewClient myBrowser;

    @Override
    protected boolean isPhraseFragment() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        phrase = bundle.getString("phrase");
        View rootView = inflater.inflate(R.layout.fragment_google_search, container, false);
        wvSearch = (WebView) rootView.findViewById(R.id.webview);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        setUpWebView();
        setUpToolBar();
        return rootView;
    }

    public void setUpWebView() {
        myBrowser = new MyWebViewClient();
        wvSearch.setWebViewClient(myBrowser);
        wvSearch.getSettings().setJavaScriptEnabled(true);
        wvSearch.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvSearch.loadUrl(getUrl());

    }

    public String getUrl() {
        String url = "https://www.google.com/search?q=";
        if (phrase != null) {
            String[] part = phrase.split(" ");
            for (int i = 0; i < part.length; i++) {
                url = url + "+" + part[i];
            }
        }
        return url;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
          return true;
        }
    }


    public void setUpToolBar() {
        getBaseActivity().setSupportActionBar(toolbar);
        toolbar.setTitle(phrase);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (wvSearch.canGoBack()) {
            wvSearch.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
