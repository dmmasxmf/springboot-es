package com.dmm.es.mapper;

import com.dmm.es.entry.AllJobInfos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/14 11:18
 * @motto If you would have leisure, do not waste it.
 */
public interface AllJobInfosRepository extends ElasticsearchRepository<AllJobInfos,Long> {

    Page<AllJobInfos> findByCompanyName(String companyName, Pageable pageable);

    Page<AllJobInfos> findByCompanyNameOrJobInfo(String companyName,String jobInfo,Pageable pageable);

    Page<AllJobInfos> findByJobInfoOrSalaryMaxBetweenAndSalaryMinBetween(String jobInfo,Double from,Double to,
                                                                         Double from1,Double to1,Pageable pageable);
}
