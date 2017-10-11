package saiwei.com.river;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import saiwei.com.river.entity.RspCruisDetailBean;
import saiwei.com.river.entity.RspTousuDetailBean;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.DrivingRecordLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.River;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.view.MyListView;

/**
 *
 * 巡河详情界面
 *
 * Created by saiwei on 9/21/17.
 */

public class XunheDetailActivity extends Activity {

    @BindView(R.id.mapview)
    MapView mapView;

    private UiSettings mUiSettings;

    private AMap aMap;
    private Polyline polyline;

    @BindView(R.id.xunhe_detail_name)
    TextView mRriveName;

    @BindView(R.id.xunhe_detail_people)
    TextView mXunhePeople;

    @BindView(R.id.xunhe_detail_date)
    TextView mXunheDate;

    @BindView(R.id.xunhe_detail_totaltime)
    TextView mXunheTotalTime;

    @BindView(R.id.xunhe_detail_sroll)
    ScrollView scrollView;


    private ImageButton mTitleLeft;
    private TextView mTitleName;


    @BindView(R.id.xunhe_end_item_1)
    View view1;
    @BindView(R.id.xunhe_end_item_2)
    View view2;
    @BindView(R.id.xunhe_end_item_3)
    View view3;
    @BindView(R.id.xunhe_end_item_4)
    View view4;
    @BindView(R.id.xunhe_end_item_5)
    View view5;
    @BindView(R.id.xunhe_end_item_6)
    View view6;
    @BindView(R.id.xunhe_end_item_7)
    View view7;
    @BindView(R.id.xunhe_end_item_8)
    View view8;
    @BindView(R.id.xunhe_end_item_9)
    View view9;
    @BindView(R.id.xunhe_end_item_10)
    View view10;
    @BindView(R.id.xunhe_end_item_11)
    View view11;
    @BindView(R.id.xunhe_end_item_12)
    View view12;

    @BindView(R.id.xunhe_detail_report_result)
    TextView mTVReportResult;

    MyListView mReportListView;

    private MyAdapter mAdapter;

    TextView tv1_content;
    TextView tv2_content;
    TextView tv3_content;
    TextView tv4_content;
    TextView tv5_content;
    TextView tv6_content;
    TextView tv7_content;
    TextView tv8_content;
    TextView tv9_content;
    TextView tv10_content;
    TextView tv11_content;
    TextView tv12_content;

    TextView tv1_result;
    TextView tv2_result;
    TextView tv3_result;
    TextView tv4_result;
    TextView tv5_result;
    TextView tv6_result;
    TextView tv7_result;
    TextView tv8_result;
    TextView tv9_result;
    TextView tv10_result;
    TextView tv11_result;
    TextView tv12_result;

    String[] item1_array={"到位","不到位"};
    String[] item2_array={"无","有"};
    String[] item3_array={"无","有"};
    String[] item4_array={"否","是"};
    String[] item5_array={"否","是"};
    String[] item6_array={"否","是"};
    String[] item7_array={"否","是"};
    String[] item8_array={"否","是"};
    String[] item9_array={"否","是"};
    String[] item10_array={"否","是"};
    String[] item11_array={"否","是"};
    String[] item12_array={"按计划推进","暂无进展"};

    List<RspCruisDetailBean.ResponseDataBean.PublicReportListBean> mReportLists;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_xunhe_detail);
