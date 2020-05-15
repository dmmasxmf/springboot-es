package com.dmm.es;

import com.alibaba.fastjson.JSON;
import com.dmm.es.entry.AllJobInfos;
import com.dmm.es.mapper.AllJobInfosRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/14 11:33
 * @motto The more learn, the more found his ignorance.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AllJobInfoTest {

    @Autowired
    AllJobInfosRepository allJobInfosRepository;

    Pageable pageable = PageRequest.of(0, 10);

    @Test
    public void select() {
        List<AllJobInfos> allJobInfosList = allJobInfosRepository.findByCompanyName("杭州", pageable).getContent();
        System.out.println("杭州 = " + allJobInfosList);
    }

    @Test
    public void selectAll() {
        List<AllJobInfos> allJobInfosList = allJobInfosRepository.findByCompanyNameOrJobInfo("杭州", "java开发", pageable).getContent();

        System.out.println("hangzhou---->" + JSON.toJSONString(allJobInfosList));
    }

    @Test
    public void selectRang() {
        List<AllJobInfos> 开发 = allJobInfosRepository.findByJobInfoOrSalaryMaxBetweenAndSalaryMinBetween("java开发", 30000D, 50000D, 10000D, 20000D, pageable).getContent();
        System.out.println("开发---->" + JSON.toJSONString(开发));
    }

    @Test
    public void selectAbc() {

        MatchQueryBuilder queryBuilder = new MatchQueryBuilder("companyName", "杭州");

        Iterable<AllJobInfos> search = allJobInfosRepository.search(queryBuilder);

        search.forEach(System.out::println);
    }

    @Test
    public void selectAbcd() {

        QueryBuilder queryBuilder1 = QueryBuilders.matchQuery("companyName", "杭州");
        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("companyInfo", "科技");

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(queryBuilder1).mustNot(queryBuilder2);


        Iterable<AllJobInfos> search = allJobInfosRepository.search(queryBuilder);

        search.forEach(System.out::println);
    }

    @Test
    public void selectAb() {

        QueryBuilder queryBuilder = QueryBuilders.commonTermsQuery("jobAddr", "杭州");

        Iterable<AllJobInfos> search = allJobInfosRepository.search(queryBuilder, pageable).getContent();

        search.forEach(System.out::println);
    }

    @Test
    public void selectAbb() {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("companyName", "杭州"));

        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("salaryMax").order(SortOrder.DESC));

        nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("jobAddr", "信阳"));

        nativeSearchQueryBuilder.withPageable(pageable);

        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("jobInfo", "科技有限公司"));

        //nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery()
        //nativeSearchQueryBuilder.withFields("companyInfo");
        //nativeSearchQueryBuilder.withFilter(QueryBuilders.boolQuery().filter(QueryBuilders.regexpQuery("","")));
        Page<AllJobInfos> search = allJobInfosRepository.search(nativeSearchQueryBuilder.build());

        search.forEach(System.out::println);
        System.out.println(search.getTotalElements());
        System.out.println(search.getTotalPages());
    }

    @Test
    public void selectAbBb() {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("salaryMaxS").field("salaryMax"));

        AggregatedPage<AllJobInfos> allJobInfos = (AggregatedPage<AllJobInfos>) allJobInfosRepository.search(nativeSearchQueryBuilder.build());

        ParsedDoubleTerms salaryMaxS = (ParsedDoubleTerms) allJobInfos.getAggregation("salaryMaxS");

        List<? extends Terms.Bucket> buckets = salaryMaxS.getBuckets();

        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }

    }


//        Page<AllJobInfos> search=allJobInfosRepository.search(nativeSearchQueryBuilder.build());
//
//        search.forEach(System.out::println);
//        System.out.println(search.getTotalElements());
//        System.out.println(search.getTotalPages());
}


