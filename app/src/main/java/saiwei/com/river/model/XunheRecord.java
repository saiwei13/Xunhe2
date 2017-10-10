package saiwei.com.river.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by saiwei on 9/22/17.
 */

@Entity
public class XunheRecord {

    /**
     * 跟gps文件名一致，统一作为主键
     */
    @Id
    private long recordId;

    private String userid;
    private String reportRiver;
    private String reportRiverId;
    private String tourTime;
    private String totalTime;
    private String isHDZZ ;
    private String isHDBJ ;
    private String isWSPK ;
    private String isSSWF ;
    private String isHALJ ;
    private String isHDSZ ;

    private String isHDWN ;
    private String isRHWK ;
    private String isPHSS ;
    private String isFFBY ;
    private String isHZTS ;
    private String isYXSZ ;

    private String xyJsonInfos;
    private String complaintInfos;

    @Generated(hash = 931310061)
    public XunheRecord(long recordId, String userid, String reportRiver,
            String reportRiverId, String tourTime, String totalTime, String isHDZZ,
            String isHDBJ, String isWSPK, String isSSWF, String isHALJ,
            String isHDSZ, String isHDWN, String isRHWK, String isPHSS,
            String isFFBY, String isHZTS, String isYXSZ, String xyJsonInfos,
            String complaintInfos) {
        this.recordId = recordId;
        this.userid = userid;
        this.reportRiver = reportRiver;
        this.reportRiverId = reportRiverId;
        this.tourTime = tourTime;
        this.totalTime = totalTime;
        this.isHDZZ = isHDZZ;
        this.isHDBJ = isHDBJ;
        this.isWSPK = isWSPK;
        this.isSSWF = isSSWF;
        this.isHALJ = isHALJ;
        this.isHDSZ = isHDSZ;
        this.isHDWN = isHDWN;
        this.isRHWK = isRHWK;
        this.isPHSS = isPHSS;
        this.isFFBY = isFFBY;
        this.isHZTS = isHZTS;
        this.isYXSZ = isYXSZ;
        this.xyJsonInfos = xyJsonInfos;
        this.complaintInfos = complaintInfos;
    }
    @Generated(hash = 1105641989)
    public XunheRecord() {
    }
    public long getRecordId() {
        return this.recordId;
    }
    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getReportRiver() {
        return this.reportRiver;
    }
    public void setReportRiver(String reportRiver) {
        this.reportRiver = reportRiver;
    }
    public String getReportRiverId() {
        return this.reportRiverId;
    }
    public void setReportRiverId(String reportRiverId) {
        this.reportRiverId = reportRiverId;
    }
    public String getTourTime() {
        return this.tourTime;
    }
    public void setTourTime(String tourTime) {
        this.tourTime = tourTime;
    }
    public String getTotalTime() {
        return this.totalTime;
    }
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
    public String getIsHDZZ() {
        return this.isHDZZ;
    }
    public void setIsHDZZ(String isHDZZ) {
        this.isHDZZ = isHDZZ;
    }
    public String getIsHDBJ() {
        return this.isHDBJ;
    }
    public void setIsHDBJ(String isHDBJ) {
        this.isHDBJ = isHDBJ;
    }
    public String getIsWSPK() {
        return this.isWSPK;
    }
    public void setIsWSPK(String isWSPK) {
        this.isWSPK = isWSPK;
    }
    public String getIsSSWF() {
        return this.isSSWF;
    }
    public void setIsSSWF(String isSSWF) {
        this.isSSWF = isSSWF;
    }
    public String getIsHALJ() {
        return this.isHALJ;
    }
    public void setIsHALJ(String isHALJ) {
        this.isHALJ = isHALJ;
    }
    public String getIsHDSZ() {
        return this.isHDSZ;
    }
    public void setIsHDSZ(String isHDSZ) {
        this.isHDSZ = isHDSZ;
    }
    public String getIsHDWN() {
        return this.isHDWN;
    }
    public void setIsHDWN(String isHDWN) {
        this.isHDWN = isHDWN;
    }
    public String getIsRHWK() {
        return this.isRHWK;
    }
    public void setIsRHWK(String isRHWK) {
        this.isRHWK = isRHWK;
    }
    public String getIsPHSS() {
        return this.isPHSS;
    }
    public void setIsPHSS(String isPHSS) {
        this.isPHSS = isPHSS;
    }
    public String getIsFFBY() {
        return this.isFFBY;
    }
    public void setIsFFBY(String isFFBY) {
        this.isFFBY = isFFBY;
    }
    public String getIsHZTS() {
        return this.isHZTS;
    }
    public void setIsHZTS(String isHZTS) {
        this.isHZTS = isHZTS;
    }
    public String getIsYXSZ() {
        return this.isYXSZ;
    }
    public void setIsYXSZ(String isYXSZ) {
        this.isYXSZ = isYXSZ;
    }
    public String getXyJsonInfos() {
        return this.xyJsonInfos;
    }
    public void setXyJsonInfos(String xyJsonInfos) {
        this.xyJsonInfos = xyJsonInfos;
    }
    public String getComplaintInfos() {
        return this.complaintInfos;
    }
    public void setComplaintInfos(String complaintInfos) {
        this.complaintInfos = complaintInfos;
    }




}