//        mapView = (MapView) findViewById(R.id.map);

        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initData();
        init();
        initListView();
    }

    private RspCruisDetailBean rspCruisDetailBean;
    private void initData(){
        rspCruisDetailBean = AccoutLogic.getInstance().getRspCruisDetailBeanCache();
//        mRriveName.setText(xunheRecord.getReportRiver());


        mReportLists = rspCruisDetailBean.getResponseData().getPublicReportList();

        String reportRiver = rspCruisDetailBean.getResponseData().getTourriver().getReportRiver();
        mRriveName.setText(reportRiver);

        String userid = rspCruisDetailBean.getResponseData().getTourriver().getUserId();
        String username = AccoutLogic.getInstance().getUserName(userid);
        mXunhePeople.setText(username);


        String tourTime = rspCruisDetailBean.getResponseData().getTourriver().getTourTime();

        mXunheDate.setText(tourTime);

        String totalTime = rspCruisDetailBean.getResponseData().getTourriver().getTotalTime();
        mXunheTotalTime.setText(totalTime);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {

        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("巡河详情");
        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setScaleControlsEnabled(true);
            setUpMap();
        }

        tv1_content = (TextView) view1.findViewById(R.id.xunhe_end_item_content);
        tv2_content = (TextView) view2.findViewById(R.id.xunhe_end_item_content);
        tv3_content = (TextView) view3.findViewById(R.id.xunhe_end_item_content);
        tv4_content = (TextView) view4.findViewById(R.id.xunhe_end_item_content);
        tv5_content = (TextView) view5.findViewById(R.id.xunhe_end_item_content);
        tv6_content = (TextView) view6.findViewById(R.id.xunhe_end_item_content);
        tv7_content = (TextView) view7.findViewById(R.id.xunhe_end_item_content);
        tv8_content = (TextView) view8.findViewById(R.id.xunhe_end_item_content);
        tv9_content = (TextView) view9.findViewById(R.id.xunhe_end_item_content);
        tv10_content = (TextView) view10.findViewById(R.id.xunhe_end_item_content);
        tv11_content = (TextView) view11.findViewById(R.id.xunhe_end_item_content);
        tv12_content = (TextView) view12.findViewById(R.id.xunhe_end_item_content);


        tv1_result = (TextView) view1.findViewById(R.id.xunhe_end_item_result);
        tv2_result = (TextView) view2.findViewById(R.id.xunhe_end_item_result);
        tv3_result = (TextView) view3.findViewById(R.id.xunhe_end_item_result);
        tv4_result = (TextView) view4.findViewById(R.id.xunhe_end_item_result);
        tv5_result = (TextView) view5.findViewById(R.id.xunhe_end_item_result);
        tv6_result = (TextView) view6.findViewById(R.id.xunhe_end_item_result);
        tv7_result = (TextView) view7.findViewById(R.id.xunhe_end_item_result);
        tv8_result = (TextView) view8.findViewById(R.id.xunhe_end_item_result);
        tv9_result = (TextView) view9.findViewById(R.id.xunhe_end_item_result);
        tv10_result = (TextView) view10.findViewById(R.id.xunhe_end_item_result);
        tv11_result = (TextView) view11.findViewById(R.id.xunhe_end_item_result);
        tv12_result = (TextView) view12.findViewById(R.id.xunhe_end_item_result);

        tv1_content.setText("1.河面、河岸保洁是否到位");
        tv2_content.setText("2.河底有无明显污泥或垃圾淤积");
        tv3_content.setText("3.河道水体有无异味，颜色是否异常（如发黑、发黄、发白等）");
        tv4_content.setText("4.是否有违法新增入河排污口");
        tv5_content.setText("5.入河排污口排放废水的颜色、气味是否异常，雨水排放口晴天有无污水排放");

        tv6_content.setText("6.汇入河流排污（水）口的工业企业、畜禽养殖场、污水处理设施、服务行业企业等是否存在明显异常排放情况");
        tv7_content.setText("7.是否存在涉水违建（构）筑物，是否存在倾倒废土弃渣、工业固废和危废，是否存在违章搭盖、擅自围垦、填堵河道或其他侵占河道的问题");
        tv8_content.setText("8.是否存在破坏涉水工程的行为");
        tv9_content.setText("9.是否存在非法电鱼、药鱼等破坏水生态环境的行为");
        tv10_content.setText("10.河长公示牌、水源地保护区提示牌等涉水告示牌是否存在倾斜、破损、变形、变色、老化等影响使用的问题");
        tv11_content.setText("11.是否存在其他影响水环境、河道水质的问题");
        tv12_content.setText("12.督查反馈问题整改情况");

        if(rspCruisDetailBean!=null){
            RspCruisDetailBean.ResponseDataBean.TourriverBean tourriverBean = rspCruisDetailBean.getResponseData().getTourriver();

            int result_1 = Integer.parseInt(tourriverBean.getIsHDBJ());
            int result_2 = Integer.parseInt(tourriverBean.getIsHDWN());
            int result_3 = Integer.parseInt(tourriverBean.getIsWSPK());
            int result_4 = Integer.parseInt(tourriverBean.getIsSSWF());
            int result_5 = Integer.parseInt(tourriverBean.getIsHALJ());
            int result_6 = Integer.parseInt(tourriverBean.getIsHDSZ());
            int result_7 = Integer.parseInt(tourriverBean.getIsRHWK());
            int result_8 = Integer.parseInt(tourriverBean.getIsPHSS());
            int result_9 = Integer.parseInt(tourriverBean.getIsFFBY());
            int result_10 = Integer.parseInt(tourriverBean.getIsHZTS());
            int result_11 = Integer.parseInt(tourriverBean.getIsYXSZ());
            int result_12 = Integer.parseInt(tourriverBean.getIsHDZZ());

            tv1_result.setText(item1_array[result_1-1]);
            tv2_result.setText(item2_array[result_2-1]);
            tv3_result.setText(item3_array[result_3-1]);
            tv4_result.setText(item4_array[result_4-1]);
            tv5_result.setText(item5_array[result_5-1]);
            tv6_result.setText(item6_array[result_6-1]);
            tv7_result.setText(item7_array[result_7-1]);
            tv8_result.setText(item8_array[result_8-1]);
            tv9_result.setText(item9_array[result_9-1]);
            tv10_result.setText(item10_array[result_10-1]);
            tv11_result.setText(item11_array[result_11-1]);
            tv12_result.setText(item12_array[result_12-1]);
        }
    }

    private void initListView(){
        mReportListView = (MyListView) findViewById(R.id.xunhe_detail_report_list);
        mAdapter = new MyAdapter(this);
        mReportListView.setAdapter(mAdapter);

        if(mReportLists!=null && mReportLists.size()>0){
            mTVReportResult.setVisibility(View.GONE);
        } else {
            mTVReportResult.setVisibility(View.VISIBLE);
        }

        mAdapter.setList(mReportLists);
    }

    @OnClick({R.id.title_btn_left})
    public void doBack(View v) {
        finish();
    }

    private void setUpMap() {

        //        coordinateX": "25.075158",
//        "coordinateY": "117.025956",
        aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(25.075158,117.025956)));

        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

