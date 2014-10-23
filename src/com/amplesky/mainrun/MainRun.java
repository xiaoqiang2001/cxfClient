package com.amplesky.mainrun;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

import com.amplesky.ICxfService;
import com.amplesky.clienthandle.AuthInterceptor;
import com.amplesky.impl.CxfService;

public class MainRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CxfService  cxfservice =new CxfService();
		ICxfService  service=     cxfservice.getCxfServiceImplPort();
		Client client =ClientProxy.getClient(service);
		client.getOutInterceptors().add(new AuthInterceptor("zhangsan", "123456"));
		client.getInInterceptors().add(new LoggingInInterceptor());
		client.getOutInterceptors().add(new LoggingOutInterceptor());
		System.out.println(service.sayHello("zhangsan"));
		
	}

}
