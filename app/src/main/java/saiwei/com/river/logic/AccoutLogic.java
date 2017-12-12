package saiwei.com.river.logic;

import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saiwei.com.river.MyApp;
import saiwei.com.river.db.DaoSession;
import saiwei.com.river.db.RiverDao;
import saiwei.com.river.db.UserDao;
import saiwei.com.river.db.XunheRecordDao;
import saiwei.com.river.entity.LoginBean;
import saiwei.com.river.entity.RiverBean;
import saiwei.com.river.entity.RspComplaintBean;
import saiwei.com.river.entity.RspCruisDetailBean;
import saiwei.com.river.entity.RspTousuDetailBean;
import saiwei.com.river.model.River;
import saiwei.com.river.model.User;
import saiwei.com.river.model.XunheRecord;
import saiwei.com.river.util.SharePreferenceUtil;

/**
 * Created by saiwei on 9/22/17.
 */

public class AccoutLogic {

    private static final String TAG = "chenwei.AccoutLogic";

    public static final String FROM_ACTIVITY= "from_activity";
    public static final int FROM_MAINACTIVITY = 1;
    public static final int FROM_MAPACTIVITY = 2;
    public static final int FROM_COMPLETEXUNHEACTIVITY = 3;

    /**
     * 单例，静态变量。请记得销毁
     */
    private static AccoutLogic instance = null;

    public static AccoutLogic getInstance() {
        if (instance == null) {
            instance = new AccoutLogic();
        }
        return instance;
    }

    private HashMap<String,String> tousuStatus = new HashMap<>();

    private AccoutLogic(){
        //TODO
        reqDefaultRiverInfo();
        initTousuStatus();
    }


