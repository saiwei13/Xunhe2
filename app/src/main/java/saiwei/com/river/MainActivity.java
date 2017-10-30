package saiwei.com.river;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saiwei.com.river.entity.RspUserCommonInfo;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.User;
import saiwei.com.river.util.HttpAssist;
import saiwei.com.river.util.SharePreferenceUtil;

public class MainActivity extends Activity {

    private static final String TAG = "chenwei.MainActivity";

    @BindView(R.id.main_xunhe)
    Button mBtXunhe;

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    String uncompleteCounts ;
    int cruiseCounts;

    @BindView(R.id.main_today_record)
    TextView mTVToday;

    @BindView(R.id.main_uncomplete)
    TextView mTVUncomplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initBottomView();
        initData();

        Beta.checkUpgrade(false,false);



//        long diff = 500*1000;
//
//        long diffSeconds = diff / 1000 % 60;
//        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000) % 24;
//        long diffDays = diff / (24 * 60 * 60 * 1000);
//
//        Log.d(TAG," 两个时间相差："+diffSeconds+" , "+diffMinutes+", "+diffHours+" , "+diffDays);

//        System.out.print("两个时间相差：");
//        System.out.print(diffDays + " 天, ");
//        System.out.print(diffHours + " 小时, ");
//        System.out.print(diffMinutes + " 分钟, ");
//        System.out.print(diffSeconds + " 秒.");

    }

    private void initData() {

        uncompleteCounts = SharePreferenceUtil.getInstance().getStr(
                SharePreferenceUtil.SHARE_PREFERENCE_UNCOMPLETECOUNTS
        );
        cruiseCounts = SharePreferenceUtil.getInstance().getInt(
                SharePreferenceUtil.SHARE_PREFERENCE_CRUISECOUNTS
        );

        updateView(cruiseCounts,uncompleteCounts);
    }

    private void doGetCommonInfo(){
        String userid = AccoutLogic.getInstance().getUserId();
        retrofit2.Call<RspUserCommonInfo> call = RetrofitLogic.getInstance().getService().doGetUserCommonInfo(userid);
        call.enqueue(new Callback<RspUserCommonInfo>() {
            @Override
            public void onResponse(Call<RspUserCommonInfo> call, Response<RspUserCommonInfo> response) {
                RspUserCommonInfo bean = response.body();

                if(bean.getRtnCode().equals("000000")){


                    uncompleteCounts = bean.getResponseData().getUncompleteCounts();
                    cruiseCounts = bean.getResponseData().getCruiseCounts();

                    SharePreferenceUtil.getInstance().putStr(
                            SharePreferenceUtil.SHARE_PREFERENCE_UNCOMPLETECOUNTS,
                            uncompleteCounts
                    );
                    SharePreferenceUtil.getInstance().putInt(
                            SharePreferenceUtil.SHARE_PREFERENCE_CRUISECOUNTS,
                            cruiseCounts
                    );

                    updateView(cruiseCounts,uncompleteCounts);
                }
            }

            @Override
            public void onFailure(Call<RspUserCommonInfo> call, Throwable t) {

            }
        });
    }

    private void updateView(int cruiseCounts, String uncompleteCounts){

        if(cruiseCounts == 0){
            mTVToday.setText("您今日尚未巡河");
        } else {
            String str = "您今日已巡河"+cruiseCounts+"次";
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(
                    new ForegroundColorSpan(Color.parseColor("#FF0000")), 6,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTVToday.setText(spannableString);

//            mTVToday.setText("您今日已巡河3次");
        }

        if(TextUtils.isEmpty(uncompleteCounts)){


            String str = "您有"+"0"+"件投诉件待处理";
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(
                    new ForegroundColorSpan(Color.parseColor("#FF0000")), 2,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTVUncomplete.setText(spannableString);

//            mTVUncomplete.setText("您有0件投诉件待处理");
        } else {

            String str = "您有"+Integer.parseInt(uncompleteCounts)+"件投诉件待处理";
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(
                    new ForegroundColorSpan(Color.parseColor("#FF0000")), 2,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTVUncomplete.setText(spannableString);

//            mTVUncomplete.setText("您有1件投诉件待处理");
        }


//        mTVToday
    }

    private void initView(){
        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
//        mTitleLeft.setOnClickListener(this);

        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("龙岩河长制");
        findViewById(R.id.title_btn_left).setVisibility(View.GONE);

        findViewById(R.id.title_btn_right).setVisibility(View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @OnClick({R.id.main_xunhe})
    public  void doXunhe(View v){
        Intent intent =new Intent(MainActivity.this,MapActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.main_index)
    public void doIndexBottom(View v){

    }

    @OnClick(R.id.main_complain)
    public void doTousuBottom(View v){
        goToTousuActivity();
    }

    @OnClick(R.id.main_personcenter)
    public void doUser(View v){

//        CrashReport.testJavaCrash();

        if(AccoutLogic.getInstance().isLogin()){
            Intent intent =new Intent(MainActivity.this,UserCenterActivity.class);
            startActivity(intent);
        } else {
            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void goToTousuActivity(){
        Intent intent =new Intent(MainActivity.this,TousuDealActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.main_reportissue)
    public void doReportIssue(View v){
        Intent intent = new Intent(MainActivity.this,FeedBackActivity.class);
        intent.putExtra(AccoutLogic.FROM_ACTIVITY,AccoutLogic.FROM_MAINACTIVITY);
        startActivity(intent);
    }


    @OnClick({R.id.main_tousudeal})
    public  void doTousu(View v){
        goToTousuActivity();
    }



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
        mWantedView.setPressed(true);
        mWantedView.setClickable(false);

        doGetCommonInfo();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();


        showSimpleDialog(null);

    }

    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    //显示基本Dialog
    private void showSimpleDialog(View view) {

        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("提示");
        builder.setMessage("是否要退出程序？");

        //监听下方button点击事件
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                System.exit(0);

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


     private void test(){
         final File file = new File("/sdcard/hechang/feedback/image.jpg");
         if(!file.exists()){
             Toast.makeText(MainActivity.this,"文件不存在",Toast.LENGTH_SHORT);
             return ;
         }

         new Thread(new Runnable() {
             @Override
             public void run() {
                 HttpAssist.uploadSpotFile(file);
             }
         }).start();
     }


    public static void upload(String path) {

        String descriptionString = "hello, this is description speaking";

        File file = new File("/sdcard/hechang/feedback/image.jpg");

        RequestBody requestBody1 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        Call<String> call = RetrofitLogic.getInstance().getFileUploadService().uploadImage("image.jpg", requestBody1);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("Upload", response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("Upload", t.toString());
            }
        });
    }
}
