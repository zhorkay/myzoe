package io.myzoe.site.entities;

import io.myzoe.site.common.converter.InstantConverter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "video")
@DiscriminatorValue("VIDEO")
public class Video extends Post {

    private static final long serialVersionUID = 1L;

    private String videoWidthValue;
    private String videoHeightValue;
    private String videoThumbSourceText;
    private Instant createdAt;
    private Long createdBy;
    private Instant updatedAt;
    private Long updatedBy;

    @Column(name = "VIDEO_WIDTH_VALUE")
    public String getVideoWidthValue() {
        return videoWidthValue;
    }

    public void setVideoWidthValue(String videoWidthValue) {
        this.videoWidthValue = videoWidthValue;
    }

    @Column(name = "VIDEO_HEIGHT_VALUE")
    public String getVideoHeightValue() {
        return videoHeightValue;
    }

    public void setVideoHeightValue(String videoHeightValue) {
        this.videoHeightValue = videoHeightValue;
    }

    @Column(name = "VIDEO_THUMB_SOURCE_TXT")
    public String getVideoThumbSourceText() {
        return videoThumbSourceText;
    }

    public void setVideoThumbSourceText(String videoThumbSourceText) {
        this.videoThumbSourceText = videoThumbSourceText;
    }

    @Convert(converter = InstantConverter.class)
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Convert(converter = InstantConverter.class)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "updated_by", nullable = false)
    @LastModifiedBy
    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
