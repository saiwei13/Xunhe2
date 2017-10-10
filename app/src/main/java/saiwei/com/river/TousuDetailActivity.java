package saiwei.com.river;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saiwei.com.river.entity.RspComplaintHandleInfo;
import saiwei.com.river.entity.RspTousuDetailBean;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.River;
import saiwei.com.river.util.DrivingRecordTool;
import saiwei.com.river.view.MyListView;

/**
 *
 * 投诉详情界面
 *
 * 显示3部分信息
 * 1. 基本信息
 * 2. 办事进度
 * 3. 处理事件
 *
 * Created by saiwei on 9/21/17.
 */

public class TousuDetailActivity extends Activity {

    private static final String TAG = "chenwei.TousuDetailA";

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

    @BindView(R.id.tousu_detail_renling)
    Button mBtTousuRenling;

    @BindView(R.id.tousu_detail_chongfu)
    Button mBtTousuChongfu;

    @BindView(R.id.tousu_detail_close)
    Button mBtTousuClose;

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    @BindView(R.id.tousu_detail_renling_select_view)
    View mRenLingSelectView;

    @BindView(R.id.tousu_detail_river_view)
    View mLayoutRiver;

    @BindView(R.id.tousu_detail_type_view)
    View mLayoutType;

    @BindView(R.id.feedback_river_name)
    TextView mTVRiverName;

    @BindView(R.id.feedback_type)
    TextView mTVType;

    @BindView(R.id.tousu_detail_content)
    TextView mTVContent;

    @BindView(R.id.tousu_detail_deal_layout)
    View mDealLayout;

//    @BindView(R.id.tousu_detail_process_listview)
    MyListView mProcessListView,mDealListView;

    List<River> mRivers;
//    private ListView mProcessListView ;
    private MyProcessAdapter myProcessAdapter;
    private MyDealAdapter myDealAdapter;

    private String DEFAULT_TIP="请点击选择";

    private int curQueryType = Constant.QUERY_TYPE_UNCOMPLETED;

