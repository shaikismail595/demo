package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.ApplicationDetails;

@SpringBootTest
public class ApplicationDetailsControllerTest {
		@Autowired
	    private ApplicationDetailsController applicationController;

	    @Test
	    public void testGetAllApp_ReturnsExpectedApplications() throws Exception {
	          List<ApplicationDetails> expectedApplications = List.of(
	        		new ApplicationDetails("My Test App", 1234567890L, "example.okta.com", "SAML_2_0", "ACTIVE", "https://example.com/my-test-app"),
					new ApplicationDetails("HR Portal", 9876543210L, "example2.okta.com", "OIDC", "INACTIVE", "https://example2.com/hr-portal"),
					new ApplicationDetails("Salesforce Integration", 1111111111L, "example3.okta.com", "SAML_1_1", "ACTIVE", "https://example3.com/salesforce-integration"),
					new ApplicationDetails("Marketing Dashboard", 2222222222L, "example4.okta.com", "LDAP", "PENDING", "https://example4.com/marketing-dashboard"),
					new ApplicationDetails("IT Service Desk", 3333333333L, "example5.okta.com", "SAML_2_0", "ACTIVE", "https://example5.com/it-service-desk"),
					new ApplicationDetails("Finance Portal", 4444444444L, "example6.okta.com", "OIDC", "INACTIVE", "https://example6.com/finance-portal"),
					new ApplicationDetails("Customer Support", 5555555555L, "example7.okta.com", "SAML_1_1", "PENDING", "https://example7.com/customer-support"),
					new ApplicationDetails("HR System", 6666666666L, "example8.okta.com", "LDAP", "ACTIVE", "https://example8.com/hr-system"),
					new ApplicationDetails("Sales Portal", 7777777777L, "example9.okta.com", "SAML_2_0", "INACTIVE", "https://example9.com/sales-portal"),
					new ApplicationDetails("Marketing System", 8888888888L, "example10.okta.com", "OIDC", "PENDING", "https://example10.com/marketing-system") );
	        ResponseEntity<List<ApplicationDetails>> response = applicationController.getAllApp();
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(expectedApplications, response.getBody());
	    }
	    
}

