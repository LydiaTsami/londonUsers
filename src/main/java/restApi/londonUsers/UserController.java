package restApi.londonUsers;
import static spark.Spark.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import static restApi.londonUsers.Utils.*;

public class UserController {

	public UserController(final UserService userService) {
		//All endpoints are listed here. This API only has 1.
		
		//Returns people who are listed as either living in London, or whose current coordinates are within 50 miles of London
		get("/users", (req, res) -> userService.getAllUsers(), json());

		//set all responses to Content-Type application/json
		after((req, res) -> {
			res.type("application/json");
		});
		
		/*
		 * An ExceptionHandler will be called if an Exception is thrown while processing a route
		 */

		//Exception thrown when call is missing a required parameter
		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(400);
			res.body(toJson(new ResponseError(e)));
		});
		
		//Exception thrown when an HTTP/S call fails.
		exception(UnirestException.class, (e, req, res) -> {
			res.status(500);
			res.body(toJson(new ResponseError(e)));
		});
		
		
	}
}
