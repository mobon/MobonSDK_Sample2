# IGAWORK Mediation SDK

 미디에이션 기능을 사용하는 방법을 제공합니다.

## 개발환경
- 최소 SDK Version : Android 14
- Compile SDK : Android 28 이상
- Build Tool : Android Studio 
- Activity Context 를 요구

|지원 광고 타입|
|---:|
|Banner 320x50,320x100,300x250|
|Interstital Popup Size, Full Size|



**  App 의 build.gradle 에 아래와 같이 주소가 추가되야 합니다.
 ```XML
dependencies {
  ...
  implementation 'io.github.mobon:adapter-igaw:0.9.0' 
  ...
}
```

** AndroidManifest.xml 에 전달받은 App key 를 추가합니다.
 ```XML
<application>
... 
<meta-data android:name="adpopcorn_ssp_app_key" android:value="이곳에_앱키를_입력하세요" /> 
...
</application>
```
   

