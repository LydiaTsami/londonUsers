package restApi.londonUsers;

/*
 * Small helper class used to convert error messages and exceptions to JSON
 */
public class ResponseError {
	
	private String message;
	
	public ResponseError(String message, String... args) {
		this.message = String.format(message, args);
	}

	public ResponseError(Exception e) {
		this.message = e.getMessage();
	}
	
	public String getMessage() {
		return this.message;
	}
}