    public int getTmp() {
        return tmp;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    private int tmp ;

    public static int tmp2 ;


    public String getTousuStatus(String key){
        String value = tousuStatus.get(key);
        return value;
    }

    private void initTousuStatus(){
        tousuStatus.clear();
        tousuStatus.put("01","初始，乡镇专管员待认领");
        tousuStatus.put("02","乡镇专管员认领成功，乡镇河长待处理");
        tousuStatus.put("03","乡镇专管员上报，乡镇河长待处理");
        tousuStatus.put("05","点击乡镇认领处理，乡镇待处理");
        tousuStatus.put("06","乡镇上报，县级待处理");
        tousuStatus.put("07","县级分发乡镇，乡镇待处理");
        tousuStatus.put("08","点击县级认领处理，县级待处理");
        tousuStatus.put("09","乡镇上报，县级挂起诉求件");
        tousuStatus.put("91","重复");
        tousuStatus.put("92","关闭");
        tousuStatus.put("97","提交结果，县级处理完成");
        tousuStatus.put("98","提交结果，乡镇处理完成");

    }


    public XunheRecord getXunheRecordCache() {
        return xunheRecordCache;
    }

    public void setXunheRecordCache(XunheRecord xunheRecordCache) {
        this.xunheRecordCache = xunheRecordCache;
    }

    private XunheRecord xunheRecordCache;



    public RspCruisDetailBean rspCruisDetailBeanCache;
    public RspCruisDetailBean getRspCruisDetailBeanCache(){
        return rspCruisDetailBeanCache;
    }

    public void setRspCruisDetailBeanCache(RspCruisDetailBean  cache){
        rspCruisDetailBeanCache = cache;
    }


    public RspComplaintBean.ResponseDataBean.VarListBean rspComplaintDetailBeanCache;
    public RspComplaintBean.ResponseDataBean.VarListBean getRspComplainDetailBeanCache(){
        return rspComplaintDetailBeanCache;
    }

    public void setRspComplaintDetailBeanCache(RspComplaintBean.ResponseDataBean.VarListBean  cache){
        rspComplaintDetailBeanCache = cache;
    }


    public RspTousuDetailBean getRspTousuDetailBeanCache() {
        return rspTousuDetailBeanCache;
    }

    public void setRspTousuDetailBeanCache(RspTousuDetailBean rspTousuDetailBeanCache) {
        this.rspTousuDetailBeanCache = rspTousuDetailBeanCache;
    }

    public RspTousuDetailBean rspTousuDetailBeanCache;


    /**
     * 是否登陆
     * @return
     */
    public boolean isLogin(){

        DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();

        UserDao userDao = daoSession.getUserDao();

        QueryBuilder<User> qb = userDao.queryBuilder()
                .where(UserDao.Properties.Userid.isNotNull());

        long count = qb.count();

        Log.d(TAG," isLogin() count="+count);

        if(count > 0){
            return true;
        } else {
            return false ;
        }
    }

    public String getTown(){
        String town = SharePreferenceUtil.getInstance().getStr(
                SharePreferenceUtil.SHARE_PREFERENCE_TOWN
        );

        return town;
    }

    public String getTownCode(){
        String towncode = SharePreferenceUtil.getInstance().getStr(
                SharePreferenceUtil.SHARE_PREFERENCE_TOWNCODE
        );

        return towncode;
    }

    public String getCountyCode(){
        String countycode = SharePreferenceUtil.getInstance().getStr(
                SharePreferenceUtil.SHARE_PREFERENCE_COUNTYCODE
        );

        return countycode;
    }

    public String getUserPhone(){
        String phone = SharePreferenceUtil.getInstance().getStr(
                SharePreferenceUtil.SHARE_PREFERENCE_MOBIEL
        );

        return phone;
    }

    public String getUserId(){

        DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();

        UserDao userDao = daoSession.getUserDao();

        User user = userDao.loadByRowId(1);

        if(user!=null){
            return user.getUserid();
        }

        return "";

    }

    public void delUser(String userid){
        DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();

        UserDao userDao = daoSession.getUserDao();

        userDao.deleteByKey(userid);

        RiverDao riverDao = daoSession.getRiverDao();
        riverDao.deleteAll();

//        QueryBuilder<User> qb = userDao.queryBuilder()
//                .where(UserDao.Properties.Userid.eq(userid));
//        User user = qb.build().unique();

        return;
    }

    public String getUserName(String userid){

        DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();

        UserDao userDao = daoSession.getUserDao();

        QueryBuilder<User> qb = userDao.queryBuilder()
                .where(UserDao.Properties.Userid.eq(userid));
        User user = qb.build().unique();

        return user.getName();

//        return user.getUserid();
    }

    /**
     * 登陆
     */
    public void login(String loginName,String password,Callback callback){
        retrofit2.Call<LoginBean> call = RetrofitLogic.getInstance().getService().doLogin(loginName,password);
        call.enqueue(callback);
    }

    public List<XunheRecord> getXunheRecordsFromDB(){

        String userid = getUserId();

        DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();

        XunheRecordDao xunheRecordDao = daoSession.getXunheRecordDao();

        QueryBuilder<XunheRecord> qb = xunheRecordDao.queryBuilder()
                .where(XunheRecordDao.Properties.Userid.eq(userid));
        List<XunheRecord>  lists = qb.list();

        return lists;
//        return riverDao.loadAll();
    }

    /**
     * 根据河道id ，获取河道实体
     * @param riverBaseinfoId
     * @return
     */
    public River getRiver(String riverBaseinfoId){

        DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();

        RiverDao riverDao = daoSession.getRiverDao();
        QueryBuilder<River> qb = riverDao.queryBuilder()
                .where(RiverDao.Properties.RiverBaseinfoId.eq(riverBaseinfoId));
        River river = qb.build().unique();
        return river;
    }



    public List<River> getRiverInfoFromDB(){

        DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();

        RiverDao riverDao = daoSession.getRiverDao();

        return riverDao.loadAll();

//        QueryBuilder<River> qb = riverDao.queryBuilder()
//                .where(RiverDao.Properties.RiverBaseinfoId.isNotNull());
//
//
//
//        long count = qb.count();
    }

    public void reqDefaultRiverInfo(){

        if(!isLogin()){
            return;
        }


        String userid = getUserId();

        retrofit2.Call<RiverBean> call = RetrofitLogic.getInstance().getService().reqRiverInfo(userid);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG,"onResponse()  "+response.body());

                RiverBean bean = (RiverBean) response.body();

//                bean.getResponseData()

                AccoutLogic.getInstance().insertDBRiverInfo(bean.getResponseData());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG,"onFailure()  "+t.toString());
            }
        });
    }


    /**
     * 请求河道信息
     */
    public void reqRiverInfo(Callback callback){


        String userid = getUserId();

        retrofit2.Call<RiverBean> call = RetrofitLogic.getInstance().getService().reqRiverInfo(userid);
        call.enqueue(callback);
    }

    /**
     *  更新用户数据
     */
    public void insertDBUser(User user){

        if(user != null){
            DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();
            UserDao userDao = daoSession.getUserDao();
//            userDao.update(user);
            userDao.insert(user);
        }
    }



    /**
     *  数据库：河道信息
     */
    public void insertDBRiverInfo(List<RiverBean.ResponseDataBean> lists){

        if(lists != null){

            DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();
            RiverDao riverDao = daoSession.getRiverDao();

            for(int i=0;i<lists.size();i++){
                RiverBean.ResponseDataBean bean = lists.get(i);

                River river = new River();
                river.setIsNewRecord(bean.isIsNewRecord());
                river.setRiverBaseinfoId(bean.getRiverBaseinfoId());
                river.setCountyCode(bean.getCountyCode());
                river.setTownCode(bean.getTownCode());
                river.setRiverName(bean.getRiverName());
                river.setRiverLength(bean.getRiverLength());
                river.setCreateTime(bean.getCreateTime());
                river.setUpdateTime(bean.getUpdateTime());
                river.setRelationPeople(bean.getRelationPeople());

                riverDao.insertOrReplace(river);
            }
        }
    }

    /**
     * 将巡河保存到数据库中
     */
    public void insertDBXunheRecord(XunheRecord xunheRecord){

        DaoSession daoSession =  ((MyApp)MyApp.getApp()).getDaoSession();
        XunheRecordDao xunheRecordDao = daoSession.getXunheRecordDao();

//        XunheRecord xunheRecord = new XunheRecord();
//        xunheRecord.
        xunheRecordDao.insertOrReplace(xunheRecord);
    }
}
