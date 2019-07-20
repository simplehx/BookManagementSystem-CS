package tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import model.Book;
import model.ClientBookRequest;
import model.ClientUserRequest;
import model.User;

public class JsonTool {
	
	public static ClientBookRequest bookRequestForwardAnalysis(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		String requestMethod = jsonObject.getString("requestMethod");
		String requestParameter = jsonObject.getString("requestParameter");
		Book requestBook = null;
		List<Book> requestBookList = null;
		Map<String, Book> requestBookMap = null;
		
		switch (requestMethod) {
			case "addBook":
				requestBook = jsonObject.parseObject(requestParameter, Book.class);
				break;
			case "deleteBook":
				requestBookList = jsonObject.parseArray(requestParameter, Book.class);
				break;
			case "changeBook":
				Map<String, Object> map = JSONObject.toJavaObject(JSON.parseObject(requestParameter), Map.class);
				requestBookMap = new HashMap<String, Book>();
				for(String key : map.keySet()) {
					Book book = JSON.parseObject(map.get(key).toString(), Book.class);
					requestBookMap.put(key, book);
				}
				break;
			case "searchBook":
				requestBook = jsonObject.parseObject(requestParameter, Book.class);
				break;
		}
		
		ClientBookRequest clientRequest = new ClientBookRequest();
		clientRequest.setRequestMethod(requestMethod);
		clientRequest.setRequestBook(requestBook);
		clientRequest.setRequestBookList(requestBookList);
		clientRequest.setRequestBookMap(requestBookMap);
		return clientRequest;
	}
	
	public static ClientUserRequest userRequestForwardAnalysis(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		String requestMethod = jsonObject.getString("requestMethod");
		String requestParameter = jsonObject.getString("requestParameter");
		User user = jsonObject.parseObject(requestParameter, User.class);
		ClientUserRequest clientUserRequest = new ClientUserRequest();
		clientUserRequest.setRequestMethod(requestMethod);
		clientUserRequest.setRequestUser(user);
		return clientUserRequest;
	}
}
