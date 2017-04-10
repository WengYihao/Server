package cn.yitu.test;

import cn.yitu.tcp.ServiceTcp;

public class Test {

	public static void main(String[] args) {
		ServiceTcp serviceTcp = new ServiceTcp(8080);
		serviceTcp.start();
	}
} 