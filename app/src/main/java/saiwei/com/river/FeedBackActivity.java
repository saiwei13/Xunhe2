package saiwei.com.river;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saiwei.com.river.entity.ReqFeedbackBean;
import saiwei.com.river.entity.RspFeedBack;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.DrivingRecordLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.River;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.util.BitmapTool;
import saiwei.com.river.util.DrivingRecordTool;
import saiwei.com.river.util.HttpAssist;

import static saiwei.com.river.Constant.REQ_CODE_LOCATION;

/**
 * Created by saiwei on 9/23/17.
 *
 * 1. 从主页进来
 * 2. 从巡河上报进来
 * 3. 从结束巡河界面进来
 *
 */

public class FeedBackActivity extends Activity {

    private final String TAG = "chenwei.FeedBackAc";

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    @BindView(R.id.feedback_river_name)
    TextView mTVRiverName;

    @BindView(R.id.feedback_type)
    TextView mTVType;

    @BindView(R.id.feedback_address)
    TextView mTVAddress;

    @BindView(R.id.feedback_address_desc)
    EditText mEDAddrDesc;

    @BindView(R.id.feedback_tousu_content)
    EditText mEDTousuContent;

    ReqFeedbackBean mFeedbackBean;

    private GridView gridView1;
    private Bitmap bmp;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;

    private int fromType;

    XunheRecord xunheRecord;
    String mImagePath = "";
    private static final String IMAGE_NAME = "image.jpg";

    String userid ;
    String reportContent ;
    String reportPhone= "13720823605";
    String reportName ;
//    String reportRiverId ;
//    String reportRiver ;
    String realAddr ;
    String latitude;
    String longitude;
    String locationAddress;

//    String reportimg ="";

    List<String> imglist = new ArrayList<>();

    List<River> mRivers;

