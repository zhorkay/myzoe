package io.myzoe.site.services;

import io.myzoe.site.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface PostService
{
    @NotNull
    List<Post> getAllPosts();

    @NotNull
    Page<Post> getPagesPosts(@NotNull Pageable page);

    Post getPost(long id);

    void create(@NotNull @Valid @P("post") Post post);

    void update(@NotNull @Valid @P("post") Post post);

    void delete(long id);

    void deleteAll();
}
