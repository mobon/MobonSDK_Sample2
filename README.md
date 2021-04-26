# Mobon Android SDK

모비온 SDK 를 이용하여 모비온 광고를 노출하는 방법을 제공하고 있습니다.  
또한 기타 광고 플랫폼을 이용하여 미디에이션 기능을 사용하는 방법을 제공합니다.

* [Mobon SDK 의 라이브러리 주소가 변경되었습니다.]

# Mobon Android SDK Release History
 |version|Description|
|---|:---:|
|1.0.0.5|Script AD UI 변경|
|1.0.0.2|라이브러리 저장소 변경 작업|

## 개발환경
- 최소 SDK Version : Android 14
- Compile SDK : Android 26 이상
- Build Tool : Android Studio 
- androidX 권장

## 미디에이션 SDK 지원 목록

 모비온 SDK 라이브러리 와 함께 Gradle 에 선언 만으로 사용가능하며,  
 필요로 하는 미디에이션 SDK 의 라이브러리를 선언하시고 연동가이드를 클릭하여 
 안내에 따라 적용하시기 바랍니다.
 되도록 최신버전을 유지바랍니다.

|플랫폼|LATEST SDK VERSION|가이드|
|---|:---:|:---:|
|Adfit|implementation 'io.github.mobon:adapter-adfit:1.0.3'|[연동가이드](adfit.md)|
|Admixer|implementation 'io.github.mobon:adapter-admixer:1.0.1'|[연동가이드](admixer.md)|
|Criteo|implementation 'io.github.mobon:adapter-criteo:1.0.1'|[연동가이드](criteo.md)|
|Perpl|implementation 'io.github.mobon:adapter-perpl:1.0.0'|[연동가이드](perpl.md)|
 
 
## 1. Mobon SDK 기본설정

- project build.gradle 에 mavenCentral() 을 추가합니다.

```XML
allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
```

- app build.gradle 에 mobonSDK 라이브러리를 추가합니다.
```XML
dependencies {
  implementation fileTree(dir: 'libs', include: ['*.jar'])
  implementation('io.github.mobon:mobonSDK:1.0.0.5') {
        transitive = true
    }
}
```

** Android 9 (Pie) 업데이트에 따른 추가설정

- targetSdkVersion 28 부터 네트워크 통신 시 암호화 되지 않은 HTTP통신이 차단되도록 기본설정이
변경되었습니다. SDK 내 모든 미디에이션 광고가 정상동작하기 위해서는 HTTP 통신을 허용해주셔야 하며, 방법은 아래와 같습니다.

AndroidManifest.xml 파일에서 application 항목의 속성값으로
usesCleartextTraffic을 true로 설정해야 합니다.
(Android 9 부터 해당값이 default로 false 설정되어 HTTP 통신이 제한됩니다.)

```
<manifest ...>
<application
...
android:usesCleartextTraffic="true"
...>
</application>
</manifest>
```

## 2. Mobon SDK 선언

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    new MobonSDK(this, "YOUR_MEDIA_CODE"); //두번째 인자에 발급받은 미디어코드로 수정하세요.
    
}

```

## 띠 배너 


```java

LinearLayout banner_container = findViewById(R.id.banner_container);
// 각 광고 뷰 당 발급받은 UNIT_ID 값을 필수로 넣어주어야 합니다.
RectBannerView rv = new RectBannerView(this,BannerType.BANNER_320x50).setBannerUnitId(TEST_UNIT_ID);

// 배너뷰의 리스너를 등록합니다.
rv.setAdListener(new iMobonBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorcode) {
                if (result) {
                    //배너 광고 로딩 성공
                    System.out.println("배너 광고로딩");
                     // 광고를 띄우고자 하는 layout 에 배너뷰를 삽입합니다.
                       banner_container.addView(rv);

                } else {
                
                    System.out.println("광고실패 : " + errorcode);
                    rv.destroyAd();
                    rv = null;
                }
            }

            @Override
            public void onAdClicked() {
                System.out.println("광고클릭");
            }
        });
        
      
        
        // 광고를 호출합니다.
        rv.loadAd();

```

##바콘(바탕화면 아이콘) 띠 배너 

** 바콘 배너에 대해
 - 배너를 클릭시 광고 사이트로 이동하며 바로가기를 추가적으로 설치를 유도합니다. 
   유저의 바로가기 추가로 인해 앱 사용시가 아니더라도 지속적인 수익창출이 가능합니다.

```java

