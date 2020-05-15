package com.dmm.es.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Mr. Du
 * @explain
 * @createTime 2020/5/14 11:19
 * @motto The more learn, the more found his ignorance.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "job51",type = "_doc")
public class AllJobInfos {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword,index = false)
    private String companyAddr;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String companyInfo;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String companyName;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String jobAddr;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String jobInfo;
    @Field(type = FieldType.Keyword,index = false)
    private String jobName;
    @Field(type = FieldType.Double)
    private Double salaryMax;
    @Field(type = FieldType.Double)
    private Double salaryMin;
    @Field(type = FieldType.Keyword,index = false)
    private String time;
    @Field(type = FieldType.Text)
    private String url;

}

