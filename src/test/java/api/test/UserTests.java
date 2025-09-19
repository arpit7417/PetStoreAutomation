package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoint;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
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
		
		Response response=UserEndPoint.createUser(userPayload);
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
		
		Response response=UserEndPoint.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("********************User info is displayed********************");
	}
	
	@Test(priority=3)
	public void testUpdateUser() {
		 try {
		        Thread.sleep(3000); // Wait 1 second
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		logger.info("********************Updating User********************");
		// update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		
		
		Response response=UserEndPoint.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********************User update********************");
		
		//checking data after updation 
		
		Response responseAfterUpdate=UserEndPoint.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
		
	}
	
	@Test(priority=4)
	public void testdeleteByName() {
		
		logger.info("********************Deleting User********************");
	Response response=	UserEndPoint.deleteUser(this.userPayload.getUsername());
	Assert.assertEquals(response.getStatusCode(), 200);
	
	logger.info("********************User is Deleting********************");
		
	}

}
