package com.dmm.es;

import com.dmm.es.entry.AllJobInfos;
import com.dmm.es.mapper.AllJobInfosRepository;
import org.apache.lucene.search.SortField;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.DoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregator;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.sort.*;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Score;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/15 9:52
 * @motto The more learn, the more found his ignorance.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Job51Test {

    @Autowired
    private AllJobInfosRepository allJobInfosRepository;


    /**
     * GET /job51/_search
     * {
     *   "query": {"match_all": {
     *
     *   }},"sort": [
     *     {
     *       "salaryMax": {
     *         "order": "desc"
     *       }
     *     }
     *   ], "size": 10
     * }
     */

    @Test
    public void matchAll(){

//        Iterable<AllJobInfos> allJobInfosRepositoryAll = allJobInfosRepository.findAll(Sort.by(Sort.Direction.DESC, "salaryMax"));
        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "salaryMax")));

        allJobInfosPage.getContent().forEach(item-> System.out.println(item));
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
    }

    /**
     * GET /job51/_search
     * {
     *   "query": {
     *     "match": {
     *       "companyInfo": {
     *         "query": "JAVA开发",
     *         "operator": "and","minimum_should_match": "80%"
     *       }
     *     }
     *   },"sort": [
     *     {
     *       "salaryMax": {
     *         "order": "desc"
     *       }
     *     }
     *   ]
     * }
     */
    @Test
    public void match(){

        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withPageable(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"salaryMax")));
        //,
        //        "minimum_should_match": "80%"
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("companyInfo","JAVA开发").operator(Operator.AND).minimumShouldMatch("80%"));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());

        allJobInfosPage.getContent().forEach(item-> System.out.println(item));
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
    }

    /**
     * GET /job51/_search
     * {
     *   "query": {
     *     "multi_match": {
     *       "query": "JAVA开发","operator": "and",
     *       "fields": ["jobInfo","companyInfo"],
     *       "minimum_should_match": "80%"
     *     }
     *   },"sort": [
     *     {
     *       "salaryMax": {
     *         "order": "desc"
     *       }
     *     }
     *   ]
     * }
     */
    @Test
    public void multiMatch(){

        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withPageable(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"salaryMax")));
        //,
        //        "minimum_should_match": "80%"
        nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery("JAVA开发","jobInfo","companyInfo").operator(Operator.AND).minimumShouldMatch("80%"));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());

        allJobInfosPage.getContent().forEach(item-> System.out.println(item));
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
    }

    /**
     * GET /job51/_search
     * {
     *   "query": {"term": {
     *     "salaryMax": {
     *       "value": "15000"
     *     }
     *   }}
     * }
     */
    @Test
    public void term(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"id")));
        //,
        //        "minimum_should_match": "80%"
        nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("salaryMax","15000"));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));

    }

    /**
     * GET /job51/_search
     * {
     *   "query": {"terms": {
     *     "salaryMin": [1000,15000]
     *   }
     *   }
     * }
     */
    @Test
    public void terms(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"id")));
        //,
        //        "minimum_should_match": "80%"
        nativeSearchQueryBuilder.withQuery(QueryBuilders.termsQuery("salaryMin","1000D","15000D"));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));

    }

