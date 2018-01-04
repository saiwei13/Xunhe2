package saiwei.com.river;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saiwei.com.river.entity.ReqFeedbackBean;
import saiwei.com.river.entity.ResponseBean;
import saiwei.com.river.entity.RspTousuDetailBean;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.DrivingRecordLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.GpsInfo;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.util.BitmapTool;
import saiwei.com.river.util.DrivingRecordTool;
import saiwei.com.river.util.HttpAssist;
import saiwei.com.river.util.LogUtil;
import saiwei.com.river.view.MyListView;

/**
 * Created by saiwei on 9/22/17.
 *
 * 结束巡河界面
 */
public class CompleteXunheActivity extends Activity implements View.OnClickListener{

    private static final String TAG= "chenwei.CompleteXunheA";

    /** 图片URI **/
    private Uri mImageUri;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int ALBUM_IMAGE_ACTIVITY_REQUEST_CODE = 200;

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
    @BindView(R.id.xunhe_end_bt)
    Button mBtEnd;

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

    XunheRecord xunheRecord;

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    private MyListView mReportListView ;
    private ReportAdapter mReportAdapter;

    private GridView gridView1;
    private Bitmap bmp;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;

    String mImagePath = "";
    private static final String IMAGE_NAME = "image_yuanshi.jpg";

