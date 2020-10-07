package sample.sdk.mobon.com.mobonsdk_sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.mobon.sdk.InterstitialDialog;
import com.mobon.sdk.Key;
import com.mobon.sdk.MobonSDK;
import com.mobon.sdk.callback.iMobonInterstitialAdCallback;

/**
 * 전면팝업의 샘플 예제
 */
public class SampleInterstitialActivity extends Activity {

    private InterstitialDialog mInterstitialDialog;
    private String TEST_UNIT_ID = "432394"; // 모비온 테스트 UNIT_ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_interstitial_layout);

        mInterstitialDialog = new InterstitialDialog(this).setType(Key.INTERSTITIAL_TYPE.NORMAL).setUnitId(TEST_UNIT_ID).build(); //발급받은 UnitId 로 교체하세요.

        findViewById(R.id.ad_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialDialog != null)
                    mInterstitialDialog.loadAd();
            }
        });

        findViewById(R.id.ad_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialDialog != null && mInterstitialDialog.isLoaded())
                    mInterstitialDialog.show();
                else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SampleInterstitialActivity.this, "please load first...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        //callback
        mInterstitialDialog.setAdListener(new iMobonInterstitialAdCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, final String errorStr) {
                if (result) {
                    //광고 성공
                    System.out.println("onLoadedAdInfo success");
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SampleInterstitialActivity.this, "Load Success", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    System.out.println("onLoadedAdInfo fail" + errorStr);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SampleInterstitialActivity.this, "Load fail : " + errorStr, Toast.LENGTH_SHORT).show();
                        }
                    });

                    if (errorStr.equals(Key.NOFILL)) {
                        //광고 없음
                    } else {
                        //기타 오류
                    }
                   /////////////////////////////////////
                    showBaconBanner(); // 바콘 배너 광고 진행시에만 호출바랍니다.
                    /////////////////////////////////////
                }
            }

            @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE event_code) {
                if (event_code == Key.INTERSTITIAL_KEYCODE.CLOSE) {
//                    if (mInterstitialDialog != null)
//                        mInterstitialDialog.loadAd();
                } else if (event_code == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
                    System.out.println("Interstitial Ad Click");
                    if (mInterstitialDialog != null)
                        mInterstitialDialog.close();
                }
            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
    
    private void showBaconBanner(){
        final InterstitialDialog interstitialDialog = new InterstitialDialog(this).setType(Key.INTERSTITIAL_TYPE.NORMAL).setUnitId(BACON_UNIT_ID).build(); //발급받은 BACON 용 UnitId 로 교체하세요.
       
        //callback
        interstitialDialog.setAdListener(new iMobonInterstitialAdCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, final String errorStr) {
                if (result) {
                    //광고 성공
                    System.out.println("onLoadedAdInfo success");
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SampleInterstitialActivity.this, "Load Success", Toast.LENGTH_SHORT).show();
                            interstitialDialog.show();
                        }
                    });

                } else {
                    System.out.println("onLoadedAdInfo fail" + errorStr);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SampleInterstitialActivity.this, "Load fail : " + errorStr, Toast.LENGTH_SHORT).show();
                        }
                    });

                    if (errorStr.equals(Key.NOFILL)) {
                        //광고 없음
                    } else {
                        //기타 오류
                    }
                }
            }

            @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE event_code) {
                if (event_code == Key.INTERSTITIAL_KEYCODE.CLOSE) {
                    
                } else if (event_code == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
                    System.out.println("Interstitial Ad Click");
                    if (interstitialDialog != null)
                        interstitialDialog.close();
                }
            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });
        
        interstitialDialog.loadBaconAd();
    }
}
