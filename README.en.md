# Mobon Android SDK

For your APP monetization with MOBON or our mediation partners' advertisements, 
please use following instructions to integrate MOBON SDK with your Android APP.

# Mobon Android SDK Release History
 |version|Description|
|---|:---:|
|1.0.4.0|Adapter for Criteo network's Ads Added|
|1.0.3.75|Adapter for Admixer network's Ads Added|
|1.0.3.66|Rare CPU overload issue Fixed|
|1.0.3.59|SDK Mediation function Stabilized|



## Development Environment
- Minimum required SDK Version : Android 14
- Compile SDK : Android 26 ↑
- Build Tool : Android Studio 

## List of Integrated SDK Networks

 You can mediate our integrated network partners ONLY by 
 initializing wanted network's implementation with our MOBON SDK on gradle.
 If you want to add our mediation partners, please follow the guide on each network's guide page.
 We recommend you to maintain latest SDK version as per network's SDK update. 

|Network|LATEST SDK VERSION|Guide|
|---|:---:|:---:|
|Adfit|implementation 'com.mobon.sdk:adapter-adfit:0.9.0.7'|[Integration Guide](adfit.md)|
|Admixer|implementation 'com.mobon.sdk:adapter-admixer:0.9.2.1'|[Integration Guide](admixer.md)|
|Criteo|implementation 'com.mobon.sdk:adapter-criteo:0.9.0.8'|[Integration Guide](criteo.md)|
 
 
## 1. Integrate the MOBON SDK for Android

```XML
dependencies {
  implementation fileTree(dir: 'libs', include: ['*.jar'])
  implementation('com.mobon.sdk:com.mobon.sdk:1.0.4.0') {
        transitive = true
    }
}
```

** Notes about Additional requirements related to Android 9 (Pie) Updates

- Under higher version from targetSdkVersion 28, all HTTP communication will be blocked.
  To make all MOBON and other networks' ads working properly, please allow HTTP communication as follows.

Please set the usesCleartextTraffic as application's attribute value 
which is placed in AndroidManifest.xml file, and set it as true. 

(From Android version 9, the default value is set as false which lead HTTP communication to be blocked.)

```
<manifest ...>
<application
...
android:usesCleartextTraffic="true"
...>
</application>
</manifest>
```

## 2. Initialize Mobon SDK

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    new MobonSDK(this, "YOUR_MEDIA_CODE"); // Please enter your media code (issued by MOBON manager) in second factor.
    
}

```

## Bar Banner 


```java

LinearLayout banner_container = findViewById(R.id.banner_container);
// Please enter issued UNIT_ID in each RectBannerView.
RectBannerView rv = new RectBannerView(this,BannerType.BANNER_320x50).setBannerUnitId(TEST_UNIT_ID);

// Implement AdListener with BannerView.
rv.setAdListener(new iMobonBannerCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorcode) {
                if (result) {
                    // Banner Ad loading success
                    System.out.println("Banner Ad loading");
                     // Enter the BannerView on the layout which you want to show the Ad.
                       banner_container.addView(rv);

                } else {
                
                    System.out.println("Ad loading fail : " + errorcode);
                    rv.destroyAd();
                    rv = null;
                }
            }

            @Override
            public void onAdClicked() {
                System.out.println("Ad Click");
            }
        });
        
      
        
        // Call the Ad
        rv.loadAd();

```
### Type of Bar Banner sizes
 
 |Size in DP (W X H)|Description|AdType Constant|
|---|:---:|:---:|
|320x50|Standard Banner|BannerType.BANNER_320x50|
|320x100|Large Banner|BannerType.BANNER_320x100|
|300x250|Big Banner|BannerType.BANNER_300x250|
|600x600|Biggest Banner|BannerType.BANNER_600x600|
|FILLx60|Horizontal Fill Banner(height 60) |BannerType.BANNER_FIILx60|
|FILLx90|Horizontal Fill Banner(height 90) |BannerType.BANNER_FIILx90|
|CUSTOM SIZE|Layout Cunstom Banner|BannerType.BANNER_CUSTOM|
 
** To use Custom Size, please specify width and height in layout.xml.  
   If there is so large gap between width and height, the banner image could be drawn bad.
   
   
   
## Interstitial Banner (Intro)


```java

