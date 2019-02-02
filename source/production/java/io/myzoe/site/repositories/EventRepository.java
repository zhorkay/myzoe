package io.myzoe.site.repositories;

import io.myzoe.site.entities.Event;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository extends PagingAndSortingRepository<Event, Long>
{

}
