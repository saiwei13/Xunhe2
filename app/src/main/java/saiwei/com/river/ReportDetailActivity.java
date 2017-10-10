package saiwei.com.river;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saiwei.com.river.entity.RspComplaintBean;
import saiwei.com.river.entity.RspComplaintDetailBean;
import saiwei.com.river.entity.RspComplaintHandleInfo;
import saiwei.com.river.entity.RspCruisDetailBean;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.view.MyListView;

/**
 *
 * 上报问题详情界面
 *
 * Created by saiwei on 9/21/17.
 */

public class ReportDetailActivity extends Activity {

    private static final String TAG = "chenwei.ReportDetailA";

    @BindView(R.id.tousu_detail_no)
    TextView mTousuNo;

    @BindView(R.id.tousu_detail_status)
    TextView mTousuStatus;

    @BindView(R.id.tousu_detail_river)
    TextView mTousuRiver;

    @BindView(R.id.tousu_detail_realaddr)
    TextView mTousuAddr;

    @BindView(R.id.tousu_detail_time)
    TextView mTousuTime;

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    MyListView mProcessListView;
    private MyAdapter myAdapter;
    List<RspComplaintHandleInfo.ResponseDataBean> mProcessInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_report_detail);
        ButterKnife.bind(this);

        init();
        initListView();
        initData();
    }

    private RspComplaintBean.ResponseDataBean.VarListBean rspComplaintDetailBean;
    private void initData(){
        rspComplaintDetailBean = AccoutLogic.getInstance().getRspComplainDetailBeanCache();
//        mRriveName.setText(xunheRecord.getReportRiver());

        String reportRiver = rspComplaintDetailBean.getReportRiver();
//        mRriveName.setText(reportRiver);
//        mXunhePeople.setText(rspComplaintDetailBean.getComplaintsType());
//        mXunheDate.setText(rspComplaintDetailBean.getBussinessNo());

//        publicReportBean = AccoutLogic.getInstance().getPublicReportBeanCache();
////        mRriveName.setText(xunheRecord.getReportRiver());
//        String reportRiver = publicReportBean.getReportRiver();
        mTousuNo.setText(rspComplaintDetailBean.getBussinessNo());
////        String status = AccoutLogic.getInstance().getTousuStatus(publicReportBean.getBusinessState());
        mTousuStatus.setText(rspComplaintDetailBean.getBusinessState());
        mTousuRiver.setText(rspComplaintDetailBean.getReportRiver());
        mTousuAddr.setText(rspComplaintDetailBean.getLocationAddress());
////        mTousuAddr.setText(publicReportBean.getRealAddress());
        mTousuTime.setText(rspComplaintDetailBean.getUpdateTime()+"");

        doGetComplaintHandleInfo(rspComplaintDetailBean.getProcessId());

    }
    /**
     * 初始化AMap对象
     */
    private void init() {
        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
//        mTitleLeft.setOnClickListener(this);

        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("上报详情");

        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

    }

    private void initListView(){

        mProcessListView = (MyListView) findViewById(R.id.tousu_detail_process_listview);
        myAdapter = new MyAdapter(this);
        mProcessListView.setAdapter(myAdapter);
    }

    @OnClick({R.id.title_btn_left})
    public void doBack(View v) {
        finish();
    }




    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        AccoutLogic.getInstance().setRspComplaintDetailBeanCache(null);
    }

    private void doGetComplaintHandleInfo(String processId){

        Call<RspComplaintHandleInfo> call = RetrofitLogic.getInstance().getService().doGetComplaintHandleInfo(
                processId
        );
        call.enqueue(new Callback<RspComplaintHandleInfo>() {

            @Override
            public void onResponse(Call<RspComplaintHandleInfo> call, Response<RspComplaintHandleInfo> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspComplaintHandleInfo bean = response.body();

                if(bean.getRtnCode().equals("000000")){
//                    Toast.makeText(TousuDetailActivity.this,"获取成功 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();


                    mProcessInfos = bean.getResponseData();

                    updateProcessListView();
                } else {

                }
            }

            @Override
            public void onFailure(Call<RspComplaintHandleInfo> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
//                Toast.makeText(TousuDetailActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProcessListView(){
        myAdapter.setList(mProcessInfos);
    }

    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspComplaintHandleInfo.ResponseDataBean> mList = null;
        private Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public void setList(List<RspComplaintHandleInfo.ResponseDataBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspComplaintHandleInfo.ResponseDataBean> getList() {
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

            MyAdapter.ViewHolder holder;

            if (convertView == null) {
//                convertView = mInflater.inflate(R.layout.search_list_item, null);
                convertView = mInflater.inflate(R.layout.process_list_item, null);
                holder = new MyAdapter.ViewHolder();
                holder.content = (TextView) convertView.findViewById(R.id.process_item_content);
                holder.time = (TextView) convertView.findViewById(R.id.process_item_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String content = mList.get(position).getProcess_Content();
            if (TextUtils.isEmpty(content)) {
                content = "曹溪街道dev_lytest001上报问题";
            }

            holder.content.setText(content);
            holder.time.setText(mList.get(position).getCreate_Time());
            return convertView;
        }

        class ViewHolder {
            //            View container;
            TextView content;
            TextView time;
            //            ImageView image;
            TextView look;
        }

    }



}