// Please initialize the Interstitial Intro Banner and enter issued UnitId.
InterstitialDialog interstitialDialog = new InterstitialDialog(this).setType(Key.INTERSTITIAL_TYPE.NORMAL).setUnitId(YOUR_UNIT_ID).build(); 
 
 // Implement AdListener with Interstitial Intro Banner.
 interstitialDialog.setAdListener(new iMobonInterstitialAdCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, final String errorStr) {
                if (result) {
                    // Ad loading success     
                } else {
                    // Ad loading fail 
                    System.out.println("onLoadedAdInfo fail" + errorStr);       
                }
            }
            
           @Override
            public void onClickEvent(Key.INTERSTITIAL_KEYCODE event_code) {
                if (event_code == Key.INTERSTITIAL_KEYCODE.CLOSE) {
//                    if (interstitialDialog != null)
//                        interstitialDialog.loadAd();
                } else if (event_code == Key.INTERSTITIAL_KEYCODE.ADCLICK) {
                    System.out.println("Click the Ad");
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
    
    // Call the Ad
      interstitialDialog.loadAd();
      
    // Show the Ad
       if(interstitialDialog.isLoaded())
           interstitialDialog.show();
      

```
    
### Type of Interstitial Intro Banner sizes
 
 |Size|Description|AdType Constant|
|---|:---:|:---:|
|SMALL|250dp x 300dp|INTERSTITIAL_TYPE.SMALL|
|NORMAL|device screen 70%|INTERSTITIAL_TYPE.NORMAL|
|FULL|device screen 100% |INTERSTITIAL_TYPE.FULL|



## Interstitial Banner (Ending) 
```java

private EndingDialog mEndingDialog;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
  ...
// Please initialize the Interstitial Ending Banner and enter issued UnitId.
 mEndingDialog = new EndingDialog(this).setType(Key.ENDING_TYPE.NORMAL).setUnitId(TEST_UNIT_ID).build(); 
 // Implement AdListener with Interstitial Ending Banner.
mEndingDialog.setAdListener(new iMobonEndingPopupCallback() {
            @Override
            public void onLoadedAdInfo(boolean result, String errorStr) {
                if (result) {
                    // Ad loading success
                } else {
                    // Ad loading fail
                }
            }

            @Override
            public void onClickEvent(Key.ENDING_KEYCODE event_code) {
                switch (event_code) {
                    case CLOSE:
                        // Click the close button
                        finish();
                        break;
                    case CANCLE:
                        // Click the cancel button or BackKey
                        mEndingDialog.loadAd(); // Reload the Ad when the Interstitial Ending Banner...
                        break;
                    case ADCLICK:
                        // Click the Ad
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
        // Call the Ad
        mEndingDialog.loadAd();
        }
        
        
        @Override
    public void onBackPressed() {
        if (mEndingDialog != null && !mEndingDialog.isShowing() && mEndingDialog.isLoaded()) {
            mEndingDialog.show();
            return;
        } else {
//                // Close the APP or call the other network's Interstitial Ending Banner...
        }
        super.onBackPressed();
    }
        
```

### Type of Interstitial Ending Banner sizes
 
 |Size|Description|AdType Constant|
|---|:---:|:---:|
|NORMAL|device screen 60%|ENDING_TYPE.NORMAL|
|FULL|device screen 100% |ENDING_TYPE.FULL|


## Call the Ad as data (JSON)
 - Please use this method, if you want to call the Ad as data, so that you can design BannerView as you want.
 - The type of Ad is divided to Normal Ad and Targeting Ad. Please set 2 layouts for each depending on the sample project.
 - To clarify whether all impressions and clicks of the Ads working properly or not, the APK MUST take MOBON manager's test.
 
 ```java
private MobonSDK mMobonSDK;

@Override
    protected void onCreate(Bundle savedInstanceState) {
  ...
  // Please enter your media code (issued by MOBON manager) in second factor.
  mMobonSDK = new MobonSDK(this,"Your_media_code"); 
       
 //Second factor : The number of Ads requested. 
 //Third factor : Enter your UnitId (issued by MOBON manager).
 //Fourth factor : Call the Ad callback Listener
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
                     // Data processing of the Ad...
                    }


                    } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                }else{
                  if (errorStr.equals(Key.NOFILL)) {// No Ad 
                   } else {// Connection Error
                   }
                  }
             }
       }); 


```
** sample Project 의 SampleJsonDataActivity 를 참조하세요.

### JSON Response parameter

|name|type|description|
|---|:---:|:---:|
|client|string|JSON packet PREPIX returned by MOBON|
|target|string|AD Type : All types without AD(Code name of Non-targeting Ads) belong to Targeting Ads|
|length|int|Ad number|
|logo|string|Ad logo|
|img_logo|string|Ad icon|
|mobonLogo|string|Mobon logo|
|pcode|string|Code number of the item of Ad|
|pnm|string|In case of Ad for item, the value is a name of the item. In case of Ad for the other, the value is a name of the Ad|
|price|string|In case of Ad for item, the value is a price of the item. In case of Ad for the other, no value|
|img|string|The url to Ad imange|
|mimg_W_H|string|The url to Ad image for each size, ONLY for Non-targeting Ads|
|purl|string|Ad landing url |
|increaseViewKey|string|Key value for Ad Impression|
|site_desc|string|Ad description|


## Other options
- setImageSizeLimit(int kb) : Set the limitation for maximum size of the Ad image.  
  (ex) insterstitial.setImageSizeLimit(500); // ONLY view the Ads under 500kb.


## Caution

- If you want to apply Proguard, you need to modify the proguard configuration file as well.  
For details, please refer to `proguard.cfg ` file or [proguard-rules.pro](/app/proguard-rules.pro) in the sample project.
