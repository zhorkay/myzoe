package io.myzoe.site.services;

import io.myzoe.site.entities.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface VideoService {

    @NotNull
    List<Video> getAllVideos();

    @NotNull
    Page<Video> getPagesVideos(@NotNull Pageable page);

    Video getVideo(long id);

    void create(@NotNull @Valid @P("video") Video video);

    void update(@NotNull @Valid @P("video") Video video);

    void delete(long id);

    void deleteAll();

}
