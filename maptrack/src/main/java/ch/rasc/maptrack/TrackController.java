package ch.rasc.maptrack;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrackController {

	@RequestMapping(value = "/time", method = RequestMethod.GET,
			produces = "text/event-stream")
	@ResponseBody
	public String getTime() {
		return "data: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
				+ "\n\n";
	}

}
