package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/25/17.
 */

public class RspCruiseBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 本次请求成功!
     * responseData : {"count":6,"varList":[{"isNewRecord":true,"tourRiverID":"7af89cbdfbde46fe9eb493a6cc0216ea","userId":"8490f2aa96dd4f72929a97e64031e53e","reportRiver":"测试曹溪1","reportRiverId":"2","tourTime":"2017-08-14 16:06:47","totalTime":"00 :00: 35","isHDZZ":"1","isHDBJ":"1","isWSPK":"1","isSSWF":"1","isHALJ":"1","isHDSZ":"1","createTime":1502696891000,"isHDWN":"1","isRHWK":"1","isPHSS":"1","isFFBY":"1","isHZTS":"1","isYXSZ":"1"}]}
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
         * count : 6
         * varList : [{"isNewRecord":true,"tourRiverID":"7af89cbdfbde46fe9eb493a6cc0216ea","userId":"8490f2aa96dd4f72929a97e64031e53e","reportRiver":"测试曹溪1","reportRiverId":"2","tourTime":"2017-08-14 16:06:47","totalTime":"00 :00: 35","isHDZZ":"1","isHDBJ":"1","isWSPK":"1","isSSWF":"1","isHALJ":"1","isHDSZ":"1","createTime":1502696891000,"isHDWN":"1","isRHWK":"1","isPHSS":"1","isFFBY":"1","isHZTS":"1","isYXSZ":"1"}]
         */

        private int count;
        private List<VarListBean> varList;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<VarListBean> getVarList() {
            return varList;
        }

        public void setVarList(List<VarListBean> varList) {
            this.varList = varList;
        }

        public static class VarListBean {
            /**
             * isNewRecord : true
             * tourRiverID : 7af89cbdfbde46fe9eb493a6cc0216ea
             * userId : 8490f2aa96dd4f72929a97e64031e53e
             * reportRiver : 测试曹溪1
             * reportRiverId : 2
             * tourTime : 2017-08-14 16:06:47
             * totalTime : 00 :00: 35
             * isHDZZ : 1
             * isHDBJ : 1
             * isWSPK : 1
             * isSSWF : 1
             * isHALJ : 1
             * isHDSZ : 1
             * createTime : 1502696891000
             * isHDWN : 1
             * isRHWK : 1
             * isPHSS : 1
             * isFFBY : 1
             * isHZTS : 1
             * isYXSZ : 1
             */

            private boolean isNewRecord;
            private String tourRiverID;
            private String userId;
            private String reportRiver;
            private String reportRiverId;
            private String tourTime;
            private String totalTime;
            private String isHDZZ;
            private String isHDBJ;
            private String isWSPK;
            private String isSSWF;
            private String isHALJ;
            private String isHDSZ;
            private long createTime;
            private String isHDWN;
            private String isRHWK;
            private String isPHSS;
            private String isFFBY;
            private String isHZTS;
            private String isYXSZ;

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getTourRiverID() {
                return tourRiverID;
            }

            public void setTourRiverID(String tourRiverID) {
                this.tourRiverID = tourRiverID;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getReportRiver() {
                return reportRiver;
            }

            public void setReportRiver(String reportRiver) {
                this.reportRiver = reportRiver;
            }

            public String getReportRiverId() {
                return reportRiverId;
            }

            public void setReportRiverId(String reportRiverId) {
                this.reportRiverId = reportRiverId;
            }

            public String getTourTime() {
                return tourTime;
            }

            public void setTourTime(String tourTime) {
                this.tourTime = tourTime;
            }

            public String getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(String totalTime) {
                this.totalTime = totalTime;
            }

            public String getIsHDZZ() {
                return isHDZZ;
            }

            public void setIsHDZZ(String isHDZZ) {
                this.isHDZZ = isHDZZ;
            }

            public String getIsHDBJ() {
                return isHDBJ;
            }

            public void setIsHDBJ(String isHDBJ) {
                this.isHDBJ = isHDBJ;
            }

            public String getIsWSPK() {
                return isWSPK;
            }

            public void setIsWSPK(String isWSPK) {
                this.isWSPK = isWSPK;
            }

            public String getIsSSWF() {
                return isSSWF;
            }

            public void setIsSSWF(String isSSWF) {
                this.isSSWF = isSSWF;
            }

            public String getIsHALJ() {
                return isHALJ;
            }

            public void setIsHALJ(String isHALJ) {
                this.isHALJ = isHALJ;
            }

            public String getIsHDSZ() {
                return isHDSZ;
            }

            public void setIsHDSZ(String isHDSZ) {
                this.isHDSZ = isHDSZ;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getIsHDWN() {
                return isHDWN;
            }

            public void setIsHDWN(String isHDWN) {
                this.isHDWN = isHDWN;
            }

            public String getIsRHWK() {
                return isRHWK;
            }

            public void setIsRHWK(String isRHWK) {
                this.isRHWK = isRHWK;
            }

            public String getIsPHSS() {
                return isPHSS;
            }

            public void setIsPHSS(String isPHSS) {
                this.isPHSS = isPHSS;
            }

            public String getIsFFBY() {
                return isFFBY;
            }

            public void setIsFFBY(String isFFBY) {
                this.isFFBY = isFFBY;
            }

            public String getIsHZTS() {
                return isHZTS;
            }

            public void setIsHZTS(String isHZTS) {
                this.isHZTS = isHZTS;
            }

            public String getIsYXSZ() {
                return isYXSZ;
            }

            public void setIsYXSZ(String isYXSZ) {
                this.isYXSZ = isYXSZ;
            }
        }
    }
}
