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
 * @createTime 2020/5/13 13:59
 * @motto The more learn, the more found his ignorance.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "area-code",type = "_doc")
public class AreaCode {

    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Long)
    private Long code;

    @Field(type = FieldType.Integer)
    private Integer level;

    @Field(type = FieldType.Long)
    private Long parentCode;

    public AreaCode (String name,Long code,Integer level,Long parentCode){
        this.name=name;
        this.code=code;
        this.level=level;
        this.parentCode=parentCode;
    }

}

