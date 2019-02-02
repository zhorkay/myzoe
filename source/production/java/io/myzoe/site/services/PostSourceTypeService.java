package io.myzoe.site.services;

import io.myzoe.site.entities.PostSourceType;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface PostSourceTypeService
{
    @NotNull
    List<PostSourceType> getAllPostSourceTypes();

    PostSourceType getPostSourceType(String cd);

    void create(@NotNull @Valid @P("type") PostSourceType type);

    void update(@NotNull @Valid @P("type") PostSourceType type);

    void delete(String cd);

    void deleteAll();
}
