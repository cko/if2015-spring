package org.informatica.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.informatica.domain.Event;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private Map<String, Event> events = new HashMap<>();

    public EventService() {
        createEvents();
    }

    public List<Event> getAllFutureEvents() {
        return new ArrayList<Event>(events.values());
    }

    public Event getEvent(String name) {
        return events.get(name);
    }

    public Event saveEvent(Event event) {
        return event;
    }
    
    private void createEvents (){
        // Monate werden ab 0 gezählt, 10=November
        Date startDate1 = (new GregorianCalendar(2015, 10, 22, 9, 0)).getTime();
        Date endDate1 = (new GregorianCalendar(2015, 10, 29, 17, 0)).getTime();

        Event event1 = new Event();
        event1.setId(3l);
        event1.setName("Informatica Feminale");
        event1.setStartDate(startDate1);
        event1.setEndDate(endDate1);
        events.put(event1.getName(), event1);

        Date startDate2 = (new GregorianCalendar(2015, 9, 22, 19, 0)).getTime();
        Date endDate2 = (new GregorianCalendar(2015, 9, 22, 21, 0)).getTime();
        Event event2 = new Event();
        event2.setId(4l);
        event2.setName("Mecchanica Feminale");
        event2.setStartDate(startDate2);
        event2.setEndDate(endDate2);
        events.put(event2.getName(), event2);
    }

}
