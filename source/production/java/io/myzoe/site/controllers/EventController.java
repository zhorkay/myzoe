package io.myzoe.site.controllers;

import io.myzoe.config.annotation.RestEndpoint;
import io.myzoe.site.entities.Event;
import io.myzoe.site.services.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.List;

@RestEndpoint
public class EventController 
{
    private static final Logger Log = LogManager.getLogger();

    @Inject
    EventService eventService;

    //-------------------Retrieve All Events---------------------------------------------------------

    @RequestMapping(value = "events", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> listAllEvents()
    {
        List<Event> events = this.eventService.getAllEvents();
        if (events.isEmpty())
        {
            return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
    }

    //-------------------Retrieve All Events per Pages-----------------------------------------------

    @RequestMapping(value = "event", method = RequestMethod.GET)
    public ResponseEntity<Page<Event>> pagingEvents(@RequestParam int pageindex,
                                                  @RequestParam int pagesize)
    {
        Pageable page = new PageRequest(pageindex, pagesize);
        Page<Event> events = this.eventService.getPagesEvents(page);
        if (events.getSize() == 0)
        {
            Log.debug("Event Not Found");
            return new ResponseEntity<Page<Event>>(HttpStatus.NO_CONTENT);
        }
        Log.debug("Event found.");
        return new ResponseEntity<Page<Event>>(events, HttpStatus.OK);
    }

    //-------------------Retrieve Single Event--------------------------------------------------------

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEvent(@PathVariable("id") long id) {
        Log.debug("Fetching Event with id " + id);
        Event event = this.eventService.getEvent(id);
        if (event == null) {
            Log.debug("Event with id " + id + " not found");
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    //-------------------Create a Event-----------------------------------------------------------------

    @RequestMapping(value = "event", method = RequestMethod.POST)
    public ResponseEntity<Void> createEvent(@RequestBody Event event, UriComponentsBuilder ucBuilder)
    {
        if (this.eventService.getEvent(event.getEventId()) != null) {
            Log.debug("A Event with name " + event.getEventName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        this.eventService.create(event);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/event/{id}").buildAndExpand(event.getEventId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //-------------------Update a Event-----------------------------------------------------------------

    @RequestMapping(value = "/event/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@PathVariable("id") long id,
                                           @RequestBody Event event)
    {
        Event currentEvent = this.eventService.getEvent(id);

        if (currentEvent == null)
        {
            Log.debug("Event does not exist: {}.", id);
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }

        currentEvent.setEventTypeCd(event.getEventTypeCd());
        currentEvent.setEventName(event.getEventName());
        currentEvent.setEventDesc(event.getEventDesc());
        currentEvent.setEventEndDate(event.getEventEndDate());
        currentEvent.setEventStartDate(event.getEventStartDate());
        this.eventService.update(currentEvent);

        return new ResponseEntity<Event>(currentEvent, HttpStatus.OK);
    }

    //------------------- Delete a Event --------------------------------------------------------

    @RequestMapping(value = "/event/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") long id) {
        Log.debug("Fetching & Deleting Event with id " + id);

        Event event = this.eventService.getEvent(id);
        if (event == null) {
            Log.debug("Unable to delete. Event with id " + id + " not found");
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }

        this.eventService.delete(id);
        return new ResponseEntity<Event>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Events --------------------------------------------------------

    @RequestMapping(value = "event", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteAllEvents()
    {
        Log.debug("Deleting All Events");

        this.eventService.deleteAll();
        return new ResponseEntity<Event>(HttpStatus.NO_CONTENT);
    }
}
