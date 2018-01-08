package saiwei.com.river;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import saiwei.com.river.entity.RspComplaintBean;
import saiwei.com.river.entity.RspCruisDetailBean;
import saiwei.com.river.entity.RspCruiseBean;
import saiwei.com.river.entity.RspTousuDetailBean;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.DrivingRecordLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.service.NetWorkService;

/**
 * Created by saiwei on 9/21/17.
 */

public class UserCenterActivity extends Activity {

    private static final String TAG = "chenwei.UserCent";

    @BindView(R.id.usercenter_login)
    Button mBtLogin;
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

    /** 空白页 */
    private View mWantedBankView;
    private View mRentBankView;

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    @BindView(R.id.usercenter_name)
    TextView mTVName;

    @BindView(R.id.usercenter_jiedaoname)
    TextView mTVJiedao;

    @BindView(R.id.usercenter_photo)
    ImageView mPhotoView;


    private int total_complaints ,total_cruises;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_usercenter);

        ButterKnife.bind(this);

        initBottomView();
        initView();
        initTabView();
        initData();
    }


    private void initTabView(){
        mRentTabTv=(TextView)findViewById(R.id.personpark_rent_tab_tv);
        mRentTabBotLine=(View)findViewById(R.id.personpark_rent_tab_bot_line);

        mWantedTabTv=(TextView)findViewById(R.id.personpark_wanted_tab_tv);
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
        mTitleName.setText("个人中心");
        findViewById(R.id.title_btn_right).setVisibility(View.GONE);


        mXunHeListView = (ListView) findViewById(R.id.usercenter_xunhe_list);
        mXunHeAdapter = new XunHeAdapter(this);
        mXunHeListView.setAdapter(mXunHeAdapter);
        mXunHeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toast("正在下载巡河详情。");

                String userid = mXunhelists.get(i).getUserId();
                String tourRiverID = mXunhelists.get(i).getTourRiverID();
                doGetCruiseDetail(userid,tourRiverID);
            }
        });

        mReportListView = (ListView) findViewById(R.id.usercenter_report_list);
        mReportAdapter = new ReportAdapter(this);
        mReportListView.setAdapter(mReportAdapter);
        mReportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String publicReportId = mReportLists.get(i).getPublicReportId();
                doGetComplaintDetail(publicReportId);
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
//                doGetCruise(userId,startDate,endDate, curXunHePage +"");

                if(mXunhelists!=null){
                    if(mXunhelists.size()>= total_cruises){
                        Log.d(TAG,"setPullLoadEnable(false)");
                        mXunheRefreshView.setLoadComplete(true);
                    } else {
                        Log.d(TAG,"setPullLoadEnable(true)");
                        mXunheRefreshView.setLoadComplete(false);
                        doGetCruise(userId,startDate,endDate, curXunHePage +"");
                    }
                }
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
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
//                doGetComplaints(userId,"","", curReportPage +"","10");


                if(mReportLists!=null){
                    if(mReportLists.size()>= total_complaints){
                        Log.d(TAG,"setPullLoadEnable(false)");
                        mReportRefreshView.setLoadComplete(true);
                    } else {
                        Log.d(TAG,"setPullLoadEnable(true)");
                        mReportRefreshView.setLoadComplete(false);
                        doGetComplaints(userId,"","", curReportPage +"","10");
                    }
                }
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
            }
        });

    }

    List<RspCruiseBean.ResponseDataBean.VarListBean> mXunhelists;
    List<RspComplaintBean.ResponseDataBean.VarListBean> mReportLists;
    String startDate = "2017-08-01";
    String endDate = "2018-09-10";
    int curXunHePage = 1;
    int curReportPage = 1;
    String userId;

    private void initData(){

        userId = AccoutLogic.getInstance().getUserId();

        String town = AccoutLogic.getInstance().getTown();

        if(TextUtils.isEmpty(town)){
            town = "漕溪街道";
        }

        mTVJiedao.setText(town);

        String name = AccoutLogic.getInstance().getUserName(userId);
        mTVName.setText(name);

        mXunhelists = new ArrayList<>();
        mReportLists = new ArrayList<>();

//        String userId ="8490f2aa96dd4f72929a97e64031e53e";

        doGetCruise(userId,startDate,endDate, curXunHePage +"");
        doGetComplaints(userId,"","",curReportPage+"","10");

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
        mSearchView.setPressed(true);
        mSearchView.setClickable(false);
    }

    @OnClick(R.id.main_index)
    public void doIndexBottom(View v){
        Intent intent =new Intent(UserCenterActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_complain)
    public void doTousuBottom(View v){
        goToTousuActivity();
    }

    @OnClick(R.id.main_personcenter)
    public void doUser(View v){

    }

    private void goToTousuActivity(){
        Intent intent =new Intent(UserCenterActivity.this,TousuDealActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.bank_content_do_req)
    public void doReqRecord(){

//        doGetCruiseDefault(userId);

        doGetCruise(userId,startDate,endDate,curXunHePage+"");
    }

    @OnClick({R.id.title_btn_left})
    public void doBack(View v) {

        Log.d(TAG,"doBack() tmp = "+AccoutLogic.getInstance().getTmp());

        finish();
    }

    @OnClick({R.id.usercenter_photo})
    public void doClickPhoto(View v) {
        showLogoutDialog(null);
    }


    @OnClick({R.id.usercenter_login})
    public  void doLogin(View v){

        String userId ="8490f2aa96dd4f72929a97e64031e53e";
        String startDate = "2017-08-01";
        String endDate = "2017-09-10";
        doGetCruise(userId,startDate,endDate,curXunHePage+"");
    }


//    /**
//     * 默认首次下载
//     * @param userId
//     */
//    private void doGetCruiseDefault(String userId){
//        doGetCruise(userId,startDate,endDate,"0");
//    }

    private void doGetComplaints(String userId,String status,String isAppraise,String pageIndex,String pageCount){

        Log.d(TAG,"doGetComplaints()  userId="+userId+"  , startDate="+startDate+" , endDate="+endDate+" , pageIndex="+pageIndex);

        retrofit2.Call<RspComplaintBean> call = RetrofitLogic.getInstance().getService().doGetComplaintListByUser(
                userId,status,isAppraise,pageIndex,pageCount
        );
        call.enqueue(new Callback<RspComplaintBean>() {

            @Override
            public void onResponse(Call<RspComplaintBean> call, Response<RspComplaintBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());

                RspComplaintBean bean = response.body();

                if(bean.getRtnCode().equals("000000")){

                    total_complaints = bean.getResponseData().getCount();

                    RspComplaintBean.ResponseDataBean  responseDataBean = bean.getResponseData();

                    if(responseDataBean!=null){
                        List<RspComplaintBean.ResponseDataBean.VarListBean>  tmp_list=responseDataBean.getVarList();
                        if(tmp_list!=null && tmp_list.size()>0){
                            mReportLists.addAll(tmp_list);
                        }
                        curReportPage++;
                        updateListView();
                    }

                    mReportRefreshView.stopLoadMore();
                    mReportRefreshView.stopRefresh();
                } else {
                    toast("获取失败 "+bean.getRtnMsg());
                }


            }

            @Override
            public void onFailure(Call<RspComplaintBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(UserCenterActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
                mReportRefreshView.stopLoadMore();
                mReportRefreshView.stopRefresh();
            }
        });
    }


    private void doGetComplaintDetail(String publicReportId){

        Log.d(TAG,"doGetComplaintDetail() publicReportId="+publicReportId);

        Call<RspTousuDetailBean> call = RetrofitLogic.getInstance().getService().doGetComplaintDetail(
                publicReportId
        );
        call.enqueue(new Callback<RspTousuDetailBean>() {

            @Override
            public void onResponse(Call<RspTousuDetailBean> call, Response<RspTousuDetailBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspTousuDetailBean bean = response.body();

                if(bean.getRtnCode().equals("000000")){

                    Intent intent = new Intent(UserCenterActivity.this,TousuDetailActivity.class);
                    AccoutLogic.getInstance().setRspTousuDetailBeanCache(bean);

                    intent.putExtra(Constant.query_type,Constant.QUERY_TYPE_COMPLETED);
                    startActivity(intent);

                } else {
                    Toast.makeText(UserCenterActivity.this,"获取失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RspTousuDetailBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(UserCenterActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void doGetCruise(String userId,String startDate,String endDate,String pageIndex){

        Log.d(TAG,"doGetCruise()  userId="+userId+"  , startDate="+startDate+" , endDate="+endDate+" , pageIndex="+pageIndex);

        retrofit2.Call<RspCruiseBean> call = RetrofitLogic.getInstance().getService().doGetCruiseList(
                userId,startDate,endDate,pageIndex,"10"
        );
        call.enqueue(new Callback<RspCruiseBean>() {

            @Override
            public void onResponse(Call<RspCruiseBean> call, Response<RspCruiseBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspCruiseBean bean = response.body();

                if(bean.getRtnCode().equals("000000")){

                    total_cruises = bean.getResponseData().getCount();

                    RspCruiseBean.ResponseDataBean  responseDataBean = bean.getResponseData();

                    if(responseDataBean!=null){
                        List<RspCruiseBean.ResponseDataBean.VarListBean>  tmp_list=responseDataBean.getVarList();
                        if(tmp_list!=null && tmp_list.size()>0){
                            mXunhelists.addAll(tmp_list);
                        }
                        curXunHePage++;
                        updateListView();
                    }
                }else {
                    Toast.makeText(UserCenterActivity.this,"提交失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }

                mXunheRefreshView.stopLoadMore();
                mXunheRefreshView.stopRefresh();


            }

            @Override
            public void onFailure(Call<RspCruiseBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(UserCenterActivity.this,"提交失败",Toast.LENGTH_SHORT).show();

                mXunheRefreshView.stopLoadMore();
                mXunheRefreshView.stopRefresh();
            }
        });
    }

    /**
     * 获取巡河记录详情
     * @param userId
     * @param tourRiverID
     */
    private void doGetCruiseDetail(String userId,String tourRiverID){

        Log.d(TAG,"doGetCruiseDetail() userId="+userId+" , tourRiverID="+tourRiverID);

        retrofit2.Call<RspCruisDetailBean> call = RetrofitLogic.getInstance().getService().doGetCruiseDetail(
                userId,tourRiverID
        );
        call.enqueue(new Callback<RspCruisDetailBean>() {

            @Override
            public void onResponse(Call<RspCruisDetailBean> call, Response<RspCruisDetailBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());

//                RspCruisDetailBean bean = response.body();
                                Intent intent = new Intent(UserCenterActivity.this,XunheDetailActivity.class);
//                XunheRecord xunheRecord = mXunhelists.getStr(i);
                AccoutLogic.getInstance().setRspCruisDetailBeanCache(response.body());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<RspCruisDetailBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(UserCenterActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
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

    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class XunHeAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspCruiseBean.ResponseDataBean.VarListBean> mList = null;
        private Context mContext;

//        private PoiInfo tmp;

        public XunHeAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public void setList(List<RspCruiseBean.ResponseDataBean.VarListBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspCruiseBean.ResponseDataBean.VarListBean> getList() {
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
                convertView = mInflater.inflate(R.layout.xunhe_list_item, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.search_list_item_name);
                holder.address = (TextView) convertView.findViewById(R.id.search_list_item_address);
//                holder.look = (TextView) convertView.findViewById(R.id.search_list_item_look);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(mList.get(position).getReportRiver());
            holder.address.setText(mList.get(position).getTourTime());
            return convertView;
        }

        class ViewHolder {
            //            View container;
            TextView name;
            TextView address;
            //            ImageView image;
            TextView look;
        }
    }


    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class ReportAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspComplaintBean.ResponseDataBean.VarListBean> mList = null;
        private Context mContext;

        ImageSize targetSize;
        int widgt,height;

//        private PoiInfo tmp;

        public ReportAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            widgt = (int) getResources().getDimension(R.dimen.auto_dimen2_160);
            height = (int) getResources().getDimension(R.dimen.auto_dimen2_130);
            targetSize = new ImageSize(widgt, height);
        }

        public void setList(List<RspComplaintBean.ResponseDataBean.VarListBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspComplaintBean.ResponseDataBean.VarListBean> getList() {
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



            String img_url =  mList.get(position).getReportImg();

//            Log.d(TAG,"getView() img_url="+img_url);

            if(!TextUtils.isEmpty(img_url)){
                String[] imgs = img_url.split(",");
                if(imgs.length>0){
//                    holder.img
//                    String url = myUrls.getStr(position);

                    String url = RetrofitLogic.IMAGE_BASE_GET_URL+imgs[0].trim();
                    if(imgs[0].trim().startsWith("http")){
                        url = imgs[0].trim();
                    }
                    Log.d(TAG,"getView() load img url ="+url);

                    ImageLoader.getInstance().displayImage(url,holder.img,targetSize);

                }
            }

            holder.no.setText(mList.get(position).getBussinessNo());
            holder.status.setText(mList.get(position).getBusinessStateDescription());
//            holder.status.setText(mList.get(position).getBusinessState());
            holder.content.setText(mList.get(position).getReportContent());
            holder.time.setText(mList.get(position).getCreateTime()+"");
            holder.river.setText(mList.get(position).getReportRiver());

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

        int  i = AccoutLogic.getInstance().getTmp();
        AccoutLogic.getInstance().setTmp((i+1));

        AccoutLogic.tmp2 += 1;

        Log.d(TAG,"switchTab () tmp = "+AccoutLogic.getInstance().getTmp());

        Log.d(TAG,"switchTab () tmp2 = "+AccoutLogic.tmp2);

        switch(tabIndex){
            case TAB_XUNHE:

                mWantedTabTv.setTextColor(getResources().getColor(R.color.color_1c9d17));
                mWantedTabBotLine.setVisibility(View.VISIBLE);
                mRentTabTv.setTextColor(getResources().getColor(R.color.color_333333));
                mRentTabBotLine.setVisibility(View.GONE);

                mXunheRefreshView.setVisibility(View.VISIBLE);
                mXunHeAdapter.notifyDataSetChanged();
                mReportRefreshView.setVisibility(View.GONE);

                break;

            case TAB_REPORT:
                mWantedTabTv.setTextColor(getResources().getColor(R.color.color_333333));
                mWantedTabBotLine.setVisibility(View.GONE);
                mRentTabTv.setTextColor(getResources().getColor(R.color.color_1c9d17));
                mRentTabBotLine.setVisibility(View.VISIBLE);

                mXunheRefreshView.setVisibility(View.GONE);
                mReportRefreshView.setVisibility(View.VISIBLE);
                mReportAdapter.notifyDataSetChanged();

                break;
        }
        updateListView();
    }

    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    /**
     * 显示退出dialog
     * @param view
     */
    private void showLogoutDialog(View view) {

        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("提示");
        builder.setMessage("是否要注销帐号?");

        //监听下方button点击事件
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AccoutLogic.getInstance().delUser(userId);
                startActivity(new Intent(UserCenterActivity.this,LoginActivity.class));
                finish();

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

}