    private String taskId,publicReportId;

//    List<RspComplaintHandleInfo.ResponseDataBean> mProcessInfos;
    List<RspTousuDetailBean.ResponseDataBean.FinishProcessBean> finishProcessList;
    List<RspTousuDetailBean.ResponseDataBean.HistoriclistBean> mProcessInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_tousu_detail);
        ButterKnife.bind(this);

        curQueryType = getIntent().getIntExtra(Constant.query_type,Constant.QUERY_TYPE_UNCOMPLETED);
        taskId = getIntent().getStringExtra(Constant.taskId);
        publicReportId = getIntent().getStringExtra(Constant.publicReportId);

        initData();
        init();
        initListView();
    }

    private RspTousuDetailBean.ResponseDataBean.PublicReportBean publicReportBean;
    private void initData(){

        RspTousuDetailBean bean = AccoutLogic.getInstance().getRspTousuDetailBeanCache();
        if(bean != null){
            publicReportBean = bean.getResponseData().getPublicReport();
            finishProcessList = bean.getResponseData().getFinishProcess();
            mProcessInfos = bean.getResponseData().getHistoriclist();
        }

//        publicReportBean = AccoutLogic.getInstance().getPublicReportBeanCache();

//        finishProcessList = AccoutLogic.getInstance().getFinishProcessListCache();

//        mRriveName.setText(xunheRecord.getReportRiver());
        String reportRiver = publicReportBean.getReportRiver();
        mTousuNo.setText(publicReportBean.getBussinessNo());
//        String status = AccoutLogic.getInstance().getTousuStatus(publicReportBean.getBusinessState());
        mTousuStatus.setText(publicReportBean.getBusinessState());
        mTousuRiver.setText(publicReportBean.getReportRiver());
        mTousuAddr.setText(publicReportBean.getLocationAddress());
//        mTousuAddr.setText(publicReportBean.getRealAddress());

        String time = publicReportBean.getUpdateTime();
//        time.split(" ")[0]

        mTousuTime.setText(time.split(" ")[0]);

        mRivers = AccoutLogic.getInstance().getRiverInfoFromDB();

        mTVRiverName.setText(DEFAULT_TIP);
        mTVType.setText(DEFAULT_TIP);

        mTVContent.setText(publicReportBean.getReportContent());
//        doGetComplaintHandleInfo(publicReportBean.getProcessId());
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
//        mTitleLeft.setOnClickListener(this);
        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("详情");
        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

        if(curQueryType == Constant.QUERY_TYPE_UNCOMPLETED){
            mBtTousuRenling.setVisibility(View.VISIBLE);
            mBtTousuChongfu.setVisibility(View.VISIBLE);
            mBtTousuClose.setVisibility(View.VISIBLE);
        } else {
            mBtTousuRenling.setVisibility(View.GONE);
            mBtTousuChongfu.setVisibility(View.GONE);
            mBtTousuClose.setVisibility(View.GONE);
        }
    }

    private void initListView(){

        mProcessListView = (MyListView) findViewById(R.id.tousu_detail_process_listview);
        myProcessAdapter = new MyProcessAdapter(this);
        mProcessListView.setAdapter(myProcessAdapter);
        myProcessAdapter.setList(mProcessInfos);


        mDealListView = (MyListView) findViewById(R.id.tousu_detail_deal_listview);
        myDealAdapter = new MyDealAdapter(this);
        mDealListView.setAdapter(myDealAdapter);

        if(finishProcessList!=null && finishProcessList.size()>0){
            myDealAdapter.setList(finishProcessList);
        } else{
            mDealLayout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.title_btn_left})
    public void doBack(View v) {
        finish();
    }


    @OnClick({R.id.tousu_detail_river_view})
    public void doSelectRiver(View v) {

        if (mRivers != null && mRivers.size() > 0) {
//            showSingleChoiceDialog(v);
            showRiverSingleChoiceDialog(null);
        } else {
            Toast.makeText(this,"没有绑定河道",Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.tousu_detail_type_view})
    public void doSelectType(View v) {
        showSingleChoiceDialog(null,mTVType.getText().toString());
    }

    @OnClick({R.id.tousu_detail_renling})
    public void doTousuRenling(View v) {

        if(mRenLingSelectView.getVisibility() == View.GONE){

            mRenLingSelectView.setVisibility(View.VISIBLE);
            Toast.makeText(this,"请先选择河道名称和类型",Toast.LENGTH_SHORT).show();
        } else {

            String river = mTVRiverName.getText().toString();
            String type = mTVType.getText().toString();
            if(river.equals(DEFAULT_TIP) || type.equals(DEFAULT_TIP)){
                Toast.makeText(this,"请先选择河道名称和类型",Toast.LENGTH_SHORT).show();
            } else {
                showRenlingTipDialog(null);
            }
        }
    }

    @OnClick({R.id.tousu_detail_chongfu})
    public void doTousuChongfu(View v) {
//        Toast.makeText(this,"开发中",Toast.LENGTH_SHORT).show();

        String reportid = publicReportBean.getPublicReportId();
        Intent intent =new Intent(TousuDetailActivity.this,TousuRepeatActivity.class);
        intent.putExtra(Constant.taskId,taskId);
        intent.putExtra(Constant.publicReportId,publicReportId);
        startActivity(intent);

    }

    @OnClick({R.id.tousu_detail_close})
    public void doTousuClose(View v) {

        String reportid = publicReportBean.getPublicReportId();
        Intent intent =new Intent(TousuDetailActivity.this,TousuCloseActivity.class);
        intent.putExtra(Constant.taskId,taskId);
        intent.putExtra(Constant.publicReportId,publicReportId);
        startActivity(intent);
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

        AccoutLogic.getInstance().setRspTousuDetailBeanCache(null);
    }


    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    private void showSingleChoiceDialog(View view,String select) {


        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("投诉类型");

        int positon = Arrays.binarySearch(Constant.type_array_str, select);

        builder.setSingleChoiceItems(Constant.type_array_str, positon, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                mTVType.setText(Constant.type_array_str[i]);
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    River mRiver ;

    private void showRiverSingleChoiceDialog(View view) {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("请选择河道");

        /**
         * 设置内容区域为单选列表项
         */
//        final String[] items={"Items_one","Items_two","Items_three"};


        final String[] items =  new String[mRivers.size()];

        for(int i=0;i<items.length;i++){
            items[i] = mRivers.get(i).getRiverName();
        }

        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                mRiver = mRivers.get(i);

                mTVRiverName.setText(mRiver.getRiverName());
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    /**
     * 显示无数据dialog
     * @param view
     */
    private void showRenlingTipDialog(View view) {

        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("提示");
        builder.setMessage("你确定认领该投诉件吗？\n 温馨提示：认领后市民将获得奖励金");

        //监听下方button点击事件
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String userid = AccoutLogic.getInstance().getUserId();
                String str = mTVType.getText().toString().trim();
//                int positon = Arrays.binarySearch(Constant.type_array_str, str);

                int positon = DrivingRecordTool.findPosition(str);

                Log.d(TAG,"str="+str+" , positon="+positon);

                doCommitRenling(userid,publicReportId,taskId,"",mRiver.getRiverBaseinfoId(),mRiver.getRiverName(),Constant.type_array_int[positon]);
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

    public  void doCommitRenling(String userId, String publicReportId, String taskId, String processImg, String riverId, String reportRiver, String complaintsType){

        Call<RspTousuDetailBean> call = RetrofitLogic.getInstance().getService().doClaimComplaint(
                userId,publicReportId,taskId,processImg,riverId,reportRiver,complaintsType
        );
        call.enqueue(new Callback<RspTousuDetailBean>() {

            @Override
            public void onResponse(Call<RspTousuDetailBean> call, Response<RspTousuDetailBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspTousuDetailBean bean = response.body();

                Log.d(TAG,"onResponse() bean.getRtnCode()="+bean.getRtnCode());

                if(bean.getRtnCode().equals("000000")){
                    Toast.makeText(TousuDetailActivity.this,"认领成功",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TousuDetailActivity.this,"提交失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RspTousuDetailBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(TousuDetailActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }






    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class MyProcessAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspTousuDetailBean.ResponseDataBean.HistoriclistBean> mList = null;
        private Context mContext;

        public MyProcessAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public void setList(List<RspTousuDetailBean.ResponseDataBean.HistoriclistBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspTousuDetailBean.ResponseDataBean.HistoriclistBean> getList() {
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
                convertView = mInflater.inflate(R.layout.process_list_item, null);
                holder = new ViewHolder();
                holder.description =  (TextView) convertView.findViewById(R.id.process_item_descriptionInfo);
                holder.content = (TextView) convertView.findViewById(R.id.process_item_content);
                holder.time = (TextView) convertView.findViewById(R.id.process_item_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String des= mList.get(position).getDescriptionInfo();
            if(TextUtils.isEmpty(des)){
                holder.description.setVisibility(View.GONE);
            } else {
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(des);
            }
            holder.time.setText(mList.get(position).getCreate_Time());
            String content_str = mList.get(position).getProcess_Content();

            if(TextUtils.isEmpty(content_str)){
                holder.content.setVisibility(View.GONE);
            } else {
                holder.content.setVisibility(View.VISIBLE);
                holder.content.setText(des);
            }

            return convertView;
        }

        class ViewHolder {
            //            View container;
            TextView description;
            TextView content;
            TextView time;
            //            ImageView image;
            TextView look;
        }
    }

    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class MyDealAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspTousuDetailBean.ResponseDataBean.FinishProcessBean> mList = null;
        private Context mContext;

        public MyDealAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public void setList(List<RspTousuDetailBean.ResponseDataBean.FinishProcessBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspTousuDetailBean.ResponseDataBean.FinishProcessBean> getList() {
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
                convertView = mInflater.inflate(R.layout.deal_list_item, null);
                holder = new ViewHolder();
                holder.name =  (TextView) convertView.findViewById(R.id.deal_item_people);
                holder.time = (TextView) convertView.findViewById(R.id.deal_item_time);
                holder.content = (TextView) convertView.findViewById(R.id.deal_item_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText("处理人: "+mList.get(position).getProcessMan());
            holder.time.setText("处理时间: "+mList.get(position).getProcessTime());
            holder.content.setText("处理内容: "+mList.get(position).getProcessContent());

            return convertView;
        }

        class ViewHolder {
            //            View container;
            TextView name;
            TextView time;
            TextView content;
        }
    }
}
