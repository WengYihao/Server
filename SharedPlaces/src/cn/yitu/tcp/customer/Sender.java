package cn.yitu.tcp.customer;

import java.util.List;

import cn.yitu.entity.ErrorCode;
import cn.yitu.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Sender implements Runnable {
	private User user;
	private List<User> users = null;

	public Sender(User user, List<User> users) {
		this.user = user;
		this.users = users;
	}

	@Override
	public void run() {
		while (user.isOnLine()) {
			try {
				Thread.sleep(3 * 1000);// 3秒发1次
				user.getClient().send(ret().toString());// 发送
			} catch (Exception e) {
				user.setOnLine(false);// 设置离线
			}
		}
	}

	private JSONObject ret() {
		JSONObject jsonObject = null;
		JSONArray array = new JSONArray();
		for (User user : users) {
			if (user.getLat() != null && user.getLng() != null) {
				if (user.getUuid() != this.user.getUuid()) {
					JSONObject us = new JSONObject();
					us.put("uuid", user.getUuid());
					us.put("lat", user.getLat());
					us.put("lng", user.getLng());
					array.add(us);
				}
			}
		}
		jsonObject = ErrorCode.s_200.toJson(array);
		return jsonObject;
	}

}
