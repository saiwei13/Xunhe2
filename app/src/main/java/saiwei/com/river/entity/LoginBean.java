package saiwei.com.river.entity;

import java.util.List;

/**
 * Created by saiwei on 9/21/17.
 */

public class LoginBean {


    /**
     * rtnCode : 000000
     * rtnMsg : 登陆成功
     * responseData : {"id":"8490f2aa96dd4f72929a97e64031e53e","office":{"id":"d9e33ad828a44fde86c71727456446f0","isNewRecord":false,"remarks":"","createDate":"2017-05-24 21:48:05","updateDate":"2017-05-24 21:48:05","parentIds":"0,b9fae7ac2dde4757b067022e064de0a6,c7928f2db4694967bbe1f16117ab13fd,63da8c2f7ff94e2e9e6d777f5b84c58f,","name":"曹溪街道","sort":30,"area":{"id":"b088e22467194989ae9e9d7bc7528b60","isNewRecord":false,"parentIds":"0,1,","name":"福建省","sort":30,"parentId":"0"},"code":"350802006000","type":"2","grade":"4","address":"","zipCode":"","master":"","phone":"","fax":"","email":"","useable":"1","primaryPerson":{"id":"","isNewRecord":true,"loginFlag":"1","admin":false,"roleNames":""},"deputyPerson":{"id":"","isNewRecord":true,"loginFlag":"1","admin":false,"roleNames":""},"parentId":"63da8c2f7ff94e2e9e6d777f5b84c58f"},"loginName":"test_lytest001","no":"200001","name":"lytest001","email":"","phone":"","mobile":"18039872310","loginIp":"202.109.232.99","loginDate":1505978683640,"loginFlag":"1","photo":"","roleList":[{"id":"537b9f5d47c0401b924fc23a821e1980","isNewRecord":false,"remarks":"","name":"乡镇专管员","enname":"townManage","roleType":"assignment","dataScope":"8","sysData":"1","useable":"1","menuIds":"","officeIds":""}],"hzOrgCode":{"county":"新罗区","countycode":"350802000000","town":"曹溪街道","towncode":"350802006000"}}
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
         * id : 8490f2aa96dd4f72929a97e64031e53e
         * office : {"id":"d9e33ad828a44fde86c71727456446f0","isNewRecord":false,"remarks":"","createDate":"2017-05-24 21:48:05","updateDate":"2017-05-24 21:48:05","parentIds":"0,b9fae7ac2dde4757b067022e064de0a6,c7928f2db4694967bbe1f16117ab13fd,63da8c2f7ff94e2e9e6d777f5b84c58f,","name":"曹溪街道","sort":30,"area":{"id":"b088e22467194989ae9e9d7bc7528b60","isNewRecord":false,"parentIds":"0,1,","name":"福建省","sort":30,"parentId":"0"},"code":"350802006000","type":"2","grade":"4","address":"","zipCode":"","master":"","phone":"","fax":"","email":"","useable":"1","primaryPerson":{"id":"","isNewRecord":true,"loginFlag":"1","admin":false,"roleNames":""},"deputyPerson":{"id":"","isNewRecord":true,"loginFlag":"1","admin":false,"roleNames":""},"parentId":"63da8c2f7ff94e2e9e6d777f5b84c58f"}
         * loginName : test_lytest001
         * no : 200001
         * name : lytest001
         * email :
         * phone :
         * mobile : 18039872310
         * loginIp : 202.109.232.99
         * loginDate : 1505978683640
         * loginFlag : 1
         * photo :
         * roleList : [{"id":"537b9f5d47c0401b924fc23a821e1980","isNewRecord":false,"remarks":"","name":"乡镇专管员","enname":"townManage","roleType":"assignment","dataScope":"8","sysData":"1","useable":"1","menuIds":"","officeIds":""}]
         * hzOrgCode : {"county":"新罗区","countycode":"350802000000","town":"曹溪街道","towncode":"350802006000"}
         */

        private String id;
        private OfficeBean office;
        private String loginName;
        private String no;
        private String name;
        private String email;
        private String phone;
        private String mobile;
        private String loginIp;
        private long loginDate;
        private String loginFlag;
        private String photo;
        private HzOrgCodeBean hzOrgCode;
        private List<RoleListBean> roleList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public OfficeBean getOffice() {
            return office;
        }

