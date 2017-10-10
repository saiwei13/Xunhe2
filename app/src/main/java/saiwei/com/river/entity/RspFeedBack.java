package saiwei.com.river.entity;

/**
 * Created by saiwei on 9/24/17.
 */

public class RspFeedBack {


    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"id":"98ecf0bd1c1b4e5db9a1dc87421bddf2","insertCount":1}
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
         * id : 98ecf0bd1c1b4e5db9a1dc87421bddf2
         * insertCount : 1
         */

        private String id;
        private int insertCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getInsertCount() {
            return insertCount;
        }

        public void setInsertCount(int insertCount) {
            this.insertCount = insertCount;
        }
    }
}
