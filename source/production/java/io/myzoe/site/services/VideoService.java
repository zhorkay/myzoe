package io.myzoe.site.services;

import io.myzoe.site.entities.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface VideoService {

    @NotNull
    List<Video> getAllImages();

    @NotNull
    Page<Video> getPagesImages(@NotNull Pageable page);

    Video getImage(long id);

    void create(@NotNull @Valid @P("video") Video video);

    void update(@NotNull @Valid @P("video") Video video);

    void delete(long id);

    void deleteAll();

}
