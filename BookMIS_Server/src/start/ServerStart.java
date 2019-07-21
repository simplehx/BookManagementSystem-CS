package start;

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

public class ServerStart {
	public static void main(String[] args) {
		new ServerStart();
	}
	
	public ServerStart() {
		ServerSocket serverSocket = null;
		try {
			//启动服务器
			serverSocket = new ServerSocket(2333);
			System.out.println("服务器启动");
			while(true) {
				Socket incoming = serverSocket.accept();
				System.out.println("客户端 " + incoming.getInetAddress() + "请求已连接");
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
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Scanner in = null;
		PrintWriter out = null;
		try {
			inputStream = incoming.getInputStream();
			outputStream = incoming.getOutputStream();
			
			in = new Scanner(inputStream, "UTF-8");
			
			out = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
			
			String requestJsonData = in.nextLine();
			
			JSONObject jsonObject = JSON.parseObject(requestJsonData);
			String requestModule = jsonObject.getString("requestModule");
			
			//将请求分发到不同的处理模块
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
		} finally {
			//释放连接资源
			try {
				if(out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("客户端 " + incoming.getInetAddress() + "连接已释放");
		}
	}
}