LinearLayout banner_container = findViewById(R.id.banner_container);
// 발급받은 BACON 용 UNIT_ID 값을 필수로 넣어주어야 합니다.
RectBannerView rv = new RectBannerView(this,BannerType.BANNER_320x50).setBannerUnitId(BACON_UNIT_ID); 

// 배너뷰의 리스너를 등록합니다.
rv.setAdListener(new iMobonBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorcode) {
                if (result) {
                    //배너 광고 로딩 성공
                    System.out.println("바콘배너 광고로딩");
                     // 광고를 띄우고자 하는 layout 에 배너뷰를 삽입합니다.
                       banner_container.addView(rv);

                } else {
                
                    System.out.println("광고실패 : " + errorcode);
                    rv.destroyAd();
                    rv = null;
                }
            }

            @Override
            public void onAdClicked() {
                System.out.println("광고클릭");
            }
        });       
        
        // 광고를 호출합니다.
        rv.loadBaconAd();

```

###배너 광고 사이즈별 타입
 
 |Size in DP (W X H)|Description|AdType Constant|
|---|:---:|:---:|
|320x50|Standard Banner|BannerType.BANNER_320x50|
|320x100|Large Banner|BannerType.BANNER_320x100|
|300x250|Big Banner|BannerType.BANNER_300x250|
|600x600|Biggest Banner|BannerType.BANNER_600x600|
|FILLx60|Horizontal Fill Banner(height 60) |BannerType.BANNER_FIILx60|
|FILLx90|Horizontal Fill Banner(height 90) |BannerType.BANNER_FIILx90|
|CUSTOM SIZE|Layout Cunstom Banner|BannerType.BANNER_CUSTOM|
 
** Custom Size 사용시에는 layout.xml 에서 width 와 height 값을 지정하셔야 합니다.  
    너무 다른 비율로 설정하시면 배너뷰의 레이아웃이 이상하게 나올 수 있습니다.
    
    
   
## 전면 배너 


```java

