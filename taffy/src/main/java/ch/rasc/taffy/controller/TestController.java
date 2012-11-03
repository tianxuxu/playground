package ch.rasc.taffy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.rasc.taffy.config.MyConf;

@Controller
public class TestController {

	@Autowired
	private Twitter twitter;

	@Autowired
	private DataService dataService;

	@Autowired
	private MyConf myConf;

	@RequestMapping("/getPublicTimeline")
	@ResponseBody
	public List<Tweet> returnSomeTweets() {
		System.out.println(myConf.getEnv());
		return twitter.timelineOperations().getPublicTimeline();
	}

	@RequestMapping("/getUsers")
	@ResponseBody
	public List<User> getUsers() {
		return dataService.getUsers();
	}

}
