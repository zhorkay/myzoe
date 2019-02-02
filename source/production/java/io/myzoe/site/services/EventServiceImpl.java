package io.myzoe.site.services;

import io.myzoe.site.entities.Event;
import io.myzoe.site.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService
{
    @Inject
    EventRepository eventRepository;

    @Override
    @Transactional
    public List<Event> getAllEvents()
    {
        Iterable<Event> iterable = this.eventRepository.findAll();
        List<Event> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    @Transactional
    public Page<Event> getPagesEvents(@NotNull Pageable page)
    {
        Page<Event> events = this.eventRepository.findAll(page);
        return events;
    }

    @Override
    @Transactional
    public Event getEvent(long id) {
        return this.eventRepository.findOne(id);
    }

    @Override
    @Transactional
    public void create(Event event) {
        this.eventRepository.save(event);
    }

    @Override
    @Transactional
    public void update(Event event) {
        this.eventRepository.save(event);
    }

    @Override
    @Transactional
    public void delete(long id)
    {
        this.eventRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        this.eventRepository.deleteAll();
    }
}
