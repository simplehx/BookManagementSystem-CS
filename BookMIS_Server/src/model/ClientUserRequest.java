package model;

public class ClientUserRequest {
	private String requestMethod;
	private User requestUser;
	public ClientUserRequest() {
		super();
	}
	public ClientUserRequest(String requestMethod, User requestUser) {
		super();
		this.requestMethod = requestMethod;
		this.requestUser = requestUser;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public User getRequestUser() {
		return requestUser;
	}
	public void setRequestUser(User requestUser) {
		this.requestUser = requestUser;
	}
}
