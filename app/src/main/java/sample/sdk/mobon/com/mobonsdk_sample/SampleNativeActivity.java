package sample.sdk.mobon.com.mobonsdk_sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.mobon.sdk.Key;
import com.mobon.sdk.MobonSDK;
import com.mobon.sdk.NativeAdView;
import com.mobon.sdk.NativeType;
import com.mobon.sdk.callback.iMobonNativeCallback;

/**
 * NativeView 의 샘플 예제
 */
public class SampleNativeActivity extends Activity {
    private NativeAdView nv;
    private LinearLayout nativeContainer;
    private String TEST_UNIT_ID = "18506"; // 모비온 테스트 UNIT_ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_native_layout);
        nativeContainer = (LinearLayout) findViewById(R.id.native_layout);
        nv = new NativeAdView(SampleNativeActivity.this, NativeType.NATIVE_360x160).RoundedCorners().setUnitId(TEST_UNIT_ID); //발급받은 UnitId 로 교체하세요.
        nv.setBgColor("#ffffff"); // 비 선언시 배경 투명처리
        nativeContainer.addView(nv);

        nv.setAdListener(new iMobonNativeCallback() {
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
                    if (nv != null)
                        nv.destroyAd();
                    nv = null;
                }
            }
        });
    }

}
