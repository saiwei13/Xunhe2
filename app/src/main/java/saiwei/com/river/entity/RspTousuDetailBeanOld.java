package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/27/17.
 */

public class RspTousuDetailBeanOld {


    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"taskId":"582594","publicReport":{"createTime":"2017-09-29 11:54:25","complaintsSource":"02","processId":"582588","updateTime":"2017-09-29 11:54:25","villageCode":"","isYawp":"0","townName":"曹溪街道","reportContent":"测试定位地址测试","bussinessNo":"LY2017092900316","countyCode":"350802000000","townCode":"350802006000","realAddress":"哈哈哈地址","isDelete":"0","countyName":"新罗区","isNewRecord":true,"reportRiver":"龙津河段上游","isAppraise":"0","longitude":"117.029029","businessState":"03","latitude":"25.089782","publicReportId":"7eabd2d38e4b4e4a8a98073e1bedea21","complaintsType":"01","villageName":"","locationAddress":"测试地址1"},"appraisalList":[],"finishProcess":[]}
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
         * taskId : 582594
         * publicReport : {"createTime":"2017-09-29 11:54:25","complaintsSource":"02","processId":"582588","updateTime":"2017-09-29 11:54:25","villageCode":"","isYawp":"0","townName":"曹溪街道","reportContent":"测试定位地址测试","bussinessNo":"LY2017092900316","countyCode":"350802000000","townCode":"350802006000","realAddress":"哈哈哈地址","isDelete":"0","countyName":"新罗区","isNewRecord":true,"reportRiver":"龙津河段上游","isAppraise":"0","longitude":"117.029029","businessState":"03","latitude":"25.089782","publicReportId":"7eabd2d38e4b4e4a8a98073e1bedea21","complaintsType":"01","villageName":"","locationAddress":"测试地址1"}
         * appraisalList : []
         * finishProcess : []
         */

        private String taskId;
        private PublicReportBean publicReport;
        private List<?> appraisalList;
        private List<?> finishProcess;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public PublicReportBean getPublicReport() {
            return publicReport;
        }

        public void setPublicReport(PublicReportBean publicReport) {
            this.publicReport = publicReport;
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
             * createTime : 2017-09-29 11:54:25
             * complaintsSource : 02
             * processId : 582588
             * updateTime : 2017-09-29 11:54:25
             * villageCode :
             * isYawp : 0
             * townName : 曹溪街道
             * reportContent : 测试定位地址测试
             * bussinessNo : LY2017092900316
             * countyCode : 350802000000
             * townCode : 350802006000
             * realAddress : 哈哈哈地址
             * isDelete : 0
             * countyName : 新罗区
             * isNewRecord : true
             * reportRiver : 龙津河段上游
             * isAppraise : 0
             * longitude : 117.029029
             * businessState : 03
             * latitude : 25.089782
             * publicReportId : 7eabd2d38e4b4e4a8a98073e1bedea21
             * complaintsType : 01
             * villageName :
             * locationAddress : 测试地址1
             */

            private String createTime;
            private String complaintsSource;
            private String processId;
            private String updateTime;
            private String villageCode;
            private String isYawp;
            private String townName;
            private String reportContent;
            private String bussinessNo;
            private String countyCode;
            private String townCode;
            private String realAddress;
            private String isDelete;
            private String countyName;
            private boolean isNewRecord;
            private String reportRiver;
            private String isAppraise;
            private String longitude;
            private String businessState;
            private String latitude;
            private String publicReportId;
            private String complaintsType;
            private String villageName;
            private String locationAddress;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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

            public String getVillageCode() {
                return villageCode;
            }

            public void setVillageCode(String villageCode) {
                this.villageCode = villageCode;
            }

            public String getIsYawp() {
                return isYawp;
            }

            public void setIsYawp(String isYawp) {
                this.isYawp = isYawp;
            }

            public String getTownName() {
                return townName;
            }

            public void setTownName(String townName) {
                this.townName = townName;
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

            public String getCountyName() {
                return countyName;
            }

            public void setCountyName(String countyName) {
                this.countyName = countyName;
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

            public String getVillageName() {
                return villageName;
            }

            public void setVillageName(String villageName) {
                this.villageName = villageName;
            }

            public String getLocationAddress() {
                return locationAddress;
            }

            public void setLocationAddress(String locationAddress) {
                this.locationAddress = locationAddress;
            }
        }
    }
}
