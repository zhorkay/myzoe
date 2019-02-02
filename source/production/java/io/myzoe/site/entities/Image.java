package io.myzoe.site.entities;

import io.myzoe.site.common.converter.InstantConverter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "image")
@DiscriminatorValue("IMAGE")
public class Image extends Post
{
    private static final long serialVersionUID = 1L;

    private String imageWidthValue;
    private String imageHeightValue;
    private String imageThumbSourceText;
    private Instant createdAt;
    private Long createdBy;
    private Instant updatedAt;
    private Long updatedBy;

    @Column(name = "IMAGE_WIDTH_VALUE")
    public String getImageWidthValue() {
        return this.imageWidthValue;
    }

    public void setImageWidthValue(String imageWidthValue) {
        this.imageWidthValue = imageWidthValue;
    }

    @Column(name = "IMAGE_HEIGHT_VALUE")
    public String getImageHeightValue() {
        return this.imageHeightValue;
    }

    public void setImageHeightValue(String imageHeightValue) {
        this.imageHeightValue = imageHeightValue;
    }

    @Column(name = "IMAGE_THUMB_SOURCE_TXT")
    public String getImageThumbSourceText() {
        return this.imageThumbSourceText;
    }

    public void setImageThumbSourceText(String imageThumbSourceText) {
        this.imageThumbSourceText = imageThumbSourceText;
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
