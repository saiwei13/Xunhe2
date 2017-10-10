package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/27/17.
 */
public class RspComplaintDetailBean {

    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"publicReport":{"isNewRecord":true,"publicReportId":"c0c295fc609d4753b4d84614bf1b4bc1","userId":"8490f2aa96dd4f72929a97e64031e53e","bussinessNo":"LY2017091500194","reportRiver":"测试曹溪1","countyCode":"350802000000","townCode":"350802006000","realAddress":"新罗区西城街道西安南路121号B座","locationAddress":"测试地址1","longitude":"117.029029","latitude":"25.089782","reportContent":"测试地址1","reportImg":"/upload/system/2017/09/bb3c492040c8aa3a8ff03792420c211e.jpg,/upload/system/2017/09/7d6b2cbc00c18b80ee35d1d0345d9747.jpg,/upload/system/2017/09/d91d65529fc74577a7301e9d09753fb6.jpg","imgPaths":["/upload/system/2017/09/bb3c492040c8aa3a8ff03792420c211e.jpg","/upload/system/2017/09/7d6b2cbc00c18b80ee35d1d0345d9747.jpg","/upload/system/2017/09/d91d65529fc74577a7301e9d09753fb6.jpg"],"businessState":"03","createTime":1505465807000,"updateTime":1505465816000,"isDelete":"0","isAppraise":"0","processId":"575019","isYawp":"0","complaintsType":"01","complaintsSource":"02"},"historiclist":[{"START_TIME_":"2017-9-15 16:56:54","ACT_TYPE_":"startEvent","transUnit":"","complaints_source":"02","ACT_NAME_":"Start","ID_":"575021","create_Time":"2017-9-15 16:56:47","report_Name":"lytest001","END_TIME_":"2017-9-15 16:56:54"}],"appraisalList":[],"finishProcess":[]}
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
         * publicReport : {"isNewRecord":true,"publicReportId":"c0c295fc609d4753b4d84614bf1b4bc1","userId":"8490f2aa96dd4f72929a97e64031e53e","bussinessNo":"LY2017091500194","reportRiver":"测试曹溪1","countyCode":"350802000000","townCode":"350802006000","realAddress":"新罗区西城街道西安南路121号B座","locationAddress":"测试地址1","longitude":"117.029029","latitude":"25.089782","reportContent":"测试地址1","reportImg":"/upload/system/2017/09/bb3c492040c8aa3a8ff03792420c211e.jpg,/upload/system/2017/09/7d6b2cbc00c18b80ee35d1d0345d9747.jpg,/upload/system/2017/09/d91d65529fc74577a7301e9d09753fb6.jpg","imgPaths":["/upload/system/2017/09/bb3c492040c8aa3a8ff03792420c211e.jpg","/upload/system/2017/09/7d6b2cbc00c18b80ee35d1d0345d9747.jpg","/upload/system/2017/09/d91d65529fc74577a7301e9d09753fb6.jpg"],"businessState":"03","createTime":1505465807000,"updateTime":1505465816000,"isDelete":"0","isAppraise":"0","processId":"575019","isYawp":"0","complaintsType":"01","complaintsSource":"02"}
         * historiclist : [{"START_TIME_":"2017-9-15 16:56:54","ACT_TYPE_":"startEvent","transUnit":"","complaints_source":"02","ACT_NAME_":"Start","ID_":"575021","create_Time":"2017-9-15 16:56:47","report_Name":"lytest001","END_TIME_":"2017-9-15 16:56:54"}]
         * appraisalList : []
         * finishProcess : []
         */

        private PublicReportBean publicReport;
        private List<HistoriclistBean> historiclist;
        private List<?> appraisalList;
        private List<?> finishProcess;

        public PublicReportBean getPublicReport() {
            return publicReport;
        }

        public void setPublicReport(PublicReportBean publicReport) {
            this.publicReport = publicReport;
        }

        public List<HistoriclistBean> getHistoriclist() {
            return historiclist;
        }

        public void setHistoriclist(List<HistoriclistBean> historiclist) {
            this.historiclist = historiclist;
        }

        public List<?> getAppraisalList() {
            return appraisalList;
        }

        public void setAppraisalList(List<?> appraisalList) {
            this.appraisalList = appraisalList;
        }

        public List<?> getFinishProcess() {
            return finishProcess;
        }

        public void setFinishProcess(List<?> finishProcess) {
            this.finishProcess = finishProcess;
        }

        public static class PublicReportBean {
            /**
             * isNewRecord : true
             * publicReportId : c0c295fc609d4753b4d84614bf1b4bc1
             * userId : 8490f2aa96dd4f72929a97e64031e53e
             * bussinessNo : LY2017091500194
             * reportRiver : 测试曹溪1
             * countyCode : 350802000000
             * townCode : 350802006000
             * realAddress : 新罗区西城街道西安南路121号B座
             * locationAddress : 测试地址1
             * longitude : 117.029029
             * latitude : 25.089782
             * reportContent : 测试地址1
             * reportImg : /upload/system/2017/09/bb3c492040c8aa3a8ff03792420c211e.jpg,/upload/system/2017/09/7d6b2cbc00c18b80ee35d1d0345d9747.jpg,/upload/system/2017/09/d91d65529fc74577a7301e9d09753fb6.jpg
             * imgPaths : ["/upload/system/2017/09/bb3c492040c8aa3a8ff03792420c211e.jpg","/upload/system/2017/09/7d6b2cbc00c18b80ee35d1d0345d9747.jpg","/upload/system/2017/09/d91d65529fc74577a7301e9d09753fb6.jpg"]
             * businessState : 03
             * createTime : 1505465807000
             * updateTime : 1505465816000
             * isDelete : 0
             * isAppraise : 0
             * processId : 575019
             * isYawp : 0
             * complaintsType : 01
             * complaintsSource : 02
             */

            private boolean isNewRecord;
            private String publicReportId;
            private String userId;
            private String bussinessNo;
            private String reportRiver;
            private String countyCode;
            private String townCode;
            private String realAddress;
            private String locationAddress;
            private String longitude;
            private String latitude;
            private String reportContent;
            private String reportImg;
            private String businessState;
            private long createTime;
            private long updateTime;
            private String isDelete;
            private String isAppraise;
            private String processId;
            private String isYawp;
            private String complaintsType;
            private String complaintsSource;
            private List<String> imgPaths;

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getPublicReportId() {
                return publicReportId;
            }

            public void setPublicReportId(String publicReportId) {
                this.publicReportId = publicReportId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getBussinessNo() {
                return bussinessNo;
            }

            public void setBussinessNo(String bussinessNo) {
                this.bussinessNo = bussinessNo;
            }

            public String getReportRiver() {
                return reportRiver;
            }

            public void setReportRiver(String reportRiver) {
                this.reportRiver = reportRiver;
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

            public String getLocationAddress() {
                return locationAddress;
            }

            public void setLocationAddress(String locationAddress) {
                this.locationAddress = locationAddress;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getReportContent() {
                return reportContent;
            }

            public void setReportContent(String reportContent) {
                this.reportContent = reportContent;
            }

            public String getReportImg() {
                return reportImg;
            }

            public void setReportImg(String reportImg) {
                this.reportImg = reportImg;
            }

            public String getBusinessState() {
                return businessState;
            }

            public void setBusinessState(String businessState) {
                this.businessState = businessState;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public String getIsAppraise() {
                return isAppraise;
            }

            public void setIsAppraise(String isAppraise) {
                this.isAppraise = isAppraise;
            }

            public String getProcessId() {
                return processId;
            }

            public void setProcessId(String processId) {
                this.processId = processId;
            }

            public String getIsYawp() {
                return isYawp;
            }

            public void setIsYawp(String isYawp) {
                this.isYawp = isYawp;
            }

            public String getComplaintsType() {
                return complaintsType;
            }

            public void setComplaintsType(String complaintsType) {
                this.complaintsType = complaintsType;
            }

            public String getComplaintsSource() {
                return complaintsSource;
            }

            public void setComplaintsSource(String complaintsSource) {
                this.complaintsSource = complaintsSource;
            }

            public List<String> getImgPaths() {
                return imgPaths;
            }

            public void setImgPaths(List<String> imgPaths) {
                this.imgPaths = imgPaths;
            }
        }

        public static class HistoriclistBean {
            /**
             * START_TIME_ : 2017-9-15 16:56:54
             * ACT_TYPE_ : startEvent
             * transUnit :
             * complaints_source : 02
             * ACT_NAME_ : Start
             * ID_ : 575021
             * create_Time : 2017-9-15 16:56:47
             * report_Name : lytest001
             * END_TIME_ : 2017-9-15 16:56:54
             */

            private String START_TIME_;
            private String ACT_TYPE_;
            private String transUnit;
            private String complaints_source;
            private String ACT_NAME_;
            private String ID_;
            private String create_Time;
            private String report_Name;
            private String END_TIME_;

            public String getSTART_TIME_() {
                return START_TIME_;
            }

            public void setSTART_TIME_(String START_TIME_) {
                this.START_TIME_ = START_TIME_;
            }

            public String getACT_TYPE_() {
                return ACT_TYPE_;
            }

            public void setACT_TYPE_(String ACT_TYPE_) {
                this.ACT_TYPE_ = ACT_TYPE_;
            }

            public String getTransUnit() {
                return transUnit;
            }

            public void setTransUnit(String transUnit) {
                this.transUnit = transUnit;
            }

            public String getComplaints_source() {
                return complaints_source;
            }

            public void setComplaints_source(String complaints_source) {
                this.complaints_source = complaints_source;
            }

            public String getACT_NAME_() {
                return ACT_NAME_;
            }

            public void setACT_NAME_(String ACT_NAME_) {
                this.ACT_NAME_ = ACT_NAME_;
            }

            public String getID_() {
                return ID_;
            }

            public void setID_(String ID_) {
                this.ID_ = ID_;
            }

            public String getCreate_Time() {
                return create_Time;
            }

            public void setCreate_Time(String create_Time) {
                this.create_Time = create_Time;
            }

            public String getReport_Name() {
                return report_Name;
            }

            public void setReport_Name(String report_Name) {
                this.report_Name = report_Name;
            }

            public String getEND_TIME_() {
                return END_TIME_;
            }

            public void setEND_TIME_(String END_TIME_) {
                this.END_TIME_ = END_TIME_;
            }
        }
    }
}

