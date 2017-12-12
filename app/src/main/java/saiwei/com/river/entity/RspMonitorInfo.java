package saiwei.com.river.entity;

/**
 * Created by saiwei on 12/12/17.
 */

public class RspMonitorInfo {

    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"flag":"insert,Tue Dec 12 11:47:41 CST 2017","count":1}
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
         * flag : insert,Tue Dec 12 11:47:41 CST 2017
         * count : 1
         */

        private String flag;
        private int count;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
