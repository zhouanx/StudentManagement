package com.kgc.stu;

import com.kgc.stu.bean.StudentInfo;
import com.kgc.stu.service.StuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class StuServiceApplicationTests {
    @Reference
    StuService stuService;
    @Resource
    JestClient jestClient;

    @Test
    void contextLoads() {
        List<StudentInfo> allstu = stuService.getAllStu();
        System.out.println("students:"+allstu);
        List<StudentInfo> studentInfos=new ArrayList<>();
        for (StudentInfo stu : allstu) {
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(stu,studentInfo);
            studentInfos.add(studentInfo);
        }
        System.out.println(studentInfos);
        for (StudentInfo stu : studentInfos) {
            Index index=new Index.Builder(stu).index("stu").type("studentinfo").id(stu.getSid()+"").build();
            try {
                jestClient.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    void test(){
        List<StudentInfo> studentInfos = stuService.StuList();
        for (StudentInfo studentInfo : studentInfos) {
            System.out.println(studentInfo);
        }
    }
}
