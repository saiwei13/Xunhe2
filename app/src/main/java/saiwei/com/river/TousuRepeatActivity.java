package saiwei.com.river;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import saiwei.com.river.entity.ReqFeedbackBean;
import saiwei.com.river.entity.RspCloseTousu;
import saiwei.com.river.entity.RspComplaintBean;
import saiwei.com.river.entity.RspRepeatComplaint;
import saiwei.com.river.entity.RspTousuBean;
import saiwei.com.river.entity.RspTousuRepeatBean;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.DrivingRecordLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.util.BitmapTool;
import saiwei.com.river.util.HttpAssist;

/**
 * 重复投诉
 *
 * Created by saiwei on 9/23/17.
 */

public class TousuRepeatActivity extends Activity {

    private final String TAG = "chenwei.TousuRepeatAc";

    private ImageButton mTitleLeft;
    private TextView mTitleName;


    private ListView mXunHeListView ;
    private XunHeAdapter mXunHeAdapter;

    private List<RspTousuRepeatBean.ResponseDataBean.DataListBean> mDataList = new ArrayList<>();

    @BindView(R.id.etSearch)
    EditText mEditText;

    @BindView(R.id.title_comm_btn_right)
    Button mBtSearch;

    @BindView(R.id.want_bank_view)
    RelativeLayout mBankView;

    private String taskId,publicReportId;

    int curXunHePage = 1;
    String userId;
    private String mKeyword="";
    private int total ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_tousu_repeat);

        ButterKnife.bind(this);


        taskId = getIntent().getStringExtra(Constant.taskId);
        publicReportId = getIntent().getStringExtra(Constant.publicReportId);

        initView();
        initListView();
        initData();

    }

    private void initData(){

        userId = AccoutLogic.getInstance().getUserId();

        doGetSimilarComplaintList(userId,mKeyword,curXunHePage+"","10");
    }

    private void initView(){

        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
//        mTitleLeft.setOnClickListener(this);

        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("重复投诉处理");
        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

        mXunHeListView = (ListView) findViewById(R.id.tousu_repeat_listview);
        mXunHeAdapter = new XunHeAdapter(this);
        mXunHeListView.setAdapter(mXunHeAdapter);
        mXunHeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(mDataList != null){
                    String str = mDataList.get(i).getReport_Content();
                    String repeat_id = mDataList.get(i).getPublic_Report_ID();
                    showSimpleDialog(null,str,repeat_id);
                }
            }
        });
    }

    private XRefreshView mXunheRefreshView;
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
//                doGetSimilarComplaintList(userId,"",curXunHePage+"","10");


                if(mDataList!=null){
                    if(mDataList.size()>= total){
                        Log.d(TAG,"setPullLoadEnable(false)");
//                mXunheRefreshView.setMoveHeadWhenDisablePullRefresh(false);
//                        mXunheRefreshView.setPullLoadEnable(false);

                        mXunheRefreshView.setLoadComplete(true);
                    } else {
                        Log.d(TAG,"setPullLoadEnable(true)");
                        mXunheRefreshView.setLoadComplete(false);
//                        mXunheRefreshView.setPullLoadEnable(true);
                        doGetSimilarComplaintList(userId,mKeyword,curXunHePage+"","10");
                    }
                } else {
                    doGetSimilarComplaintList(userId,mKeyword,curXunHePage+"","10");
                }


