package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/27/17.
 */

public class RspComplaintBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"count":100,"varList":[{"createTime":"2017-10-09 14:16:05","businessStateDescription":"乡镇河长待处理","complaintsSource":"02","processId":"597774","updateTime":"2017-10-09 14:16:05","isYawp":"0","imgPaths":["/user/default/2017/10/2d51134604ab3c1ce3de681052213130.jpg"],"reportContent":"测试一张图片","bussinessNo":"LY2017100900355","countyCode":"350802000000","townCode":"350802006000","realAddress":"测试地点1417","isDelete":"0","isNewRecord":true,"reportRiver":"龙津河段上游","reportImg":"/user/default/2017/10/2d51134604ab3c1ce3de681052213130.jpg","isAppraise":"0","longitude":"117.022537","businessState":"03","latitude":"25.07356","publicReportId":"9fab8b0b69f5481fad69143b795ea6ff","complaintsType":"03","locationAddress":"福建省龙岩市新罗区西陂街道龙岩大道260号太古广场附近"}]}
     */

    private String rtnCode;
    private String rtnMsg;
    private ResponseDataBean responseData;

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public ResponseDataBean getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseDataBean responseData) {
        this.responseData = responseData;
    }

    public static class ResponseDataBean {
        /**
         * count : 100
         * varList : [{"createTime":"2017-10-09 14:16:05","businessStateDescription":"乡镇河长待处理","complaintsSource":"02","processId":"597774","updateTime":"2017-10-09 14:16:05","isYawp":"0","imgPaths":["/user/default/2017/10/2d51134604ab3c1ce3de681052213130.jpg"],"reportContent":"测试一张图片","bussinessNo":"LY2017100900355","countyCode":"350802000000","townCode":"350802006000","realAddress":"测试地点1417","isDelete":"0","isNewRecord":true,"reportRiver":"龙津河段上游","reportImg":"/user/default/2017/10/2d51134604ab3c1ce3de681052213130.jpg","isAppraise":"0","longitude":"117.022537","businessState":"03","latitude":"25.07356","publicReportId":"9fab8b0b69f5481fad69143b795ea6ff","complaintsType":"03","locationAddress":"福建省龙岩市新罗区西陂街道龙岩大道260号太古广场附近"}]
         */

        private int count;
        private List<VarListBean> varList;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<VarListBean> getVarList() {
            return varList;
        }

        public void setVarList(List<VarListBean> varList) {
            this.varList = varList;
        }

        public static class VarListBean {
            /**
             * createTime : 2017-10-09 14:16:05
             * businessStateDescription : 乡镇河长待处理
             * complaintsSource : 02
             * processId : 597774
             * updateTime : 2017-10-09 14:16:05
             * isYawp : 0
             * imgPaths : ["/user/default/2017/10/2d51134604ab3c1ce3de681052213130.jpg"]
             * reportContent : 测试一张图片
             * bussinessNo : LY2017100900355
             * countyCode : 350802000000
             * townCode : 350802006000
             * realAddress : 测试地点1417
             * isDelete : 0
             * isNewRecord : true
             * reportRiver : 龙津河段上游
             * reportImg : /user/default/2017/10/2d51134604ab3c1ce3de681052213130.jpg
             * isAppraise : 0
             * longitude : 117.022537
             * businessState : 03
             * latitude : 25.07356
             * publicReportId : 9fab8b0b69f5481fad69143b795ea6ff
             * complaintsType : 03
             * locationAddress : 福建省龙岩市新罗区西陂街道龙岩大道260号太古广场附近
             */

            private String createTime;
            private String businessStateDescription;
            private String complaintsSource;
            private String processId;
            private String updateTime;
            private String isYawp;
            private String reportContent;
            private String bussinessNo;
            private String countyCode;
            private String townCode;
            private String realAddress;
            private String isDelete;
            private boolean isNewRecord;
            private String reportRiver;
            private String reportImg;
            private String isAppraise;
            private String longitude;
            private String businessState;
            private String latitude;
            private String publicReportId;
            private String complaintsType;
            private String locationAddress;
//            private List<String> imgPaths;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getBusinessStateDescription() {
                return businessStateDescription;
            }

            public void setBusinessStateDescription(String businessStateDescription) {
                this.businessStateDescription = businessStateDescription;
            }

            public String getComplaintsSource() {
                return complaintsSource;
            }

            public void setComplaintsSource(String complaintsSource) {
                this.complaintsSource = complaintsSource;
            }

            public String getProcessId() {
                return processId;
            }

            public void setProcessId(String processId) {
                this.processId = processId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getIsYawp() {
                return isYawp;
            }

            public void setIsYawp(String isYawp) {
                this.isYawp = isYawp;
            }

            public String getReportContent() {
                return reportContent;
            }

            public void setReportContent(String reportContent) {
                this.reportContent = reportContent;
            }

            public String getBussinessNo() {
                return bussinessNo;
            }

            public void setBussinessNo(String bussinessNo) {
                this.bussinessNo = bussinessNo;
            }

            public String getCountyCode() {
                return countyCode;
            }

            public void setCountyCode(String countyCode) {
                this.countyCode = countyCode;
            }

            public String getTownCode() {
                return townCode;
            }

            public void setTownCode(String townCode) {
                this.townCode = townCode;
            }

            public String getRealAddress() {
                return realAddress;
            }

            public void setRealAddress(String realAddress) {
                this.realAddress = realAddress;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getReportRiver() {
                return reportRiver;
            }

            public void setReportRiver(String reportRiver) {
                this.reportRiver = reportRiver;
            }

            public String getReportImg() {
                return reportImg;
            }

            public void setReportImg(String reportImg) {
                this.reportImg = reportImg;
            }

            public String getIsAppraise() {
                return isAppraise;
            }

            public void setIsAppraise(String isAppraise) {
                this.isAppraise = isAppraise;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getBusinessState() {
                return businessState;
            }

            public void setBusinessState(String businessState) {
                this.businessState = businessState;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getPublicReportId() {
                return publicReportId;
            }

            public void setPublicReportId(String publicReportId) {
                this.publicReportId = publicReportId;
            }

            public String getComplaintsType() {
                return complaintsType;
            }

            public void setComplaintsType(String complaintsType) {
                this.complaintsType = complaintsType;
            }

            public String getLocationAddress() {
                return locationAddress;
            }

            public void setLocationAddress(String locationAddress) {
                this.locationAddress = locationAddress;
            }

//            public List<String> getImgPaths() {
//                return imgPaths;
//            }
//
//            public void setImgPaths(List<String> imgPaths) {
//                this.imgPaths = imgPaths;
//            }
        }
    }
}
