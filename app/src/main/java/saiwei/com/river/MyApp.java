package saiwei.com.river;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.zxy.tiny.Tiny;

import java.io.File;

import saiwei.com.river.db.DaoMaster;
import saiwei.com.river.db.DaoSession;
import timber.log.Timber;

/**
 * Created by saiwei on 9/22/17.
 */

public class MyApp extends Application {

    private static final String TAG = "chenwei.MyApp";

    private DaoSession daoSession;


    private static MyApp app;

    /**
     * 获取app
     * @return
     */
    public static Application getApp() {
        return app;
    }

    public static Context getContext() {
        return app.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG,"onCreate()");

        initGreenDao();
        initImageLoader();

        app = this;

//        CrashReport.initCrashReport(getApplicationContext(), "d1eb097298", true);
        Bugly.init(getApplicationContext(), "d1eb097298", true);
        Tiny.getInstance().init(this);

//        if(BuildConfig.DEBUG){
//            Timber.plant(new Timber.DebugTree());
//        } else {
////            Timber.plant(new CrashReportingTree());
//        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new ThreadAwareDebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }

    private void initGreenDao(){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this,"test.db");
        SQLiteDatabase db = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public void initImageLoader() {

        float widgt = getResources().getDimension(R.dimen.auto_dimen2_160);
        float height = getResources().getDimension(R.dimen.auto_dimen2_130);

//.memoryCacheExtraOptions(480, 800)

        // 获取默认的路径
//        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());

        File cacheDir = new File("/sdcard/hechang/cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        Log.d(TAG,"initImageLoader()  cacheDir="+cacheDir);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                // 设置内存图片的宽高
//                .memoryCacheExtraOptions((int)widgt, (int)height)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                // 缓存到磁盘中的图片宽高
                .diskCacheExtraOptions((int)widgt, (int)height, null)
                // .taskExecutor(null)
                // .taskExecutorForCachedImages()
                .threadPoolSize(3)
                // default 线程优先级
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // // default设置在内存中缓存图像的多种尺寸
                //加载同一URL图片时,imageView从小变大时,从内存缓存中加载
                .denyCacheImageMultipleSizesInMemory()
                // 超过设定的缓存大小时,内存缓存的清除机制
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                // 内存的一个大小
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                // default 将图片信息缓存到该路径下
                .diskCache(new UnlimitedDiskCache(cacheDir))
                // default 磁盘缓存的大小
                .diskCacheSize(50 * 1024 * 1024)
                // 磁盘缓存文件的个数
                .diskCacheFileCount(100)
                //磁盘缓存的文件名的命名方式//一般使用默认值 (获取文件名称的hashcode然后转换成字符串)或MD5 new Md5FileNameGenerator()源文件的名称同过md5加密后保存
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                // 设置默认的图片加载
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext())) // default
                // 使用默认的图片解析器
                .imageDecoder(new BaseImageDecoder(true)) // default
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .defaultDisplayImageOptions(getDisplayOptions())
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.zanwu_img)// 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.zanwu_img)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.zanwu_img) // 设置图片加载/解码过程中错误时候显示的图片

                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中

//                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
//                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
//                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();// 构建完成
        return options;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Log.d(TAG,"onLowMemory()");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.d(TAG,"onTerminate()");
    }


    public class ThreadAwareDebugTree extends Timber.DebugTree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (tag != null) {
                String threadName = Thread.currentThread().getName();
                tag = "<" + threadName + "> " + tag;
            }
            super.log(priority, tag, message, t);
        }
        @Override
        protected String createStackElementTag(StackTraceElement element) {
            return super.createStackElementTag(element) + "(Line " + element.getLineNumber() + ")";  //日志显示行号
        }
    }

    public class ReleaseTree extends ThreadAwareDebugTree {
        @Override
        protected boolean isLoggable(String tag, int priority) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return false;
            }
            return true;
        }
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (!isLoggable(tag, priority)) {
                return;
            }
            super.log(priority, tag, message, t);
        }
    }

}
