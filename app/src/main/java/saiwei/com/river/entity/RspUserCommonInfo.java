package saiwei.com.river.entity;

/**
 * Created by saiwei on 9/30/17.
 */
public class RspUserCommonInfo {

    /**
     * rtnCode : 000000
     * rtnMsg : 获取用户信息成功
     * responseData : {"uncompleteCounts":"13","cruiseCounts":2}
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
         * uncompleteCounts : 13
         * cruiseCounts : 2
         */
        private String uncompleteCounts;
        private int cruiseCounts;

        public String getUncompleteCounts() {
            return uncompleteCounts;
        }

        public void setUncompleteCounts(String uncompleteCounts) {
            this.uncompleteCounts = uncompleteCounts;
        }

        public int getCruiseCounts() {
            return cruiseCounts;
        }

        public void setCruiseCounts(int cruiseCounts) {
            this.cruiseCounts = cruiseCounts;
        }
    }
}

