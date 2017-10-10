package saiwei.com.river.util;

import android.util.Log;

//import com.zhongka.park.global.Global;
//import com.zhongka.park.logic.CarLogic;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

public class HttpAssist {
    private static final String TAG = "chenwei.uploadFile";
    private static final int TIME_OUT = 15 * 1000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";

    public static String uploadSpotFile(File file) {
        String BOUNDARY =   "---------------------------123821742118716"; //"170911356213484";//UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
//        String RequestURL = "http://192.168.0.100:7080/YkyPhoneService/Uploadfile1";
//        String RequestURL = "http://xtcetc.com/picture/upload_spot_pic/";
//        String RequestURL = "http://www.longyan.cn/image/upload/user/default";
        String RequestURL = "http://www.longyan.cn/file/upload/system";

        Log.i(TAG,"uploadSpotFile()  url = "+RequestURL+" , length = "+file.length());

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            conn.setRequestProperty("Charset", CHARSET); // 设置编码

            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();

                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"pic_tag\"; filename=\"12X323240-11649.jpg\"" + LINE_END);
//                sb.append("Content-Disposition: form-data; name=\"").append("photo1").append("\"").append(LINE_END).append(LINE_END);
                sb.append("Content-Type:image/jpeg" + LINE_END);
                sb.append(LINE_END);
                Log.i(TAG,""+sb.toString().getBytes().length);

                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());

                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();

                Log.i(TAG, "end_data.length=" + end_data.length);

                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200) {
//                    return SUCCESS;
                }
                InputStream in = new BufferedInputStream(conn.getInputStream());

                BufferedReader reader = null;
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                StringBuffer sb_in = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb_in.append(line);
                }
//                {
//                    "Message": "upload success",
//                        "Result": "SUCCESS",
//                        "ori_path": "asdasdsa",
//                }
//                try {
//                    JSONObject json = new JSONObject(sb_in.toString());
//                    String result = json.optString("Result");
//
//                    if (result.equals(Global.RSP_RESULT_SUCCESS)) {
//                        String ori_path = json.optString("ori_path");
//                        if(type == CarLogic.getInstance().TYPE_PIC_IDENT_FRONT_01){
//                            CarLogic.getInstance().ori_path_01 = ori_path;
//                        } else if(type == CarLogic.getInstance().TYPE_PIC_IDENT_BACK_02){
//                            CarLogic.getInstance().ori_path_02 = ori_path;
//                        } else if(type == CarLogic.getInstance().TYPE_PIC_CERT_FRONT_03){
//                            CarLogic.getInstance().ori_path_03 = ori_path;
//                        } else if(type == CarLogic.getInstance().TYPE_PIC_CERT_BACK_04){
//                            CarLogic.getInstance().ori_path_04 = ori_path;
//                        } else if(type == CarLogic.getInstance().TYPE_PIC_SPOT_LEAD_05){
//                            CarLogic.getInstance().ori_path_05 = ori_path;
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                Log.i(TAG, "server: " + sb_in);

                return sb_in.toString();
            }
        } catch (MalformedURLException e) {
            Log.e(TAG,""+e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG,""+e.toString());
            e.printStackTrace();
        }
        return FAILURE;
    }

    public static String uploadFile(File file, String sid) {
        String BOUNDARY = "170911356213484";//UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
//        String RequestURL = "http://192.168.0.100:7080/YkyPhoneService/Uploadfile1";
        String RequestURL = "http://xtcetc.com/auth_user/update_headimage/";

        Log.i(TAG,"uploadFile()  url = "+RequestURL+" , length = "+file.length());

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            conn.setRequestProperty("Cookie","sessionid="+sid);

            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();

                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"pic_tag\"; filename=\"12X319223240-11649.jpg\"" + LINE_END);


//                sb.append("Content-Disposition: form-data; name=\"").append("photo1").append("\"").append(LINE_END).append(LINE_END);
                sb.append("Content-Type:image/jpeg" + LINE_END);
                sb.append(LINE_END);

                Log.i(TAG,""+sb.toString().getBytes().length);

                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();


                Log.i(TAG, "end_data.length=" + end_data.length);

                dos.write(end_data);
                dos.flush();

                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200) {
//                    return SUCCESS;
                }


                InputStream in = new BufferedInputStream(conn.getInputStream());

                BufferedReader reader = null;
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                StringBuffer sb_in = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb_in.append(line + "\n");
                }

                Log.i(TAG, "server: " + sb_in);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG,""+e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG,""+e.toString());
            e.printStackTrace();
        }
        return FAILURE;
    }
}
