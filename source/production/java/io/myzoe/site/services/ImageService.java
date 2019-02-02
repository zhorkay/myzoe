package io.myzoe.site.services;

import io.myzoe.site.entities.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface ImageService
{
    @NotNull
    List<Image> getAllImages();

    @NotNull
    Page<Image> getPagesImages(@NotNull Pageable page);

    Image getImage(long id);

    void create(@NotNull @Valid @P("image") Image image);

    void update(@NotNull @Valid @P("image") Image image);

    void delete(long id);

    void deleteAll();
}
