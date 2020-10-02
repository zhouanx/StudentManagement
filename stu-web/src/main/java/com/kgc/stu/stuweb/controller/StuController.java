package com.kgc.stu.stuweb.controller;

import com.kgc.stu.bean.StudentInfo;
import com.kgc.stu.service.StuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StuController {
    @Reference
    StuService stuService;


    @RequestMapping("/stu/list")
    @ResponseBody
    public List<StudentInfo> studentInfoList(){
        List<StudentInfo> studentInfos = stuService.StuList();
        return studentInfos;
    }

    @RequestMapping("/get/id")
    @ResponseBody
    public StudentInfo getId(Integer id){
        StudentInfo student = stuService.getStudentById(id);
        return student;
    }
    @RequestMapping("/saveStu")
    @ResponseBody
    public int saveStu(@RequestBody StudentInfo studentInfo){
        int i = stuService.stuSave(studentInfo);
        return i;
    }
}
