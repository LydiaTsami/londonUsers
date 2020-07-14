package restApi.londonUsers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static restApi.londonUsers.Utils.*;

public class UserService {
	
    // 51 deg 30 min 26 sec N
    static double londonLat = 51 + (30 / 60.0) + (26 / 60.0 / 60.0);
    // 0 deg 7 min 39 sec W
    static double londonLon = 0 - (7 / 60.0) - (39 / 60.0 / 60.0);

	public JsonElement getAllUsers () throws UnirestException {
		// Host url
	    String host = "https://bpdts-test-app.herokuapp.com/";
	    //URI Params
	    String city = "London";
	    
    	HttpResponse<JsonNode> responseLondon;
    	HttpResponse<JsonNode> responseAllUsers;
    	
    	
			responseLondon = Unirest.get(host + "city/" + city + "/users")
				      .asJson();
			
		//If http status is different than 200 raise error
		if(responseLondon.getStatus() != 200) {
			throw new UnirestException("Could not retrieve data from " + host);
		}
		//Parse json response
  	    JsonParser jp = new JsonParser();
  	    JsonElement je = jp.parse(responseLondon.getBody().toString());
  	      
  	    responseAllUsers = Unirest.get(host + "users")
				      .asJson();
  	    //If http status is different than 200 raise error
		if(responseAllUsers.getStatus() != 200) {
			throw new UnirestException("Could not retrieve data from " + host);
		}
  	    
  	    
		JsonElement jeAllUsers = jp.parse(responseAllUsers.getBody().toString());
		if (jeAllUsers.isJsonArray()) {
			for(JsonElement e: jeAllUsers.getAsJsonArray()) {
				JsonObject o = e.getAsJsonObject();
				double lon = o.get("longitude").getAsFloat();
				double lat = o.get("latitude").getAsFloat();
				double distance = getDistance(lon, lat, londonLon, londonLat);
				if(distance < 50.00) {
					je.getAsJsonArray().add(e.getAsJsonObject());
				}
				//For testing purposes
		//  	e.getAsJsonObject().addProperty("Distance", distance);
			}
		}
		return je;
	}
}
