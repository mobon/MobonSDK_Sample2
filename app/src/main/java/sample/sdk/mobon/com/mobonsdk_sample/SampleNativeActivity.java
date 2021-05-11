package sample.sdk.mobon.com.mobonsdk_sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobon.sdk.NativeAdView;
import com.mobon.sdk.callback.iMobonNativeCallback;

/**
 * NativeView 의 샘플 예제
 */
public class SampleNativeActivity extends Activity {
    private String TEST_UNIT_ID = "432394"; // 모비온 테스트 UNIT_ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_native_layout);

        RelativeLayout container = findViewById(R.id.container);
        ImageView ContentImageView = findViewById(R.id.content_iv);
        ImageView logoImageView = findViewById(R.id.logo_iv);
        TextView titleView = findViewById(R.id.title_tv);
        TextView descView = findViewById(R.id.desc_tv);
        TextView priceView = findViewById(R.id.price_tv);


        NativeAdView nativeView = new NativeAdView(this, container)
                .setUnitId(TEST_UNIT_ID)
                .setAdLogoView(logoImageView)
                .setTitleView(titleView)
                .setDescritionView(descView)
                .setAdPriceView(priceView)
                .setAdImageView(ContentImageView);


        nativeView.setAdListener(new iMobonNativeCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                if (result) {
                    System.out.println("### 광고 성공 ###");
                } else {
                    System.out.println("### 광고 실패 : " + errorStr);
                }
            }
        });


        nativeView.loadAd();

    }

}
