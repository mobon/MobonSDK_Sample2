# AdMixer Mediation SDK

 미디에이션 기능을 사용하는 방법을 제공합니다.

## 개발환경
- 최소 SDK Version : Android 19
- Compile SDK : Android 26 이상
- Build Tool : Android Studio 
- Activity Context 를 요구

 모비온 SDK 라이브러리 와 함께 Gradle 에 선언 만으로 사용가능하며,  
 추가 코드 수정없이 적용할 수 있도록 구성되었습니다.  
 되도록 최신버전을 유지바랍니다. 


**  App 의 build.gradle 에 아래와 같이 주소가 추가되야 합니다.
 ```XML
dependencies {
  ...
  implementation 'com.mobon.sdk:adapter-admixer:0.9.1.6'
  ...
}
```
   
**  Progaurd 난독화 시 아래와 같이 예외 처리합니다.
 ```java
 -keep class com.httpmodule.** { *; }
-keep class com.imgmodule.** { *; }
-keep public class com.mobon.**{
 public *;
}

-keep class com.admixer.** { *; }
```
