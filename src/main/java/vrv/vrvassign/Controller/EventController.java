package vrv.vrvassign.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import vrv.vrvassign.Model.Event;
import vrv.vrvassign.Repos.EventRepository;

@Controller
public class EventController {
    
    @Autowired
    private EventRepository eventRepository;
    
	@GetMapping("/showCreateEvent")
	public String showCreateEvent() {
		return "createEvent";
	}

	@PostMapping("/saveEvent")
	public String save(Event event) {
		eventRepository.save(event);
		return "createResponse";
	}

	@GetMapping("/showGetEvent")
	public String showGetEvent() {
		return "getEvent";
	}

    
	@PostMapping("/getEvent")
	public ModelAndView getEvent(String code) {
		ModelAndView mav = new ModelAndView("eventDetails");
		System.out.println(code);
		mav.addObject(eventRepository.findByName(code));
		return mav;
	}
}
