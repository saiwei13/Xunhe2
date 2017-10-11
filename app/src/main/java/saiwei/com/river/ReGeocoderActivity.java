package saiwei.com.river;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import saiwei.com.river.util.AMapUtil;
import saiwei.com.river.util.ToastUtil;

/**
 * 地理编码与逆地理编码功能介绍
 *
 * 1. 定位
 * 2. 获取地图中心逆地理信息
 */
public class ReGeocoderActivity extends Activity implements
        OnGeocodeSearchListener, AMap.OnCameraChangeListener {

	private static final String TAG = "chenwei.ReGeocoderA";
	private ProgressDialog progDialog = null;
	private GeocodeSearch geocoderSearch;
	private String addressName;
	private AMap aMap;
	private UiSettings mUiSettings;
	private MapView mapView;
	private LatLonPoint latLonPoint = new LatLonPoint(39.90865, 116.39751);
//	private Marker regeoMarker;
	private ExecutorService mExecutorService;

	@BindView(R.id.geomap_addr)
	TextView mTVAddr;

	private ImageButton mTitleLeft;
	private TextView mTitleName;

	@BindView(R.id.title_comm_btn_right)
	Button mBtSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
		setContentView(R.layout.geocoder_activity);

		ButterKnife.bind(this);
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
	    //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
//        MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
//        mTitleLeft.setOnClickListener(this);

		mTitleName = (TextView) findViewById(R.id.title_text_name);
		mTitleName.setText("获取位置");

		findViewById(R.id.title_btn_right).setVisibility(View.GONE);
		mBtSave.setVisibility(View.VISIBLE);
		mBtSave.setText("保存");

		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
			aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
			aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
			aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
			MyLocationStyle myLocationStyle;
			myLocationStyle = new MyLocationStyle();
			myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
			aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
			mUiSettings.setZoomControlsEnabled(false);

			aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
		}
		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);
		progDialog = new ProgressDialog(this);
	}

	@OnClick(R.id.title_comm_btn_right)
	public void doSave(View view){

		if(curLat<1 || curLon < 1){

			Toast.makeText(this,"获取失败，请稍等",Toast.LENGTH_SHORT).show();
			return;
		}

		Intent intent = new Intent();
		intent.putExtra(Constant.latitude,curLat+"");
		intent.putExtra(Constant.longitude,curLon+"");
		intent.putExtra(Constant.locationAddress,curAddr);
		setResult(RESULT_OK,intent);
		finish();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
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
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		if (mExecutorService != null){
			mExecutorService.shutdownNow();
		}
	}

	@OnClick({R.id.title_btn_left})
	public void doBack(View v) {
		finish();
	}

	/**
	 * 响应逆地理编码
	 */
	public void getAddress(final LatLonPoint latLonPoint) {
//		showDialog();
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
	}

	/**
	 * 地理编码查询回调
	 */
	@Override
	public void onGeocodeSearched(GeocodeResult result, int rCode) {
	}

	double curLat,curLon;
	String curAddr;

	/**
	 * 逆地理编码回调
	 */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
//		dismissDialog();
		if (rCode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getRegeocodeAddress() != null
					&& result.getRegeocodeAddress().getFormatAddress() != null) {

				curLat = result.getRegeocodeQuery().getPoint().getLatitude();
				curLon = result.getRegeocodeQuery().getPoint().getLongitude();

				Log.d(TAG,"onRegeocodeSearched() curLat="+curLat+" , "+curLon);

				Log.d(TAG,""+result.getRegeocodeAddress().getProvince());
				Log.d(TAG,""+result.getRegeocodeAddress().getCity());
				Log.d(TAG,""+result.getRegeocodeAddress().getDistrict());
				Log.d(TAG,""+result.getRegeocodeAddress().getBuilding());
				Log.d(TAG,""+result.getRegeocodeAddress().getNeighborhood());
				Log.d(TAG,""+result.getRegeocodeAddress().getTowncode());

				String province = result.getRegeocodeAddress().getProvince();
				String city = result.getRegeocodeAddress().getCity();

				addressName = result.getRegeocodeAddress().getFormatAddress()
						+ "附近";

				addressName = addressName.replace(province,"").replace(city,"");

				curAddr = addressName;
				mTVAddr.setText(addressName);
			} else {
				ToastUtil.show(ReGeocoderActivity.this, "对不起，没有搜索到相关数据！");

				mTVAddr.setText("未知道路");
			}
		} else {
			ToastUtil.showerror(this, rCode);
		}
	}

	@Override
	public void onCameraChange(CameraPosition cameraPosition) {
//		Log.d(TAG,"onCameraChange() :" + cameraPosition.toString());
	}

	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition) {

		if(cameraPosition!=null){

			latLonPoint.setLatitude(cameraPosition.target.latitude);
			latLonPoint.setLongitude(cameraPosition.target.longitude);
			getAddress(latLonPoint);
		}
		Log.d(TAG,"onCameraChangeFinish():" + cameraPosition.toString());
	}
}