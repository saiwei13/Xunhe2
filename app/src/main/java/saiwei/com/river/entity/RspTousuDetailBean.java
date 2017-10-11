package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/27/17.
 */

public class RspTousuDetailBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"taskId":"595007","publicReport":{"createTime":"2017-09-30 18:58:57","complaintsSource":"01","processId":"595001","updateTime":"2017-09-30 18:58:57","villageCode":"350802006202","isYawp":"0","townName":"曹溪街道","reportContent":"0930，3333","bussinessNo":"LY2017093000338","countyCode":"350802000000","townCode":"350802006000","realAddress":"新罗区西陂街道天平路40号太古广场","isDelete":"0","countyName":"新罗区","isNewRecord":true,"reportRiver":"河道投诉3330930","isAppraise":"0","longitude":"117.022862","businessState":"01","latitude":"25.075877","publicReportId":"4938883f649c483da36bbb836aa90322","complaintsType":"02","villageName":"下寮村委会","locationAddress":"灭了"},"historiclist":[{"office_name":"曹溪街道","process_Time":"2017-9-13 17:41:10","transUnit":"","complaints_source":"02","ACT_NAME_":"乡镇专管员","ID_":"555006","create_Time":"2017-9-13 17:41:09","END_TIME_":"2017-9-13 17:41:10","process_Content":"曹溪街道dev_lytest001上报问题","id":"8490f2aa96dd4f72929a97e64031e53e","TASK_ID_":"555007","user_Id":"8490f2aa96dd4f72929a97e64031e53e","user_name":"lytest001","ACT_TYPE_":"userTask","START_TIME_":"2017-9-13 17:41:09","business_State":"03","report_Name":"lytest001","descriptionInfo":"市民林学威提交河道投诉,等待乡镇专管员认领 "}],"appraisalList":[],"finishProcess":[{"taskId":"590135","villageCode":"","processId":"590129","busineseeProcessId":"14e4fd32ea0643018230f4ad18d447f0","processDept":"曹溪街道","bussinessNo":"LY2017093000336","countyCode":"350802000000","townCode":"350802006000","processContent":"当前办件与投诉件：LY2017093000334重复，点击查看！","userId":"8490f2aa96dd4f72929a97e64031e53e","processTime":"2017-09-30 18:51:09","businessState":"91","processMan":"lytest001"}]}
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
         * taskId : 595007
         * publicReport : {"createTime":"2017-09-30 18:58:57","complaintsSource":"01","processId":"595001","updateTime":"2017-09-30 18:58:57","villageCode":"350802006202","isYawp":"0","townName":"曹溪街道","reportContent":"0930，3333","bussinessNo":"LY2017093000338","countyCode":"350802000000","townCode":"350802006000","realAddress":"新罗区西陂街道天平路40号太古广场","isDelete":"0","countyName":"新罗区","isNewRecord":true,"reportRiver":"河道投诉3330930","isAppraise":"0","longitude":"117.022862","businessState":"01","latitude":"25.075877","publicReportId":"4938883f649c483da36bbb836aa90322","complaintsType":"02","villageName":"下寮村委会","locationAddress":"灭了"}
         * historiclist : [{"office_name":"曹溪街道","process_Time":"2017-9-13 17:41:10","transUnit":"","complaints_source":"02","ACT_NAME_":"乡镇专管员","ID_":"555006","create_Time":"2017-9-13 17:41:09","END_TIME_":"2017-9-13 17:41:10","process_Content":"曹溪街道dev_lytest001上报问题","id":"8490f2aa96dd4f72929a97e64031e53e","TASK_ID_":"555007","user_Id":"8490f2aa96dd4f72929a97e64031e53e","user_name":"lytest001","ACT_TYPE_":"userTask","START_TIME_":"2017-9-13 17:41:09","business_State":"03","report_Name":"lytest001","descriptionInfo":"市民林学威提交河道投诉,等待乡镇专管员认领 "}]
         * appraisalList : []
         * finishProcess : [{"taskId":"590135","villageCode":"","processId":"590129","busineseeProcessId":"14e4fd32ea0643018230f4ad18d447f0","processDept":"曹溪街道","bussinessNo":"LY2017093000336","countyCode":"350802000000","townCode":"350802006000","processContent":"当前办件与投诉件：LY2017093000334重复，点击查看！","userId":"8490f2aa96dd4f72929a97e64031e53e","processTime":"2017-09-30 18:51:09","businessState":"91","processMan":"lytest001"}]
         */

        private String taskId;
        private PublicReportBean publicReport;
        private List<HistoriclistBean> historiclist;
        private List<?> appraisalList;
        private List<FinishProcessBean> finishProcess;

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

        public List<FinishProcessBean> getFinishProcess() {
            return finishProcess;
        }

        public void setFinishProcess(List<FinishProcessBean> finishProcess) {
            this.finishProcess = finishProcess;
        }

        public static class PublicReportBean {
            /**
             * createTime : 2017-09-30 18:58:57
             * complaintsSource : 01
             * processId : 595001
             * updateTime : 2017-09-30 18:58:57
             * villageCode : 350802006202
             * isYawp : 0
             * townName : 曹溪街道
             * reportContent : 0930，3333
             * bussinessNo : LY2017093000338
             * countyCode : 350802000000
             * townCode : 350802006000
             * realAddress : 新罗区西陂街道天平路40号太古广场
             * isDelete : 0
             * countyName : 新罗区
             * isNewRecord : true
             * reportRiver : 河道投诉3330930
             * isAppraise : 0
             * longitude : 117.022862
             * businessState : 01
             * latitude : 25.075877
             * publicReportId : 4938883f649c483da36bbb836aa90322
             * complaintsType : 02
             * villageName : 下寮村委会
             * locationAddress : 灭了
             * "reportImg": "/upload/system/2017/10/a82860d100da9137cf2de2e1b2c1b881.jpg",
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
            private String reportImg;

            public String getReportImg() {
                return reportImg;
            }

            public void setReportImg(String reportImg) {
                this.reportImg = reportImg;
            }

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

        public static class HistoriclistBean {
            /**
             * office_name : 曹溪街道
             * process_Time : 2017-9-13 17:41:10
             * transUnit :
             * complaints_source : 02
             * ACT_NAME_ : 乡镇专管员
             * ID_ : 555006
             * create_Time : 2017-9-13 17:41:09
             * END_TIME_ : 2017-9-13 17:41:10
             * process_Content : 曹溪街道dev_lytest001上报问题
             * id : 8490f2aa96dd4f72929a97e64031e53e
             * TASK_ID_ : 555007
             * user_Id : 8490f2aa96dd4f72929a97e64031e53e
             * user_name : lytest001
             * ACT_TYPE_ : userTask
             * START_TIME_ : 2017-9-13 17:41:09
             * business_State : 03
             * report_Name : lytest001
             * descriptionInfo : 市民林学威提交河道投诉,等待乡镇专管员认领
             */

            private String office_name;
            private String process_Time;
            private String transUnit;
            private String complaints_source;
            private String ACT_NAME_;
            private String ID_;
            private String create_Time;
            private String END_TIME_;
            private String process_Content;
            private String id;
            private String TASK_ID_;
            private String user_Id;
            private String user_name;
            private String ACT_TYPE_;
            private String START_TIME_;
            private String business_State;
            private String report_Name;
            private String descriptionInfo;

            public String getOffice_name() {
                return office_name;
            }

            public void setOffice_name(String office_name) {
                this.office_name = office_name;
            }

            public String getProcess_Time() {
                return process_Time;
            }

            public void setProcess_Time(String process_Time) {
                this.process_Time = process_Time;
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

            public String getEND_TIME_() {
                return END_TIME_;
            }

            public void setEND_TIME_(String END_TIME_) {
                this.END_TIME_ = END_TIME_;
            }

            public String getProcess_Content() {
                return process_Content;
            }

            public void setProcess_Content(String process_Content) {
                this.process_Content = process_Content;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTASK_ID_() {
                return TASK_ID_;
            }

            public void setTASK_ID_(String TASK_ID_) {
                this.TASK_ID_ = TASK_ID_;
            }

            public String getUser_Id() {
                return user_Id;
            }

            public void setUser_Id(String user_Id) {
                this.user_Id = user_Id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getACT_TYPE_() {
                return ACT_TYPE_;
            }

            public void setACT_TYPE_(String ACT_TYPE_) {
                this.ACT_TYPE_ = ACT_TYPE_;
            }

            public String getSTART_TIME_() {
                return START_TIME_;
            }

            public void setSTART_TIME_(String START_TIME_) {
                this.START_TIME_ = START_TIME_;
            }

            public String getBusiness_State() {
                return business_State;
            }

            public void setBusiness_State(String business_State) {
                this.business_State = business_State;
            }

            public String getReport_Name() {
                return report_Name;
            }

            public void setReport_Name(String report_Name) {
                this.report_Name = report_Name;
            }

            public String getDescriptionInfo() {
                return descriptionInfo;
            }

            public void setDescriptionInfo(String descriptionInfo) {
                this.descriptionInfo = descriptionInfo;
            }
        }

        public static class FinishProcessBean {
            /**
             * taskId : 590135
             * villageCode :
             * processId : 590129
             * busineseeProcessId : 14e4fd32ea0643018230f4ad18d447f0
             * processDept : 曹溪街道
             * bussinessNo : LY2017093000336
             * countyCode : 350802000000
             * townCode : 350802006000
             * processContent : 当前办件与投诉件：LY2017093000334重复，点击查看！
             * userId : 8490f2aa96dd4f72929a97e64031e53e
             * processTime : 2017-09-30 18:51:09
             * businessState : 91
             * processMan : lytest001
             * "processImg": "/upload/system/2017/10/326a23dd79cf178fdaa50e5b5e564721.jpg",
             */

            private String taskId;
            private String villageCode;
            private String processId;
            private String busineseeProcessId;
            private String processDept;
            private String bussinessNo;
            private String countyCode;
            private String townCode;
            private String processContent;
            private String userId;
            private String processTime;
            private String businessState;
            private String processMan;
            private String processImg;

            public String getProcessImg() {
                return processImg;
            }

            public void setProcessImg(String processImg) {
                this.processImg = processImg;
            }

            public String getTaskId() {
                return taskId;
            }

            public void setTaskId(String taskId) {
                this.taskId = taskId;
            }

            public String getVillageCode() {
                return villageCode;
            }

            public void setVillageCode(String villageCode) {
                this.villageCode = villageCode;
            }

            public String getProcessId() {
                return processId;
            }

            public void setProcessId(String processId) {
                this.processId = processId;
            }

            public String getBusineseeProcessId() {
                return busineseeProcessId;
            }

            public void setBusineseeProcessId(String busineseeProcessId) {
                this.busineseeProcessId = busineseeProcessId;
            }

            public String getProcessDept() {
                return processDept;
            }

            public void setProcessDept(String processDept) {
                this.processDept = processDept;
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

            public String getProcessContent() {
                return processContent;
            }

            public void setProcessContent(String processContent) {
                this.processContent = processContent;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getProcessTime() {
                return processTime;
            }

            public void setProcessTime(String processTime) {
                this.processTime = processTime;
            }

            public String getBusinessState() {
                return businessState;
            }

            public void setBusinessState(String businessState) {
                this.businessState = businessState;
            }

            public String getProcessMan() {
                return processMan;
            }

            public void setProcessMan(String processMan) {
                this.processMan = processMan;
            }
        }
    }
}
