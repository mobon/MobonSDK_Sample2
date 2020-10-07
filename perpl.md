
# Perpl Mediation SDK

 미디에이션 기능을 사용하는 방법을 제공합니다.

## 개발환경
- 최소 SDK Version : Android 14
- Compile SDK : Android 26 이상
- Build Tool : Android Studio 
- Activity Context 를 요구

|지원 광고 타입|
|---:|
|Interstital Full Size|


**  App 의 build.gradle 에 아래와 같이 주소가 추가되야 합니다.
 ```XML
dependencies {
  ...
  implementation ('com.mobon.sdk:adapter-perpl:0.9.0.4'){
        exclude group: 'com.google.android.gms'
    }
  ...
}
```
   
**  Progaurd 난독화 시 아래와 같이 예외 처리합니다.
 ```java
 -keep class com.crossmedia.** { *; }
 -dontwarn com.crossmedia.**
```
