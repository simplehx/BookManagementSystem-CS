package control;


import java.awt.Component;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import model.User;
import tool.SocketTool;

public class UserOperator {
	
	public boolean login(User user) throws IOException {
		boolean loginResult = false;
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("requestModule", "userModule");
		jsonMessage.put("requestMethod", "login");
		jsonMessage.put("requestParameter", JSON.toJSONString(user));
		String serverResponse = SocketTool.sendMessageToServer(jsonMessage);
		loginResult = Boolean.valueOf(serverResponse);
		return loginResult;
	}
}
