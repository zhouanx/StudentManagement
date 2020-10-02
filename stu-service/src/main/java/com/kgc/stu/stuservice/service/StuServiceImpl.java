package com.kgc.stu.stuservice.service;

import com.alibaba.fastjson.JSON;
import com.kgc.stu.bean.StudentInfo;
import com.kgc.stu.service.StuService;
import com.kgc.stu.stuservice.mapper.StudentInfoMapper;
import com.kgc.stu.util.RedisUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;

@Service
public class StuServiceImpl implements StuService {
    @Resource
    StudentInfoMapper stuMapper;
    @Resource
    JestClient jestClient;
    @Resource
    RedisUtil redisUtil;
    @Resource
    RedissonClient redissonClient;
    @Override
    public List<StudentInfo> getAllStu() {
        List<StudentInfo> studentInfos = stuMapper.selectByExample(null);
        return studentInfos;
    }

    @Override
    public List<StudentInfo> StuList() {
        return this.getEs();
    }

    @Override
    public StudentInfo getStudentById(Integer id) {
        String stukey="stu:"+id+":info";
        Jedis jedis=redisUtil.getJedis();
        String stuinfoJson=jedis.get(stukey);
        StudentInfo studentInfo=null;
        if(stuinfoJson!=null){//redis有缓存
            if(stuinfoJson.equals("empty")){
                return null;
            }
            studentInfo= JSON.parseObject(jedis.get(stukey),StudentInfo.class);
        }else{//无缓存

            Lock lock = redissonClient.getLock("lock");// 声明锁
            lock.lock();//上锁
            //查询sku
            studentInfo = stuMapper.selectByPrimaryKey(id);

            if(studentInfo!=null){
                //随机时间，防止缓存雪崩
                Random random=new Random();
                int i = random.nextInt(10);
                jedis.setex(stukey,i*60*10,JSON.toJSONString(studentInfo));
            }else{
                //空数据，存储5分钟，防止缓存穿透
                jedis.setex(stukey,5*6*1,"empty");
            }
            // 删除分布式锁
            lock.unlock();
        }
        jedis.close();

        return studentInfo;
    }

    @Override
    public int stuSave(StudentInfo studentInfo) {
       int result=stuMapper.updateByPrimaryKeySelective(studentInfo);
       if(result>0){
           //es
           this.setEs();
           //redis
           String stukey="stu:"+studentInfo.getSid()+":info";
           //随机时间，防止缓存雪崩
           Random random=new Random();
           int i = random.nextInt(10);
           Jedis jedis=redisUtil.getJedis();
           jedis.del(stukey);
           jedis.setex(stukey,i*60*10,JSON.toJSONString(studentInfo));
       }
        return result;
    }

    public  List<StudentInfo> getEs(){
        List<StudentInfo> stulist=new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        searchSourceBuilder.sort("sid", SortOrder.ASC);
        String dsl=searchSourceBuilder.toString();
        Search search=new Search.Builder(dsl).addIndex("stu").addType("studentinfo").build();
        try {
            SearchResult searchResult=jestClient.execute(search);
            List<SearchResult.Hit<StudentInfo,Void>> hits=searchResult.getHits(StudentInfo.class);
            for (SearchResult.Hit<StudentInfo,Void> hit: hits){
                StudentInfo studentInfo=hit.source;
                stulist.add(studentInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stulist;
    }
    public void setEs(){
        List<StudentInfo> allstu = this.getAllStu();
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
}
