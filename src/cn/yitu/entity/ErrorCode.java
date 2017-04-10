package cn.yitu.entity;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

//错误编码
public enum ErrorCode {
	s_200(200, "成功"),
	e_201(201, "JSON对象不合法");

	private int errorCode;
	private String message;

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	private ErrorCode(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public JSONObject toJson(JSON date) {
		JSONObject retJson = new JSONObject();
		retJson.put("errorCode", errorCode);
		retJson.put("message", message);
		if (date != null) {
			retJson.put("date", date);
		}
		return retJson;
	}

	public JSONObject toJson() {
		return this.toJson(null);
	}
}
