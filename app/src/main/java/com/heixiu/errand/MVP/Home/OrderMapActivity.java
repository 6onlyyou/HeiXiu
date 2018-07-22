package com.heixiu.errand.MVP.Home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.fushuaige.common.utils.ToastUtils;
import com.heixiu.errand.DrivingRouteOverlay;
import com.heixiu.errand.MyApplication.MyApplication;
import com.heixiu.errand.OverlayManager;
import com.heixiu.errand.R;
import com.heixiu.errand.bean.OrderInfo;

import java.util.ArrayList;
import java.util.List;

public class OrderMapActivity extends AppCompatActivity {

    private static final String TAG = "OrderMapActivity";
    private static final String[] authBaseArr = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    RouteLine route = null;
    OverlayManager routeOverlay = null;
    LocationClient mLocationClient;
    String city = "";
    String addr;
    ArrayAdapter sugAdapter;
    ArrayList<String> addressData = new ArrayList<>();
    SuggestionSearch mSuggestionSearch;
    List<Poi> poiList;
    boolean isLocation = false;
    private OrderInfo orderInfo;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private AutoCompleteTextView address;
    private int clickPosition;

    public static void startSelf(Context context, OrderInfo orderInfo) {
        Intent intent = new Intent(context, OrderMapActivity.class);
        intent.putExtra("data", orderInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(MyApplication.getInstance());
        setContentView(R.layout.activity_order_map);

        orderInfo = ((OrderInfo) getIntent().getSerializableExtra("data"));

        mMapView = (MapView) findViewById(R.id.mmap);
        findViewById(R.id.start_navigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNavi();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        address = ((AutoCompleteTextView) findViewById(R.id.address));
        addressData.add(orderInfo.getReceiveAddress());
        sugAdapter = new ArrayAdapter<>(this, R.layout.item_auto_text, R.id.address_name, addressData);
        address.setAdapter(sugAdapter);
        address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickPosition = position;
                startNavi();
            }
        });
        address.setThreshold(1);
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    searchAddress();
                }
            }
        });


        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setIsNeedLocationPoiList(true);
        option.setIsNeedAddress(true);
        option.setLocationNotify(true);
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(true);
        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）

        mLocationClient = new LocationClient(getApplicationContext());
        MyLocationListener bdAbstractLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(bdAbstractLocationListener);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        iniSearch();
    }

    private void searchAddress() {
        if (TextUtils.isEmpty(addr)) {
            ToastUtils.showShort("暂未获取到您的位置");
            return;
        }

        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption().keyword(address.getText().toString()).city(MyApplication.getInstance().city));
    }

    private void iniSearch() {
        mSuggestionSearch = SuggestionSearch.newInstance();

        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                if (suggestionResult.getAllSuggestions() == null) {
                    Log.i("result: ", "未找到相关结果\n");
                } else {
                    //获取在线建议检索结果
                    addressData.clear();
                    for (SuggestionResult.SuggestionInfo suggestionInfo : suggestionResult.getAllSuggestions()) {
                        addressData.add(suggestionInfo.key);
                    }
                    sugAdapter = new ArrayAdapter<>(OrderMapActivity.this, R.layout.item_auto_text, R.id.address_name, addressData);
                    address.setAdapter(sugAdapter);
                    sugAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void startNavi() {
        if (TextUtils.isEmpty(addr)) {
            ToastUtils.showShort("暂未获取到您的位置");
            return;
        }
        RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                Log.i(TAG, "onGetWalkingRouteResult: ");
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                Log.i(TAG, "onGetTransitRouteResult: ");
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
                Log.i(TAG, "onGetMassTransitRouteResult: ");
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult result) {

                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(OrderMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    result.getSuggestAddrInfo();
                    return;
                }
                try {
                    if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                        if (result.getRouteLines().size() >= 1) {
                            route = result.getRouteLines().get(0);
                            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
                            if (routeOverlay != null) {
                                routeOverlay.removeFromMap();
                            }
                            routeOverlay = overlay;
                            mBaiduMap.setOnMarkerClickListener(overlay);
                            overlay.setData(result.getRouteLines().get(0));
                            overlay.addToMap();
                            overlay.zoomToSpan();
                        } else {
                            Log.d("route result", "结果数<0");
                            return;
                        }
                    }
                } catch (Exception e) {
                    Log.i(TAG, "onGetDrivingRouteResult: " + e.getMessage());
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
                Log.i(TAG, "onGetIndoorRouteResult: ");
            }

            public void onGetBikingRouteResult(BikingRouteResult result) {
                //获取驾车线路规划结果
                Log.i(TAG, "onGetBikingRouteResult: ");
            }
        };
        mSearch.setOnGetRoutePlanResultListener(listener);
        if (poiList == null && poiList.size() == 0) {
            ToastUtils.showLong("暂时无法获取重点位置导航");
            return;
        }
        Log.i(TAG, "startNavi: " + poiList.get(0).getName());
        Log.i(TAG, "startNavi: " + addressData.get(clickPosition));
        PlanNode stNode = PlanNode.withCityNameAndPlaceName(MyApplication.getInstance().city, poiList.get(0).getName());
        PlanNode enNode = PlanNode.withCityNameAndPlaceName(MyApplication.getInstance().city, addressData.get(clickPosition));
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }

    }

    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            if (location.getPoiList() != null)
                Log.i(TAG, "onReceiveLocation: 定位结果地点个数" + location.getPoiList().size());
            // 造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();

            if (isLocation) {
                mBaiduMap.setMyLocationData(locData);
                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_location);
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);

                mBaiduMap.setMyLocationConfiguration(config);
                isLocation = true;
            }


            addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            Address address = location.getAddress();
            String buildingName = location.getBuildingName();
            poiList = location.getPoiList();
        }
    }
}