//        ArrayList<LatLng> latLngs = DrivingRecordLogic.getInstance().readFromFile(xunheRecord.getRecordId()+"");

        List<RspCruisDetailBean.ResponseDataBean.TourcoordinateListBean>  points = rspCruisDetailBean.getResponseData().getTourcoordinateList();

//        double lat = Double.parseDouble(strs[1]);
//        double lon = Double.parseDouble(strs[2]);
//        LatLng latLng = new LatLng(lat,lon);
//
//        list.add(latLng);

        if(points != null){

            LatLng[] array =new LatLng[points.size()];
            for(int i=0;i<points.size();i++){
                RspCruisDetailBean.ResponseDataBean.TourcoordinateListBean item;
                item = points.get(i);
                LatLng latLng = new LatLng(
                        Double.parseDouble(item.getCoordinateX()),
                        Double.parseDouble(item.getCoordinateY()));

                array[i]= latLng;
            }

//            LatLng[] array =new LatLng[latLngs.size()];
//            latLngs.toArray(array);


//        public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
            if(array.length>1){
//            if(latLngs != null && latLngs.size()>1){
                // 绘制一个乌鲁木齐到哈尔滨的线
//            aMap.addPolyline((new PolylineOptions()).add(
//                    new LatLng(43.828, 87.621), new LatLng(45.808, 126.55)).color(
//                    Color.RED));

                aMap.addPolyline((new PolylineOptions()).add(array).color(
                        Color.RED));

                int index = array.length/2;

                LatLng centerLatlng = array[index];

                // 500 m 比例尺， 同时移到地图中心
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                centerLatlng, 14, 0, 30)), null);


                addMarkersToMap(array[0],array[array.length-1]);
            }
        } else {
            Toast.makeText(this,"无gps信息",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

//    private MarkerOptions markerOption;

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng start_poi,LatLng end_poi) {

        MarkerOptions start_markerOption , end_markerOption;
        start_markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(start_poi);

        end_markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(end_poi);

        aMap.addMarker(start_markerOption);
        aMap.addMarker(end_markerOption);

//        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(Constants.CHENGDU).title("成都市")
//                .snippet("成都市:30.679879, 104.064855").draggable(true));
    }


    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
//        boolean animated = ((CompoundButton) findViewById(R.id.animate))
//                .isChecked();
//        if (animated) {
//            aMap.animateCamera(update, 1000, callback);
//        } else {
//            aMap.moveCamera(update);
//        }

        aMap.moveCamera(update);

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

        AccoutLogic.getInstance().setRspCruisDetailBeanCache(null);
    }

    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspCruisDetailBean.ResponseDataBean.PublicReportListBean> mList = null;
        private Context mContext;
        int widgt,height;
        ImageSize targetSize;

        public MyAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            widgt = (int) getResources().getDimension(R.dimen.auto_dimen2_160);
            height = (int) getResources().getDimension(R.dimen.auto_dimen2_130);
            targetSize = new ImageSize(widgt, height);
        }

        public void setList(List<RspCruisDetailBean.ResponseDataBean.PublicReportListBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspCruisDetailBean.ResponseDataBean.PublicReportListBean> getList() {
            return mList;
        }

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {

            if (mList != null) {
                return mList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
//                convertView = mInflater.inflate(R.layout.search_list_item, null);
                convertView = mInflater.inflate(R.layout.xunhe_detail_report_list_item, null);
                holder = new ViewHolder();
                holder.report =  convertView.findViewById(R.id.xunhe_detail_report_item_report);
                holder.process =  convertView.findViewById(R.id.xunhe_detail_report_item_process);


                holder.reportcontent = (TextView) convertView.findViewById(R.id.xunhe_detail_report_item_reportcontent);

                holder.processcontent = (TextView) convertView.findViewById(R.id.xunhe_detail_report_item_processcontent);

                holder.reportimg = (ImageView) convertView.findViewById(R.id.xunhe_detail_report_item_reportimg);

                holder.processimg = (ImageView) convertView.findViewById(R.id.xunhe_detail_report_item_processimg);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String reportcontent = mList.get(position).getReportContent();
            String processcontent = mList.get(position).getProcessContent();




            if(TextUtils.isEmpty(reportcontent)){
                holder.report.setVisibility(View.GONE);
            } else {
                holder.report.setVisibility(View.VISIBLE);
                holder.reportcontent.setText(reportcontent);


                String img_url =  mList.get(position).getReportImg();

//            Log.d(TAG,"getView() img_url="+img_url);

                if(!TextUtils.isEmpty(img_url)){
                    String[] imgs = img_url.split(",");
                    if(imgs.length>0){
//                    holder.img

//                    String url = myUrls.getStr(position);

                        String url = RetrofitLogic.IMAGE_BASE_GET_URL+imgs[0].trim();

//                        Log.d(TAG,"getView() load img url ="+url);
                        ImageLoader.getInstance().displayImage(url,holder.reportimg,targetSize);
                    }
                }
            }

            if(TextUtils.isEmpty(processcontent)){
                holder.process.setVisibility(View.GONE);
            } else {
                holder.process.setVisibility(View.VISIBLE);
                holder.processcontent.setText(processcontent);


                String img_url =  mList.get(position).getProcessImg();

//            Log.d(TAG,"getView() img_url="+img_url);

                if(!TextUtils.isEmpty(img_url)){
                    String[] imgs = img_url.split(",");
                    if(imgs.length>0){
//                    holder.img

//                    String url = myUrls.getStr(position);

                        String url = RetrofitLogic.IMAGE_BASE_GET_URL+imgs[0].trim();

//                        Log.d(TAG,"getView() load img url ="+url);
                        ImageLoader.getInstance().displayImage(url,holder.processimg,targetSize);
                    }
                }
            }

            holder.reportcontent.setText(reportcontent);

            return convertView;
        }

        class ViewHolder {
            //            View container;
            View report;
            View process;

            TextView reportcontent;
            TextView processcontent;

            ImageView reportimg;
            ImageView processimg;
        }
    }
}
