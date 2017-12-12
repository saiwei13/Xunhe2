package saiwei.com.river;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import saiwei.com.river.entity.RspCloseTousu;
import saiwei.com.river.entity.RspMonitorInfo;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.DrivingRecordLogic;
import saiwei.com.river.logic.LocationLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.River;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.service.WhiteService;
import saiwei.com.river.util.DrivingRecordTool;
import saiwei.com.river.util.SharePreferenceUtil;
import timber.log.Timber;

/**
 * Created by saiwei on 9/21/17.
 */

public class MapActivity extends Activity implements AMap.OnMyLocationChangeListener{

    private static final String TAG = "chenwei.MapActivity";

    @BindView(R.id.mapview)
    MapView mapView;

    private AMap aMap;
    private UiSettings mUiSettings;

    private MyLocationStyle myLocationStyle;
    @BindView(R.id.map_start)
    Button mBtStart;
    @BindView(R.id.map_stop)
    Button mBtStop;
    @BindView(R.id.map_report)
    Button mBtReport;

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    @BindView(R.id.map_xunhe_info)
    View mXunHeInfoLayout;

    @BindView(R.id.map_revier_name)
    TextView mTVRiverName;

    @BindView(R.id.map_timer)
    TextView mTimer;

    @BindView(R.id.map_gps_statuss)
    TextView mGpsStatus;

    private String last_filename;

    private String continue_reportRiverId;

    private List<LatLng> linepoints;

    private int mTimerSecond = 0;

    private Handler mHandler = new Handler() {
        /*
         * edit by yuanjingchao 2014-08-04 19:10
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mTimerSecond++;
                    String str = DrivingRecordTool.secToTime(mTimerSecond);
                    updateTimerView(str);
                    System.currentTimeMillis();
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    break;
                case 0:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"onCreate()");

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_map);
//        mapView = (MapView) findViewById(R.id.map);

        ButterKnife.bind(this);

        mapView.onCreate(savedInstanceState);// 此方法必须重写

        init();

//        LocationLogic.getInstance().startLocation();
        acquireWakeLock();

    }

    List<River> mRivers;

    @OnClick({R.id.map_start})
    public void doStart(View v) {

        Log.d(TAG, "doStart() ccc  islogin=" + AccoutLogic.getInstance().isLogin());


        if (!isOPen()) {
            Toast.makeText(this, "请先打开gps开关", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!AccoutLogic.getInstance().isLogin()) {
            Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
        } else {
            mRivers = AccoutLogic.getInstance().getRiverInfoFromDB();
            if (mRivers != null && mRivers.size() > 0) {
                showSingleChoiceDialog(v);
            } else {
                showNotDataDialog(v);
            }
        }
//        CameraUpdateFactory.
//        changeCamera(CameraUpdateFactory.zoomIn(), null);
//        changeCamera(CameraUpdateFactory.zoomTo(15), null);
    }

    public void doContitue(){
        if (!isOPen()) {
            Toast.makeText(this, "请先打开gps开关", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AccoutLogic.getInstance().isLogin()) {
            Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
        } else {
            River continueRiver = AccoutLogic.getInstance().getRiver(continue_reportRiverId);
            mRiverBaseinfoId = continueRiver.getRiverBaseinfoId();
            continueXunHe(continueRiver);
        }
    }

    /**
     * 开始巡河
     */
    private void startXunHe(River river) {

        if (river == null) {
            Toast.makeText(this, "startXunHe 异常", Toast.LENGTH_SHORT).show();
            return;
        }

        mRiverBaseinfoId = river.getRiverBaseinfoId();


        Toast.makeText(this, "开始巡河", Toast.LENGTH_SHORT).show();
        DrivingRecordLogic.getInstance().startRecord(river);

        mTVRiverName.setText(river.getRiverName());

        mXunHeInfoLayout.setVisibility(View.VISIBLE);
        mBtStart.setVisibility(View.INVISIBLE);
        mBtStop.setVisibility(View.VISIBLE);
        mBtReport.setVisibility(View.VISIBLE);

        mTimerSecond = 0;

        mHandler.sendEmptyMessageDelayed(1, 1000);

        if (aMap != null) {
            aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));
        }
        linepoints = new ArrayList<>();

