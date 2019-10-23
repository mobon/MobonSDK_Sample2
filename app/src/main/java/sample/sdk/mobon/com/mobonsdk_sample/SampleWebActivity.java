package sample.sdk.mobon.com.mobonsdk_sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.mobon.sdk.BannerType;
import com.mobon.sdk.Key;
import com.mobon.sdk.MobonSDK;
import com.mobon.sdk.RectBannerView;
import com.mobon.sdk.callback.iMobonBannerCallback;

/**
 * RectBannerView 의 샘플 예제
 */
public class SampleWebActivity extends Activity {

    private WebView webview;
    private MobonSDK mobonSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        webview = (WebView) findViewById(R.id.webview);

        mobonSDK = new MobonSDK(this);




        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        //settings.setDefaultTextEncodingName("EUC-KR");
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(false);

        // 웹뷰 - HTML5 창 속성 추가
        String path = getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(path);
        settings.setDomStorageEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.contains("www.mediacategory.com")) {
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    startActivity(i);
//                    return true;
//                }

               return false;
            }

        });


        // 롤리팝 버전에서 ssl 블럭 이슈에 대한 대응 코드
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            cookieManager.setAcceptThirdPartyCookies(webview, true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }


        // 웹뷰 - 화면
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);

        webview.loadUrl("http://www.topstarnews.net/news/articleView.html?idxno=460859#_enliple");
    }

}
