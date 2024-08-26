package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ApplicationDetails;

@RestController
@RequestMapping("/app")
public class ApplicationDetailsController {
	
	@GetMapping("/appList")
	public ResponseEntity<List<ApplicationDetails>> getAllApp(){
		List<ApplicationDetails> response= List.of(
				new ApplicationDetails("My Test App", 1234567890L, "example.okta.com", "SAML_2_0", "ACTIVE", "https://example.com/my-test-app"),
				new ApplicationDetails("HR Portal", 9876543210L, "example2.okta.com", "OIDC", "INACTIVE", "https://example2.com/hr-portal"),
				new ApplicationDetails("Salesforce Integration", 1111111111L, "example3.okta.com", "SAML_1_1", "ACTIVE", "https://example3.com/salesforce-integration"),
				new ApplicationDetails("Marketing Dashboard", 2222222222L, "example4.okta.com", "LDAP", "PENDING", "https://example4.com/marketing-dashboard"),
				new ApplicationDetails("IT Service Desk", 3333333333L, "example5.okta.com", "SAML_2_0", "ACTIVE", "https://example5.com/it-service-desk"),
				new ApplicationDetails("Finance Portal", 4444444444L, "example6.okta.com", "OIDC", "INACTIVE", "https://example6.com/finance-portal"),
				new ApplicationDetails("Customer Support", 5555555555L, "example7.okta.com", "SAML_1_1", "PENDING", "https://example7.com/customer-support"),
				new ApplicationDetails("HR System", 6666666666L, "example8.okta.com", "LDAP", "ACTIVE", "https://example8.com/hr-system"),
				new ApplicationDetails("Sales Portal", 7777777777L, "example9.okta.com", "SAML_2_0", "INACTIVE", "https://example9.com/sales-portal"),
				new ApplicationDetails("Marketing System", 8888888888L, "example10.okta.com", "OIDC", "PENDING", "https://example10.com/marketing-system")
				);
		return ResponseEntity.ok(response);
		
	}

}
