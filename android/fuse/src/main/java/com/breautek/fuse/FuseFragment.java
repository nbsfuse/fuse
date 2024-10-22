package com.breautek.fuse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.webkit.WebViewAssetLoader;
import androidx.webkit.WebViewClientCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.breautek.fuse.views.SplashLoaderView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FuseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FuseFragment extends Fragment {
    public static final String LOG_TAG = "FuseFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FuseContext $fuseContext;
    private FuseContext.IReadyCallback $fuseCB;

    public FuseFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FuseFragment.
     */
    public static FuseFragment newInstance(FuseContext.IReadyCallback callback) {
        FuseFragment fragment = new FuseFragment();
        fragment.$fuseCB = callback;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        $fuseContext = new FuseContext(this, this.getActivity(), this::_onContextReady);
        $fuseContext.onCreate(savedInstanceState);
    }

    public FuseContext getFuseContext() {
        return $fuseContext;
    }

    @Nullable
    public WebView getWebview() {
        return getView().findViewById(R.id.webview);
    }

    protected void _onContextReady() {
        WebView webview = this.getView().findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.addJavascriptInterface($fuseContext, "BTFuseNative");
        webview.loadUrl("https://localhost/assets/index.html");
        $fuseCB.onReady();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fuse, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            $fuseContext.getRuntime().onInsetChange(insets);
            return insets;
        });

        SplashLoaderView loaderView = root.findViewById(R.id.splash_loader);
        loaderView.setVisibility(View.VISIBLE);

        $fuseContext.getProgressContext().addListener(loaderView);

        WebView webview = root.findViewById(R.id.webview);

        final WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                                               .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler($fuseContext.getActivityContext()))
                                               .setHttpAllowed(false)
                                               .setDomain($fuseContext.getHost())
                                               .build();

        webview.setWebViewClient(new WebViewClientCompat() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                $fuseContext.getLogger().info(LOG_TAG, "DOM Request: " + request.getUrl());
                return assetLoader.shouldInterceptRequest(request.getUrl());
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            @Override
            public void onReceivedSslError(WebView webview, SslErrorHandler handler, SslError error) {
                $fuseContext.onReceivedSslError(webview, handler, error);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        $fuseContext.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        $fuseContext.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        $fuseContext.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
        $fuseContext.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        $fuseContext.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        $fuseContext.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        $fuseContext.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        $fuseContext.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        $fuseContext.onDestroy();
    }

    void onFuseLoad() {
        SplashLoaderView loaderView = getView().findViewById(R.id.splash_loader);
        WebView wb = getView().findViewById(R.id.webview);

        wb.setVisibility(View.VISIBLE);
        loaderView.setVisibility(View.GONE);
    }

    public ViewGroup getLayout() {
        return getView().findViewById(R.id.layout_container);
    }
}