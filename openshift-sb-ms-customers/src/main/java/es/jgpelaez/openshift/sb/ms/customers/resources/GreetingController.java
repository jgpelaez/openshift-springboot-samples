package es.jgpelaez.openshift.sb.ms.customers.resources;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import es.jgpelaez.openshift.sb.ms.customers.model.Greeting;
import es.jgpelaez.openshift.sb.ms.customers.model.HelloMessage;

@Controller
public class GreetingController {
	/*
	 * @RequestMapping("/greeting") public String greeting(@RequestParam(value =
	 * "name", required = false, defaultValue = "World") String name, Model
	 * model) { model.addAttribute("name", name); return "greeting"; }
	 */
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + message.getName() + "!");
	}
}