//    @Autowired
//    ElasticsearchRestTemplate elasticsearchRestTemplate;
//    @Test
//    public void analyze(){
//        elasticsearchRestTemplate.
//    }

    /**
     * GET /job51/_search
     * {
     *   "query": {
     *     "multi_match": {
     *       "query": "java开发",
     *       "fields": ["jobInfo","companyInfo"]
     *     }
     *   },
     *   "_source": ["companyAddr","companyName"]
     * }
     */
    @Test
    public void source(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"id")));
        nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery("java开发","jobInfo","companyInfo"));
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"companyAddr","companyName"},null));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));

    }

    /**
     * /"includes": ["companyAddr","companyName"],
     *GET /job51/_search
     * {
     *   "query": {
     *     "multi_match": {
     *       "query": "java开发",
     *       "fields": ["jobInfo","companyInfo"]
     *     }
     *   },
     *   "_source": {
     *
     *     "excludes": ["jobAddr"]
     *   }
     * }
     */
    @Test
    public void sourceExcludes(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"id")));
        nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery("java开发","jobInfo","companyInfo"));
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(null,new String[]{"jobAddr"}));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));

    }

    @Test
    public void bool(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        QueryBuilder queryBuilder=QueryBuilders.matchQuery("jobInfo","多线程");
        QueryBuilder queryBuilder2=QueryBuilders.matchQuery("jobInfo","java");
        QueryBuilder queryBuilder3=QueryBuilders.matchQuery("jobInfo","工程师");

        //
        List<QueryBuilder> queryBuilderList=new ArrayList<>();
        queryBuilderList.add(queryBuilder);
        queryBuilderList.add(queryBuilder2);
        queryBuilderList.add(queryBuilder3);

        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"id")));



        /**
         * 1.
         * GET /job51/_search
         * {
         *   "query": {"bool": {"must": [
         *     {"match": {
         *       "jobInfo": "软件有限公司"
         *     }}
         *   ]}}
         * }
         *
         */
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().must(queryBuilder));

        /**
         * 2.
         * GET /job51/_search
         * {
         *   "query": {"bool": {"must": [{"match": {
         *     "jobInfo": "工程师"
         *   }},{"match": {
         *     "jobInfo": "java"
         *   }},
         *     {"match": {
         *       "jobInfo": "多线程"
         *     }}
         *   ]}}
         * }
         *
         */
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().must(queryBuilder).must(queryBuilder2).must(queryBuilder3));

        /**
         * sxk
         */
        //must.must(QueryBuilders.boolQuery().must(queryBuilder2));
        //must.must(QueryBuilders.boolQuery().must(queryBuilder));
        //nativeSearchQueryBuilder.withQuery(must);

