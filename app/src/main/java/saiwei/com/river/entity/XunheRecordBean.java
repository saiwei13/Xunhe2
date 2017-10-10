package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/24/17.
 */

public class XunheRecordBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 巡河上报成功
     * responseData : [{"userId":"","reportRiver":"","reportRiverId":"","tourTime":"","totalTime":"","isHDZZ":"","isHDBJ":"","isWSPK":"","isSSWF":"","isHALJ":"","isHDSZ":"","isHDWN":"","isRHWK":"","isPHSS":"","isFFBY":"","isHZTS":"","isYXSZ":"","xyJsonInfos":"","complaintInfos":""}]
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
         * userId :
         * reportRiver :
         * reportRiverId :
         * tourTime :
         * totalTime :
         * isHDZZ :
         * isHDBJ :
         * isWSPK :
         * isSSWF :
         * isHALJ :
         * isHDSZ :
         * isHDWN :
         * isRHWK :
         * isPHSS :
         * isFFBY :
         * isHZTS :
         * isYXSZ :
         * xyJsonInfos :
         * complaintInfos :
         */

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
        private String isHDWN;
        private String isRHWK;
        private String isPHSS;
        private String isFFBY;
        private String isHZTS;
        private String isYXSZ;
        private String xyJsonInfos;
        private String complaintInfos;

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

        public String getXyJsonInfos() {
            return xyJsonInfos;
        }

        public void setXyJsonInfos(String xyJsonInfos) {
            this.xyJsonInfos = xyJsonInfos;
        }

        public String getComplaintInfos() {
            return complaintInfos;
        }

        public void setComplaintInfos(String complaintInfos) {
            this.complaintInfos = complaintInfos;
        }
    }
}
