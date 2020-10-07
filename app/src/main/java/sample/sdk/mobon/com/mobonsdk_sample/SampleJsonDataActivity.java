package sample.sdk.mobon.com.mobonsdk_sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mobon.sdk.Key;
import com.mobon.sdk.MobonSDK;
import com.mobon.sdk.callback.iMobonAdCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Mobon Json 광고 데이터 처리
 */
public class SampleJsonDataActivity extends Activity {


    private BaseAdapter mAdapter;
    private ArrayList<AdItem> arrayList = new ArrayList<>();
    private ListView mListView;
    private MobonSDK mMobonSDK;
    private String TEST_UNIT_ID = "432394"; // 모비온 테스트 UNIT_ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adlistitem_fragment);

        mMobonSDK = new MobonSDK(this, "YOUR_MEDIA_CODE"); // AndroidManifest.xml 에 meta-data 로 Media_code 선언한 경우 두번째 인자 생략!!
        arrayList.clear();
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new MainAdapter(this, arrayList);
        mListView.setAdapter(mAdapter);
        
        mMobonSDK.getMobonAdData(this, 1, TEST_UNIT_ID, new iMobonAdCallback() {

            @Override
            public void onLoadedMobonAdData(boolean result, JSONObject objData, String errorStr) {
                if (result) {
                    try {
                        JSONObject jObj = objData.getJSONArray("client").getJSONObject(0);
                        JSONArray jArray = jObj.getJSONArray("data");

                        int length = jObj.getInt("length");
                        String AdType = jObj.getString("target");

                        for (int i = 0; i < length; i++) {
                            AdItem item = new AdItem(AdType, jArray.getJSONObject(i));
                            arrayList.add(item);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    if (errorStr.equals(Key.NOFILL)) {//광고 없음

                    } else {//통신 오류

                    }
                }
            }

        }); //두번째 인자 : 받을 광고의 갯수, 세번쟤 인자 : 발급받은 UnitId 로 교체하세요. 

    }


    public class MainAdapter extends BaseAdapter {
        private static final String TAG = "MainAdapter";

        public MainAdapter(Context context, ArrayList<AdItem> data) {

        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public AdItem getItem(int position) {
            if (position < 0) {
                position = 0;
            }
            AdItem item = arrayList.get(position);
            return item;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        MainAdapter.MainWrapper wrapper;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final AdItem data = getItem(position);
            convertView = data.isAD ? View.inflate(SampleJsonDataActivity.this, R.layout.adlist_ad_item, null)
                    : View.inflate(SampleJsonDataActivity.this, R.layout.adlist_item, null);
            wrapper = new MainAdapter.MainWrapper(convertView);

            if (!data.isAD) {
                wrapper.mTitle.setText(data.title);
                wrapper.mDescription.setText(data.desc);

                if (TextUtils.isEmpty(data.price))
                    wrapper.mPrice.setVisibility(View.GONE);
                else {
                    wrapper.mPrice.setText(data.price);
                    wrapper.mPrice.setVisibility(View.VISIBLE);
                }

                Glide.with(SampleJsonDataActivity.this).load(data.imgUrl).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(wrapper.mThumbNail);
            } else {
                Glide.with(SampleJsonDataActivity.this).load(data.imgUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(wrapper.mThumbNail);
            }
            //노출 카운트(##필수##)           
                mMobonSDK.onImpression(data.viewKey);

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    Uri u = Uri.parse(data.landingUrl);
                    i.setData(u);
                    startActivity(i);
                }
            });

            return convertView;

        }

        private class MainWrapper {

            public ImageView mThumbNail;
            public TextView mTitle;
            public TextView mDescription;
            public TextView mPrice;

            public MainWrapper(View v) {
                mThumbNail = (ImageView) v.findViewById(R.id.main_item_img);
                mTitle = (TextView) v.findViewById(R.id.main_item_title);
                mDescription = (TextView) v.findViewById(R.id.main_item_description);
                mPrice = (TextView) v.findViewById(R.id.main_item_price);
            }
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

    }

    private class AdItem {
        public String imgUrl;
        public String title;
        public String desc;
        public String landingUrl;
        public String price;
        public boolean isAD;
        public String viewKey;

        public AdItem(String adType, JSONObject objData) {
            try {
                viewKey = objData.optString("increaseViewKey"); //노출 증가 필수 키
                final String pcode = objData.optString("pcode");
                if (TextUtils.isEmpty(pcode)) // 비 타게팅 광고!!
                {
                    // 비 타게팅 광고는 여러가지 사이즈의 이미지가 넘어옵니다. mimg 로 시작되는 키값은 비어 있을 수
                    // 있습니다. img 키 값을 사용하기를 권장합니다.
                    // img, mimg_120_600,mimg_160_300, mimg_160_600,
                    // mimg_250_250,mimg_300_65, mimg_300_180, mimg_320_100,
                    // mimg_300_250,
                    // mimg_640_350,mimg_720_1230, mimg_728_90, mimg_850_800,
                    // mimg_800_1500, mimg_960_100
                    isAD = true;
                    title = objData.getString("pnm");
                    if (TextUtils.isEmpty(objData.getString("site_desc1")))
                        desc = objData.getString("desc");
                    else
                        desc = objData.getString("site_desc1");

                    title = objData.getString("pnm");
                    price = "";

                    String[] imgArray = {objData.optString("mimg_720_120"), objData.optString("mimg_728_90"), objData.optString("img")};
                    for (int i = 0; i < imgArray.length; i++) {
                        String ext = imgArray[i].substring(imgArray[i].lastIndexOf(".") + 1);
                        if (ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
                            imgUrl = imgArray[i];
                            break;
                        }
                    }
                } else {
                    // 타게팅 광고 시 img 한 사이즈의 이미지가 내려 받을 수 있으며 상품 이미지에 따라 사이즈가
                    // 가변하므로 이미지 영역을 정하고 처리해야 합니다.
                    isAD = false;
                    title = objData.getString("pnm");
                    desc = objData.getString("site_desc1");
                    price = objData.getString("price") + "원";
                    imgUrl = objData.getString("img");
                }

                landingUrl = objData.getString("purl");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