//        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
//        boolQueryBuilder.must().addAll(queryBuilderList);
//
//        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        /**
         *GET /job51/_search
         * {
         *   "query": {"bool": {"should": [
         *     {"match": {
         *       "jobInfo": "多线程"
         *     }}
         *   ]}}
         * }
         */
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().should(queryBuilder));


        /**
         * 4.
         *  GET /job51/_search
         * {
         *   "query": {"bool": {"should": [{"match": {
         *     "jobInfo": "工程师"
         *   }},{"match": {
         *     "jobInfo": "java"
         *   }},
         *     {"match": {
         *       "jobInfo": "多线程"
         *     }}
         *   ]}}
         * }
         *
         * {"match": {
         *     "jobInfo": "工程师"
         *   }},{"match": {
         *     "jobInfo": "java"
         *   }},
         */
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().should(queryBuilder).should(queryBuilder2).should(queryBuilder3));

        /**
         * 5.
         *GET /job51/_search
         * {
         *   "query": {"bool": {"must_not": [
         *     {"match": {
         *       "jobInfo": "多线程"
         *     }}
         *   ]}}
         * }
         */
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().mustNot(queryBuilder));

        /**
         * 6.
         *
         * GET /job51/_search
         * {
         *   "query": {"bool": {"must_not": [{"match": {
         *     "jobInfo": "工程师"
         *   }},{"match": {
         *     "jobInfo": "java"
         *   }},
         *     {"match": {
         *       "jobInfo": "多线程"
         *     }}
         *   ]}}
         * }
         */
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().mustNot(queryBuilder).mustNot(queryBuilder2).mustNot(queryBuilder3));


        /**
         * 7.
         *GET /job51/_search
         * {
         * 	"query": {
         * 		"bool": {"should": [{"match": {
         * 		  "jobInfo": "开发"
         *                }},
         *          {"match": {
         * 		    "companyName": "公司"
         *          }}
         * 		],
         * 			"must": [{
         * 				"match": {
         * 					"jobInfo": {
         * 						"query": "前端开发"
         *                    }
         *                }
         *            },{
         * 			  "match": {
         * 			    "companyName": "科技"
         *              }
         *            }]
         * 		,"must_not": [{"match": {
         * 		  "companyName": "有限"
         *        }},
         *          {"match": {
         * 		    "jobInfo": "熟练掌握"
         *          }}
         * 		]}* 	}
         * }
         */
        QueryBuilder queryBuilder11=QueryBuilders.matchQuery("jobInfo","开发");
        QueryBuilder queryBuilder12=QueryBuilders.matchQuery("companyName","公司");
        QueryBuilder queryBuilder13=QueryBuilders.matchQuery("jobInfo","前端开发");
        QueryBuilder queryBuilder14=QueryBuilders.matchQuery("companyName","科技");
        QueryBuilder queryBuilder15=QueryBuilders.matchQuery("companyName","有限");
        QueryBuilder queryBuilder16=QueryBuilders.matchQuery("jobInfo","熟练掌握");

        nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().
                should(queryBuilder11).should(queryBuilder12).
                must(queryBuilder13).must(queryBuilder14).
                mustNot(queryBuilder15).mustNot(queryBuilder16));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));

    }

    /**
     * GET /job51/_search
     * {
     *   "query": {"range": {
     *     "salaryMax": {
     *       "gte": 1000,
     *       "lte": 2000
     *     }
     *   }}
     * }
     */
    @Test
    public void range(){

        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withQuery(QueryBuilders.rangeQuery("salaryMax").gte(1000).lte(2000));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));
    }

    /**
     * GET /job51/_search
     * {
     *   "query": {"fuzzy": {
     *     "jobInfo":"java前端web"
     *   }}
     * }
     */
    @Test
    public void fuzzy() {

        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        /**
         * GET /job51/_search
         * {
         *   "query": {"fuzzy": {
         *     "jobInfo":"java前端web"
         *   }}
         * }
         */
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.fuzzyQuery("jobInfo","java前端web"));

        /**
         * 2.
         *
         *GET /job51/_search
         * {
         *   "query": {"fuzzy": {
         *     "jobInfo": {
         *       "fuzziness": 2,
         *       "value": "java前端web"
         *     }
         *   }}
         * }
         *
         */
        nativeSearchQueryBuilder.withQuery(QueryBuilders.fuzzyQuery("jobInfo","java前端web").fuzziness(Fuzziness.TWO).
                prefixLength(0).
                maxExpansions(5));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));
    }

    /**
     * GET /job51/_search
     * {
     * 	"query": {
     * 		"bool": {
     * 			"filter": [{
     * 				"range": {
     * 					"salaryMax": {
     * 						"gte": 1000,
     * 						"lte": 2000
     *                                        }* 				}* 			}],
     * 			"must": [{
     * 				"match": {
     * 					"jobInfo": "java开发"
     *                }
     *            }, {
     * 				"match": {
     * 					"companyName": "科技"
     *                }
     *            }]
     *        }* 	}
     * }
     */
    @Test
    public void filter() {

        /**
         * filter between
         */
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().
                filter(QueryBuilders.rangeQuery("salaryMax").gte(1000).lte(2000)).
                must(QueryBuilders.matchQuery("jobInfo","java开发")).must(QueryBuilders.matchQuery("companyName","科技")));

        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));
    }

    /**
     *
     *GET /job51/_search
     * {
     * 	"query": {
     * 		"bool": {
     * 			"filter": [{
     * 				"range": {
     * 					"salaryMax": {
     * 						"gte": 1000,
     * 						"lte": 2000
     *                                        }* 				}* 			}],
     * 			"must": [{
     * 				"match": {
     * 					"jobInfo": "java开发"
     *                }
     *            }, {
     * 				"match": {
     * 					"companyName": "科技"
     *                }
     *            }]
     *        }* 	},
     * 	"sort": [{
     * 		"salaryMax": {
     * 			"order": "asc"
     * 		}
     * 	}    , {
     * 		"_score": {
     * 			"order": "desc"
     *        }
     *    }]
     * }
     */
    @Test
    public void order(){
        /**
         * filter between
         */
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        //QueryBuilders.matchQuery("")

        nativeSearchQueryBuilder.withQuery(QueryBuilders.boolQuery().
                filter(QueryBuilders.rangeQuery("salaryMax").gte(1000).lte(2000)).
                must(QueryBuilders.matchQuery("jobInfo","java开发")).must(QueryBuilders.matchQuery("companyName","科技")));

        /**
         * java 排序
         */
        //Sort sort=Sort.by(Sort.Direction.ASC,"salaryMax");

        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("salaryMax").order(SortOrder.ASC));
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));



        Page<AllJobInfos> allJobInfosPage = allJobInfosRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(allJobInfosPage.getTotalPages());
        System.out.println(allJobInfosPage.getTotalElements());
        allJobInfosPage.getContent().forEach(item-> System.out.println(item));
    }

    @Test
    public void aggregations(){

        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));

        /**
         * GET /job51/_search
         * {
         *   "size": 0,
         *   "aggs": {
         *     "jobNameS": {
         *       "terms": {
         *         "field": "jobName"
         *       }
         *     }
         *   }
         * }
         */

        //nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("salaryMaxS").field("salaryMax"));
        //AggregatedPage<AllJobInfos> allJobInfosPage = (AggregatedPage)allJobInfosRepository.search(nativeSearchQueryBuilder.build());

