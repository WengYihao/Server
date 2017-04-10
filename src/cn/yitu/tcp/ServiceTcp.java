package cn.yitu.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cn.yitu.entity.User;
import cn.yitu.tcp.customer.Client;

public class ServiceTcp {
	private int port;

	public ServiceTcp() {

	}

	public ServiceTcp(int port) {
		this.port = port;
	}

	private List<User> users = new ArrayList<User>();// 用户列表

	public void start() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket socket = serverSocket.accept();
				Client client = new Client(socket, users);
				Thread thread = new Thread(client);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("启动失败!!!");
			e.printStackTrace();
		}
	}
}
