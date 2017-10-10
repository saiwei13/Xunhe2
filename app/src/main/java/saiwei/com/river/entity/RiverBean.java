package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/22/17.
 */

public class RiverBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 获取河道信息成功
     * responseData : [{"isNewRecord":true,"riverBaseinfoId":"2","countyCode":"350802000000","townCode":"350802006000","riverName":"测试曹溪1","riverLength":"1000","createTime":1499999844000,"updateTime":1499999844000,"relationPeople":0}]
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
         * isNewRecord : true
         * riverBaseinfoId : 2
         * countyCode : 350802000000
         * townCode : 350802006000
         * riverName : 测试曹溪1
         * riverLength : 1000
         * createTime : 1499999844000
         * updateTime : 1499999844000
         * relationPeople : 0
         */

        private boolean isNewRecord;
        private String riverBaseinfoId;
        private String countyCode;
        private String townCode;
        private String riverName;
        private String riverLength;
        private long createTime;
        private long updateTime;
        private int relationPeople;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRiverBaseinfoId() {
            return riverBaseinfoId;
        }

        public void setRiverBaseinfoId(String riverBaseinfoId) {
            this.riverBaseinfoId = riverBaseinfoId;
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

        public String getRiverName() {
            return riverName;
        }

        public void setRiverName(String riverName) {
            this.riverName = riverName;
        }

        public String getRiverLength() {
            return riverLength;
        }

        public void setRiverLength(String riverLength) {
            this.riverLength = riverLength;
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

        public int getRelationPeople() {
            return relationPeople;
        }

        public void setRelationPeople(int relationPeople) {
            this.relationPeople = relationPeople;
        }
    }
}
