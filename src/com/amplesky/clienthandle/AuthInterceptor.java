package com.amplesky.clienthandle;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private String userId;
	
	private String passwd;
	public AuthInterceptor(String userId , String passwd) {
		//super 表示显示调用父类有参数构造器。这样就不会再调用父类无参构造器
		// 准备发送消息前 ，调用
		super(Phase.PREPARE_SEND);
		this.userId = userId;
		this.passwd = passwd;
		// TODO Auto-generated constructor stub
	}

	
	
	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getPasswd() {
		return passwd;
	}



	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}



	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		List<Header>  headerlist = msg.getHeaders();
		
		Document doc =DOMUtils.createDocument(); 
	   Element e	=doc.createElement("authHeader");
	   Element userIdEle	=doc.createElement("userId");
	   userIdEle.setTextContent(userId);
	   Element passwordEle	=doc.createElement("password");
	   passwordEle.setTextContent(passwd);
	   e.appendChild(userIdEle);
	   e.appendChild(passwordEle);
	   headerlist.add(new Header(new QName("ee"), e));
		if(headerlist == null || headerlist.size() <1)
		{
			throw new Fault(new IllegalArgumentException("没有header信息"));
		}
		//假如要求第一个header携带了用户名和密码信息
		Header firstHeader = headerlist.get(0);
		Element element= (Element)firstHeader.getObject();
		NodeList  userIds =element.getElementsByTagName("userId");
		NodeList  userpasswds =element.getElementsByTagName("password");
		if(userIds.getLength() !=1)
		{
			throw new Fault(new IllegalArgumentException("用户名格式不对"));
		}
		if(userpasswds.getLength() !=1)
		{
			throw new Fault(new IllegalArgumentException("密码格式不对"));
		}
		
		String userId= userIds.item(0).getTextContent();
		String password= userpasswds.item(0).getTextContent();
		if(!"zhangsan".equals(userId) && !"123456".equals(password))
		{
			throw new Fault(new IllegalArgumentException("用户名,密码格式不对"));
		}
	}

}
