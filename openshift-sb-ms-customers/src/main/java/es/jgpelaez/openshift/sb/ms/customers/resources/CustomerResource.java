package es.jgpelaez.openshift.sb.ms.customers.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.jgpelaez.openshift.sb.ms.customers.model.Address;
import es.jgpelaez.openshift.sb.ms.customers.model.Customer;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("api")
public class CustomerResource {
	/*
	 * @RequestMapping("/")
	 * 
	 * @ResponseBody String home(@RequestHeader(value = "User-Agent") String
	 * userAgent,
	 * 
	 * @RequestHeader(name = "Test-Header", required = false) String testHeader)
	 * { // @RequestHeader("testHeader") String testHeader // String
	 * testHeader=headers.getFirst("testHeader"); // String testHeader = "";
	 * String template = " [Header: Test-Header=%s]"; StringBuffer message = new
	 * StringBuffer("customer"); if (testHeader != null) {
	 * message.append(String.format(template, testHeader)); }
	 * 
	 * return message.toString(); }
	 */

	@RequestMapping("/test")
	@ResponseBody
	String apiTest() {
		log.info("apitest");
		return "customers/api/test";
	}

	@RequestMapping("/api/customers")
	@ResponseBody
	List<Customer> getCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(new Long(1), "John", "Smith", new Address("White Street","","",null)));
		customers.add(new Customer(new Long(2), "Anne", "Smith", new Address("White Street","","",null)));
		
		log.info("api/customers");
		return customers;
	}
}
