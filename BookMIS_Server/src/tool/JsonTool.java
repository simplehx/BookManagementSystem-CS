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
	
	/**
	 * 处理图书相关请求
	 * */
	public static ClientBookRequest bookRequestForwardAnalysis(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		String requestMethod = jsonObject.getString("requestMethod");
		String requestParameter = jsonObject.getString("requestParameter");
		Book requestBook = null;
		List<Book> requestBookList = null;
		Map<String, Book> requestBookMap = null;
		
		//根据请求的方法转发请求
		switch (requestMethod) {
			case "addBook":
				requestBook = JSONObject.parseObject(requestParameter, Book.class);
				break;
			case "deleteBook":
				requestBookList = JSONObject.parseArray(requestParameter, Book.class);
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
				requestBook = JSONObject.parseObject(requestParameter, Book.class);
				break;
		}
		
		ClientBookRequest clientRequest = new ClientBookRequest();
		clientRequest.setRequestMethod(requestMethod);
		clientRequest.setRequestBook(requestBook);
		clientRequest.setRequestBookList(requestBookList);
		clientRequest.setRequestBookMap(requestBookMap);
		return clientRequest;
	}
	
	/***
	 * 处理用户相关请求
	 */
	public static ClientUserRequest userRequestForwardAnalysis(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		String requestMethod = jsonObject.getString("requestMethod");
		String requestParameter = jsonObject.getString("requestParameter");
		User user = JSONObject.parseObject(requestParameter, User.class);
		ClientUserRequest clientUserRequest = new ClientUserRequest();
		clientUserRequest.setRequestMethod(requestMethod);
		clientUserRequest.setRequestUser(user);
		return clientUserRequest;
	}
}