//        ParsedDoubleTerms parsedDoubleTerms=(ParsedDoubleTerms)allJobInfosPage.getAggregation("salaryMaxS");
//
//        System.out.println(parsedDoubleTerms.getSumOfOtherDocCounts());
//        System.out.println(parsedDoubleTerms.getDocCountError());
//
//        List<? extends Terms.Bucket> buckets = parsedDoubleTerms.getBuckets();
//
//        for(Terms.Bucket bucket:buckets){
//            System.out.println(bucket.getDocCountError());
//            System.out.println(bucket.getDocCount());
//            System.out.println(bucket.getKey());
//        }
//
//        //allJobInfosPage.
//        System.out.println(allJobInfosPage.getTotalPages());
//        System.out.println(allJobInfosPage.getTotalElements());
//        allJobInfosPage.getContent().forEach(item-> System.out.println(item));

        /**
         * GET /job51/_search
         * {
         *   "size": 0,
         *   "aggs": {
         *     "jobNameS": {
         *       "terms": {
         *         "field": "jobName"
         *       },"aggs": {
         *         "salaryMaxAvg": {
         *           "avg": {
         *             "field": "salaryMax"
         *           }
         *         }
         *       }
         *     },
         *     "companyAddrS": {
         *       "terms": {
         *         "field": "companyAddr"
         *       },"aggs": {
         *         "salaryMinAvg": {
         *           "avg": {
         *             "field": "salaryMin"
         *           }
         *         }
         *       }
         *     }
         *   }
         * }
         */
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("jobNameS").field("jobName").subAggregation(
                AggregationBuilders.avg("salaryMaxAvg").field("salaryMax")
        ));

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("companyAddrS").field("companyAddr").subAggregation(
                AggregationBuilders.avg("salaryMinAvg").field("salaryMin")
        ));
        AggregatedPage<AllJobInfos> allJobInfosPage=(AggregatedPage<AllJobInfos>)allJobInfosRepository.search(nativeSearchQueryBuilder.build());

        ParsedStringTerms parsedDoubleTerms=(ParsedStringTerms)allJobInfosPage.getAggregation("jobNameS");

        List<? extends Terms.Bucket> buckets = parsedDoubleTerms.getBuckets();

        for(Terms.Bucket bucket:buckets){
            System.out.println(bucket.getDocCountError());
            //文档数
            System.out.println(bucket.getDocCount());
            System.out.println(bucket.getKey());
            //职业
            System.out.println(bucket.getKeyAsString());

            ParsedAvg internalAvg=(ParsedAvg)bucket.getAggregations().asMap().get("salaryMaxAvg");

            System.out.println(internalAvg.getValue());
        }

        ParsedStringTerms parsedDoubleTerms2=(ParsedStringTerms)allJobInfosPage.getAggregation("companyAddrS");

        List<? extends Terms.Bucket> buckets2 = parsedDoubleTerms2.getBuckets();

        for(Terms.Bucket bucket:buckets2){
            System.out.println(bucket.getDocCountError());
            //文档数
            System.out.println(bucket.getDocCount());
            System.out.println(bucket.getKey());
            //职业
            System.out.println(bucket.getKeyAsString());

            ParsedAvg internalAvg=(ParsedAvg)bucket.getAggregations().asMap().get("salaryMinAvg");

            System.out.println(internalAvg.getValue());
        }

    }

}

