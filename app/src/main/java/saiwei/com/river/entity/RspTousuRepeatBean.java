package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 10/9/17.
 */

public class RspTousuRepeatBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"total":21,"dataList":[{"river_baseinfo_id":"58e56ae4804548fe8eae18ae14f9277c","report_River":"龙津河段上游","town_Code":"350802006000","update_Time":"2017-09-29 22:19:32","complaints_source":"02","real_Address":"武宣县禄新乡坡贵","is_yawp":"0","is_delete":"0","report_Img":"/upload/system/2017/09/18053a2534035432334855b798352325.jpg","report_Phone":"18094042406","complaints_type":"02","create_Time":"2017-09-29 22:19:32","location_Address":"uii","user_Id":"8490f2aa96dd4f72929a97e64031e53e","county_Code":"350802000000","process_id":"562527","is_appraise":"0","business_State":"03","bussiness_No":"LY2017092900310","longitude":"109.509326","public_Report_ID":"cf3fdf81c4724c648e117448d6e41322","latitude":"23.519443","report_Name":"lytest001","report_Content":"uuu"}]}
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
         * total : 21
         * dataList : [{"river_baseinfo_id":"58e56ae4804548fe8eae18ae14f9277c","report_River":"龙津河段上游","town_Code":"350802006000","update_Time":"2017-09-29 22:19:32","complaints_source":"02","real_Address":"武宣县禄新乡坡贵","is_yawp":"0","is_delete":"0","report_Img":"/upload/system/2017/09/18053a2534035432334855b798352325.jpg","report_Phone":"18094042406","complaints_type":"02","create_Time":"2017-09-29 22:19:32","location_Address":"uii","user_Id":"8490f2aa96dd4f72929a97e64031e53e","county_Code":"350802000000","process_id":"562527","is_appraise":"0","business_State":"03","bussiness_No":"LY2017092900310","longitude":"109.509326","public_Report_ID":"cf3fdf81c4724c648e117448d6e41322","latitude":"23.519443","report_Name":"lytest001","report_Content":"uuu"}]
         */

        private int total;
        private List<DataListBean> dataList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * river_baseinfo_id : 58e56ae4804548fe8eae18ae14f9277c
             * report_River : 龙津河段上游
             * town_Code : 350802006000
             * update_Time : 2017-09-29 22:19:32
             * complaints_source : 02
             * real_Address : 武宣县禄新乡坡贵
             * is_yawp : 0
             * is_delete : 0
             * report_Img : /upload/system/2017/09/18053a2534035432334855b798352325.jpg
             * report_Phone : 18094042406
             * complaints_type : 02
             * create_Time : 2017-09-29 22:19:32
             * location_Address : uii
             * user_Id : 8490f2aa96dd4f72929a97e64031e53e
             * county_Code : 350802000000
             * process_id : 562527
             * is_appraise : 0
             * business_State : 03
             * bussiness_No : LY2017092900310
             * longitude : 109.509326
             * public_Report_ID : cf3fdf81c4724c648e117448d6e41322
             * latitude : 23.519443
             * report_Name : lytest001
             * report_Content : uuu
             */

            private String river_baseinfo_id;
            private String report_River;
            private String town_Code;
            private String update_Time;
            private String complaints_source;
            private String real_Address;
            private String is_yawp;
            private String is_delete;
            private String report_Img;
            private String report_Phone;
            private String complaints_type;
            private String create_Time;
            private String location_Address;
            private String user_Id;
            private String county_Code;
            private String process_id;
            private String is_appraise;
            private String business_State;
            private String bussiness_No;
            private String longitude;
            private String public_Report_ID;
            private String latitude;
            private String report_Name;
            private String report_Content;

            public String getRiver_baseinfo_id() {
                return river_baseinfo_id;
            }

            public void setRiver_baseinfo_id(String river_baseinfo_id) {
                this.river_baseinfo_id = river_baseinfo_id;
            }

            public String getReport_River() {
                return report_River;
            }

            public void setReport_River(String report_River) {
                this.report_River = report_River;
            }

            public String getTown_Code() {
                return town_Code;
            }

            public void setTown_Code(String town_Code) {
                this.town_Code = town_Code;
            }

            public String getUpdate_Time() {
                return update_Time;
            }

            public void setUpdate_Time(String update_Time) {
                this.update_Time = update_Time;
            }

            public String getComplaints_source() {
                return complaints_source;
            }

            public void setComplaints_source(String complaints_source) {
                this.complaints_source = complaints_source;
            }

            public String getReal_Address() {
                return real_Address;
            }

            public void setReal_Address(String real_Address) {
                this.real_Address = real_Address;
            }

            public String getIs_yawp() {
                return is_yawp;
            }

            public void setIs_yawp(String is_yawp) {
                this.is_yawp = is_yawp;
            }

            public String getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(String is_delete) {
                this.is_delete = is_delete;
            }

            public String getReport_Img() {
                return report_Img;
            }

            public void setReport_Img(String report_Img) {
                this.report_Img = report_Img;
            }

            public String getReport_Phone() {
                return report_Phone;
            }

            public void setReport_Phone(String report_Phone) {
                this.report_Phone = report_Phone;
            }

            public String getComplaints_type() {
                return complaints_type;
            }

            public void setComplaints_type(String complaints_type) {
                this.complaints_type = complaints_type;
            }

            public String getCreate_Time() {
                return create_Time;
            }

            public void setCreate_Time(String create_Time) {
                this.create_Time = create_Time;
            }

            public String getLocation_Address() {
                return location_Address;
            }

            public void setLocation_Address(String location_Address) {
                this.location_Address = location_Address;
            }

            public String getUser_Id() {
                return user_Id;
            }

            public void setUser_Id(String user_Id) {
                this.user_Id = user_Id;
            }

            public String getCounty_Code() {
                return county_Code;
            }

            public void setCounty_Code(String county_Code) {
                this.county_Code = county_Code;
            }

            public String getProcess_id() {
                return process_id;
            }

            public void setProcess_id(String process_id) {
                this.process_id = process_id;
            }

            public String getIs_appraise() {
                return is_appraise;
            }

            public void setIs_appraise(String is_appraise) {
                this.is_appraise = is_appraise;
            }

            public String getBusiness_State() {
                return business_State;
            }

            public void setBusiness_State(String business_State) {
                this.business_State = business_State;
            }

            public String getBussiness_No() {
                return bussiness_No;
            }

            public void setBussiness_No(String bussiness_No) {
                this.bussiness_No = bussiness_No;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getPublic_Report_ID() {
                return public_Report_ID;
            }

            public void setPublic_Report_ID(String public_Report_ID) {
                this.public_Report_ID = public_Report_ID;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getReport_Name() {
                return report_Name;
            }

            public void setReport_Name(String report_Name) {
                this.report_Name = report_Name;
            }

            public String getReport_Content() {
                return report_Content;
            }

            public void setReport_Content(String report_Content) {
                this.report_Content = report_Content;
            }
        }
    }
}
