package saiwei.com.river.logic;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.maps2d.model.LatLng;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import saiwei.com.river.MyApp;
import saiwei.com.river.util.DrivingRecordTool;
import saiwei.com.river.entity.ReqFeedbackBean;
import saiwei.com.river.model.GpsInfo;
import saiwei.com.river.model.River;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.util.SharePreferenceUtil;
import timber.log.Timber;

/**
 * Created by saiwei on 9/21/17.
 */

public class DrivingRecordLogic {

    private static final String TAG = "chenwei.DrivingLogic";

    /** 当前gps信息目录 */
//    public static String CUR_GPS_DIR =   "/sdcard/hechang/trace";
    public static String CUR_GPS_DIR ;//=Environment.getExternalStorageDirectory().getPath()+File.separator+"hechang/trace";

    private void test(){
        File tmp  = MyApp.getApp().getExternalFilesDir(null);
        Log.d(TAG,"createFile2()  path = "+tmp.getPath());

        String sf = tmp.getPath()+File.separator+"hechang_test2";
    }

    /**
     * 单例，静态变量。请记得销毁
     */
    private static DrivingRecordLogic instance = null;

    public static DrivingRecordLogic getInstance() {
        if (instance == null) {
            instance = new DrivingRecordLogic();
        }
        return instance;
    }

    /**
     * 构造方法
     */
    private DrivingRecordLogic() {
        // mContext = context;
        // dealOldTraceData();
//        Log.i(TAG2, "构造方法");
//        requestLocation();
        File tmp  = MyApp.getApp().getExternalFilesDir(null);
//        Log.d(TAG,"createFile2()  path = "+tmp.getPath());
        CUR_GPS_DIR =tmp.getPath()+File.separator+"hechang/trace";
//        String sf = tmp.getPath()+File.separator+"hechang_test2";
    }

    private boolean isRecord;
    private XunheRecord curXunheRecord;

    public XunheRecord getCurXunheRecord() {
        return curXunheRecord;
    }

    public void setCurXunheRecord(XunheRecord curXunheRecord) {
        this.curXunheRecord = curXunheRecord;
    }

