package tool;

import java.awt.Component;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class SocketTool {
	public static String sendMessageToServer(JSONObject jsonMessage) throws IOException {
		Socket socket = null;
		OutputStream out = null;
		Scanner in = null;
		String serverResponse = null;
		String message = JSON.toJSONString(jsonMessage);
		socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", 2333), 8000);
		out = socket.getOutputStream();
		in = new Scanner(socket.getInputStream(), "UTF-8");
		PrintWriter print = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
		print.println(message);
		serverResponse = in.nextLine();
		closeSocketResource(socket, out, in);
		return serverResponse;
	}
	
	public static void closeSocketResource(Socket socket, OutputStream out, Scanner in) {
		try {
			if(in != null) {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(out != null) {
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
