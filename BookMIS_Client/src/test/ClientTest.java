package test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import model.Book;
import model.BookOperatorResult;

public class ClientTest {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 2333);
			OutputStream out = socket.getOutputStream();
			Scanner in = new Scanner(socket.getInputStream(), "UTF-8");
			PrintWriter print = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
			Book book = new Book();
			book.setName("a");
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("requestMethod", "searchBook");
			jsonObject.put("requestBook", JSON.toJSONString(book));
			
			print.println(JSON.toJSONString(jsonObject));
			
			String serverResponse = in.nextLine();
			
			BookOperatorResult serverResponseJsonObject = JSON.parseObject(serverResponse, BookOperatorResult.class);
			System.out.println(serverResponseJsonObject.getStatus());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}