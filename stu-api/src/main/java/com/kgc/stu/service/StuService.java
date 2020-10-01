package com.kgc.stu.service;

import com.kgc.stu.bean.StudentInfo;

import java.util.List;

public interface StuService {
    //
    public List<StudentInfo> getAllStu();
    //显示学员列表
    public List<StudentInfo> StuList();
    //根据id查询
    public StudentInfo getStudentById(Integer id);
    //更新学员信息
    public int stuSave(StudentInfo studentInfo);
}
