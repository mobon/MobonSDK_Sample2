# Criteo Mediation SDK

 미디에이션 기능을 사용하는 방법을 제공합니다.

## 개발환경
- 최소 SDK Version : Android 19
- Compile SDK : Android 26 이상
- Build Tool : Android Studio 
- Application Context 를 요구

 모비온 SDK 라이브러리 와 함께 Gradle 에 선언 만으로 사용가능하며,  
 추가 코드 수정없이 적용할 수 있도록 구성되었습니다.  
 되도록 최신버전을 유지바랍니다. 


**  App 의 build.gradle 에 아래와 같이 주소가 추가되야 합니다.
 ```XML
dependencies {
  ...
  implementation 'com.google.android.gms:play-services-ads-identifier:16.0.0'
  implementation 'com.google.android.gms:play-services-base:16.0.1'
  implementation 'com.mobon.sdk:adapter-criteo:0.9.0.0'
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
   
**  Progaurd 난독화 시 아래와 같이 예외 처리합니다.
 ```java
 -keep class com.httpmodule.** { *; }
-keep class com.imgmodule.** { *; }
-keep public class com.mobon.**{
 public *;
}

-keep class com.criteo.** { *; }
```
