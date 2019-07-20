package model;

import java.util.List;
import java.util.Map;

public class ClientBookRequest {
	private String requestMethod;
	private Book requestBook;
	private List<Book> requestBookList;
	private Map<String, Book> requestBookMap;
	public ClientBookRequest() {
		super();
	}
	public ClientBookRequest(String requestMethod, Book requestBook, List<Book> requestBookList,
			Map<String, Book> requestBookMap, User user) {
		super();
		this.requestMethod = requestMethod;
		this.requestBook = requestBook;
		this.requestBookList = requestBookList;
		this.requestBookMap = requestBookMap;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public Book getRequestBook() {
		return requestBook;
	}
	public void setRequestBook(Book requestBook) {
		this.requestBook = requestBook;
	}
	public List<Book> getRequestBookList() {
		return requestBookList;
	}
	public void setRequestBookList(List<Book> requestBookList) {
		this.requestBookList = requestBookList;
	}
	public Map<String, Book> getRequestBookMap() {
		return requestBookMap;
	}
	public void setRequestBookMap(Map<String, Book> requestBookMap) {
		this.requestBookMap = requestBookMap;
	}
}