//                doGetSimilarComplaintList(userId,mKeyword,curXunHePage+"","10");
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
                Log.d(TAG,"onRelease()  direction ="+direction);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.title_btn_left})
    public  void doBack(View v){
        finish();
    }

    @OnClick({R.id.tousu_repeat_search})
    public  void doSearch(View v){
        String str= mEditText.getText().toString();

        Log.d(TAG,"doSearch() str="+str);

        if(str.equals(mKeyword)){

        } else {
            mKeyword = str;
            curXunHePage = 1;
            mDataList.clear();
            mXunheRefreshView.setLoadComplete(false);
            doGetSimilarComplaintList(userId,mKeyword,curXunHePage+"","10");
        }
    }

    public  void doCommit(String userId, String publicReportId, String taskId, String repeatId){

        Call<RspRepeatComplaint> call = RetrofitLogic.getInstance().getService().doRepeatComplaintHandle(
                userId, publicReportId, taskId, repeatId
        );
        call.enqueue(new Callback<RspRepeatComplaint>() {

            @Override
            public void onResponse(Call<RspRepeatComplaint> call, Response<RspRepeatComplaint> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspRepeatComplaint bean = response.body();

                Log.d(TAG,"onResponse() bean.getRtnCode()="+bean.getRtnCode());

                if(bean.getRtnCode().equals("000000")){
                    Toast.makeText(TousuRepeatActivity.this,"关闭投诉成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TousuRepeatActivity.this,MainActivity.class));

                    finish();
                } else {
                    Toast.makeText(TousuRepeatActivity.this,"提交失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RspRepeatComplaint> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(TousuRepeatActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doGetSimilarComplaintList(String userId, String keyword, String pageIndex, String pageCount){

        Call<RspTousuRepeatBean> call = RetrofitLogic.getInstance().getService().doGetSimilarComplaintList(
                userId,keyword,pageIndex,pageCount
        );
        call.enqueue(new Callback<RspTousuRepeatBean>() {

            @Override
            public void onResponse(Call<RspTousuRepeatBean> call, Response<RspTousuRepeatBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspTousuRepeatBean bean = response.body();

                if(bean.getRtnCode().equals("000000")){

                    total = bean.getResponseData().getTotal();
                    List<RspTousuRepeatBean.ResponseDataBean.DataListBean> tmp = bean.getResponseData().getDataList();
                    mDataList.addAll(tmp);

                    curXunHePage++;

                    updateListView();

                } else {
                    Toast.makeText(TousuRepeatActivity.this,"提交失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();

                }
                mXunheRefreshView.stopLoadMore();
                mXunheRefreshView.stopRefresh();
            }

            @Override
            public void onFailure(Call<RspTousuRepeatBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(TousuRepeatActivity.this,"提交失败",Toast.LENGTH_SHORT).show();

                mXunheRefreshView.stopLoadMore();
                mXunheRefreshView.stopRefresh();
            }
        });
    }

    private void updateListView(){
        if(mDataList !=null && mDataList.size()>0){
            mBankView.setVisibility(View.GONE);
        }
        mXunHeAdapter.setList(mDataList);

//        if(mDataList!=null){
//
//            Log.d(TAG,"updateListView()  mDataList.size()="+mDataList.size()+" , total="+total);
//
//            if(mDataList.size()>= total){
//                Log.d(TAG,"setPullLoadEnable(false)");
////                mXunheRefreshView.setMoveHeadWhenDisablePullRefresh(false);
////                mXunheRefreshView.setPullLoadEnable(false);
//            } else {
//                Log.d(TAG,"setPullLoadEnable(true)");
////                mXunheRefreshView.setPullLoadEnable(true);
//            }
//        }
    }

    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    //显示基本Dialog
    private void showSimpleDialog(View view, String str, final String repeatId) {

        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("您确定与该投诉件重复吗？");
        builder.setMessage(str);

        //监听下方button点击事件
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                doCommit(userId, publicReportId, taskId, repeatId);
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
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class XunHeAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<RspTousuRepeatBean.ResponseDataBean.DataListBean> mList = null;
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

        public void setList(List<RspTousuRepeatBean.ResponseDataBean.DataListBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<RspTousuRepeatBean.ResponseDataBean.DataListBean> getList() {
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

            XunHeAdapter.ViewHolder holder;

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

                    String url = RetrofitLogic.IMAGE_BASE_GET_URL+imgs[0].trim();
                    Log.d(TAG,"getView() load img url ="+url);

                    ImageLoader.getInstance().displayImage(url,holder.img,targetSize);
//                    imageLoader.displayImage(url,holder.img);
                } else {
                    Log.d(TAG,"getView() imgs.length  = 0 ");
                }
            }

            holder.no.setText(mList.get(position).getBussiness_No());
            holder.status.setText(mList.get(position).getBusiness_State());
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
}