    public boolean isExistFile(long record_id) {
        File file = new File(CUR_GPS_DIR + "/" + record_id);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 外部接口：开始记录gps信息
     */
    public void startRecord(River river) {

        if(river == null){
            return;
        }

        isRecord = true;

//        start_poi = new UIPoi(AnMap.getMyLatLng());
//
//        setDefaultFinishStatus();
//        mCurDrivingResult = null;
//        isGpsRecord = true;
//        isRecordOverSpeed = false;
//        isRecordYaw = false;
//
//        curFileUrl = System.currentTimeMillis();
//
//        curFile = DrivingRecordTool.createGpsFileName(curFileUrl);
//        Tool.LOG_I(TAG2, "startRecordGps()  curFileUrl=" + curFileUrl);
        curFileName = System.currentTimeMillis();

        Timber.d("startRecord() curFileName=%d",curFileName);

        curXunheRecord = new XunheRecord();
        curXunheRecord.setRecordId(curFileName);
        curXunheRecord.setUserid(AccoutLogic.getInstance().getUserId());
        curXunheRecord.setReportRiver(river.getRiverName());
        curXunheRecord.setReportRiverId(river.getRiverBaseinfoId());
        String createtime = DrivingRecordTool.conversionTime(curFileName);
        curXunheRecord.setTourTime(createtime);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("recordId",curFileName);
            jsonObject.put("userid",AccoutLogic.getInstance().getUserId());
            jsonObject.put("reportRiver",river.getRiverName());
            jsonObject.put("reportRiverId",river.getRiverBaseinfoId());
            jsonObject.put("tourTime",createtime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharePreferenceUtil.getInstance().putStr(SharePreferenceUtil.SHARE_PREFERENCE_LASTXUNHE,curFileName+"");

        SharePreferenceUtil.getInstance().putStr(SharePreferenceUtil.SHARE_PREFERENCE_LASTXUNHE_RECORD,jsonObject.toString());
    }


    public void continueRecord(long filename){

        Timber.d("continueRecord()  filename=%d",filename);

        curFileName = filename;

        isRecord = true;
    }

    public boolean isRecord(){
        return isRecord;
    }

    public void stopRecord(){
        isRecord = false;

        SharePreferenceUtil.getInstance().putStr(SharePreferenceUtil.SHARE_PREFERENCE_LASTXUNHE,"");
    }

    public void stopRecord(XunheRecord xunheRecord){



        isRecord = false;

        AccoutLogic.getInstance().insertDBXunheRecord(xunheRecord);

        SharePreferenceUtil.getInstance().putStr(SharePreferenceUtil.SHARE_PREFERENCE_LASTXUNHE,"");

        //清空投诉数组
        feedbackBeens.clear();
        setCurXunheRecord(null);
    }

    public void recordGps(){

    }

    public long curFileName;


    public void writeLocationGpsLog(String str){

        String filename;
        if(curFileName<1){
            filename = System.currentTimeMillis()+"_loc";
        } else{
            filename = curFileName+"_loc";
        }

        if(!TextUtils.isEmpty(filename)){
            writeGpsLog(filename,str);
        }  else {
            Log.d(TAG,"writeGpsLog  filenname is  null")   ;
        }
    }


    public void writeYuanshiGpsLog(String str){
        String filename = curFileName+"_yuanshi";

        if(!TextUtils.isEmpty(filename)){
            writeGpsLog(filename,str);
        }  else {
            Log.d(TAG,"writeGpsLog  filenname is  null")   ;
        }
    }

    public void writeGpsLog(String str){

        String filename = curFileName+"";

        if(!TextUtils.isEmpty(filename)){
            writeGpsLog(filename,str);
        }  else {
            Log.d(TAG,"writeGpsLog  filenname is  null")   ;
        }
    }


    /**
     * 将GPS信息，写成文本，保存到sdcard 上
     *
     * @param fileName
     * @param str
     */
    private void writeGpsLog(String fileName, String str) {
        File logFile = new File(CUR_GPS_DIR);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }

        FileOutputStream trace = null;
        try {
            File file = new File(CUR_GPS_DIR + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            trace = new FileOutputStream(file, true);
            trace.write((str + "\r\n").getBytes());
            trace.flush();
        } catch (Exception e) {
            e.toString();
//			writeTestFIle(DrivingRecordTool.getStandardFormatTime(System.currentTimeMillis())
//					+ " , writeGpsLog() exception=" + e.toString());
        } finally {
            if (trace != null) {
                try {
                    trace.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long cache_starttime;
    private long cache_endtime;

    public int getContinueTime(){

        Timber.d("getContinueTime() cache_starttime=%d   cache_endtime=%d",cache_starttime,cache_endtime);

        int difftime = 0;

        if(cache_starttime == 0 || cache_endtime == 0){
            difftime = 0;
        } else {
            difftime = (int) ((cache_endtime-cache_starttime)/1000);
        }

        Timber.d("getContinueTime() cache_starttime=%d , cache_endtime=%d , difftime=%d",cache_starttime,cache_endtime,difftime);

        return difftime;
    }

    public ArrayList<LatLng>  readFromFile(String filename){

        Log.d(TAG,"readFromFile()  filename="+filename);
//        File targetFile=new File("/sdcard/hechang/trace/"+filename);


        File targetFile=new File(CUR_GPS_DIR+"/"+filename);

        Timber.d("readFromFile()  targetFile=%s",targetFile.getPath());

            String readedStr="";
            try{
                InputStream in = new BufferedInputStream(new FileInputStream(targetFile));
                BufferedReader br= new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String tmp;
                int x = 0;
//                     String [] arr = new String[60];
                ArrayList<LatLng> list = new ArrayList<LatLng>();
                while((tmp=br.readLine())!=null){

                    String[] strs = tmp.split(",");

                    if(x==0){
                        cache_starttime = Long.parseLong(strs[0].trim());
                    } else {
                        cache_endtime = Long.parseLong(strs[0].trim());
                    }

                    double lat = Double.parseDouble(strs[1]);
                    double lon = Double.parseDouble(strs[2]);
                    LatLng latLng = new LatLng(lat,lon);

                    list.add(latLng);

//                    List.add(x, tmp) ;
//                       arr[x] = tmp;
//                    System.out.println("123+"+List);
//                       System.out.println("123+"+arr[x]);
                    x++;
                }
                br.close();
                in.close();
                return list;
//                     return tmp;
            } catch (Exception e) {
//                Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
//                return e.toString();

                Timber.e(e.toString());

                return null;
            }
    }

    ArrayList<ReqFeedbackBean> feedbackBeens = new ArrayList<>();


    public void addFeedbackBean(ReqFeedbackBean bean){
        feedbackBeens.add(bean);
    }

    public ArrayList<ReqFeedbackBean>  getFeedbackBeans(){
        return feedbackBeens;
    }


    public ArrayList<GpsInfo>  readFromFileGpsInfos(String filename){

        Log.d(TAG,"readFromFile()  filename="+filename);

//        File targetFile=new File("/sdcard/hechang/trace/"+filename);
        File targetFile=new File(CUR_GPS_DIR+"/"+filename);
        String readedStr="";
        try{
            InputStream in = new BufferedInputStream(new FileInputStream(targetFile));
            BufferedReader br= new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tmp;
            int x = 0;
//                     String [] arr = new String[60];
            ArrayList<GpsInfo> list = new ArrayList<GpsInfo>();
            while((tmp=br.readLine())!=null){


                String[] strs = tmp.split(",");

//                double time = Double.parseDouble(strs[0]);

                double lat = Double.parseDouble(strs[1]);
                double lon = Double.parseDouble(strs[2]);
                LatLng latLng = new LatLng(lat,lon);

                GpsInfo gpsInfo = new GpsInfo();
                gpsInfo.setTime(strs[0].trim()+"");
                gpsInfo.setLatLng(latLng);

                list.add(gpsInfo);

//                    List.add(x, tmp) ;
//                       arr[x] = tmp;
//                    System.out.println("123+"+List);
//                       System.out.println("123+"+arr[x]);
//                    x++;
            }
            br.close();
            in.close();
            return list;
        } catch (Exception e) {
            return null;
        }
    }



}
