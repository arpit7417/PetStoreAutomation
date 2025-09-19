package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoint;
import api.endpoints.UserEndPoint2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker=new Faker();
		userPayload=new User();
		
		String username = faker.name().username().replaceAll("[^a-zA-Z0-9]", ""); // ✅ Clean username
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(username);
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		System.out.println("Generated username: " + username); // ✅ Log it
		
		// logs
		logger=LogManager.getLogger(this.getClass());
	}
	@Test(priority=1)
	public void  testPostUser() {
		
		logger.info("********************Creating user********************");
		
		Response response=UserEndPoint2.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********************User is Created********************");
	}
	
	@Test(priority=2)
	public void testGetUser() {
		
		logger.info("********************Reading user info*****************");
		 try {
		        Thread.sleep(3000); // Wait 1 second
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		
		Response response=UserEndPoint2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("********************User info is displayed********************");
	}
	@Test(priority=3)
	public void testUpdateUser() {

	    logger.info("********************Updating User********************");

	    try {
	        Thread.sleep(2000); // 2 seconds wait
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    // Update user payload with new data
	    userPayload.setFirstName(faker.name().firstName());
	    userPayload.setLastName(faker.name().lastName());
	    userPayload.setEmail(faker.internet().emailAddress());

	    // Send PUT request to update user
	    Response response = UserEndPoint2.updateUser(this.userPayload.getUsername(), userPayload);
	    response.then().log().all();
	    Assert.assertEquals(response.getStatusCode(), 200);

	    logger.info("********************User is Updated********************");

	    // Send GET request to verify the updated data
	    Response responseAfterUpdate = UserEndPoint2.readUser(this.userPayload.getUsername());
	    responseAfterUpdate.then().log().all();
	    Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);

	    // Additional field-level assertions to ensure data was updated correctly
	    Assert.assertEquals(responseAfterUpdate.jsonPath().getString("firstName"), userPayload.getFirstName());
	    Assert.assertEquals(responseAfterUpdate.jsonPath().getString("lastName"), userPayload.getLastName());
	    Assert.assertEquals(responseAfterUpdate.jsonPath().getString("email"), userPayload.getEmail());
	}

	@Test(priority=4)
	public void testdeleteByName() {
		
		try {
		    Thread.sleep(3000); // 2 seconds wait
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		logger.info("********************Deleting User********************");
	Response response=	UserEndPoint2.deleteUser(this.userPayload.getUsername());
	Assert.assertEquals(response.getStatusCode(), 200);
	
	logger.info("********************User is Deleting********************");
		
	}

}
