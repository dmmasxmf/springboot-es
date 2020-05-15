package com.dmm.es;

import com.dmm.es.entry.AreaCode;
import com.dmm.es.entry.DocBean;
import com.dmm.es.service.IElasticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/12 18:52
 * @motto The more learn, the more found his ignorance.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsTest {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    IElasticService iElasticService;

    @Test
    public void ssTest(){


//        elasticsearchRestTemplate.deleteIndex(DocBean.class);
//        elasticsearchRestTemplate.deleteIndex(AreaCode.class);
//        elasticsearchRestTemplate.createIndex(DocBean.class);
        elasticsearchRestTemplate.createIndex(AreaCode.class);
    }

    @Test
    public void create(){
        iElasticService.createIndex();
    }

    @Test
    public void delete(){
        iElasticService.deleteIndex("");
    }

    @Test
    public void add(){

        DocBean docBean=new DocBean();
        docBean.setId(100001L);
        docBean.setContent("孙学凯是不是是不傻逼傻逼傻逼是不是");
        docBean.setSecordCode("是不是");
        docBean.setFirstCode("傻逼");
        docBean.setType(0);

        iElasticService.save(docBean);
    }

    @Test
    public void addAll(){

        List<DocBean> docBeanList=new ArrayList<>(12000);


        for (int i=0;i<10000;i++){
            DocBean docBean=new DocBean();
            docBean.setId(Long.valueOf(i));


            if(new Random(100).nextInt(100)%3==1){
                docBean.setContent("孙学凯是不是傻"+i);
            }else {
                docBean.setContent("孙学凯是不是"+i);
            }

            docBean.setSecordCode("是不是");
            docBean.setFirstCode("傻逼");
            docBean.setType(0);
            docBeanList.add(docBean);
        }



        iElasticService.saveAll(docBeanList);
    }

    @Test
    public void findContext(){
        Page<DocBean> docBeanPage= iElasticService.findByContent("凯");

        System.out.println(docBeanPage.getContent());

        System.out.println(docBeanPage.getContent());
    }

}

