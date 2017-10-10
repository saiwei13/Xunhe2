package saiwei.com.river.entity;

/**
 * Created by saiwei on 9/28/17.
 */

public class RspClaimComplaint {


    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"update":1,"insert":1}
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
         * update : 1
         * insert : 1
         */

        private int update;
        private int insert;

        public int getUpdate() {
            return update;
        }

        public void setUpdate(int update) {
            this.update = update;
        }

        public int getInsert() {
            return insert;
        }

        public void setInsert(int insert) {
            this.insert = insert;
        }
    }
}
