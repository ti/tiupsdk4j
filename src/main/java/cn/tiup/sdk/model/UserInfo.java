package cn.tiup.sdk.model;

import java.util.List;

public class UserInfo {
    private String uid;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private String avatar;
    private String birthday;
    private List<ProfilesBean> profiles;

    public String getUserId() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public List<ProfilesBean> getProfiles() {
        return profiles;
    }

    public static class ProfilesBean {
        private String code;
        private String schoolname;
        private String departmenttype;
        private String departmentname;
        private String departmentid;
        private String roletype;
        private String rolename;
        private String stno;
        private boolean isprimary;

        public String getDepartmentid() {
            return departmentid;
        }

        public void setDepartmentid(String departmentid) {
            this.departmentid = departmentid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getDepartmenttype() {
            return departmenttype;
        }

        public void setDepartmenttype(String departmenttype) {
            this.departmenttype = departmenttype;
        }

        public String getDepartmentname() {
            return departmentname;
        }

        public void setDepartmentname(String departmentname) {
            this.departmentname = departmentname;
        }

        public String getRoletype() {
            return roletype;
        }

        public void setRoletype(String roletype) {
            this.roletype = roletype;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        public String getStno() {
            return stno;
        }

        public void setStno(String stno) {
            this.stno = stno;
        }

        public boolean isIsprimary() {
            return isprimary;
        }

        public void setIsprimary(boolean isprimary) {
            this.isprimary = isprimary;
        }
    }


}
