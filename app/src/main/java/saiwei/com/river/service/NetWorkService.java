package saiwei.com.river.service;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import saiwei.com.river.entity.DataEntity1;
import saiwei.com.river.entity.DataEntity2;
import saiwei.com.river.entity.LoginBean;
import saiwei.com.river.entity.ReqFeedbackBean;
import saiwei.com.river.entity.ResponseBean;
import saiwei.com.river.entity.RiverBean;
import saiwei.com.river.entity.RspCloseTousu;
import saiwei.com.river.entity.RspComplaintBean;
import saiwei.com.river.entity.RspComplaintHandleInfo;
import saiwei.com.river.entity.RspCruisDetailBean;
import saiwei.com.river.entity.RspCruiseBean;
import saiwei.com.river.entity.RspFeedBack;
import saiwei.com.river.entity.RspRepeatComplaint;
import saiwei.com.river.entity.RspTousuBean;
import saiwei.com.river.entity.RspTousuDetailBean;
import saiwei.com.river.entity.RspTousuRepeatBean;
import saiwei.com.river.entity.RspUserCommonInfo;
import saiwei.com.river.entity.XunheRecordBean;

/**
 * Description:
 * author         xulei
 * Date           16/4/27
 */
public interface NetWorkService {
    //根据不同请求方式Retrofit提供了5种注解方式：GET、POST、PUT、DELETE和HEAD
    //转换HTTP API为Java接口

    //可直接指定源绝对URL,baseUrl就失效了
//    @GET("https://api.github.com/users/basil2style")

    //可指定源相对URL
    @GET("users/basil2style")
    Call<DataEntity1> getData();

    //也可在URL里使用替换块和参数进行动态更新
    //替换块是{ and }包围的字母数字组成的字符串，相应的参数必须使用相同的字符串被@Path进行注释
    @GET("users/{params1}")
    Call<DataEntity1> getData(@Path("params1") String parameter1);

    @GET("repos/{params1}/{params2}/{params3}")
    Call<List<DataEntity2>> getData(
            @Path("params1") String parameter1,
            @Path("params2") String parameter2,
            @Path("params3") String parameter3
    );

    //添加查询参数(?x1=y1)
    @GET("repos/square/{retrofit}/contributors")
    Call<List<DataEntity2>> groupData(@Path("retrofit") String retrofit, @Query("sort") String sort);

    //复杂的查询参数可以使用Map进行组合(?x1=y1&?x2=y2&...)
    @GET("repos/square/{retrofit}/contributors")
    Call<List<DataEntity2>> getData(@Path("repos") String repos, @QueryMap Map<String, String> parameters);

    //可以通过@Body注解指定一个对象作为Http请求的请求体
    @POST("users/new")
    Call<DataEntity2> createUser(@Body DataEntity2 dataEntity2);



    @POST("complaint/getReachList")
    @FormUrlEncoded
    Call<RiverBean> reqRiverInfo(@Field("userId") String userId);


    //实现键值对形式的传输
    @POST("user/userLogin")
    @FormUrlEncoded
    Call<LoginBean> doLogin(@Field("loginName") String loginName, @Field("password")String password);//实现json格式的传输
//    //键值对传输的第二种方法
//    @POST("/yos/user/user_login.ysmd?")
//    @FormUrlEncoded
//    Call<LoginBean> doLogin(@FieldMap Map<String,String> params);


    //实现键值对形式的传输
    @POST("cruise/sumbitCruise")
    @FormUrlEncoded
    Call<ResponseBean> doCommitXunheRecord(
            @Field("userId") String userId,
            @Field("reportRiver") String reportRiver,

            @Field("reportRiverId") String reportRiverId,
            @Field("tourTime") String tourTime,
            @Field("totalTime") String totalTime,
            @Field("isHDZZ") String isHDZZ,
            @Field("isHDBJ") String isHDBJ,
            @Field("isWSPK") String isWSPK,
            @Field("isSSWF") String isSSWF,
            @Field("isHALJ") String isHALJ,
            @Field("isHDSZ") String isHDSZ,

            @Field("isHDWN") String isHDWN,
            @Field("isRHWK") String isRHWK,

            @Field("isPHSS") String isPHSS,

            @Field("isFFBY") String isFFBY,
            @Field("isHZTS") String isHZTS,
            @Field("isYXSZ") String isYXSZ,

            @Field("xyJsonInfos") String xyJsonInfos,
            @Field("complaintInfos") String complaintInfos

    );//实现json格式的传输


