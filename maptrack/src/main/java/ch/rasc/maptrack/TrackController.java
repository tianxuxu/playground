package ch.rasc.maptrack;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrackController {

	@RequestMapping(value = "/time", method = RequestMethod.GET, produces = "text/event-stream")
	@ResponseBody
	public String getTime() {
		return "data: " + DateTime.now().toString(ISODateTimeFormat.basicDateTime()) + "\n\n";
	}

}
