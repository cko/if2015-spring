package org.informatica.controller;

import java.util.List;

import org.informatica.domain.Event;
import org.informatica.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	@Autowired
	EventService eventService;
	
    
    @RequestMapping("/events")
    List<Event> getAllEvents() {
        return eventService.getAllFutureEvents();
    }

    @RequestMapping(value = "/events/byName")
    Event getEvent(@RequestParam String name){
    	return eventService.getEvent(name);
    }
    
    @RequestMapping(value = "/events", method=RequestMethod.POST)
    Event createEvent(@RequestBody Event event){
    	return eventService.saveEvent(event);
    }
}
