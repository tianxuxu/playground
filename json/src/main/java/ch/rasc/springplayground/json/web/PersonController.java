package ch.rasc.springplayground.json.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonController {

	@RequestMapping(value = "/getPerson")
	@ResponseBody
	public String getSimpleBean() {
		return "test";
	}

}
