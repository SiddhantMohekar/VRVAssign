package vrv.vrvassign.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vrv.vrvassign.Model.Event;
import vrv.vrvassign.Repos.EventRepository;

@RestController
@RequestMapping("/eventapi")
public class EventRestController {
    
    @Autowired
     private EventRepository eventRepository;
    
    @PostMapping("/events")
    @PreAuthorize("hasRole('ADMIN')")
    public Event create(@RequestBody Event event){
        return eventRepository.save(event);
    }

    @GetMapping("/events/{ename}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Event getEvent(@PathVariable("ename") String ename){
        return eventRepository.findByName(ename);
    }
}
