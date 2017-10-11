package saiwei.com.river;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saiwei.com.river.entity.RspTousuBean;
import saiwei.com.river.entity.RspTousuDetailBean;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.XunheRecord;

/**
 * Created by saiwei on 9/21/17.
 * 投诉处理界面
 */

public class TousuDealActivity extends Activity {

    private static final String TAG = "chenwei.TousuDealAc";

    /**
     * 首页的View
     */
    private RelativeLayout mWantedView;
    private ImageView naviImageView;
    private TextView naviText;
    /**
     * 投诉处理的view
     */
    private RelativeLayout mSearchView;
    private ImageView searchImageView;
    private TextView searchText;

    /**
     * 个人中心的view
     */
    private RelativeLayout mRentView;
    private ImageView collectionImageView;
    private TextView collectionText;


    private ListView mXunHeListView,mReportListView ;
    private XunHeAdapter mXunHeAdapter;
    private ReportAdapter mReportAdapter;

    private List<XunheRecord> records ;

    @BindView(R.id.want_bank_view)
    RelativeLayout mBankView;

    private final int TAB_XUNHE = 0;
    private final int TAB_REPORT = 1;
    private int mCurTab = TAB_XUNHE;

    /** 出租按钮 */
//    private RelativeLayout mRentTabRl;
    private TextView mRentTabTv;
    private View mRentTabBotLine;

