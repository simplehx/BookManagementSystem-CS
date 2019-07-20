package control;

import java.util.List;
import java.util.Map;

import model.Book;
import model.BookOperatorResult;
import model.ClientBookRequest;
import model.ClientUserRequest;
import model.User;

public class ClientRequestForwardOperator {
	
	public static BookOperatorResult getBookOperatorResult (ClientBookRequest clientRequest) {
		String requestMethod = clientRequest.getRequestMethod();
		Book requestBook = clientRequest.getRequestBook();
		BookOperator bookOperator = new BookOperator();
		BookOperatorResult bookOperatorResult = null;
		switch (requestMethod) {
			case "addBook":
				bookOperatorResult = bookOperator.addBook(requestBook);
				break;
			case "deleteBook":
				List<Book> requestBookList = clientRequest.getRequestBookList();
				bookOperatorResult = bookOperator.deleteBook(requestBookList);
				break;
			case "searchBook":
				bookOperatorResult = bookOperator.searchBook(requestBook);
				break;
			case "changeBook":
				Map<String, Book> requestBookMap = clientRequest.getRequestBookMap();
				bookOperatorResult = bookOperator.changeBook(requestBookMap);
				break;
		}
		return bookOperatorResult;
	}

	public static boolean getUserOperatorResult (ClientUserRequest clientRequest) {
		String requestMethod = clientRequest.getRequestMethod();
		User user = clientRequest.getRequestUser();
		UserOperator userOperator = new UserOperator();
		boolean loginResult = false;
		//写成switch case方便后期拓展
		switch (requestMethod) {
			case "login":
				loginResult = userOperator.login(user);
				break;
		}
		return loginResult;
	}
}
