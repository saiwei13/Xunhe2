package saiwei.com.river.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by saiwei on 9/22/17.
 */
@Entity
public class User {
    

    /**
     * 用户id ，网络请求的时候要用到
     */
    @Id
    private String userid;
    
    private String name;
    private int no;
    private String loginName;
    private String email;
    private String phone;
    private String mobile;
    private long loginDate;

    @Generated(hash = 1430464751)
    public User(String userid, String name, int no, String loginName, String email,
            String phone, String mobile, long loginDate) {
        this.userid = userid;
        this.name = name;
        this.no = no;
        this.loginName = loginName;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.loginDate = loginDate;
    }

    @Generated(hash = 586692638)
    public User() {
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return this.no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
