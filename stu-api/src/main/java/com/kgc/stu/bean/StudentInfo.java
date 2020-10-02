package com.kgc.stu.bean;

import java.io.Serializable;

public class StudentInfo implements Serializable {
    private Integer sid;

    private String sname;

    private String sgender;

    private Integer sage;

    private String saddress;

    private String semail;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getSgender() {
        return sgender;
    }

    public void setSgender(String sgender) {
        this.sgender = sgender == null ? null : sgender.trim();
    }

    public Integer getSage() {
        return sage;
    }

    public void setSage(Integer sage) {
        this.sage = sage;
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress == null ? null : saddress.trim();
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", sgender='" + sgender + '\'' +
                ", sage=" + sage +
                ", saddress='" + saddress + '\'' +
                ", semail='" + semail + '\'' +
                '}';
    }
}