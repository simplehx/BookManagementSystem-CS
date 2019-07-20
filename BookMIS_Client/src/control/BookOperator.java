package control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import model.Book;
import model.BookOperatorResult;
import tool.SocketTool;

public class BookOperator {
	/**
	 * 添加图书
	 * @throws IOException 
	 * */
	public BookOperatorResult addBook(Book addBook) throws IOException {
		BookOperatorResult bookOperatorResult = null;
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("requestModule", "bookModule");
		jsonMessage.put("requestMethod", "addBook");
		jsonMessage.put("requestParameter", JSON.toJSONString(addBook));
		String serverResponse = SocketTool.sendMessageToServer(jsonMessage);
		bookOperatorResult = JSON.parseObject(serverResponse, BookOperatorResult.class);
		return bookOperatorResult;
	}
	
	/**
	 * 修改图书
	 * @throws IOException 
	 * */
	public BookOperatorResult changeBook(Map<String, Book> changeBookMap) throws IOException {
		BookOperatorResult bookOperatorResult = null;
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("requestModule", "bookModule");
		jsonMessage.put("requestMethod", "changeBook");
		System.out.println(JSON.toJSONString(changeBookMap));
		jsonMessage.put("requestParameter", JSON.toJSONString(changeBookMap));
		String serverResponse = SocketTool.sendMessageToServer(jsonMessage);
		bookOperatorResult = JSON.parseObject(serverResponse, BookOperatorResult.class);
		return bookOperatorResult;
	}
	
	/**
	 * 删除图书
	 * @throws IOException 
	 * */
	public BookOperatorResult deleteBook(List<Book> deleteBookList) throws IOException {
		BookOperatorResult bookOperatorResult = null;
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("requestModule", "bookModule");
		jsonMessage.put("requestMethod", "deleteBook");
		jsonMessage.put("requestParameter", JSON.toJSONString(deleteBookList));
		String serverResponse = SocketTool.sendMessageToServer(jsonMessage);
		bookOperatorResult = JSON.parseObject(serverResponse, BookOperatorResult.class);
		return bookOperatorResult;
	}
	
	/**
	 * 查找图书
	 * @throws IOException 
	 * */
	public BookOperatorResult searchBook(Book searchBook) throws IOException {
		BookOperatorResult bookOperatorResult = null;
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("requestModule", "bookModule");
		jsonMessage.put("requestMethod", "searchBook");
		jsonMessage.put("requestParameter", JSON.toJSONString(searchBook));
		String serverResponse = SocketTool.sendMessageToServer(jsonMessage);
		bookOperatorResult = JSON.parseObject(serverResponse, BookOperatorResult.class);
		return bookOperatorResult;
	}
}