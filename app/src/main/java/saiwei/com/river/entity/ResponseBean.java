package saiwei.com.river.entity;

/**
 * Created by saiwei on 9/24/17.
 */

public class ResponseBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 巡河上报成功
     * responseData :
     */

    private String rtnCode;
    private String rtnMsg;
    private String responseData;

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

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
