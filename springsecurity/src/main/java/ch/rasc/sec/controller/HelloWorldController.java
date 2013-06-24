package ch.rasc.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

	@RequestMapping(value = "/sayHello", method = RequestMethod.GET)
	@ResponseBody
	public String sayHello(@RequestParam(value = "name", required = false) String name) {
		return "Hello " + name + ". My name is Server";
	}

}
