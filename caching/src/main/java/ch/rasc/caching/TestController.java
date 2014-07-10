package ch.rasc.caching;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping(value = "/getSomething", produces = "application/json")
	@ResponseBody
	@Cacheable("tenMinutesCache")
	public List<String> getSomething() {
		System.out.println("inside getSomething");
		return Arrays.asList(testService.getData("one"), testService.getData("two"),
				testService.getData("three"), testService.getData("four"),
				testService.getData("five"), testService.getData("six"),
				testService.getData("seven"), testService.getData("eight"),
				testService.getData("nine"), testService.getData("ten"),
				testService.getData("eleven"));
	}

	@RequestMapping(value = "/cacheEvict", produces = "application/json")
	@ResponseBody
	@CacheEvict(value = "tenMinutesCache", allEntries = true)
	public String cacheEvict() {
		return "CACHE EVICT";
	}
}