    @BindView(R.id.feedback_river_view)
    View mRiverLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        fromType = getIntent().getIntExtra(AccoutLogic.FROM_ACTIVITY,AccoutLogic.FROM_MAINACTIVITY);
        initView();
        initData();
    }

    private void initView() {
        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("问题上报");
        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

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
                    Toast.makeText(FeedBackActivity.this, "最多上传9张", Toast.LENGTH_SHORT).show();
                }
                else if(position == 0) {
                    doAddPic(null);
                } else {
                    dialog(position);
                }
            }
        });
    }

    private void initData(){

        if(fromType  ==  AccoutLogic.FROM_MAINACTIVITY){
            mRivers = AccoutLogic.getInstance().getRiverInfoFromDB();
            mRiverLayout.setEnabled(true);
//            reportRiverId ="2";
//            reportRiver = "请选择";
        } else if(fromType == AccoutLogic.FROM_MAPACTIVITY  || fromType==AccoutLogic.FROM_COMPLETEXUNHEACTIVITY){
            mRiverLayout.setEnabled(false);
            xunheRecord = DrivingRecordLogic.getInstance().getCurXunheRecord();
//            reportRiverId = xunheRecord.getReportRiverId();
//            reportRiver = xunheRecord.getReportRiver();
            mTVRiverName.setText(xunheRecord.getReportRiver());

            xunheRecord.getReportRiverId();
            mRiver = AccoutLogic.getInstance().getRiver(xunheRecord.getReportRiverId());
        }

        userid = AccoutLogic.getInstance().getUserId();
        reportName = AccoutLogic.getInstance().getUserName(userid);

//        mTVRiverName.setText(reportRiver);
//        mTVType.setText(type_array[0]);
        mImagePath = "/sdcard/hechang/feedback/";
        File image = new File(mImagePath);
        if (!image.exists()) {
            image.mkdirs();
        }
        mFeedbackBean = new ReqFeedbackBean();
        mFeedbackBean.setComplaintsType("01");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void updateImage(){
        if(mImageUri != null){
//            mCapBitmap = BitmapTool.getBitmapFormUri(getApplicationContext(), mImageUri, false);
            Bitmap addbmp=BitmapTool.getBitmapFormUri(getApplicationContext(), mImageUri, false);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(1,map);
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

    @OnClick({R.id.title_btn_left})
    public  void doBack(View v){
        finish();
    }

    @OnClick({R.id.feedback_addr_view})
    public  void doChooseAddr(View v){
        Intent intent = new Intent(FeedBackActivity.this,ReGeocoderActivity.class);
        startActivityForResult(intent, REQ_CODE_LOCATION);
    }

    @OnClick({R.id.feedback_river_view})
    public  void doChooseRiver(View v){

        showRiverDialog(null);
    }

    @OnClick({R.id.feedback_tousu_view})
    public  void doSelectType(View v){

        showTpyeDialog(v,mTVType.getText().toString());
    }

    @OnClick({R.id.feedback_submit})
    public void saveData(){

        reportContent = mEDTousuContent.getText().toString();
        realAddr = mEDAddrDesc.getText().toString();


        if(TextUtils.isEmpty(mTVRiverName.getText().toString())){
            Toast.makeText(this,"请选择河道名称",Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(mTVAddress.getText().toString())){
            Toast.makeText(this,"请选择位置",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(mTVType.getText().toString())){
            Toast.makeText(this,"请选择投诉类型",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(realAddr)){
            Toast.makeText(this,"请填写地址描述",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(reportContent)){
            Toast.makeText(this,"请填写反馈内容",Toast.LENGTH_SHORT).show();
        }else {


            mFeedbackBean.setUserId(userid);
//            mFeedbackBean.setCountyCode("350802000000");  //TODO
//            mFeedbackBean.setTownCode("350802006000");  //TODO
            mFeedbackBean.setCountyCode(mRiver.getCountyCode());
            mFeedbackBean.setTownCode(mRiver.getTownCode());
            mFeedbackBean.setLatitude(latitude);
            mFeedbackBean.setLongitude(longitude);
            mFeedbackBean.setLocationAddress(locationAddress);
            mFeedbackBean.setRealAddress(realAddr);
            mFeedbackBean.setReportContent(reportContent);
            mFeedbackBean.setReportName(reportName);
            mFeedbackBean.setReportPhone(reportPhone);

            if(fromType == AccoutLogic.FROM_MAINACTIVITY){

                mFeedbackBean.setReportRiverId(mRiver.getRiverBaseinfoId());
                mFeedbackBean.setReportRiver(mRiver.getRiverName());

            } else if(fromType == AccoutLogic.FROM_MAPACTIVITY || fromType==AccoutLogic.FROM_COMPLETEXUNHEACTIVITY){

                mFeedbackBean.setReportRiverId(xunheRecord.getReportRiverId());
                mFeedbackBean.setReportRiver(xunheRecord.getReportRiver());

            }

//            int positon = Arrays.binarySearch(Constant.type_array_str, mTVType.getText().toString());
            int positon =  DrivingRecordTool.findPosition(mTVType.getText().toString().trim());

            mFeedbackBean.setComplaintsType(Constant.type_array_int[positon]);

            String reportimg = "";

            for(int i=0;i<imglist.size();i++){
                if(i== imglist.size()-1){
                    reportimg += imglist.get(i);
                }else {
                    reportimg += imglist.get(i)+",";
                }
            }

            Log.d(TAG,"reportimg = "+reportimg);

            mFeedbackBean.setReportImg(reportimg);

            if(fromType == AccoutLogic.FROM_MAINACTIVITY){
                doCommit(null);
            } else if(fromType == AccoutLogic.FROM_MAPACTIVITY){
                DrivingRecordLogic.getInstance().addFeedbackBean(mFeedbackBean);
                finish();
            } else if(fromType == AccoutLogic.FROM_COMPLETEXUNHEACTIVITY){
                DrivingRecordLogic.getInstance().addFeedbackBean(mFeedbackBean);
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    public  void doCommit(View v){

        retrofit2.Call<RspFeedBack> call = RetrofitLogic.getInstance().getService().doReportFeedback(
                mFeedbackBean.getUserId(),
                mFeedbackBean.getCountyCode(),
                mFeedbackBean.getTownCode(),
                mFeedbackBean.getLatitude(),
                mFeedbackBean.getLongitude(),
                mFeedbackBean.getLocationAddress(),
                mFeedbackBean.getRealAddress(),
                mFeedbackBean.getReportContent(),
                mFeedbackBean.getReportName(),
                mFeedbackBean.getReportPhone(),
                mFeedbackBean.getReportRiverId(),
                mFeedbackBean.getReportRiver(),
                mFeedbackBean.getComplaintsType(),
                mFeedbackBean.getReportImg()
        );
        call.enqueue(new Callback<RspFeedBack>() {

            @Override
            public void onResponse(Call<RspFeedBack> call, Response<RspFeedBack> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspFeedBack bean = response.body();


                Log.d(TAG,"onResponse() bean.getRtnCode()="+bean.getRtnCode());

                if(bean.getRtnCode().equals("000000")){
                    Toast.makeText(FeedBackActivity.this,"提交成功",Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    Toast.makeText(FeedBackActivity.this,"提交失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RspFeedBack> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(FeedBackActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @OnClick({R.id.feedback_add_pic})
    public  void doAddPic(View v){
        dialogList();
    }

    /** 通过拍照或相册中获取到的图片 **/
    private Bitmap mCapBitmap;

    /** 图片URI **/
    private Uri mImageUri;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int ALBUM_IMAGE_ACTIVITY_REQUEST_CODE = 200;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
                mImageUri = getCaptureUri();

                scaleImage(mImageUri);
                updateImage();
//                uploadImage();

            } else if(requestCode == ALBUM_IMAGE_ACTIVITY_REQUEST_CODE){
                mImageUri = intent.getData();
                scaleImage(mImageUri);
                updateImage();
//                uploadImage();

            } else if(requestCode == Constant.REQ_CODE_LOCATION){
                if(intent != null){
                    latitude = intent.getStringExtra(Constant.latitude);
                    longitude = intent.getStringExtra(Constant.longitude);
                    locationAddress = intent.getStringExtra(Constant.locationAddress);
                    mTVAddress.setText(locationAddress);
                }
            }
        }
    }

    /**
     * 相机拍照后输出照片的路径
     *
     * @return
     */
    public Uri getCaptureUri() {
        return Uri.fromFile(new File(mImagePath + IMAGE_NAME));
    }

    /**
     * 列表
     */
    private void dialogList() {
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
                Toast.makeText(FeedBackActivity.this, items[which],
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

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 200);//图片输出大小
        intent.putExtra("outputY", 200);
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, 3);
    }

    /**
     * 将图片image压缩成大小为 size的图片（size表示图片大小，单位是KB）
     *
     * @param image
     *            图片资源
     * @param size
     *            图片大小
     * @return Bitmap
     */
    private Bitmap compressImage(Bitmap image, int size) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > size) {
            // 重置baos即清空baos
            baos.reset();
            // 每次都减少10
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);

        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }



    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    private void showTpyeDialog(View view, String select) {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.bulb);
        builder.setTitle("投诉类型");

        int positon = Arrays.binarySearch(Constant.type_array_str, select);

        builder.setSingleChoiceItems(Constant.type_array_str, positon, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                mTVType.setText(Constant.type_array_str[i]);
                mFeedbackBean.setComplaintsType(Constant.type_array_int[i]);
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    River mRiver;

    private void showRiverDialog(View view) {
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

                mTVRiverName.setText(mRivers.get(i).getRiverName());
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FeedBackActivity.this);
        builder.setMessage("提示");
        builder.setTitle("删除该图片");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


                if(position>0){
                    imageItem.remove(position);
                    simpleAdapter.notifyDataSetChanged();

                    Log.d(TAG,"remove img position="+position);
                    imglist.remove(position-1);
                }
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

    private void uploadImage(final File file){

        Log.d(TAG,"uploadImage()");

//        final File file = new File("/sdcard/hechang/feedback/image.jpg");
        if(!file.exists()){
            Toast.makeText(FeedBackActivity.this,"文件不存在",Toast.LENGTH_SHORT);
            return ;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpAssist.uploadSpotFile(file);
//                reportimg += str+",";
                imglist.add(0,str);
                Log.d(TAG,"uploadImage()  str="+str);
            }
        }).start();
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

    private void saveBitmap(Bitmap map) {
//        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");

        File file = new File("/sdcard/hechang/feedback/image.jpg");

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            map.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
//            updateImg(file);//上传图片接口
            uploadImage(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
