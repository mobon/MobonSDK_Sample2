# Criteo Mediation SDK

 미디에이션 기능을 사용하는 방법을 제공합니다.

## 개발환경
- 최소 SDK Version : Android 18
- Compile SDK : Android 26 이상
- Build Tool : Android Studio 
- Application Context 를 요구

|지원 광고 타입|
|---:|
|Banner 320x50|
|interstital|

**  App 의 build.gradle 에 아래와 같이 주소가 추가되야 합니다.
 ```XML
dependencies {
  ...
  implementation 'com.google.android.gms:play-services-ads-identifier:16.0.0'
  implementation 'com.google.android.gms:play-services-base:16.0.1'
  implementation 'com.mobon.sdk:adapter-criteo:0.9.0.9'
  ...
}
```

**  App 의 Application 에서 mobon SDK 를 초기화 해주어야 합니다.
 ```java
public class MyApplication extends Application {

    @Override
    protected void onCreate() {
        super.onCreate();       
        MobonSDK.init(this);
    }
}
```

**  App 의 Application 에서 어려울시 MainActivity 에서 초기화 방법입니다.
 ```java
public class MainActivity extends Activity {
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobonSDK.init(this.getApplication());
    }
}
```
   
**  Progaurd 난독화 시 아래와 같이 예외 처리합니다.
 ```java
 -keep class com.httpmodule.** { *; }
-keep class com.imgmodule.** { *; }
-keep public class com.mobon.**{
 public *;
}

-keep class com.criteo.** { *; }
```
