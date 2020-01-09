package sample.sdk.mobon.com.mobonsdk_sample;

//import android.content.Context;
//import android.support.multidex.MultiDex;
//import android.support.multidex.MultiDexApplication;
//
//public class Application extends MultiDexApplication {
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }
//}

public class Application{
  MobonSDK.init(this); //criteo 미디에이션 사용시 선언
}
