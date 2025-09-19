package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.response.Response;
import io.restassured.http.ContentType;


// it perform CRUD operation     implementation (means Create,Read,Update,Delete request to the user API)
public class UserEndPoint2 {
	
	// methos cretaed for getting URL from properties file
 static ResourceBundle	getURL(){
		ResourceBundle routes=ResourceBundle.getBundle("routes");// Load properties
		return routes;
	}
	
	
	
	
	public static Response createUser(User payload){
		
		String post_url=getURL().getString("post_url");
		Response res=given()
		     .contentType(ContentType.JSON)
		     .accept(ContentType.JSON)
		     .body(payload)
		.when()
		     .post(post_url);
		
		
		return res;
		
	}
	
public static Response readUser(String userName){
		
	String get_url=getURL().getString("get_url");
		Response res=given()
		     .pathParam("username", userName)
		     
		.when()
		     .get(get_url);
		
		
		return res;
		
	}

	
	public static Response updateUser( String userName ,User payload){
		
		String update_url=getURL().getString("update_url");
		Response res=given()
		     .contentType(ContentType.JSON)
		     .accept(ContentType.JSON)
		     .body(payload)
		     .pathParam("username", userName)
		.when()
		     .put(update_url);
		
		
		return res;
		
	}
	
public static Response deleteUser(String userName){
		
	String delete_url=getURL().getString("delete_url");
		Response res=given()
		     .pathParam("username", userName)
		     
		.when()
		     .delete(delete_url);
		
		
		return res;
		
	}



}
