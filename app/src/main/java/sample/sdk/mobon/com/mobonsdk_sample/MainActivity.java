package sample.sdk.mobon.com.mobonsdk_sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobon.sdk.BannerType;
import com.mobon.sdk.CommonUtils;
import com.mobon.sdk.EndingDialog;
import com.mobon.sdk.Key;
import com.mobon.sdk.MobonSDK;
import com.mobon.sdk.RectBannerView;
import com.mobon.sdk.callback.iMobonBannerCallback;
import com.mobon.sdk.callback.iMobonEndingPopupCallback;
import com.mobon.sdk.callback.iMobonMCoverCallback;

public class MainActivity extends Activity {

    private EndingDialog mEndingDialog;
    private RelativeLayout banner_container;
    private String TEST_UNIT_ID = "18506"; // 모비온 테스트 UNIT_ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner_container = findViewById(R.id.banner_container);

        new MobonSDK(this, "YOUR_MEDIA_CODE"); // 발급받은 mediaCode로 교체바랍니다.

        Button btnNextSub1 = (Button) findViewById(R.id.btnNextSub1);
        btnNextSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SampleBannerActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        Button btnNextSub3 = (Button) findViewById(R.id.btnNextSub3);
        btnNextSub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SampleJsonDataActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


        Button btnNextSub4 = (Button) findViewById(R.id.btnNextSub4);
        btnNextSub4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SampleInterstitialActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        Button btnNextSub5 = (Button) findViewById(R.id.btnNextSub5);
        btnNextSub5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SampleNativeActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


        //엔딩팝업 생성및 이벤트 처리
        mEndingDialog = new EndingDialog(this).setType(Key.ENDING_TYPE.NORMAL).setUnitId(TEST_UNIT_ID).build(); //발급받은 UnitId 로 교체하세요.
        mEndingDialog.setAdListener(new iMobonEndingPopupCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                if (result) {
                    //광고 성공
                } else {
                    if (errorStr.equals(Key.NOFILL)) {
                        //광고 없음
                    } else {
                        //통신 오류
                    }
                }
            }

            @Override
            public void onClickEvent(Key.ENDING_KEYCODE event_code) {
                switch (event_code) {
                    case CLOSE:
                        //종료 클릭
                        finish();
                        break;
                    case CANCLE:
                        //닫기 클릭 또는 BackKey 시
                        mEndingDialog.loadAd(); // 창 닫았을시 미리 로딩...
                        break;
                    case ADCLICK:
                        //광고 클릭
                        break;
                }
            }

            @Override
            public void onOpened() {

            }

            @Override
            public void onClosed() {

            }
        });

        mEndingDialog.loadAd();

        final RectBannerView rv = new RectBannerView(this,BannerType.BANNER_320x50).setBannerUnitId(TEST_UNIT_ID);

        rv.setAdListener(new iMobonBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorcode) {
                if (result) {
                    //배너 광고 로딩 성공
                    System.out.println("배너 광고로딩");
                    banner_container.addView(rv);
                } else {
                    if (errorcode.equals(Key.NOFILL)) {
                        //광고 없음
                    } else {
                        //통신 오류
                    }

                }
                System.out.println("광고실패 : " + errorcode);
            }

            @Override
            public void onAdClicked() {
                System.out.println("광고클릭");
            }
        });


        rv.loadAd();

    }

    @Override
    public void onBackPressed() {
        if (mEndingDialog != null && !mEndingDialog.isShowing() && mEndingDialog.isLoaded()) {
            mEndingDialog.show();
            return;
        } else {
//                // 앱 종료 or 타 엔딩팝업 처리...
        }
        super.onBackPressed();
    }
    
}