    //问题上报
    //实现键值对形式的传输
    @POST("cruise/submitComplaint")
    @FormUrlEncoded
    Call<RspFeedBack> doReportFeedback(
            @Field("userId") String userId,
            @Field("countyCode") String countyCode,
            @Field("townCode") String townCode,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("locationAddress") String locationAddress,
            @Field("realAddress") String realAddress,
            @Field("reportContent") String reportContent,
            @Field("reportName") String reportName,
            @Field("reportPhone") String reportPhone,
            @Field("reportRiverId") String reportRiverId,
            @Field("reportRiver") String reportRiver,
            @Field("complaintsType") String complaintsType,
            @Field("reportImg") String reportImg
    );//实现json格式的传输

    //获取巡河记录列表
    //实现键值对形式的传输
    @POST("cruise/getCruiseList")
    @FormUrlEncoded
    Call<RspCruiseBean> doGetCruiseList(
            @Field("userId") String userId,
            @Field("startDate") String startDate,      //查询起始时间 2017-08-01
            @Field("endDate") String endDate,
            @Field("pageIndex") String pageIndex,
            @Field("pageCount") String pageCount
    );//实现json格式的传输

    //获取巡河记录详情
    @POST("cruise/getCruiseDetail")
    @FormUrlEncoded
    Call<RspCruisDetailBean> doGetCruiseDetail(
            @Field("userId") String userId,
            @Field("tourRiverID") String tourRiverID
    );//实现json格式的传输

    //获取问题记录列表
    @POST("cruise/getComplaintListByUser")
    @FormUrlEncoded
    Call<RspComplaintBean> doGetComplaintListByUser(
            @Field("userId") String userId,
            @Field("status") String status,
            @Field("isAppraise") String isAppraise,
            @Field("pageIndex") String pageIndex,
            @Field("pageCount") String pageCount
    );//实现json格式的传输

    //获取问题记录详情
    @POST("cruise/getComplaintDetailInfo")
    @FormUrlEncoded
    Call<RspTousuDetailBean> doGetComplaintDetail(
            @Field("publicReportId") String publicReportId
    );//实现json格式的传输

    //投诉问题列表
    @POST("complaint/getComplaintList")
    @FormUrlEncoded
    Call<RspTousuBean> doGetComplaintList(
            @Field("userId") String userId,
            @Field("keyword") String keyword,
            @Field("queryType") String queryType,
            @Field("pageIndex") String pageIndex,
            @Field("pageCount") String pageCount
    );//实现json格式的传输


    //分页获取相似的投诉件列表
    @POST("complaint/getSimilarComplaintList")
    @FormUrlEncoded
    Call<RspTousuRepeatBean> doGetSimilarComplaintList(
            @Field("userId") String userId,
            @Field("keyword") String keyword,
            @Field("pageIndex") String pageIndex,
            @Field("pageCount") String pageCount
    );//实现json格式的传输

    //投诉问题详情
    @POST("complaint/getComplaintDetail")
    @FormUrlEncoded
    Call<RspTousuDetailBean> doGetTousuDetail(
            @Field("publicReportId") String publicReportId,
            @Field("taskId") String taskId
    );//实现json格式的传输





    //投诉问题认领
    @POST("complaint/claimComplaint")
    @FormUrlEncoded
    Call<RspTousuDetailBean> doClaimComplaint(
            @Field("userId") String userId,
            @Field("publicReportId") String publicReportId,
            @Field("taskId") String taskId,
            @Field("processImg") String processImg,
            @Field("riverId") String riverId,
            @Field("reportRiver") String reportRiver,
            @Field("complaintsType") String complaintsType
    );//实现json格式的传输

    //关闭投诉
    @POST("complaint/closeComplaint")
    @FormUrlEncoded
    Call<RspCloseTousu> doCloseComplaint(
            @Field("userId") String userId,
            @Field("publicReportId") String publicReportId,
            @Field("taskId") String taskId,
            @Field("content") String content,
            @Field("processImg") String processImg
    );//实现json格式的传输




    //获取办事进度
    @POST("complaint/getComplaintHandleInfo")
    @FormUrlEncoded
    Call<RspComplaintHandleInfo> doGetComplaintHandleInfo(
            @Field("processId") String processId
    );//实现json格式的传输


    //投诉问题重复处理
    @POST("complaint/repeatComplaintHandle")
    @FormUrlEncoded
    Call<RspRepeatComplaint> doRepeatComplaintHandle(
            @Field("userId") String userId,
            @Field("publicReportId") String publicReportId,
            @Field("taskId") String taskId,
            @Field("repeatId") String repeatId

    );//实现json格式的传输


    //用户通用信息
    @POST("user/ getUserCommonInfo")
    @FormUrlEncoded
    Call<RspUserCommonInfo> doGetUserCommonInfo(
            @Field("userId") String userId

    );//实现json格式的传输

}
