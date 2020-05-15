package com.dmm.es.service;

import com.dmm.es.entry.DocBean;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/12 11:16
 * @motto If you would have leisure, do not waste it.
 */

public interface IElasticService {

    void createIndex();

    void deleteIndex(String index);

    void save(DocBean docBean);

    void saveAll(List<DocBean> list);

    Iterator<DocBean> findAll();

    Page<DocBean> findByContent(String content);

    Page<DocBean> findByFirstCode(String firstCode);

    Page<DocBean> findBySecordCode(String secordCode);

    Page<DocBean> query(String key);
}
