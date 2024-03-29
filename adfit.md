# Adfit Mediation SDK

 미디에이션 기능을 사용하는 방법을 제공합니다.

## 개발환경
- 최소 SDK Version : Android 21
- Compile SDK : Android 28 이상
- Build Tool : Android Studio 
- Activity Context 를 요구

|지원 광고 타입|
|---:|
|Banner 320x50,300x250|
|Interstital Popup Size, Full Size|
|Ending|

**  project 의 build.gradle 에 아래와 같이 빌드 버전과 주소가 추가되야 합니다.
  ```java  
    dependencies {
        ...
        classpath 'com.android.tools.build:gradle:4.0.1' //최소 요구 버전
        ...
    }
    
    repositories { 
       ...
       maven { url 'https://devrepo.kakao.com/nexus/content/groups/public/' } // necessary for Adfit
       ...
    }
    
```


**  gradle-wrapper.properties 의  distribution 버전은 6.1.1 이상을 요구합니다.
 ```XML
   distributionUrl=https\://services.gradle.org/distributions/gradle-6.1.1-all.zip
```


**  App 의 build.gradle 에 아래와 같이 주소가 추가되야 합니다.
 ```XML

android {
...
compileOptions {
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }
...
}

dependencies {
  ...
  implementation 'io.github.mobon:adapter-adfit:1.0.9'
  implementation 'com.kakao.adfit:ads-base:3.13.6'
  ...
}
```
** AdfitSDK의 경우 3.13.6에 최적화 되어 있으나, 특별히 충돌이 발생하지 않는다면 다른 버전을 사용하셔도 무방합니다.



   
**  Progaurd 난독화 시 아래와 같이 예외 처리합니다.
 ```java
 -keep class com.httpmodule.** { *; }
-keep class com.imgmodule.** { *; }
-keep public class com.mobon.**{
 public *;
}

-keep class com.kakao.adfit.** { *; }
```