//전면 배너를 선언하시고 발급받은 UnitId 로 교체하세요.
InterstitialDialog interstitialDialog = new InterstitialDialog(this).setType(Key.INTERSTITIAL_TYPE.NORMAL).setUnitId(YOUR_UNIT_ID).build(); 
 
 //전면 배너 리스너를 등록합니다.
 interstitialDialog.setAdListener(new iMobonInterstitialAdCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, final String errorStr) {
                if (result) {
                    //광고 성공     
                    //전면 광고를 띄웁니다.    
                    interstitialDialog.show();
                } else {
                    //광고 실패 
                    System.out.println("onLoadedAdInfo fail" + errorStr);       
                }
            }
            
           @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE event_code) {
                if (event_code == Key.INTERSTITIAL_KEYCODE.CLOSE) {
//                    if (interstitialDialog != null)
//                        interstitialDialog.loadAd();
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
    
    //광고를 호출합니다
      interstitialDialog.loadAd();
      
    
      

```

##바콘 전면 배너 

```java

LinearLayout banner_container = findViewById(R.id.banner_container);
// 발급받은 BACON 용 UNIT_ID 값을 필수로 넣어주어야 합니다.
InterstitialDialog interstitialDialog = new InterstitialDialog(this).setType(Key.INTERSTITIAL_TYPE.NORMAL).setUnitId(BACON_UNIT_ID).build(); 

 //전면 배너 리스너를 등록합니다.
 interstitialDialog.setAdListener(new iMobonInterstitialAdCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, final String errorStr) {
                if (result) {
                    //광고 성공     
                } else {
                    //광고 실패 
                    System.out.println("onLoadedAdInfo fail" + errorStr);       
                }
            }
            
           @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE event_code) {
                if (event_code == Key.INTERSTITIAL_KEYCODE.CLOSE) {
//                    if (interstitialDialog != null)
//                        interstitialDialog.loadAd();
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
    
    ///바콘 광고를 호출합니다
      interstitialDialog.loadBaconAd();
      
    //전면 광고를 띄웁니다.
       if(interstitialDialog.isLoaded())
           interstitialDialog.show();

```

### 전면 배너광고 사이즈별 타입
 
 |Size|Description|AdType Constant|
|---|:---:|:---:|
|SMALL|250dp x 300dp|INTERSTITIAL_TYPE.SMALL|
|NORMAL|device screen 70%|INTERSTITIAL_TYPE.NORMAL|
|FULL|device screen 100% |INTERSTITIAL_TYPE.FULL|



## 엔딩 배너 
```java

private EndingDialog mEndingDialog;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
  ...
// 엔딩 배너를 선언하시고 발급받은 UnitId 로 교체하세요.
 mEndingDialog = new EndingDialog(this).setType(Key.ENDING_TYPE.NORMAL).setUnitId(TEST_UNIT_ID).build(); 
 //엔딩 배너의 리스너를 등록합니다.
mEndingDialog.setAdListener(new iMobonEndingPopupCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                if (result) {
                    //광고 성공
                } else {
                    //광고 실패
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
                        mEndingDialog.loadAd(); // 창 닫았을시 다시 로딩...
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
        //광고를 호출합니다
        mEndingDialog.loadAd();
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
        
```

### 엔딩 배너 광고 사이즈별 타입
 
 |Size|Description|AdType Constant|
|---|:---:|:---:|
|NORMAL|device screen 60%|ENDING_TYPE.NORMAL|
|FULL|device screen 100% |ENDING_TYPE.FULL|


## 광고 데이터(JSON) 호출
 - 광고 데이터 호출 후 원하는 형태로 광고 뷰를 구현할 때 사용합니다.
 - 광고 타입은 일반 광고와 타게팅 광고로 구분되어 있으니 샘플 프로젝트를 참고 하시어 두개의 레이아웃을 구성하여 주세요.
 - 원활한 광고의 노출을 확인 및 클릭시 사이트 호출 기능이 제대로 동작하는지를 위하여 모비온 담당자의 검수가 필요합니다.
 
 ```java
private MobonSDK mMobonSDK;

@Override
    protected void onCreate(Bundle savedInstanceState) {
  ...
  //두번째 인자를 발급받은 미디어코드로 교체하세요.
  mMobonSDK = new MobonSDK(this,"Your_media_code"); 
       
 //두번째 인자 : 받을 광고의 개수 
 //세번째 인자 : 발급받은 UnitId 로 교체하세요.
 //네번째 인자 : 광고 호출 callback Listener
   mMobonSDK.getMobonAdData(activity, 1, "unitId",new iMobonAdCallback() {
      @Override
      public void onLoadedMobonAdData(boolean result, JSONObject objData, String errorStr) {
           if(result){
                    try {
                    JSONObject jObj = objData.getJSONArray("client").getJSONObject(0);
                    JSONArray jArray = jObj.getJSONArray("data");
                    int length = jObj.getInt("length");
                    String AdType = jObj.getString("target");
                    for (int i = 0; i < length; i++) {
                    AdItem item = new AdItem(AdType, jArray.getJSONObject(i));
                     //광고 데이터 처리...
                    }


                    } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                }else{
                  if (errorStr.equals(Key.NOFILL)) {//광고 없음
                   } else {//통신 오류
                   }
                  }
             }
       }); 


```
** sample Project 의 SampleJsonDataActivity 를 참조하세요.

### 광고 데이터 Response parameter

|name|type|description|
|---|:---:|:---:|
|client|string|MOBON에서 반환한 JSON 패킷 PREPIX|
|target|string|광고 구분 : AD(베이스) 그외는 타게팅 광고|
|length|int|광고 개수|
|logo|string|광고 로고|
|img_logo|string|광고 아이콘|
|mobonLogo|string|Mobon 로고|
|pcode|string|광고 상품코드|
|pnm|string|상품 일 경우 상품명, 상품이 아닐 경우 광고명|
|price|string|상품 일 경우 상품가격, 아닐경우 빈값|
|img|string|대표 광고 이미지 url |
|mimg_W_H|string|사이즈별 이미지 url, 비타게팅 광고일 경우만 넘어옴 |
|purl|string|광고 landing url |
|increaseViewKey|string|광고 노출키 |
|site_desc|string|광고 소개글 |


## 기타 옵션
- setImageSizeLimit(int kb) : 광고 이미지의 용량을 제한합니다.  
  (ex) insterstitial.setImageSizeLimit(500); //500kb 미만의 광고만 띄움.
  
  - setExtractColor(boolean is) : 배너 광고의 배경색상을 소재에 맞게 변경합니다.(기본값 true)
  (ex) rectbanner.setExtractColor(false); //배경색상 변경 안함.
  
  - setOrderBrowser(List<string>) : 광고 클릭시 호출되는 브라우저의 순서를 정할 수 있습니다.
  (ex) mobonSDK.setOrderBrowser(Arrays.asList("com.sec.android.app.sbrowser", "com.android.chrome")); //1순위 삼성브라우저, 2순위 크롬


## 주의 사항

- Proguard를 적용하는 경우 proguard configuration 파일 수정이 필요합니다.  
자세한 구현 내용은 샘플 프로젝트의 `proguard.cfg ` 파일 또는 [proguard-rules.pro](/app/proguard-rules.pro) 참고해 주세요.
