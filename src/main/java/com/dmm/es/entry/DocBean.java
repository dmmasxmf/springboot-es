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
 * @explain 实体类
 * @createTime 2020/5/12 11:18
 * @motto The more learn, the more found his ignorance.
 */

/**
 * , shards = 1, replicas = 0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "doc_bean",type = "_doc")
public class DocBean {

    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String firstCode;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String secordCode;

    //

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Integer)
    private Integer type;

    public DocBean(Long id, String firstCode, String secordCode, String content, Integer type){
        this.id=id;
        this.firstCode=firstCode;
        this.secordCode=secordCode;
        this.content=content;
        this.type=type;
    }
}

