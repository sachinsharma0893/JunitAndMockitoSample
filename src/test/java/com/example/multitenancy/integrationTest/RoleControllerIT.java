package com.example.multitenancy.integrationTest;

import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.multitenancy.customeAnnotations.IntegrationTest;
import com.example.multitenancy.domain.Role;
import com.example.multitenancy.enums.RoleEnum;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@IntegrationTest
public class RoleControllerIT {

	@Before
	public void setupTest() {
		System.out.print("***************** " + DEFAULT_PROFILES_PROPERTY_NAME);
	}

	@Autowired
	public TestRestTemplate restTemplate;

	@Test
	public void validateGetAllRoles() throws JSONException {
		String response = this.restTemplate.getForObject("/api/v1/role", String.class);
		JSONAssert.assertEquals("[{},{},{},{}]", response, false);
	}

	@Test
	public void validateGetRolesById() throws JSONException {
		String response = this.restTemplate.getForObject("/api/v1/role/1", String.class);
		JSONAssert.assertEquals("{}", response, false);
	}

	@Test
	public void validateSaveRole() throws Exception {
		Role role = new Role(null, RoleEnum.TENANT1, "Tenant1");
		HttpEntity<Role> request = new HttpEntity<>(role);
		ResponseEntity<Role> response = this.restTemplate.postForEntity("/api/v1/role", request, Role.class);
		Assert.assertEquals(RoleEnum.TENANT1, response.getBody().getType());

	}

}