    /** 求租按钮 */
//    private RelativeLayout mWantedTabRl;
    private TextView mWantedTabTv;
    private View mWantedTabBotLine;

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    private int total_uncompleted,total_completed ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_tousu_deal);

        ButterKnife.bind(this);

        initBottomView();
        initView();
        initTabView();
        initData();
    }


    private void initTabView(){
        mRentTabTv=(TextView)findViewById(R.id.personpark_rent_tab_tv);
        mRentTabTv.setText("已处理问题");
        mRentTabBotLine=(View)findViewById(R.id.personpark_rent_tab_bot_line);

        mWantedTabTv=(TextView)findViewById(R.id.personpark_wanted_tab_tv);
        mWantedTabTv.setText("未处理问题");
        mWantedTabBotLine=(View)findViewById(R.id.personpark_wanted_tab_bot_line);

        onClickXunHeTab(null);

    }

    @OnClick(R.id.usercenter_xunhe_tab)
    public void onClickXunHeTab(View view){
        switchTab(TAB_XUNHE);
    }

    @OnClick(R.id.usercenter_report_tab)
    public void onClickReportTab(View view){
        switchTab(TAB_REPORT);
    }


    private void initView(){

        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
//        mTitleLeft.setOnClickListener(this);

        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("投诉处理");
        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

        mXunHeListView = (ListView) findViewById(R.id.usercenter_xunhe_list);
        mXunHeAdapter = new XunHeAdapter(this);
        mXunHeListView.setAdapter(mXunHeAdapter);
        mXunHeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String publicReportId = mXunhelists.get(i).getPublic_Report_ID();
                String taskId = mXunhelists.get(i).getTask_id();
                doGetTousuDetail(publicReportId,taskId,"uncompleted");

            }
        });

        mReportListView = (ListView) findViewById(R.id.usercenter_report_list);
        mReportAdapter = new ReportAdapter(this);
        mReportListView.setAdapter(mReportAdapter);
        mReportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String publicReportId = mReportLists.get(i).getPublic_Report_ID();
                String taskId = mReportLists.get(i).getTask_id();
                doGetTousuDetail(publicReportId,taskId,"completed");
            }
        });
        initListView();
    }


    private XRefreshView mXunheRefreshView,mReportRefreshView;
    public static long lastRefreshTime;

    private void initListView(){

        //巡河下拉列表
        mXunheRefreshView = (XRefreshView) findViewById(R.id.usercenter_xunhe_refreshview);
        // 设置是否可以下拉刷新
        mXunheRefreshView.setPullRefreshEnable(false);
        // 设置是否可以上拉加载
        mXunheRefreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        mXunheRefreshView.restoreLastRefreshTime(lastRefreshTime);
        //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
        mXunheRefreshView.setMoveHeadWhenDisablePullRefresh(true);
        // 设置时候可以自动刷新
        mXunheRefreshView.setAutoRefresh(false);

        mXunheRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                Log.d(TAG,"onRefresh()");
            }

            @Override
            public void onLoadMore(boolean isSilence) {

                Log.d(TAG,"onLoadMore()");
//                doGetComplaints(userId,"","uncompleted",curXunHePage+"","10");

                if(mXunhelists!=null){
                    if(mXunhelists.size()>= total_uncompleted){
                        Log.d(TAG,"setPullLoadEnable(false)");
                        mXunheRefreshView.setLoadComplete(true);
                        doGetComplaints(userId,"","uncompleted",curXunHePage+"","10");
                    } else {
                        Log.d(TAG,"setPullLoadEnable(true)");
                        mXunheRefreshView.setLoadComplete(false);
                        doGetComplaints(userId,"","uncompleted",curXunHePage+"","10");
                    }
                }
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);

                Log.d(TAG,"onRelease()  direction ="+direction);
            }
        });



        //上报问题刷新列表
        mReportRefreshView = (XRefreshView) findViewById(R.id.usercenter_report_refreshview);
        // 设置是否可以下拉刷新
        mReportRefreshView.setPullRefreshEnable(false);
        // 设置是否可以上拉加载
        mReportRefreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        mReportRefreshView.restoreLastRefreshTime(lastRefreshTime);
        //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
        mReportRefreshView.setMoveHeadWhenDisablePullRefresh(true);
        // 设置时候可以自动刷新
        mReportRefreshView.setAutoRefresh(false);
        mReportRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                Log.d(TAG,"onRefresh()");
            }

            @Override
            public void onLoadMore(boolean isSilence) {

                Log.d(TAG,"onLoadMore()");
                doGetComplaints(userId,"","completed",curReportPage+"","10");


                if(mReportLists!=null){
                    if(mReportLists.size()>= total_completed){
                        Log.d(TAG,"setPullLoadEnable(false)");
                        mReportRefreshView.setLoadComplete(true);
                        doGetComplaints(userId,"","uncompleted",curXunHePage+"","10");
                    } else {
                        Log.d(TAG,"setPullLoadEnable(true)");
                        mReportRefreshView.setLoadComplete(false);
                        doGetComplaints(userId,"","uncompleted",curXunHePage+"","10");
                    }
                }
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);

                Log.d(TAG,"onRelease()  direction ="+direction);
            }
        });

    }


    List<RspTousuBean.ResponseDataBean.DataListBean> mXunhelists;
    List<RspTousuBean.ResponseDataBean.DataListBean> mReportLists;
    int curXunHePage = 1;
    int curReportPage = 1;
    String userId;

    private void initData(){
        userId = AccoutLogic.getInstance().getUserId();

        mXunhelists = new ArrayList<>();
        mReportLists = new ArrayList<>();

//        String userId ="8490f2aa96dd4f72929a97e64031e53e";

//        doGetCruise(userId,startDate,endDate, curXunHePage +"");
        doGetComplaints(userId,"","uncompleted",curXunHePage+"","10");

        doGetComplaints(userId,"","completed",curReportPage+"","10");

    }

    private void initBottomView(){
        mWantedView = (RelativeLayout) findViewById(R.id.main_index);
//        mWantedView.setOnClickListener(this);
        naviImageView = (ImageView) mWantedView.findViewById(R.id.imageView_icon);
        naviImageView.setBackgroundResource(R.drawable.bottom_index);  //ic_start_on
        naviText = (TextView) mWantedView.findViewById(R.id.tv_content);
        naviText.setText("首页");

        mRentView = (RelativeLayout) findViewById(R.id.main_complain);
//        mRentView.setOnClickListener(this);
        collectionImageView = (ImageView) mRentView.findViewById(R.id.imageView_icon);
        collectionImageView.setBackgroundResource(R.drawable.bottom_tousu);
        collectionText = (TextView) mRentView.findViewById(R.id.tv_content);
        collectionText.setText("投诉处理");

        mSearchView = (RelativeLayout) findViewById(R.id.main_personcenter);
//        mSearchView.setOnClickListener(this);
        searchImageView = (ImageView) mSearchView.findViewById(R.id.imageView_icon);
        searchImageView.setBackgroundResource(R.drawable.bottom_user);
        searchText = (TextView) mSearchView.findViewById(R.id.tv_content);
        searchText.setText("个人中心");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRentView.setPressed(true);
        mRentView.setClickable(false);
    }

    @OnClick(R.id.bank_content_do_req)
    public void doReqRecord(){
//        doGetCruiseDefault(userId);
    }

    @OnClick({R.id.title_btn_left})
    public void doBack(View v) {
        finish();
    }

    @OnClick(R.id.main_index)
    public void doIndexBottom(View v){
        Intent intent =new Intent(TousuDealActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_complain)
    public void doTousuBottom(View v){

    }

    @OnClick(R.id.main_personcenter)
    public void doUser(View v){
        if(AccoutLogic.getInstance().isLogin()){
            Intent intent =new Intent(TousuDealActivity.this,UserCenterActivity.class);
            startActivity(intent);
        } else {
            Intent intent =new Intent(TousuDealActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }


    private void doGetTousuDetail(String publicReportId, String taskId, final String queryType){

        Log.d(TAG,"doGetTousuDetail() publicReportId="+publicReportId+" , taskId="+taskId);

        Call<RspTousuDetailBean> call = RetrofitLogic.getInstance().getService().doGetTousuDetail(
                publicReportId,taskId
        );
        call.enqueue(new Callback<RspTousuDetailBean>() {

            @Override
            public void onResponse(Call<RspTousuDetailBean> call, Response<RspTousuDetailBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspTousuDetailBean bean = response.body();

                if(bean.getRtnCode().equals("000000")){

                    Intent intent = new Intent(TousuDealActivity.this,TousuDetailActivity.class);
                    RspTousuDetailBean.ResponseDataBean.PublicReportBean publicReportBean =  bean.getResponseData().getPublicReport();


                    AccoutLogic.getInstance().setRspTousuDetailBeanCache(bean);

                    if(queryType.equals("uncompleted")){
                        intent.putExtra(Constant.query_type,Constant.QUERY_TYPE_UNCOMPLETED);
                        intent.putExtra(Constant.taskId,bean.getResponseData().getTaskId());
                        intent.putExtra(Constant.publicReportId,publicReportBean.getPublicReportId());
                    } else {
                        intent.putExtra(Constant.query_type,Constant.QUERY_TYPE_COMPLETED);
                    }

                    startActivity(intent);

                } else {
                    Toast.makeText(TousuDealActivity.this,"获取失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RspTousuDetailBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(TousuDealActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doGetComplaints(String userId, String keyword, final String queryType, String pageIndex, String pageCount){

//        Log.d(TAG,"doGetComplaints()  userId="+userId+"  , startDate="+startDate+" , endDate="+endDate+" , pageIndex="+pageIndex);

        Call<RspTousuBean> call = RetrofitLogic.getInstance().getService().doGetComplaintList(
                userId,keyword,queryType,pageIndex,"10"
        );
        call.enqueue(new Callback<RspTousuBean>() {

            @Override
            public void onResponse(Call<RspTousuBean> call, Response<RspTousuBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspTousuBean bean = response.body();

                if(bean.getRtnCode().equals("000000")){

                        List<RspTousuBean.ResponseDataBean.DataListBean>  tmp_list=bean.getResponseData().getDataList();

                    if(queryType.equals("uncompleted")){

                        total_uncompleted = bean.getResponseData().getTotal();

                        if(tmp_list!=null && tmp_list.size()>0){
                            mXunhelists.addAll(tmp_list);
                        }
                        curXunHePage++;
                    } else {

                        total_uncompleted = bean.getResponseData().getTotal();

                        if(tmp_list!=null && tmp_list.size()>0){
                            mReportLists.addAll(tmp_list);
                        }
                        curReportPage++;
                    }
                    updateListView();

                } else {
                    Toast.makeText(TousuDealActivity.this,"提交失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }

                mXunheRefreshView.stopLoadMore();
                mXunheRefreshView.stopRefresh();

                mReportRefreshView.stopLoadMore();
                mReportRefreshView.stopRefresh();
            }

            @Override
            public void onFailure(Call<RspTousuBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(TousuDealActivity.this,"提交失败",Toast.LENGTH_SHORT).show();

                mXunheRefreshView.stopLoadMore();
                mXunheRefreshView.stopRefresh();

                mReportRefreshView.stopLoadMore();
                mReportRefreshView.stopRefresh();
//                lastRefreshTime = mXunheRefreshView.getLastRefreshTime();
            }
        });
    }


    private void updateListView(){

        if(mCurTab == TAB_XUNHE){
            if(mXunhelists !=null && mXunhelists.size()>0){
                mBankView.setVisibility(View.GONE);
            }
            mXunHeAdapter.setList(mXunhelists);
        } else {
            if(mReportLists !=null && mReportLists.size()>0){
                mBankView.setVisibility(View.GONE);
            }

            mReportAdapter.setList(mReportLists);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class XunHeAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspTousuBean.ResponseDataBean.DataListBean> mList = null;
        private Context mContext;
        int widgt,height;
        ImageSize targetSize;

//        private PoiInfo tmp;

        public XunHeAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);

            widgt = (int) getResources().getDimension(R.dimen.auto_dimen2_160);
            height = (int) getResources().getDimension(R.dimen.auto_dimen2_130);
            targetSize = new ImageSize(widgt, height);
        }

        public void setList(List<RspTousuBean.ResponseDataBean.DataListBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspTousuBean.ResponseDataBean.DataListBean> getList() {
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
                convertView = mInflater.inflate(R.layout.item_news_template5, null);
                holder = new ViewHolder();
                holder.img = (ImageView) convertView.findViewById(R.id.iv_thumb);
                holder.no = (TextView) convertView.findViewById(R.id.tousu_item_no);
                holder.status = (TextView) convertView.findViewById(R.id.tousu_item_status);
                holder.content = (TextView) convertView.findViewById(R.id.tousu_item_content);
                holder.time = (TextView) convertView.findViewById(R.id.tousu_item_time);
                holder.river = (TextView) convertView.findViewById(R.id.tousu_item_river);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            String img_url =  mList.get(position).getReport_Img();


            Log.d(TAG,"getView() position = "+position+" , img_url ="+img_url);

            if(!TextUtils.isEmpty(img_url)){
                String[] imgs = img_url.split(",");
                if(imgs.length>0){
//                    holder.img
//                    String url = myUrls.getStr(position);
                    String url = RetrofitLogic.IMAGE_BASE_GET_URL+imgs[0].trim();
//                    Glide.with(mContext)
//                            .load(url)
//                            .placeholder(R.drawable.zanwu_img)//图片加载出来前，显示的图片
//                            .error(R.drawable.zanwu_img)//图片加载失败后，显示的图片
//                            .into(holder.img);

                    Log.d(TAG,"getView() load img url ="+url);

                    ImageLoader.getInstance().displayImage(url,holder.img,targetSize);
//                    imageLoader.displayImage(url,holder.img);
                } else {
                    Log.d(TAG,"getView() imgs.length  = 0 ");
                }
            }

            holder.no.setText(mList.get(position).getBussiness_No());
//            holder.status.setText(mList.getStr(position).getBusiness_State());
            holder.status.setText(mList.get(position).getBusiness_StateDescription());
            holder.content.setText(mList.get(position).getReport_Content());
            holder.time.setText(mList.get(position).getCreate_Time());
            holder.river.setText(mList.get(position).getReport_River());

            return convertView;
        }

        class ViewHolder {
            ImageView img;
            TextView no;
            TextView status;
            TextView content;
            TextView time;
            TextView river;
        }
    }

    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class ReportAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspTousuBean.ResponseDataBean.DataListBean> mList = null;
        private Context mContext;
        int widgt,height;
        ImageSize targetSize;

//        private PoiInfo tmp;

        public ReportAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            widgt = (int) getResources().getDimension(R.dimen.auto_dimen2_160);
            height = (int) getResources().getDimension(R.dimen.auto_dimen2_130);
            targetSize = new ImageSize(widgt, height);
        }

        public void setList(List<RspTousuBean.ResponseDataBean.DataListBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspTousuBean.ResponseDataBean.DataListBean> getList() {
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
                convertView = mInflater.inflate(R.layout.item_news_template5, null);
                holder = new ViewHolder();

                holder.img = (ImageView) convertView.findViewById(R.id.iv_thumb);
                holder.no = (TextView) convertView.findViewById(R.id.tousu_item_no);
                holder.status = (TextView) convertView.findViewById(R.id.tousu_item_status);
                holder.content = (TextView) convertView.findViewById(R.id.tousu_item_content);
                holder.time = (TextView) convertView.findViewById(R.id.tousu_item_time);
                holder.river = (TextView) convertView.findViewById(R.id.tousu_item_river);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String img_url =  mList.get(position).getReport_Img();

//            Log.d(TAG,"getView() img_url="+img_url);

            if(!TextUtils.isEmpty(img_url)){
                String[] imgs = img_url.split(",");
                if(imgs.length>0){
//                    holder.img

//                    String url = myUrls.getStr(position);

                    String url = RetrofitLogic.IMAGE_BASE_GET_URL+imgs[0].trim();

                    Log.d(TAG,"getView() load img url ="+url);


                    ImageLoader.getInstance().displayImage(url,holder.img,targetSize);

                }
            }

            holder.no.setText(mList.get(position).getBussiness_No());
//            holder.status.setText(mList.getStr(position).getBusiness_State());
            holder.status.setText(mList.get(position).getBusiness_StateDescription());
            holder.content.setText(mList.get(position).getReport_Content());
            holder.time.setText(mList.get(position).getCreate_Time());
            holder.river.setText(mList.get(position).getReport_River());

            return convertView;
        }

        class ViewHolder {
            ImageView img;
            TextView no;
            TextView status;
            TextView content;
            TextView time;
            TextView river;
        }
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    private void switchTab(int tabIndex){

        Log.i(TAG,"switchTab()  tabIndex="+tabIndex);

        mCurTab = tabIndex;

        switch(tabIndex){
            case TAB_XUNHE:
                mWantedTabTv.setTextColor(getResources().getColor(R.color.color_1c9d17));
                mWantedTabBotLine.setVisibility(View.VISIBLE);
                mRentTabTv.setTextColor(getResources().getColor(R.color.color_333333));
                mRentTabBotLine.setVisibility(View.GONE);

                mXunheRefreshView.setVisibility(View.VISIBLE);
                mReportRefreshView.setVisibility(View.GONE);
                break;
            case TAB_REPORT:

                mWantedTabTv.setTextColor(getResources().getColor(R.color.color_333333));
                mWantedTabBotLine.setVisibility(View.GONE);
                mRentTabTv.setTextColor(getResources().getColor(R.color.color_1c9d17));
                mRentTabBotLine.setVisibility(View.VISIBLE);

                mXunheRefreshView.setVisibility(View.GONE);
                mReportRefreshView.setVisibility(View.VISIBLE);

                break;
        }
        updateListView();
    }

}
