package restapistepdefination;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restapi.Authenticationbody;
import restapi.Postrequest;
import restapi.Randomno;

public class APIfile {
	
	Object bearertoken;
	int toolid;
	
	@Given("user has base uri")
	public void user_has_base_uri() {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI = "https://simple-tool-rental-api.glitch.me";
		}

	@Given("user generates bearer token")
	public void user_generates_bearer_token() {
	    // Write code here that turns the phrase above into concrete actions
		
		String rdmno = Randomno.generateRandomNo();
		String bearerbody = Authenticationbody.bearerToken(rdmno);
		Response tokenres = given().header("Content-Type", "application/json").body(bearerbody).log().all().when()
				.post("/api-clients").then().log().all().extract().response();

		String brtoken = tokenres.asString();

		JsonPath obj = (JsonPath) Randomno.getJson(brtoken);
		bearertoken = obj.get("accessToken");

		System.out.println("bearer token = " + bearertoken);
	}

	@Given("user get valid toolid")
	public void user_get_valid_toolid() {
	    // Write code here that turns the phrase above into concrete actions
		Response tools = given().log().all().when().get("/tools")
				.then().log().all().extract().response();

		String tool = tools.asString();

		JsonPath ordertools = (JsonPath) Randomno.getJson(tool);
		toolid = ordertools.get("id[0]");
		System.out.println("valid tool id = " + toolid);	}

	@Then("user create order for valid tool id")
	public void user_create_order_for_valid_tool_id() {
	    // Write code here that turns the phrase above into concrete actions
		
		String createorderbody = Postrequest.creatorder(toolid);
		Response createorder = given().header("Content-Type", "application/json")
				.header("Authorization",bearertoken).body(createorderbody).log().all()
				.when().post("/orders").then().log().all().extract().response();
		
		String cror = createorder.asString();
		
		//jp = new JsonPath(cror);
		
		JsonPath obj1 = (JsonPath) Randomno.getJson(cror);
		
		Object ordercreated = obj1.get("created");
		Object orderid = obj1.get("orderId");
		
		System.out.println("order created = "+ordercreated);
		System.out.println("order id = "+orderid);
	}



}
