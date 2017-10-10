package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/29/17.
 */

public class RspComplaintHandleInfo {

    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : [{"office_name":"曹溪街道","process_Time":"2017-9-13 17:41:10","transUnit":"","complaints_source":"02","ACT_NAME_":"乡镇专管员","ID_":"555006","create_Time":"2017-9-13 17:41:09","END_TIME_":"2017-9-13 17:41:10","process_Content":"曹溪街道dev_lytest001上报问题","id":"8490f2aa96dd4f72929a97e64031e53e","TASK_ID_":"555007","user_Id":"8490f2aa96dd4f72929a97e64031e53e","user_name":"lytest001","ACT_TYPE_":"userTask","START_TIME_":"2017-9-13 17:41:09","business_State":"03","report_Name":"lytest001"}]
     */

    private String rtnCode;
    private String rtnMsg;
    private List<ResponseDataBean> responseData;

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

    public List<ResponseDataBean> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseDataBean> responseData) {
        this.responseData = responseData;
    }

    public static class ResponseDataBean {
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
         * descriptionInfo :  市民林学威提交河道投诉,等待乡镇专管员认领
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

        public String getDescriptionInfo() {
            return descriptionInfo;
        }

        public void setDescriptionInfo(String descriptionInfo) {
            this.descriptionInfo = descriptionInfo;
        }



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
    }
}
