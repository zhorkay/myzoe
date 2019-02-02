package io.myzoe.site.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "POST_TYPE_CD")
public class Post extends BaseAuditEntity
{
    private static final long serialVersionUID = 1L;

    private long id;
    private long userId;
    private String postName;
    private String postDesc;
    private String postSourceText;
    private PostSourceType postSourceType;
    private Date postStartDate;
    private Date postEndDate;
    private String postRankValue;

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "USER_ID")
    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "POST_NAME")
    public String getPostName() {
        return this.postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Column(name = "POST_DESC")
    public String getPostDesc() {
        return this.postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    @Column(name = "POST_SOURCE_TXT")
    public String getPostSourceText() {
        return this.postSourceText;
    }

    public void setPostSourceText(String postSourceText) {
        this.postSourceText = postSourceText;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POST_SOURCE_TYPE_CD",
                foreignKey = @ForeignKey(name = "FK_POST_SOURCE_TYPE_2_POST")
    )
    public PostSourceType getPostSourceType() {
        return this.postSourceType;
    }

    public void setPostSourceType(PostSourceType postSourceType) {
        this.postSourceType = postSourceType;
    }

    @Column(name = "POST_START_DT")
    public Date getPostStartDate() {
        return this.postStartDate;
    }

    public void setPostStartDate(Date postStartDate) {
        this.postStartDate = postStartDate;
    }

    @Column(name = "POST_END_DT")
    public Date getPostEndDate() {
        return this.postEndDate;
    }

    public void setPostEndDate(Date postEndDate) {
        this.postEndDate = postEndDate;
    }

    @Column(name = "POST_RANK_VALUE")
    public String getPostRankValue() {
        return this.postRankValue;
    }

    public void setPostRankValue(String postRankValue) {
        this.postRankValue = postRankValue;
    }
}
