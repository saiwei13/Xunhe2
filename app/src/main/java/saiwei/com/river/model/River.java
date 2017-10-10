package saiwei.com.river.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by saiwei on 9/22/17.
 *
 * "isNewRecord": true,
 "riverBaseinfoId": "2",
 "countyCode": "350802000000",
 "townCode": "350802006000",
 "riverName": "测试曹溪1",
 "riverLength": "1000",
 "createTime": 1499999844000,
 "updateTime": 1499999844000,
 "relationPeople": 0
 *
 */

@Entity
public class River {

    private boolean isNewRecord;
    @Id
    private String riverBaseinfoId;
    private String countyCode;
    private String townCode;
    private String riverName;
    private String riverLength;
    private long createTime;
    private long updateTime;
    private int relationPeople;
    @Generated(hash = 1134729257)
    public River(boolean isNewRecord, String riverBaseinfoId, String countyCode,
            String townCode, String riverName, String riverLength, long createTime,
            long updateTime, int relationPeople) {
        this.isNewRecord = isNewRecord;
        this.riverBaseinfoId = riverBaseinfoId;
        this.countyCode = countyCode;
        this.townCode = townCode;
        this.riverName = riverName;
        this.riverLength = riverLength;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.relationPeople = relationPeople;
    }
    @Generated(hash = 390972534)
    public River() {
    }
    public boolean getIsNewRecord() {
        return this.isNewRecord;
    }
    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }
    public String getRiverBaseinfoId() {
        return this.riverBaseinfoId;
    }
    public void setRiverBaseinfoId(String riverBaseinfoId) {
        this.riverBaseinfoId = riverBaseinfoId;
    }
    public String getCountyCode() {
        return this.countyCode;
    }
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }
    public String getTownCode() {
        return this.townCode;
    }
    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }
    public String getRiverName() {
        return this.riverName;
    }
    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }
    public String getRiverLength() {
        return this.riverLength;
    }
    public void setRiverLength(String riverLength) {
        this.riverLength = riverLength;
    }
    public long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public long getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
    public int getRelationPeople() {
        return this.relationPeople;
    }
    public void setRelationPeople(int relationPeople) {
        this.relationPeople = relationPeople;
    }
}
