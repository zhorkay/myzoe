package io.myzoe.site.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post_source_type")
public class PostSourceType extends BaseAuditEntity
{
    private static final long serialVersionUID = 1L;

    private String postSourceTypeCode;
    private String postSourceTypeDesc;

    @Id
    @Column(name = "POST_SOURCE_TYPE_CD")
    public String getPostSourceTypeCode() {
        return this.postSourceTypeCode;
    }

    public void setPostSourceTypeCode(String postSourceTypeCode) {
        this.postSourceTypeCode = postSourceTypeCode;
    }

    @Column(name = "POST_SOURCE_TYPE_DESC")
    public String getPostSourceTypeDesc() {
        return this.postSourceTypeDesc;
    }

    public void setPostSourceTypeDesc(String postSourceTypeDesc) {
        this.postSourceTypeDesc = postSourceTypeDesc;
    }
}
