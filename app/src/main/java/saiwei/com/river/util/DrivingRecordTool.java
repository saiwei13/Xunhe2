package saiwei.com.river.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import saiwei.com.river.Constant;

/**
 * 工具类
 *
 * @author chenwei
 *
 */
public class DrivingRecordTool {

	private static final String TAG2 = "chenwei.DrivingRecordTool";



	public static String getDiffTime(String  start_time,String  end_time){


		Date d1 = null;
		String diff_time = "";
		try {
			d1 = simpleDateFormat_2.parse(start_time);

			Date d2 = simpleDateFormat_2.parse(end_time);
			long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//		System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
			diff_time  = +hours+"小时"+minutes+"分";

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return diff_time;
	}

	/**
	 * 设备版本号
	 *
	 * @return
	 */
	public static String getPhoneVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	private static String format = "yyyyMMddHHmmss";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

	/**
	 * 将系统时间转化成标准格式的时间 ex: 1402381622815------->20140610142702
	 *
	 * @param _time
	 * @return
	 */
	public static String getStandardFormatTime(long _time) {
		String time = simpleDateFormat.format(new Date(_time));
		return time;
	}

	/**
	 * 将标准格式的时间转化成系统时间 ex: 20140610142702------>1402381622815
	 *
	 * @param time
	 * @return
	 */
	public static long getSystemFormatTime(String time) {
		long tmp_long_time = 0;
		try {
			tmp_long_time = simpleDateFormat.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tmp_long_time;
	}

	private static String format_2 = "yyyy/MM/dd";

//	2017-09-15 15:02:50

	public static SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private static SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat(format_2);

	/**
	 * ex： 2014/06/18
	 *
	 * @param _time
	 * @return
	 */
	public static String conversionTime(long _time) {
		return simpleDateFormat_2.format(new Date(_time));
	}

	public static int findPosition(String str){
		int index    =   -1;
		for(int i = 0; i< Constant.type_array_str.length; i++){
			if(Constant.type_array_str[i].contains(str)){
				index    =    i;
				break;
			}
		}
		return index;
	}

	/**
	 *
	 * @param name
	 * @return
	 */
	public static String getShortName(String name) {

		String str = "未知道路";
		if (!TextUtils.isEmpty(name)) {
			if (name.length() > 5) {
				str = name.substring(0, 5) + "..";
			} else {
				str = name;
			}
		}
		return str;
	}

	public static void makeText(Context context, String str) {
		if (context != null) {
			Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		}
	}

	public static void makeText(Context context, int strid) {
		if (context != null) {
			Toast.makeText(context, strid, Toast.LENGTH_SHORT).show();
		}
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

	/**
	 * 创建gps文件
	 *
	 * @param currenttimemillis
	 * @return
	 */
	public static String createGpsFileName(long currenttimemillis) {
		String filename = dateFormat.format(new Date(currenttimemillis));
		return filename + ".gps";
	}

	/**
	 * 判断是否有网络连接，wifi或者手机卡网络连接
	 *
	 * @return true 有连接， false 没有
	 */
	public static boolean isConnectInternet(Context context) {
		if (context != null) {
			// 判断网络是否可用
			ConnectivityManager cntmng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cntmng == null) {
				return false;
			}
			NetworkInfo netInfo = cntmng.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnected()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取屏幕的宽高
	 *
	 * @return int[0]-屏幕宽度；int[1]-屏幕高度
	 */
	public int[] getScreenSize(Context context) {
		if (context != null) {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display dp = wm.getDefaultDisplay();
			int[] mScreenSize = new int[2];
			mScreenSize[0] = dp.getWidth();
			mScreenSize[1] = dp.getHeight();
			return mScreenSize;
		} else {
			return null;
		}
	}

	/**
	 * 计算总里程 (单位： km)
	 *
	 * @param dis
	 *            　单位：ｍ
	 * @return
	 */
	public static long countTotalDistance(double dis) {
		return Math.round(dis / 100) / 10;
	}

	/** 根据分数等级评星星数 **/
	public static float getStarNum(double score) {
		float startNum = 0;

		if (score >= 90) {
			startNum = 5;
		} else if (score >= 80) {
			startNum = 4;
		} else if (score >= 70) {
			startNum = 3;
		} else if (score >= 60) {
			startNum = 2;
		} else if (score > 0) {
			startNum = 1;
		} else {
			startNum = 0;
		}
		return startNum;
	}

	/**
	 * 获取当前系统语言，返回值参考Const.LANGUAGE_ID_CHINESE等系列常量
	 *
	 * @return // 语种 public static final int LANGUAGE_ID_CHINESE =0; // 中文
	 *         public static final int LANGUAGE_ID_ENGLISH =1; // 英文 public
	 *         static final int LANGUAGE_ID_TRADITIONAL =2; // 繁体
	 */
	// public static GLanguage getSystemLanguage(Context context)
	// {
	// if(context != null){
	// final Locale locale = context.getResources().getConfiguration().locale;
	// if(locale.equals(Locale.SIMPLIFIED_CHINESE)) {
	// return GLanguage.GLANGUAGE_SIMPLE_CHINESE;
	// } else if(locale.equals(Locale.TRADITIONAL_CHINESE)
	// ||locale.toString().equals("zh_HK")){
	// return GLanguage.GLANGUAGE_TRADITIONAL_CHINESE;
	// } else{
	// return GLanguage.GLANGUAGE_ENGLISH;
	// }
	// }else {
	// return GLanguage.GLANGUAGE_SIMPLE_CHINESE;
	// }
	//
	// }

	/**
	 * TODO 获取轨迹点对应的经纬度外接框(top>bottom,right>left)
	 *
	 * @param traceGcoords
	 * @return
	 */
	// public static Rect getCoordRect(GCoord[] traceGcoords){
	//
	// if(traceGcoords == null || traceGcoords.length <= 0)
	// {
	// return null;
	// }
	//
	// int left = traceGcoords[0].x;
	// int top = traceGcoords[0].y;
	// int right = traceGcoords[0].x;
	// int bottom = traceGcoords[0].y;
	//
	// int length = traceGcoords.length;
	//
	// for(int i = 1; i < length; i++)
	// {
	// if(left > traceGcoords[i].x)
	// {
	// left = traceGcoords[i].x;
	// }
	// else if(right < traceGcoords[i].x)
	// {
	// right = traceGcoords[i].x;
	// }
	//
	// if(bottom > traceGcoords[i].y)
	// {
	// bottom = traceGcoords[i].y;
	// }
	// else if(top < traceGcoords[i].y)
	// {
	// top = traceGcoords[i].y;
	// }
	// }
	//
	// return new Rect(left, top, right, bottom);
	// }

	/**
	 * 判断文件是否存在
	 *
	 * @param str
	 * @return
	 */
	public static boolean isFileExist(String str) {
		File file = new File(str);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将GPS信息，写成文本，保存到sdcard 上
	 *
	 * @param fileName
	 * @param str
	 */
	private void writeGpsLog(String dir,String fileName, String str) {
		File logFile = new File(dir);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}

		FileOutputStream trace = null;
		try {
			File file = new File(dir + "/" + fileName);
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

	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

}