    List<String> imglist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_xunhe_end);
        ButterKnife.bind(this);
        initView();
        initListView();
        initData();
    }

    @OnClick({R.id.title_btn_left})
    public  void doBack(View v){
        finish();
    }

    @OnClick({R.id.xunhe_end_add_report})
    public  void doAddReport(View v){
        Intent intent = new Intent(CompleteXunheActivity.this,FeedBackActivity.class);
        intent.putExtra(AccoutLogic.FROM_ACTIVITY,AccoutLogic.FROM_COMPLETEXUNHEACTIVITY);
        startActivityForResult(intent,Constant.REQ_CODE_ADD_REPORT_FROM_COMPLETE_XUNHE);
    }

    private void initView(){

        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("结束巡河");
        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

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

        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);
        view4.setOnClickListener(this);
        view5.setOnClickListener(this);
        view6.setOnClickListener(this);
        view7.setOnClickListener(this);
        view8.setOnClickListener(this);
        view9.setOnClickListener(this);
        view10.setOnClickListener(this);
        view11.setOnClickListener(this);
        view12.setOnClickListener(this);

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

        tv1_result.setText(item1_array[0]);
        tv2_result.setText(item2_array[0]);
        tv3_result.setText(item3_array[0]);
        tv4_result.setText(item4_array[0]);
        tv5_result.setText(item5_array[0]);
        tv6_result.setText(item6_array[0]);
        tv7_result.setText(item7_array[0]);
        tv8_result.setText(item8_array[0]);
        tv9_result.setText(item9_array[0]);
        tv10_result.setText(item10_array[0]);
        tv11_result.setText(item11_array[0]);
        tv12_result.setText(item12_array[0]);


        gridView1 = (GridView) findViewById(R.id.feedback_gridView1);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.loadimg); //???
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.griditem_addpic,
                new String[] { "itemImage"}, new int[] { R.id.imageView1});
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {

                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView1.setAdapter(simpleAdapter);

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if( imageItem.size() == 10) {
                    Toast.makeText(CompleteXunheActivity.this, "最多上传9张", Toast.LENGTH_SHORT).show();
                }
                else if(position == imageItem.size()-1) {

                    curPosition = position;
                    doAddPic(null);
                } else {
                    dialog(position);
                }
            }
        });

        gridView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(CompleteXunheActivity.this,"on long click position = "+position,Toast.LENGTH_SHORT).show();

                if(position == imageItem.size()-1){

                } else {
                    reuploadImage(position);
                }

                return true;
            }
        });
    }

    private int curPosition = -1;

    private void initData(){
        xunheRecord = DrivingRecordLogic.getInstance().getCurXunheRecord();

        xunheRecord.setTotalTime("0: 5:0");

        xunheRecord.setIsHDBJ("1");
        xunheRecord.setIsHDWN("1");

        xunheRecord.setIsWSPK("1");
        xunheRecord.setIsSSWF("1");
        xunheRecord.setIsHALJ("1");
        xunheRecord.setIsHDSZ("1");
        xunheRecord.setIsHDWN("1");
        xunheRecord.setIsRHWK("1");
        xunheRecord.setIsPHSS("1");
        xunheRecord.setIsFFBY("1");
        xunheRecord.setIsHZTS("1");
        xunheRecord.setIsYXSZ("1");
        xunheRecord.setIsHDZZ("1");

        createXyJsonInfos();



        File tmp  = MyApp.getApp().getExternalFilesDir(null);
//        Log.d(TAG,"createFile2()  path = "+tmp.getPath());
//         =tmp.getPath()+File.separator+"hechang/trace";
        File image = new File(tmp.getPath()+File.separator+"hechang/feedback/");

        mImagePath = tmp.getPath()+File.separator+"hechang/feedback/" ;// "/sdcard/hechang/feedback/";

//        File image = new File(mImagePath);
        if (!image.exists()) {
            image.mkdirs();
        }
    }


    ArrayList<ReqFeedbackBean> feedbackBeens;

    private void initListView(){
        mReportListView = (MyListView) findViewById(R.id.xunhe_end_report_listview);
        mReportAdapter = new ReportAdapter(this);
        mReportListView.setAdapter(mReportAdapter);
//        mReportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent intent = new Intent(UserCenterActivity.this,ReportDetailActivity.class);
////                XunheRecord xunheRecord = mXunhelists.getStr(i);
////                AccoutLogic.getInstance().setRspCruisDetailBeanCache(mReportLists.getStr(i));
//
//                AccoutLogic.getInstance().setRspComplaintDetailBeanCache(mReportLists.getStr(i));
//
//                startActivity(intent);
//
//            }
//        });
        updateListView();
    }

    /**
     * @return
     */
    private String createXyJsonInfos(){

        ArrayList<GpsInfo> gpsInfos = DrivingRecordLogic.getInstance().readFromFileGpsInfos(xunheRecord.getRecordId()+"");

        JSONArray jsonArray = new JSONArray();

        if(gpsInfos !=null){
            for (int i=0;i<gpsInfos.size();i++){
                GpsInfo gpsInfo = gpsInfos.get(i);
                JSONObject tmp = new JSONObject();
                try {
                    tmp.put("coordinate_X",gpsInfo.getLatLng().latitude);
                    tmp.put("coordinate_Y",gpsInfo.getLatLng().longitude);
//                tmp.putStr("create_time","2017-09-15 15:04:42");

                    long tmp_time = Long.parseLong(gpsInfo.getTime());
                    String create_time=  DrivingRecordTool.conversionTime(tmp_time);
                    tmp.put("create_time",create_time);
                    jsonArray.put(tmp);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArray.toString();
    }

    /**
     * 上报问题
     * @return
     */
    private String createComplaintInfos(){

        ArrayList<ReqFeedbackBean> beans = DrivingRecordLogic.getInstance().getFeedbackBeans();

        JSONArray jsonArray = new JSONArray();

        for (int i=0;i<beans.size();i++){
            ReqFeedbackBean bean = beans.get(i);
            JSONObject tmp = new JSONObject();
            try {
                tmp.put("reportName",bean.getReportName());
                tmp.put("reportPhone",bean.getReportPhone());
                tmp.put("reportRiverId",bean.getReportRiverId());
                tmp.put("complaintsType",bean.getComplaintsType());
                tmp.put("reportRiver",bean.getReportRiver());
                tmp.put("countyCode",bean.getCountyCode());
                tmp.put("townCode",bean.getTownCode());
                tmp.put("villageCode","");
                tmp.put("realAddress",bean.getRealAddress());
                tmp.put("longitude",bean.getLongitude());
                tmp.put("latitude",bean.getLatitude());
                tmp.put("locationAddress",bean.getLocationAddress());
                tmp.put("reportContent",bean.getReportContent());
                tmp.put("reportImg",bean.getReportImg());

                jsonArray.put(tmp);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

//    @OnClick({R.id.xunhe_end_bt})
//    public void dotest(View view){
//        upload("");
//    }

    @OnClick({R.id.xunhe_end_bt})
    public  void doCommit(View v){

//        if(!(imglist!=null && imglist.size()>0)){
//
//            Toast.makeText(CompleteXunheActivity.this,"必须上传至少一张照片",Toast.LENGTH_SHORT).show();
//            return;
//        }


        if(imglist == null || imglist.size()==0){
//            if(imageItem.size()>1){
//                Toast.makeText(CompleteXunheActivity.this,"第 ["+(i+1)+"]张图片没有上传成功，请长按图片重新进行上传",Toast.LENGTH_SHORT).show();
//            } else {
//
//            }

            Toast.makeText(CompleteXunheActivity.this,"必须上传至少一张照片",Toast.LENGTH_SHORT).show();

            return;
        }




        String reportimg = "";

        for(int i=0;i<imglist.size();i++){
            if(i== imglist.size()-1){
                reportimg += imglist.get(i);


                if(imglist.get(i).equals("0")){

                    Toast.makeText(CompleteXunheActivity.this,"第["+(i+1)+"]张图片没有上传成功，请长按图片重新进行上传",Toast.LENGTH_SHORT).show();
                    return;
                }

            }else {
                if(imglist.get(i).equals("0")){
                    Toast.makeText(CompleteXunheActivity.this,"第["+(i+1)+"]张图片没有上传成功，请长按图片重新进行上传",Toast.LENGTH_SHORT).show();
                    return;
                }
                reportimg += imglist.get(i)+",";
            }
        }

        Log.d(TAG,"reportimg = "+reportimg);


        showWaitingDialog();

        String xyJsonInfos= createXyJsonInfos();
        String complaintInfos= createComplaintInfos();

        xunheRecord.setXyJsonInfos(xyJsonInfos);
        xunheRecord.setComplaintInfos(complaintInfos);
        String end_time = DrivingRecordTool.conversionTime(System.currentTimeMillis());

        String diff_time = DrivingRecordTool.getDiffTime(xunheRecord.getTourTime(),end_time);

        Log.d(TAG,"diff_time = "+diff_time);

        xunheRecord.setTotalTime(diff_time);

        retrofit2.Call<ResponseBean> call = RetrofitLogic.getInstance().getService().doCommitXunheRecord(

                xunheRecord.getUserid(),
                xunheRecord.getReportRiver(),
                xunheRecord.getReportRiverId(),
                xunheRecord.getTourTime(),
                xunheRecord.getTotalTime(),
                reportimg,      //new
                xunheRecord.getIsHDZZ(),
                xunheRecord.getIsHDBJ(),
                xunheRecord.getIsWSPK(),

                xunheRecord.getIsSSWF(),
                xunheRecord.getIsHALJ(),
                xunheRecord.getIsHDSZ(),

                xunheRecord.getIsHDWN(),
                xunheRecord.getIsRHWK(),

                xunheRecord.getIsPHSS(),
                xunheRecord.getIsFFBY(),

                xunheRecord.getIsHZTS(),
                xunheRecord.getIsYXSZ(),

                xunheRecord.getXyJsonInfos(),
                xunheRecord.getComplaintInfos()
        );
        call.enqueue(new Callback<ResponseBean>() {

            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                Log.d(TAG,"onResponse()  "+response.body());
//                XunheRecordBean bean = response.body();
                dismissWaitingDialog();

                ResponseBean bean = response.body();

                if(bean.getRtnCode().equals("000000")){
                    Toast.makeText(CompleteXunheActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                    DrivingRecordLogic.getInstance().stopRecord(xunheRecord);
                    startActivity(new Intent(CompleteXunheActivity.this,CommitSuccessActivity.class));
                } else {
                    Toast.makeText(CompleteXunheActivity.this,"获取失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                dismissWaitingDialog();
                Toast.makeText(CompleteXunheActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });
//        startActivity(new Intent(CompleteXunheActivity.this,CommitSuccessActivity.class));
    }


    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    private void showSingleChoiceDialog(final int type , final TextView view  , final String[] item_array, String select) {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("提示");

        int i = 0;
        if(item_array.length>1 ){

            if(item_array[0]==select){
                i=0;
            } else {
                i=1;
            }
        }

        builder.setSingleChoiceItems(item_array, i, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                view.setText(item_array[i]);

                String tmp = (i+1)+"";

                Log.d(TAG,"setSingleChoiceItems()  type="+type+" , tmp="+tmp+" , txt"+item_array[i]);

                if(type == 1){
                    xunheRecord.setIsHDBJ(tmp);
                }
                else if(type == 2){
                    xunheRecord.setIsHDWN(tmp);
                }
                else if(type == 3){
                    xunheRecord.setIsWSPK(tmp);
                }
                else if(type == 4){
                    xunheRecord.setIsSSWF(tmp);
                }
                else if(type == 5){
                    xunheRecord.setIsHALJ(tmp);
                }
                else if(type == 6){
                    xunheRecord.setIsHDSZ(tmp);
                }
                else if(type == 7){
                    xunheRecord.setIsRHWK(tmp);
                }else if(type == 8){
                    xunheRecord.setIsPHSS(tmp);
                }else if(type == 9){
                    xunheRecord.setIsFFBY(tmp);
                }
                else if(type == 10){
                    xunheRecord.setIsHZTS(tmp);
                }
                else if(type == 11){
//                    xunheRecord.setIsSSWF(tmp);
                    xunheRecord.setIsYXSZ(tmp);

                }else if(type == 12){
                    xunheRecord.setIsHDZZ(tmp);
                }

                dialogInterface.dismiss();
//                River river = mRivers.getStr(i);
//                startXunHe(river);
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        if(v == view1){
            showSingleChoiceDialog(1,tv1_result,item1_array,tv1_result.getText().toString());
        }
        else if(v==view2){
            showSingleChoiceDialog(2,tv2_result,item2_array,tv2_result.getText().toString());
        }
        else if(v==view3){
            showSingleChoiceDialog(3,tv3_result,item3_array,tv3_result.getText().toString());
        }
        else if(v==view4){
            showSingleChoiceDialog(4,tv4_result,item4_array,tv4_result.getText().toString());
        }
        else if(v==view5){
            showSingleChoiceDialog(5,tv5_result,item5_array,tv5_result.getText().toString());
        }
        else if(v==view6){
            showSingleChoiceDialog(6,tv6_result,item6_array,tv6_result.getText().toString());
        }
        else if(v==view7){
            showSingleChoiceDialog(7,tv7_result,item7_array,tv7_result.getText().toString());
        }
        else if(v==view8){
            showSingleChoiceDialog(8,tv8_result,item8_array,tv8_result.getText().toString());
        }
        else if(v==view9){
            showSingleChoiceDialog(9,tv9_result,item9_array,tv9_result.getText().toString());
        }
        else if(v==view10){
            showSingleChoiceDialog(10,tv10_result,item10_array,tv10_result.getText().toString());
        }
        else if(v==view11){
            showSingleChoiceDialog(11,tv11_result,item11_array,tv11_result.getText().toString());
        }
        else if(v==view12){
            showSingleChoiceDialog(12,tv12_result,item12_array,tv12_result.getText().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG,"onActivityResult()  resultCode="+resultCode+" , requestCode="+requestCode);

        if (resultCode == RESULT_OK) {

            if(requestCode == Constant.REQ_CODE_ADD_REPORT_FROM_COMPLETE_XUNHE){
                updateListView();
            }
            else if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
                mImageUri = getCaptureUri();
                scaleImage(mImageUri);
                updateImage();
//                uploadImage();

            } else if(requestCode == ALBUM_IMAGE_ACTIVITY_REQUEST_CODE){
                mImageUri = data.getData();
                scaleImage(mImageUri);
                updateImage();
//                uploadImage();

            }
        }
    }

    public void updateImage(){
        if(mImageUri != null){
//            mCapBitmap = BitmapTool.getBitmapFormUri(getApplicationContext(), mImageUri, false);
            Bitmap addbmp= BitmapTool.getBitmapFormUri(getApplicationContext(), mImageUri, false);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);

//            imageItem.size()-1
            imageItem.add(imageItem.size()-1,map);
//            imageItem.add(1,map);
//            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.griditem_addpic,
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView i = (ImageView)view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
        }
    }

    private void updateListView(){

        Log.d(TAG,"updateListView()");

        feedbackBeens = DrivingRecordLogic.getInstance().getFeedbackBeans();
        mReportAdapter.setList(feedbackBeens);
    }

    private ProgressDialog waitingDialog;

    private void showWaitingDialog() {
        waitingDialog=
                new ProgressDialog(this);
        waitingDialog.setIcon(R.drawable.bulb);
        waitingDialog.setTitle("提示");
        waitingDialog.setMessage("提交中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    private void dismissWaitingDialog(){
        if(waitingDialog!= null && waitingDialog.isShowing()){
            waitingDialog.dismiss();
        }
    }

    public  void doAddPic(View v){
        dialogList();
    }

    private void scaleImage(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > 800 || height > 800) {
                if (width > height) {
                    float scaleRate = (float) (800.0 / width);
                    width = 800;
                    height = (int) (height * scaleRate);
                    Bitmap map = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    saveBitmap(map);
                } else {
                    float scaleRate = (float) (800.0 / height);
                    height = 800;
                    width = (int) (width * scaleRate);
                    Bitmap map = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    saveBitmap(map);
                }
            } else {
                saveBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadImage(final File file, final int position){

        Log.d(TAG,"uploadImage()");

//        final File file = new File("/sdcard/hechang/feedback/image.jpg");
        if(!file.exists()){
            Toast.makeText(CompleteXunheActivity.this,"文件不存在",Toast.LENGTH_SHORT);
            return ;
        }

        imglist.add(HttpAssist.FAILURE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpAssist.uploadSpotFile(file);
//                reportimg += str+",";

//                if(imglist.size()>position){
//
//                } else {
//                    imglist.add(str);  //添加
//                }

                imglist.set(position,str) ; //更新
//                imglist.add(0,str);

                Log.d(TAG,"uploadImage()  str="+str+ ", imglist.size()="+imglist.size());

                LogUtil.d("chenwei" , "uploadImage()  str="+str);
            }
        }).start();
    }

    private void saveBitmap(Bitmap map) {
//        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");

        File tmp  = MyApp.getApp().getExternalFilesDir(null);
//        Log.d(TAG,"createFile2()  path = "+tmp.getPath());
//         =tmp.getPath()+File.separator+"hechang/trace";

        File file = new File(tmp.getPath()+File.separator+"hechang/feedback/image.jpg");
        File cachefile = new File(tmp.getPath()+File.separator+"hechang/feedback/image"+curPosition+".jpg");

//        Toast.makeText(this,""+cachefile.getPath(),Toast.LENGTH_SHORT).show();
        Log.d(TAG,"saveBitmap()  file = "+file.getPath()+" , cachefile="+cachefile.getPath());

//        File file = new File("/sdcard/hechang/feedback/image.jpg");
//        Bitmap cachebitmap = map;
//        File cachefile = new File("/sdcard/hechang/feedback/image"+curPosition+".jpg");

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            map.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            map.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bos.flush();
            bos.close();


            BufferedOutputStream cachebos = new BufferedOutputStream(new FileOutputStream(cachefile));
//            map.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            map.compress(Bitmap.CompressFormat.JPEG, 50, cachebos);
            cachebos.flush();
            cachebos.close();

//            updateImg(file);//上传图片接口
            uploadImage(file,curPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void reuploadImage(final int position){
        Log.d(TAG,"reuploadImage()");

        Toast.makeText(this,"重新上传图片",Toast.LENGTH_SHORT).show();


        File tmp  = MyApp.getApp().getExternalFilesDir(null);

        final File file = new File(tmp.getPath()+File.separator+"hechang/feedback/image"+position+".jpg");
//        final File file = new File("/sdcard/hechang/feedback/image"+position+".jpg");

//        final File file = new File("/sdcard/hechang/feedback/image.jpg");
        if(!file.exists()){
            Toast.makeText(CompleteXunheActivity.this,"文件不存在",Toast.LENGTH_SHORT);
            return ;
        }

//        imglist.add(HttpAssist.FAILURE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpAssist.uploadSpotFile(file);
                imglist.set(position,str) ; //更新
                Log.d(TAG,"reuploadImage()  str="+str+ ", imglist.size()="+imglist.size());
                LogUtil.d("chenwei" , "reuploadImage()  str="+str);
                Looper.prepare();
                Toast.makeText(CompleteXunheActivity.this,"返回地址 : "+str,Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();


    }


    /**
     * 相机拍照后输出照片的路径
     *
     * @return
     */
    public Uri getCaptureUri() {
        return Uri.fromFile(new File(mImagePath + IMAGE_NAME));
    }

    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CompleteXunheActivity.this);
        builder.setMessage("提示");
        builder.setTitle("删除该图片");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Log.d(TAG,""+imageItem.size()+" , "+imglist.size());
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();

                Log.d(TAG,"remove img position="+position);
//                    imglist.remove(position-1);
                imglist.remove(position);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 列表
     */
    private void dialogList() {

        if (ContextCompat.checkSelfPermission(CompleteXunheActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

//            Toast.makeText(CompleteXunheActivity.this,"mei shou quan ",Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(CompleteXunheActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    11);
        }else{
            //
//            Toast.makeText(CompleteXunheActivity.this," shou quan ",Toast.LENGTH_SHORT).show();

            final String items[] = {"拍照", "相册"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this,3);
            builder.setTitle("提示");
            // builder.setMessage("是否确认退出?"); //设置内容
            builder.setIcon(R.drawable.bulb);
            // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(CompleteXunheActivity.this, items[which],
                            Toast.LENGTH_SHORT).show();

                    switch (which) {
                        case 0:
                            Uri cameraUri = getCaptureUri();
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                            cameraIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 2 * 1024);
                            cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
                                    ActivityInfo.SCREEN_ORIENTATION_USER);
                            startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                            break;

                        case 1:
                            Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            albumIntent.setType("image/*");
                            startActivityForResult(albumIntent, ALBUM_IMAGE_ACTIVITY_REQUEST_CODE);
                            break;
                    }

                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create().show();

        }

    }

    /**
     * 自定义适配器
     *
     * @author wei.chen
     */
    private class ReportAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<ReqFeedbackBean> mList = null;
        private Context mContext;

//        private PoiInfo tmp;

        public ReportAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public void setList(List<ReqFeedbackBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        public List<ReqFeedbackBean> getList() {
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
                convertView = mInflater.inflate(R.layout.xunhe_end_report_list_item, null);
                holder = new ViewHolder();
                holder.content = (TextView) convertView.findViewById(R.id.xunhe_end_report_item_content);
//                holder.address = (TextView) convertView.findViewById(R.id.search_list_item_address);
//                holder.look = (TextView) convertView.findViewById(R.id.search_list_item_look);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.content.setText(mList.get(position).getReportContent());
//            holder.address.setText(mList.getStr(position).getBussinessNo()+"  "+mList.getStr(position).getCreateTime());
            return convertView;
        }

        class ViewHolder {
            //            View container;
//            TextView name;
            TextView content;
            //            ImageView image;
            TextView look;
        }
    }

}
