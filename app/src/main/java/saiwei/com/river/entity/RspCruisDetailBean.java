package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/25/17.
 */

public class RspCruisDetailBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 获取信息成功
     * responseData : {"tourriver":{"isNewRecord":true,"tourRiverID":"e052782fe58a4cb886e4db523b2027dd","userId":"8490f2aa96dd4f72929a97e64031e53e","reportRiver":"测试曹溪1","reportRiverId":"2","tourTime":"2017-09-15 15:02:50","totalTime":"00 :05: 28","isHDZZ":"1","isHDBJ":"1","isWSPK":"1","isSSWF":"1","isHALJ":"1","isHDSZ":"1","userName":"lytest001","createTime":1505624944000,"isHDWN":"1","isRHWK":"1","isPHSS":"1","isFFBY":"1","isHZTS":"1","isYXSZ":"1"},"tourcoordinateList":[{"tourCoordinateId":"f153d7e9a91d419082fba7978f0e7ce6","tourRiverID":"e052782fe58a4cb886e4db523b2027dd","coordinateX":"25.074382","coordinateY":"117.017782","createTime":1505459082000},{"tourCoordinateId":"a91edb0753614ca7bd31967e62381cec","tourRiverID":"e052782fe58a4cb886e4db523b2027dd","coordinateX":"25.07636","coordinateY":"117.017769","createTime":1505459202000},{"tourCoordinateId":"0fabf6103fdf4fe0ac679d37ba20dfc9","tourRiverID":"e052782fe58a4cb886e4db523b2027dd","coordinateX":"25.078373","coordinateY":"117.017771","createTime":1505459202000},{"tourCoordinateId":"bd808ed864c1422b89ae55bdd3f7d29f","tourRiverID":"e052782fe58a4cb886e4db523b2027dd","coordinateX":"25.08035","coordinateY":"117.017788","createTime":1505459322000}],"publicReportList":[{"publicReportId":"2118588cbee541bdb3c5cd86b92d1535","reportContent":"testq3"},{"publicReportId":"689068d59c0b417b9c3016f5b5892437","reportContent":"testq4"}]}
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
         * tourriver : {"isNewRecord":true,"tourRiverID":"e052782fe58a4cb886e4db523b2027dd","userId":"8490f2aa96dd4f72929a97e64031e53e","reportRiver":"测试曹溪1","reportRiverId":"2","tourTime":"2017-09-15 15:02:50","totalTime":"00 :05: 28","isHDZZ":"1","isHDBJ":"1","isWSPK":"1","isSSWF":"1","isHALJ":"1","isHDSZ":"1","userName":"lytest001","createTime":1505624944000,"isHDWN":"1","isRHWK":"1","isPHSS":"1","isFFBY":"1","isHZTS":"1","isYXSZ":"1"}
         * tourcoordinateList : [{"tourCoordinateId":"f153d7e9a91d419082fba7978f0e7ce6","tourRiverID":"e052782fe58a4cb886e4db523b2027dd","coordinateX":"25.074382","coordinateY":"117.017782","createTime":1505459082000},{"tourCoordinateId":"a91edb0753614ca7bd31967e62381cec","tourRiverID":"e052782fe58a4cb886e4db523b2027dd","coordinateX":"25.07636","coordinateY":"117.017769","createTime":1505459202000},{"tourCoordinateId":"0fabf6103fdf4fe0ac679d37ba20dfc9","tourRiverID":"e052782fe58a4cb886e4db523b2027dd","coordinateX":"25.078373","coordinateY":"117.017771","createTime":1505459202000},{"tourCoordinateId":"bd808ed864c1422b89ae55bdd3f7d29f","tourRiverID":"e052782fe58a4cb886e4db523b2027dd","coordinateX":"25.08035","coordinateY":"117.017788","createTime":1505459322000}]
         * publicReportList : [{"publicReportId":"2118588cbee541bdb3c5cd86b92d1535","reportContent":"testq3"},{"publicReportId":"689068d59c0b417b9c3016f5b5892437","reportContent":"testq4"}]
         */

        private TourriverBean tourriver;
        private List<TourcoordinateListBean> tourcoordinateList;
        private List<PublicReportListBean> publicReportList;

        public TourriverBean getTourriver() {
            return tourriver;
        }

        public void setTourriver(TourriverBean tourriver) {
            this.tourriver = tourriver;
        }

        public List<TourcoordinateListBean> getTourcoordinateList() {
            return tourcoordinateList;
        }

        public void setTourcoordinateList(List<TourcoordinateListBean> tourcoordinateList) {
            this.tourcoordinateList = tourcoordinateList;
        }

        public List<PublicReportListBean> getPublicReportList() {
            return publicReportList;
        }

        public void setPublicReportList(List<PublicReportListBean> publicReportList) {
            this.publicReportList = publicReportList;
        }

        public static class TourriverBean {
            /**
             * isNewRecord : true
             * tourRiverID : e052782fe58a4cb886e4db523b2027dd
             * userId : 8490f2aa96dd4f72929a97e64031e53e
             * reportRiver : 测试曹溪1
             * reportRiverId : 2
             * tourTime : 2017-09-15 15:02:50
             * totalTime : 00 :05: 28
             * isHDZZ : 1
             * isHDBJ : 1
             * isWSPK : 1
             * isSSWF : 1
             * isHALJ : 1
             * isHDSZ : 1
             * userName : lytest001
             * createTime : 1505624944000
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
            private String userName;
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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
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

        public static class TourcoordinateListBean {
            /**
             * tourCoordinateId : f153d7e9a91d419082fba7978f0e7ce6
             * tourRiverID : e052782fe58a4cb886e4db523b2027dd
             * coordinateX : 25.074382
             * coordinateY : 117.017782
             * createTime : 1505459082000
             */

            private String tourCoordinateId;
            private String tourRiverID;
            private String coordinateX;
            private String coordinateY;
            private long createTime;

            public String getTourCoordinateId() {
                return tourCoordinateId;
            }

            public void setTourCoordinateId(String tourCoordinateId) {
                this.tourCoordinateId = tourCoordinateId;
            }

            public String getTourRiverID() {
                return tourRiverID;
            }

            public void setTourRiverID(String tourRiverID) {
                this.tourRiverID = tourRiverID;
            }

            public String getCoordinateX() {
                return coordinateX;
            }

            public void setCoordinateX(String coordinateX) {
                this.coordinateX = coordinateX;
            }

            public String getCoordinateY() {
                return coordinateY;
            }

            public void setCoordinateY(String coordinateY) {
                this.coordinateY = coordinateY;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }

        public static class PublicReportListBean {
            /**
             * publicReportId : 2118588cbee541bdb3c5cd86b92d1535
             * reportContent : testq3
             * reportImg :
             * processContent:
             * processImg
             */

            private String publicReportId;
            private String reportContent;

            private String reportImg;
            private String processContent;
            private String processImg;

            public String getPublicReportId() {
                return publicReportId;
            }

            public void setPublicReportId(String publicReportId) {
                this.publicReportId = publicReportId;
            }

            public String getReportContent() {
                return reportContent;
            }

            public void setReportContent(String reportContent) {
                this.reportContent = reportContent;
            }

            public String getReportImg() {
                return reportImg;
            }

            public void setReportImg(String reportImg) {
                this.reportImg = reportImg;
            }

            public String getProcessContent() {
                return processContent;
            }

            public void setProcessContent(String processContent) {
                this.processContent = processContent;
            }

            public String getProcessImg() {
                return processImg;
            }

            public void setProcessImg(String processImg) {
                this.processImg = processImg;
            }
        }
    }
}