        public void setOffice(OfficeBean office) {
            this.office = office;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(String loginIp) {
            this.loginIp = loginIp;
        }

        public long getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(long loginDate) {
            this.loginDate = loginDate;
        }

        public String getLoginFlag() {
            return loginFlag;
        }

        public void setLoginFlag(String loginFlag) {
            this.loginFlag = loginFlag;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public HzOrgCodeBean getHzOrgCode() {
            return hzOrgCode;
        }

        public void setHzOrgCode(HzOrgCodeBean hzOrgCode) {
            this.hzOrgCode = hzOrgCode;
        }

        public List<RoleListBean> getRoleList() {
            return roleList;
        }

        public void setRoleList(List<RoleListBean> roleList) {
            this.roleList = roleList;
        }

        public static class OfficeBean {
            /**
             * id : d9e33ad828a44fde86c71727456446f0
             * isNewRecord : false
             * remarks :
             * createDate : 2017-05-24 21:48:05
             * updateDate : 2017-05-24 21:48:05
             * parentIds : 0,b9fae7ac2dde4757b067022e064de0a6,c7928f2db4694967bbe1f16117ab13fd,63da8c2f7ff94e2e9e6d777f5b84c58f,
             * name : 曹溪街道
             * sort : 30
             * area : {"id":"b088e22467194989ae9e9d7bc7528b60","isNewRecord":false,"parentIds":"0,1,","name":"福建省","sort":30,"parentId":"0"}
             * code : 350802006000
             * type : 2
             * grade : 4
             * address :
             * zipCode :
             * master :
             * phone :
             * fax :
             * email :
             * useable : 1
             * primaryPerson : {"id":"","isNewRecord":true,"loginFlag":"1","admin":false,"roleNames":""}
             * deputyPerson : {"id":"","isNewRecord":true,"loginFlag":"1","admin":false,"roleNames":""}
             * parentId : 63da8c2f7ff94e2e9e6d777f5b84c58f
             */

            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String updateDate;
            private String parentIds;
            private String name;
            private int sort;
            private AreaBean area;
            private String code;
            private String type;
            private String grade;
            private String address;
            private String zipCode;
            private String master;
            private String phone;
            private String fax;
            private String email;
            private String useable;
            private PrimaryPersonBean primaryPerson;
            private DeputyPersonBean deputyPerson;
            private String parentId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getParentIds() {
                return parentIds;
            }

            public void setParentIds(String parentIds) {
                this.parentIds = parentIds;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public AreaBean getArea() {
                return area;
            }

            public void setArea(AreaBean area) {
                this.area = area;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getZipCode() {
                return zipCode;
            }

            public void setZipCode(String zipCode) {
                this.zipCode = zipCode;
            }

            public String getMaster() {
                return master;
            }

            public void setMaster(String master) {
                this.master = master;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getFax() {
                return fax;
            }

            public void setFax(String fax) {
                this.fax = fax;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getUseable() {
                return useable;
            }

            public void setUseable(String useable) {
                this.useable = useable;
            }

            public PrimaryPersonBean getPrimaryPerson() {
                return primaryPerson;
            }

            public void setPrimaryPerson(PrimaryPersonBean primaryPerson) {
                this.primaryPerson = primaryPerson;
            }

            public DeputyPersonBean getDeputyPerson() {
                return deputyPerson;
            }

            public void setDeputyPerson(DeputyPersonBean deputyPerson) {
                this.deputyPerson = deputyPerson;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public static class AreaBean {
                /**
                 * id : b088e22467194989ae9e9d7bc7528b60
                 * isNewRecord : false
                 * parentIds : 0,1,
                 * name : 福建省
                 * sort : 30
                 * parentId : 0
                 */

                private String id;
                private boolean isNewRecord;
                private String parentIds;
                private String name;
                private int sort;
                private String parentId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isIsNewRecord() {
                    return isNewRecord;
                }

                public void setIsNewRecord(boolean isNewRecord) {
                    this.isNewRecord = isNewRecord;
                }

                public String getParentIds() {
                    return parentIds;
                }

                public void setParentIds(String parentIds) {
                    this.parentIds = parentIds;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public String getParentId() {
                    return parentId;
                }

                public void setParentId(String parentId) {
                    this.parentId = parentId;
                }
            }

            public static class PrimaryPersonBean {
                /**
                 * id :
                 * isNewRecord : true
                 * loginFlag : 1
                 * admin : false
                 * roleNames :
                 */

                private String id;
                private boolean isNewRecord;
                private String loginFlag;
                private boolean admin;
                private String roleNames;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isIsNewRecord() {
                    return isNewRecord;
                }

                public void setIsNewRecord(boolean isNewRecord) {
                    this.isNewRecord = isNewRecord;
                }

                public String getLoginFlag() {
                    return loginFlag;
                }

                public void setLoginFlag(String loginFlag) {
                    this.loginFlag = loginFlag;
                }

                public boolean isAdmin() {
                    return admin;
                }

                public void setAdmin(boolean admin) {
                    this.admin = admin;
                }

                public String getRoleNames() {
                    return roleNames;
                }

                public void setRoleNames(String roleNames) {
                    this.roleNames = roleNames;
                }
            }

            public static class DeputyPersonBean {
                /**
                 * id :
                 * isNewRecord : true
                 * loginFlag : 1
                 * admin : false
                 * roleNames :
                 */

                private String id;
                private boolean isNewRecord;
                private String loginFlag;
                private boolean admin;
                private String roleNames;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isIsNewRecord() {
                    return isNewRecord;
                }

                public void setIsNewRecord(boolean isNewRecord) {
                    this.isNewRecord = isNewRecord;
                }

                public String getLoginFlag() {
                    return loginFlag;
                }

                public void setLoginFlag(String loginFlag) {
                    this.loginFlag = loginFlag;
                }

                public boolean isAdmin() {
                    return admin;
                }

                public void setAdmin(boolean admin) {
                    this.admin = admin;
                }

                public String getRoleNames() {
                    return roleNames;
                }

                public void setRoleNames(String roleNames) {
                    this.roleNames = roleNames;
                }
            }
        }

        public static class HzOrgCodeBean {
            /**
             * county : 新罗区
             * countycode : 350802000000
             * town : 曹溪街道
             * towncode : 350802006000
             */

            private String county;
            private String countycode;
            private String town;
            private String towncode;

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getCountycode() {
                return countycode;
            }

            public void setCountycode(String countycode) {
                this.countycode = countycode;
            }

            public String getTown() {
                return town;
            }

            public void setTown(String town) {
                this.town = town;
            }

            public String getTowncode() {
                return towncode;
            }

            public void setTowncode(String towncode) {
                this.towncode = towncode;
            }
        }

        public static class RoleListBean {
            /**
             * id : 537b9f5d47c0401b924fc23a821e1980
             * isNewRecord : false
             * remarks :
             * name : 乡镇专管员
             * enname : townManage
             * roleType : assignment
             * dataScope : 8
             * sysData : 1
             * useable : 1
             * menuIds :
             * officeIds :
             */

            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String name;
            private String enname;
            private String roleType;
            private String dataScope;
            private String sysData;
            private String useable;
            private String menuIds;
            private String officeIds;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEnname() {
                return enname;
            }

            public void setEnname(String enname) {
                this.enname = enname;
            }

            public String getRoleType() {
                return roleType;
            }

            public void setRoleType(String roleType) {
                this.roleType = roleType;
            }

            public String getDataScope() {
                return dataScope;
            }

            public void setDataScope(String dataScope) {
                this.dataScope = dataScope;
            }

            public String getSysData() {
                return sysData;
            }

            public void setSysData(String sysData) {
                this.sysData = sysData;
            }

            public String getUseable() {
                return useable;
            }

            public void setUseable(String useable) {
                this.useable = useable;
            }

            public String getMenuIds() {
                return menuIds;
            }

            public void setMenuIds(String menuIds) {
                this.menuIds = menuIds;
            }

            public String getOfficeIds() {
                return officeIds;
            }

            public void setOfficeIds(String officeIds) {
                this.officeIds = officeIds;
            }
        }
    }
}