        Intent whiteIntent = new Intent(getApplicationContext(), WhiteService.class);

        Bundle bundle = new Bundle();
        bundle.putString(Constant.XUNHE_STATUE,Constant.XUNHE_STATUE_START);

        whiteIntent.putExtras(bundle);
        startService(whiteIntent);
    }

    /**
     * 继续巡河
     */
    private void continueXunHe(River river) {

        Timber.d("continueXunHe()");

        if (river == null) {
            Toast.makeText(this, "continueXunHe 异常", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "继续巡河", Toast.LENGTH_SHORT).show();
//        DrivingRecordLogic.getInstance().startRecord(river);

        mTVRiverName.setText(river.getRiverName());

        mXunHeInfoLayout.setVisibility(View.VISIBLE);
        mBtStart.setVisibility(View.INVISIBLE);
        mBtStop.setVisibility(View.VISIBLE);
        mBtReport.setVisibility(View.VISIBLE);

        if (aMap != null) {
            aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));
        }
//        linepoints = new ArrayList<>();

        linepoints = DrivingRecordLogic.getInstance().readFromFile(last_filename);
        if(linepoints == null){
            linepoints = new ArrayList<>();
            mTimerSecond = 0;
        } else {

            Timber.d("continueXunHe() linepoints.size()=%d",linepoints.size());

            if(linepoints.size()>=2){
                mTimerSecond = DrivingRecordLogic.getInstance().getContinueTime();
            }
        }

        mHandler.sendEmptyMessageDelayed(1, 1000);

        Intent whiteIntent = new Intent(getApplicationContext(), WhiteService.class);

        Bundle bundle = new Bundle();
        bundle.putString(Constant.XUNHE_STATUE,Constant.XUNHE_STATUE_START);

        whiteIntent.putExtras(bundle);
        startService(whiteIntent);
    }

    @OnClick({R.id.map_report})
    public void doReport(View v) {
        Intent intent = new Intent(MapActivity.this,FeedBackActivity.class);
        intent.putExtra(AccoutLogic.FROM_ACTIVITY,AccoutLogic.FROM_MAPACTIVITY);
        startActivity(intent);
    }

    @OnClick({R.id.map_stop})
    public void doStop(View v) {

//        startActivity(new Intent(MapActivity.this, CompleteXunheActivity.class));


        XunheRecord xunheRecord = DrivingRecordLogic.getInstance().getCurXunheRecord();

        long record_id = xunheRecord.getRecordId();

        if (DrivingRecordLogic.getInstance().isExistFile(record_id)) {
            startActivity(new Intent(MapActivity.this, CompleteXunheActivity.class));
        } else {
            Toast.makeText(this, "没有巡河轨迹生成", Toast.LENGTH_SHORT).show();
            DrivingRecordLogic.getInstance().stopRecord();

            mXunHeInfoLayout.setVisibility(View.GONE);
            mBtStart.setVisibility(View.VISIBLE);
            mBtStop.setVisibility(View.GONE);
            mBtReport.setVisibility(View.GONE);
            mHandler.removeMessages(1);

            Intent whiteIntent = new Intent(getApplicationContext(), WhiteService.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.XUNHE_STATUE,Constant.XUNHE_STATUE_END);
            whiteIntent.putExtras(bundle);
//            startService(whiteIntent);
            stopService(whiteIntent);
        }
    }

    @OnClick({R.id.title_btn_left})
    public void doBack(View v) {
        showSimpleDialog(v);
    }

    String userid ="";
    String mRiverBaseinfoId;

    /**
     * 初始化
     */
    private void init(){

        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
//        mTitleLeft.setOnClickListener(this);

        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("我要巡河");

        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
//            mUiSettings.setZoomControlsEnabled(true);
            mUiSettings.setScaleControlsEnabled(true);
            setUpMap();
        }

