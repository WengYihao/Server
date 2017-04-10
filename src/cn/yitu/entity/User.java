package cn.yitu.entity;

import java.io.Serializable;
import java.net.Socket;
import java.util.UUID;

import cn.yitu.tcp.customer.Client;

public class User implements Serializable {
	private static final long serialVersionUID = 7359389165310027183L;

	private String uuid;
	private Double lng;
	private Double lat;
	
	private Socket socket = null;
	private Client client = null;
	private boolean onLine = true;// «∑Ò‘⁄œﬂ

	public User(Socket socket,Client client) {
		this.socket = socket;
		this.client = client;
		uuid = UUID.randomUUID().toString();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}
}
