package cn.yitu.tcp.customer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;

import cn.yitu.entity.ErrorCode;
import cn.yitu.entity.User;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Client implements Runnable {
	private final String encoder = "UTF-8";

	private User user;
	private Socket socket;
	private List<User> users = null;

	public Client(Socket socket, List<User> users) {
		this.socket = socket;
		this.users = users;
	}

	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	private final String end = "end";// 结束标识

	@Override
	public void run() {
		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			User user = new User(socket, this);
			users.add(user);

			// 启动发送消息类
			Sender sender = new Sender(user, users);
			Thread thread = new Thread(sender);
			thread.start();

			byte[] bytes = new byte[1024];
			Integer length = null;
			String inString = null;
			while ((length = inputStream.read(bytes)) != -1) {
				inString = new String(bytes, 0, length, encoder);
				try {
					if (user.isOnLine() == false) {
						this.close();
						break;
					}
					if (end.equals(inString)) {
						this.close();
						break;
					}
					JSONObject reJson = JSONObject.fromObject(inString);
					Double lat = reJson.getDouble("lat");
					Double lng = reJson.getDouble("lng");
					user.setLat(lat);
					user.setLng(lng);
				} catch (JSONException e) {
					JSONObject jso = (ErrorCode.e_201).toJson();
					this.send(jso.toString());
				} catch (Exception e) {
					user.setOnLine(false);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			users.remove(user);
			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 发送数据
	public void send(String date) throws UnsupportedEncodingException, IOException {
		outputStream.write(date.getBytes(encoder));
		outputStream.flush();
	}

}
