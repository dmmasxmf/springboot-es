package com.dmm.es.mapper;

import com.dmm.es.entry.AreaCode;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/14 9:32
 * @motto If you would have leisure, do not waste it.
 */
@Repository
public interface AreaCodeRepository extends ElasticsearchRepository<AreaCode,Long> {


}
