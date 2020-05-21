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
    private String TEST_BACON_UNIT_ID = "373386"; // 모비온 테스트 바콘 UNIT_ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_banner_layout);
        bannerContainer = (LinearLayout) findViewById(R.id.banner_layout);
         rectBannerView = new RectBannerView(SampleBannerActivity.this, BannerType.BANNER_FILLx60).setBannerUnitId(TEST_UNIT_ID); //발급받은 UnitId 로 교체하세요.
        // rectBannerView.setBgColor("#ffffff"); // 비 선언시 배경 투명처리
        rectBannerView.setInterval(60); // setInterval() 배너 광고 롤링시 (second)  비선언시 롤링 안함        
        rectBannerView.setAdListener(new iMobonBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                if (result) {
                    //광고 성공
                    System.out.println("성공");
                    bannerContainer.addView(rectBannerView);
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
                    
                    /////////////////////////////////////
                    showBaconBanner(); // 바콘 배너 광고 진행시에만 호출바랍니다.
                    /////////////////////////////////////
                }
            }

            @Override
            public void onAdClicked() {

            }
        });
        rectBannerView.loadAd();
    }
    
    
     private void showBaconBanner(){

        final RectBannerView baconBannerView = new RectBannerView(this, BannerType.BANNER_FILLx90).setBannerUnitId("373386"); //바콘용 UnitId 를 넣으셔야 합니다.
        baconBannerView.setAdListener(new iMobonBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorcode) {
                if (result) {
                    //배너 광고 로딩 성공
                    System.out.println("바콘 광고로딩!!!!!");
                    banner_container.addView(baconBannerView);
                } else {
                    if (errorcode.equals(Key.NOFILL)) {
                        //광고 없음
                    } else {
                        //통신 오류
                    }
                    System.out.println("바콘 광고실패 : " + errorcode);
                    baconBannerView.destroyAd();
                }
            }

            @Override
            public void onAdClicked() {
                System.out.println("바콘 광고클릭 : ");
            }
        });

        baconBannerView.loadBaconAd(); // loadAd()가 아닌 loadBaconAD() 입니다.
    }

}
