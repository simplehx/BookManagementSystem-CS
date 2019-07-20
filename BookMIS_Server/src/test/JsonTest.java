package test;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import control.BookOperator;
import model.Book;
import model.BookOperatorResult;
import tool.JsonTool;

public class JsonTest {
	public static void main(String[] args) {
		Book book = new Book();
		book.setName("A");
		
		JSONObject a = new JSONObject();
		a.put("requestMethod", "searchBook");
		a.put("requestBook", JSON.toJSONString(book));
		
		JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(a));
		String requestMethod = jsonObject.getString("requestMethod");
		System.out.println(requestMethod);
		String requestBook = jsonObject.getString("requestBook");
		System.out.println(requestBook);
		Book book1 = JSON.parseObject(requestBook, Book.class);
		System.out.println(book1.toString());
//		BookOperator bookOperator = new BookOperator();
//		BookOperatorResult result = bookOperator.searchBook(book);
//		List<Book> bookList = result.getResultBookList();
//
//		
//		String jsonString = JSON.toJSONString(bookList);
//		JSONArray jsonArray = JSON.parseArray(jsonString);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("request", "addBook");
//		jsonObject.put("jsonArray", jsonArray);
//		System.out.println(jsonObject);
		
//		List<Book> bookList2 = JSON.parseArray(jsonString, Book.class);
//		for(Book b : bookList2) {
//			System.out.println(b.toString());
//		}
	}
}
