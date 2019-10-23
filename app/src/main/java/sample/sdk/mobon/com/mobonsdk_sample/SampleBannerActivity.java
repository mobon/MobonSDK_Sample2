package sample.sdk.mobon.com.mobonsdk_sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.mobon.sdk.BannerType;
import com.mobon.sdk.Key;
import com.mobon.sdk.MobonSDK;
import com.mobon.sdk.RectBannerView;
import com.mobon.sdk.callback.iMobonBannerCallback;

/**
 * RectBannerView 의 샘플 예제
 */
public class SampleBannerActivity extends Activity {
    private RectBannerView rectBannerView;
    private LinearLayout bannerContainer;
    private String TEST_UNIT_ID = "18506"; // 모비온 테스트 UNIT_ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_banner_layout);
        bannerContainer = (LinearLayout) findViewById(R.id.banner_layout);
         rectBannerView = new RectBannerView(SampleBannerActivity.this, BannerType.BANNER_FILLx60).setBannerUnitId(TEST_UNIT_ID); //발급받은 UnitId 로 교체하세요.
        // rectBannerView.setBgColor("#ffffff"); // 비 선언시 배경 투명처리
        rectBannerView.setInterval(60); // setInterval() 배너 광고 롤링시 (second)  비선언시 롤링 안함
        bannerContainer.addView(rectBannerView);
        rectBannerView.setAdListener(new iMobonBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                if (result) {
                    //광고 성공
                    System.out.println("성공");
                } else {
                    System.out.println("실패");
                    if (errorStr.equals(Key.NOFILL)) {
                        //광고 없음
                    } else {
                        //기타 오류
                    }
                    System.out.println(errorStr);
                    if (rectBannerView != null)
                        rectBannerView.destroyAd();
                    rectBannerView = null;
                }
            }

            @Override
            public void onAdClicked() {

            }
        });
        rectBannerView.loadAd();
    }

}
