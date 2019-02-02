package io.myzoe.site.services;

import io.myzoe.site.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface EventService 
{
    @NotNull
    List<Event> getAllEvents();

    @NotNull
    Page<Event> getPagesEvents(@NotNull Pageable page);

    Event getEvent(long id);

    void create(@NotNull @Valid @P("event") Event Event);

    void update(@NotNull @Valid @P("event") Event Event);

    void delete(long id);

    void deleteAll();
}
