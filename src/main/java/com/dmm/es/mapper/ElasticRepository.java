package com.dmm.es.mapper;

import com.dmm.es.entry.AreaCode;
import com.dmm.es.entry.DocBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/12 11:30
 * @motto If you would have leisure, do not waste it.
 */
@Repository
public interface ElasticRepository extends ElasticsearchRepository<DocBean,Long> {
    //默认的注释
    //@Query("{\"bool\" : {\"must\" : {\"field\" : {\"content\" : \"?\"}}}}")
    Page<DocBean> findByContent(String content, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"firstCode.keyword\" : \"?\"}}}}")
    Page<DocBean> findByFirstCode(String firstCode, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"secordCode.keyword\" : \"?\"}}}}")
    Page<DocBean> findBySecordCode(String secordCode, Pageable pageable);

    List<DocBean> findDocBeansByContentAndContent();
}
