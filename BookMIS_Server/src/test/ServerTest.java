package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import control.ClientRequestForwardOperator;
import model.BookOperatorResult;
import tool.JsonTool;



public class ServerTest {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(2333);
			System.out.println("服务器启动");
			while(true) {
				Socket incoming = serverSocket.accept();
				System.out.println("客户端 " + incoming.getInetAddress() + "已连接");
				Runnable runnable = new ServerThreadHandler(incoming);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}


class ServerThreadHandler implements Runnable {
	private Socket incoming;
	
	public ServerThreadHandler(Socket incomingSocket) {
		incoming = incomingSocket;
	}
	
	@Override
	public void run() {
		try {
			InputStream inputStream = incoming.getInputStream();
			OutputStream outputStream = incoming.getOutputStream();
			
			Scanner in = new Scanner(inputStream, "UTF-8");
			
			PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
			
			String requestJsonData = in.nextLine();
			
			JSONObject jsonObject = JSON.parseObject(requestJsonData);
			String requestModule = jsonObject.getString("requestModule");
			switch (requestModule) {
				case "bookModule":
					BookOperatorResult bookOperatorResult = ClientRequestForwardOperator.getBookOperatorResult(JsonTool.bookRequestForwardAnalysis(requestJsonData));
					String serverResponseOperator = JSON.toJSONString(bookOperatorResult);
					out.println(serverResponseOperator);
					break;
				case "userModule":
					boolean userOperatorResult = ClientRequestForwardOperator.getUserOperatorResult(JsonTool.userRequestForwardAnalysis(requestJsonData));
					String serverResponseOperator1 = JSON.toJSONString(userOperatorResult);
					out.println(serverResponseOperator1);
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}