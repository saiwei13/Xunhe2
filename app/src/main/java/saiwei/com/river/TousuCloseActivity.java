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
import retrofit2.http.Field;
import saiwei.com.river.entity.ReqFeedbackBean;
import saiwei.com.river.entity.RspCloseTousu;
import saiwei.com.river.entity.RspFeedBack;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.logic.DrivingRecordLogic;
import saiwei.com.river.logic.RetrofitLogic;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.util.BitmapTool;
import saiwei.com.river.util.HttpAssist;

/**
 * Created by saiwei on 9/23/17.
 */
public class TousuCloseActivity extends Activity {

    private final String TAG = "chenwei.TousuCloseAct";

    private ImageButton mTitleLeft;
    private TextView mTitleName;


    @BindView(R.id.feedback_tousu_content)
    EditText mEDTousuContent;

    ReqFeedbackBean mFeedbackBean;

    private GridView gridView1;
    private Bitmap bmp;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;


    private String taskId,publicReportId;

    List<String> imglist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_tousu_close);
        ButterKnife.bind(this);


        taskId = getIntent().getStringExtra(Constant.taskId);
        publicReportId = getIntent().getStringExtra(Constant.publicReportId);


        initView();
        initData();
    }

    private void initView() {
        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("关闭投诉");
        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

        gridView1 = (GridView) findViewById(R.id.feedback_gridView1);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic); //???
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
                    Toast.makeText(TousuCloseActivity.this, "最多上传9张", Toast.LENGTH_SHORT).show();
                }
                else if(position == 0) {
                    doAddPic(null);
                }else {
                    dialog(position);
                }
            }
        });

        setListViewHeightBasedOnChildren(gridView1);
    }

    XunheRecord xunheRecord;
    String mImagePath = "";
    private static final String IMAGE_NAME = "image.jpg";

    String userid ;
    String reportContent ;
    String reportPhone= "13720823605";
    String reportName ;
    String reportRiverId ;
    String reportRiver ;
    String realAddr ;

    private void initData(){
        xunheRecord = DrivingRecordLogic.getInstance().getCurXunheRecord();

        if(xunheRecord != null){
            reportRiverId = xunheRecord.getReportRiverId();
            reportRiver = xunheRecord.getReportRiver();
        } else {
            //TODO
            reportRiverId ="2";
                    reportRiver = "测试曹溪1";
        }

        userid = AccoutLogic.getInstance().getUserId();
        reportName = AccoutLogic.getInstance().getUserName(userid);
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

    //TODO
    @OnClick({R.id.feedback_submit})
    public void doClose(){

        String content = mEDTousuContent.getText().toString();

        if(TextUtils.isEmpty(content)){

            Toast.makeText(TousuCloseActivity.this,"投诉内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        String reportimg = "";
        for(int i=0;i<imglist.size();i++){
            if(i== imglist.size()-1){
                reportimg += imglist.get(i);
            }else {
                reportimg += imglist.get(i)+",";
            }
        }
        doCommit(userid,publicReportId,taskId,content,reportimg);
    }

    public  void doCommit(String userId, String publicReportId, String taskId, String content, String processImg){

        Call<RspCloseTousu> call = RetrofitLogic.getInstance().getService().doCloseComplaint(
                userId,publicReportId,taskId,content,processImg
        );
        call.enqueue(new Callback<RspCloseTousu>() {

            @Override
            public void onResponse(Call<RspCloseTousu> call, Response<RspCloseTousu> response) {
                Log.d(TAG,"onResponse()  "+response.body());
                RspCloseTousu bean = response.body();

                Log.d(TAG,"onResponse() bean.getRtnCode()="+bean.getRtnCode());

                if(bean.getRtnCode().equals("000000")){
                    Toast.makeText(TousuCloseActivity.this,"关闭投诉成功",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TousuCloseActivity.this,"提交失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RspCloseTousu> call, Throwable t) {
                Log.d(TAG,"onFailure() "+t.toString());
                Toast.makeText(TousuCloseActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
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
            switch (requestCode) {
                // 拍照
                case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:

                    mImageUri = getCaptureUri();

                    scaleImage(mImageUri);
                    updateImage();
//                    uploadImage();
                    break;
                // 相册
                case ALBUM_IMAGE_ACTIVITY_REQUEST_CODE:
                    mImageUri = intent.getData();
                    scaleImage(mImageUri);
                    updateImage();
//                    uploadImage();
                    break;
                default:
                    break;
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
                Toast.makeText(TousuCloseActivity.this, items[which],
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

    private void uploadImage(final File file){

        Log.d(TAG,"uploadImage()");

//        final File file = new File("/sdcard/hechang/feedback/image.jpg");
        if(!file.exists()){
            Toast.makeText(TousuCloseActivity.this,"文件不存在",Toast.LENGTH_SHORT);
            return ;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpAssist.uploadSpotFile(file);
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

    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        SimpleAdapter listAdapter = (SimpleAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TousuCloseActivity.this);
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
}