//        aMap.setLocationSource();
        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this);

        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW));
        mBtStart.setVisibility(View.VISIBLE);
        mBtStop.setVisibility(View.GONE);


        last_filename  = SharePreferenceUtil.getInstance().getStr(SharePreferenceUtil.SHARE_PREFERENCE_LASTXUNHE);

        if(!TextUtils.isEmpty(last_filename)){
            showContinueXunheDialog(null);


            String jsonstr = SharePreferenceUtil.getInstance().getStr(SharePreferenceUtil.SHARE_PREFERENCE_LASTXUNHE_RECORD);

            if(!TextUtils.isEmpty(jsonstr)){
                try {
                    JSONObject jsonObject = new JSONObject(jsonstr);

                    XunheRecord curXunheRecord = new XunheRecord();
                    curXunheRecord.setRecordId((Long) jsonObject.get("recordId"));

                    userid = (String) jsonObject.get("userid");

                    curXunheRecord.setUserid(userid);
                    curXunheRecord.setReportRiver((String) jsonObject.get("reportRiver"));

                    continue_reportRiverId = (String) jsonObject.get("reportRiverId");

                    curXunheRecord.setReportRiverId(continue_reportRiverId);
                    curXunheRecord.setTourTime((String) jsonObject.get("tourTime"));

                    DrivingRecordLogic.getInstance().setCurXunheRecord(curXunheRecord);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {

        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可

        changeCamera(CameraUpdateFactory.zoomTo(16), null);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

        if(myLocationStyle!=null){
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        aMap.moveCamera(update);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

        Log.d(TAG,"onDestroy()");

//        if(mService != null){
//            mService.removeGpsStatusListener(this);
//        }

//        if(DrivingRecordLogic.getInstance().isRecord()){
//            DrivingRecordLogic.getInstance().stopRecord();
//        }
//        LocationLogic.getInstance().stopLocation();


        releaseWakeLock();
    }

    int i = 0;

    long lastgpstime ;

    @Override
    public void onMyLocationChange(Location location) {

        // 定位回调监听
        if(location != null) {
//            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if(bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);

                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType );

                if(errorCode == 0){
                    if(locationType == 1){

                        String yuanshi_gps = location.getTime()+","+location.getLatitude()+", "+location.getLongitude()+","+location.getAltitude()+","+location.getBearing()+" , "+location.getAccuracy()+" ,"+location.getSpeed()+","+location.getProvider();

                        Log.d(TAG,yuanshi_gps);

                        DrivingRecordLogic.getInstance().writeYuanshiGpsLog(yuanshi_gps);

                        updateGspStatusView(true);

                        if(location.getAccuracy()>80 && location.getAccuracy()<1){
                            return;
                        }

                        if(lastgpstime>0){
                            if(location.getTime() == lastgpstime){
                                return;
                            } else if(location.getTime() - lastgpstime<2000){
                                lastgpstime = location.getTime();
                                return ;
                            } else{
                                lastgpstime = location.getTime();
                            }
                        }

                        if(DrivingRecordLogic.getInstance().isRecord()){

                            if(i== 0  || i==5){
                                //保存
                                long time = location.getTime();
                                double lat = location.getLatitude();
                                double lon = location.getLongitude();

                                String message = time+" ,"+lat +" ,"+lon+" ,"+locationType;
                                DrivingRecordLogic.getInstance().writeGpsLog(message);

                                LatLng latLng = new LatLng(lat,lon);
                                drawLine(latLng);

                                doMonitor(userid,lon+"",lat+"",mRiverBaseinfoId);
                            }
                            i++;
                            if(i>5){
                                i=1;
                            }
                        }

                        if(lastgpstime == 0){
                            lastgpstime = location.getTime();
                        }
                    } else {
                        long curTime = System.currentTimeMillis();
                        if(curTime-lastgpstime > 10000){
                            updateGspStatusView(false);
                        }
                    }
                }
            } else {
                updateGspStatusView(false);
                Log.e("amap", "定位信息， bundle is null ");
            }
        } else {
            updateGspStatusView(false);
            Log.e("amap", "定位失败");
        }
    }

    private void updateGspStatusView(boolean isGspSuccess){

        if(isGspSuccess){
            mGpsStatus.setText("GPS 已定位");
        } else {
            mGpsStatus.setText("GPS未定位，请避开高楼大厦");
        }
    }

    private void drawLine(LatLng latLng){

        if(linepoints!=null && latLng!=null){
            linepoints.add(latLng);
            if(linepoints.size()>1){
                LatLng[] array =new LatLng[linepoints.size()];
                linepoints.toArray(array);
                aMap.addPolyline((new PolylineOptions()).add(array).color(
                        Color.RED));
            }
        }
    }

    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    //显示基本Dialog
    private void showSimpleDialog(View view) {

        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("提示");
        builder.setMessage("是否要退出巡河界面？");

        //监听下方button点击事件
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(DrivingRecordLogic.getInstance().isRecord()){
                    Toast.makeText(getApplication(),"请先结束巡河任务！",Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    /**
     * 显示无数据dialog
     * @param view
     */
    private void showNotDataDialog(View view) {

        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("提示");
        builder.setMessage("您尚未挂钩巡河河道，请联系乡镇河长办处理！");

        //监听下方button点击事件
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(DrivingRecordLogic.getInstance().isRecord()){
                    Toast.makeText(getApplication(),"请先结束巡河任务！",Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    /**
     * 检测到上次有未完成的巡河记录 dialog
     * @param view
     */
    private void showContinueXunheDialog(View view) {

        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("提示");
        builder.setMessage("检测到上次有未完成的巡河记录，请选择操作！");

        //监听下方button点击事件
        builder.setPositiveButton("结束上次巡河任务", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                doStop(null);
            }
        });

        builder.setNegativeButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DrivingRecordLogic.getInstance().continueRecord(Long.parseLong(last_filename));

                doContitue();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }


    private void showSingleChoiceDialog(View view) {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("请选择河道");

        /**
         * 设置内容区域为单选列表项
         */

        final String[] items =  new String[mRivers.size()];

        for(int i=0;i<items.length;i++){
            items[i] = mRivers.get(i).getRiverName();
        }

        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                River river = mRivers.get(i);
                startXunHe(river);
//
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        doBack(null);
    }

    public  boolean isOPen() {
        LocationManager locationManager
                = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d(TAG,"isOPen()  gps = "+gps);

        if (gps) {
            return true;
        }

        return false;
    }

    private void updateTimerView(String str){
        mTimer.setText(str);
    }

    private PowerManager.WakeLock wakeLock = null;
    /** * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行 */

    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, getClass()
                    .getCanonicalName());
            if (null != wakeLock) {
//                Log.i(TAG, “call acquireWakeLock”);
                wakeLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
        if (null != wakeLock && wakeLock.isHeld()) {
//            Log.i(TAG, “call releaseWakeLock”);
            wakeLock.release();
            wakeLock = null;
        }
    }

    public  void doMonitor(String userId, String longitude, String latitude, String riverBaseinfoId){

        Call<RspMonitorInfo> call = RetrofitLogic.getInstance().getService().doMonitorInfo(
                userId, longitude, latitude, riverBaseinfoId
        );
        call.enqueue(new Callback<RspMonitorInfo>() {

            @Override
            public void onResponse(Call<RspMonitorInfo> call, Response<RspMonitorInfo> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspMonitorInfo bean = response.body();
                Log.d(TAG,"onResponse() bean.getRtnCode()="+bean.getRtnCode());

//                if(bean.getRtnCode().equals("000000")){
//                    Toast.makeText(TousuCloseActivity.this,"关闭投诉成功",Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(TousuCloseActivity.this,"提交失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<RspMonitorInfo> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
//                Toast.makeText(TousuCloseActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

}




